package com.xhg.studyelement.shiro.service;

import com.github.pagehelper.PageInfo;
import com.xhg.studyelement.shiro.domain.Cars;

public interface CarsService {
    PageInfo<Cars> selectAllPaging(Integer pageNum,Integer pageSize);


    PageInfo<Cars> selectByDriverPaging(Integer pageNum,Integer pageSize, String driver);
}
