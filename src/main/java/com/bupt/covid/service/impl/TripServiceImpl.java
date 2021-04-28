package com.bupt.covid.service.impl;

import com.bupt.covid.dao.PatientTripDao;
import com.bupt.covid.pojo.PatientTrip;
import com.bupt.covid.response.ResponseResult;
import com.bupt.covid.service.ITripService;
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

    @Override
    public ResponseResult searchTrip(String area, int type, String no, Date start, Date end) {
        List<PatientTrip> all=patientTripDao.findAll(new Specification<PatientTrip>() {
            @Override
            public Predicate toPredicate(Root<PatientTrip> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList=new ArrayList<>();
                if(!TextUtils.isEmpty(area)){
                    Predicate start= criteriaBuilder.like(root.get("t_start").as(String.class),"%"+area+"%");
                    Predicate end= criteriaBuilder.like(root.get("t_end").as(String.class),"%"+area+"%");
                    Predicate areaPre=criteriaBuilder.or(start,end);
                    predicateList.add(areaPre);
                }
                if(type!=0){
                    Predicate typePre=criteriaBuilder.equal(root.get("t_type").as(Integer.class),type);
                    predicateList.add(typePre);
                }
                if(!TextUtils.isEmpty(no)){
                    Predicate noPre=criteriaBuilder.equal(root.get("t_no").as(String.class),no);
                    predicateList.add(noPre);
                }
                Predicate date=criteriaBuilder.between(root.get("t_date").as(Date.class),start,end);
                predicateList.add(date);

                Predicate[] preArray=new Predicate[predicateList.size()];
                predicateList.toArray(preArray);
                criteriaQuery.where(criteriaBuilder.and(preArray));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("t_date")));
                return criteriaQuery.getRestriction();
            }
        });
        if(all.size()==0){
            return ResponseResult.FAILED("查询结果为空");
        }
        return ResponseResult.SUCCESS("查询成功").setData(all);
    }
}
