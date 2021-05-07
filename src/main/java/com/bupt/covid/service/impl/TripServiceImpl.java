package com.bupt.covid.service.impl;

import com.bupt.covid.dao.PatientTripDao;
import com.bupt.covid.dao.UserDao;
import com.bupt.covid.dao.UserTripDao;
import com.bupt.covid.pojo.PatientTrip;
import com.bupt.covid.pojo.User;
import com.bupt.covid.pojo.UserTrip;
import com.bupt.covid.response.ResponseResult;
import com.bupt.covid.service.ITripService;
import com.bupt.covid.utils.Constants;
import com.bupt.covid.utils.RedisUtil;
import com.bupt.covid.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TripServiceImpl implements ITripService {
    @Autowired
    PatientTripDao patientTripDao;
    @Autowired
    UserTripDao userTripDao;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserDao userDao;

    @Override
    public ResponseResult searchTrip(String area, int type, String no, Date start, Date end) {
        List<PatientTrip> all = patientTripDao.findAll(new Specification<PatientTrip>() {
            @Override
            public Predicate toPredicate(Root<PatientTrip> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if (!TextUtils.isEmpty(area)) {
                    Predicate start = criteriaBuilder.like(root.get("t_pos_start").as(String.class), "%" + area + "%");
                    Predicate end = criteriaBuilder.like(root.get("t_pos_end").as(String.class), "%" + area + "%");
                    Predicate areaPre = criteriaBuilder.or(start, end);
                    predicateList.add(areaPre);
                }
                if (type != 0) {
                    Predicate typePre = criteriaBuilder.equal(root.get("t_type").as(Integer.class), type);
                    predicateList.add(typePre);
                }
                if (!TextUtils.isEmpty(no)) {
                    Predicate noPre = criteriaBuilder.equal(root.get("t_no").as(String.class), no);
                    predicateList.add(noPre);
                }
                Predicate date = criteriaBuilder.between(root.get("t_date").as(Date.class), start, end);
                predicateList.add(date);

                Predicate[] preArray = new Predicate[predicateList.size()];
                predicateList.toArray(preArray);
                criteriaQuery.where(criteriaBuilder.and(preArray));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("t_date")));
                return criteriaQuery.getRestriction();
            }
        });
        if (all.size() == 0) {
            return ResponseResult.FAILED("查询结果为空");
        }
        return ResponseResult.SUCCESS("查询成功").setData(all);
    }

    @Override
    public ResponseResult addTrip(UserTrip userTrip) {
        if (userTrip.getDate().equals("")) {
            return ResponseResult.FAILED("请填写日期");
        }
        if (userTrip.getNo().equals("")) {
            return ResponseResult.FAILED("请填写车次/航班");
        }
        userTripDao.save(userTrip);
        return ResponseResult.SUCCESS("添加成功");


    }

    @Override
    public ResponseResult getTripByUser(int userId) {
        List<UserTrip> allByUserId = userTripDao.findAllByUserIdOrderByDateDesc(userId);
        if (allByUserId.size() == 0) {
            return ResponseResult.FAILED("无出行记录");
        }
        return ResponseResult.SUCCESS("获取成功").setData(allByUserId);
    }


    @Override
    public ResponseResult tripRisk(List<UserTrip> userTrips) {
        List<PatientTrip> all = new ArrayList<>();
        for (int i = 0; i < userTrips.size(); i++) {
            UserTrip userTrip = userTrips.get(i);
            List<PatientTrip> patientTripList = patientTripDao.findAll(new Specification<PatientTrip>() {
                @Override
                public Predicate toPredicate(Root<PatientTrip> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicateList = new ArrayList<>();
                    if (!TextUtils.isEmpty(userTrip.getPos_start())) {
                        Predicate start = criteriaBuilder.like(root.get("t_pos_start").as(String.class), "%" + userTrip.getPos_start() + "%");
                        predicateList.add(start);
                    }
                    if (!TextUtils.isEmpty(userTrip.getPos_end())) {
                        Predicate end = criteriaBuilder.like(root.get("t_pos_end").as(String.class), "%" + userTrip.getPos_end() + "%");
                        predicateList.add(end);
                    }
                    Predicate date = criteriaBuilder.equal(root.get("t_date").as(Date.class), userTrip.getDate());
                    predicateList.add(date);
                    Predicate type = criteriaBuilder.equal(root.get("t_type").as(Integer.class), userTrip.getType());
                    predicateList.add(type);
                    Predicate noPre = criteriaBuilder.equal(root.get("t_no").as(String.class), userTrip.getNo());
                    predicateList.add(noPre);
                    Predicate[] preArray = new Predicate[predicateList.size()];
                    predicateList.toArray(preArray);

                    return criteriaBuilder.and(preArray);

                }
            });

            if (patientTripList.size() > 0) {
                all.removeAll(patientTripList);
                all.addAll(patientTripList);
                //修改UserTrip的风险
                UserTrip oneById = userTripDao.findOneById(userTrip.getId());
                oneById.setRisk(true);
                userTripDao.save(oneById);
            }
        }

        if (all.size() == 0) {
            return ResponseResult.FAILED("无风险");
        }
        return ResponseResult.SUCCESS("以下行程存在风险").setData(all);
    }

    @Override
    public ResponseResult deleteTrip(int id) {
        UserTrip oneById = userTripDao.findOneById(id);
        if (oneById == null) {
            return ResponseResult.FAILED("该记录不存在");
        }
        userTripDao.deleteById(id);
        return ResponseResult.SUCCESS("删除成功");

    }

    @Override
    public ResponseResult riskNotify(int userId) {
        //success是要通知
        List<UserTrip> allByUserId = userTripDao.findAllByUserIdOrderByDateDesc(userId);
        if (allByUserId.size() == 0) {
            return ResponseResult.FAILED("无出行记录");
        }
        //查找用户设置
        User oneById = userDao.findOneById(userId);
        if(!oneById.isShowTripRisk()){
            return ResponseResult.FAILED("用户设置不提示风险");
        }
        for (int i = 0; i < allByUserId.size(); i++) {
            UserTrip userTrip = allByUserId.get(i);
            if (userTrip.isRisk()) {
                Object notified =  redisUtil.get(Constants.User.KEY_RISK_NOTIFY + userId);

                if (notified==null) {
                    //有效期24小时
                    redisUtil.set(Constants.User.KEY_RISK_NOTIFY + userId, "true", 60 * 60 * 24);
                    return ResponseResult.SUCCESS("用户有行程风险");
                } else {
                    return ResponseResult.FAILED("24小时内已通知过");
                }
            }
        }
        return ResponseResult.FAILED("用户无行程风险");


    }

}
