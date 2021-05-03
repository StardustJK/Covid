package com.bupt.covid.service.impl;

import com.bupt.covid.dao.StatusDao;
import com.bupt.covid.pojo.Status;
import com.bupt.covid.response.ResponseResult;
import com.bupt.covid.service.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class StatusServiceImpl implements IStatusService {
    @Autowired
    StatusDao statusDao;

    @Override
    public ResponseResult getStatusByUser(int userId) {
        List<Status> statusesFromDb = statusDao.getStatusesByUserId(userId);
        if (statusesFromDb == null) {
            return ResponseResult.FAILED("该用户无状态记录");
        } else return ResponseResult.SUCCESS("成功获取该用户记录").setData(statusesFromDb);

    }

    public void insertOneStatus(Status status){
        statusDao.saveAndFlush(status);
    }
}
