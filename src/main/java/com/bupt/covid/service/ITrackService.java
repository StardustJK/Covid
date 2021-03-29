package com.bupt.covid.service;

import com.bupt.covid.response.ResponseResult;

public interface ITrackService {
    ResponseResult getTracksByUser(String userId);
}
