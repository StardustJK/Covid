package com.bupt.covid.service;

import com.bupt.covid.response.ResponseResult;

public interface IStatusService {
    ResponseResult getStatusByUser(int userId);
}
