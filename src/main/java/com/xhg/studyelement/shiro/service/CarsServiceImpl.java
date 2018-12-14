package com.xhg.studyelement.shiro.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhg.studyelement.shiro.dao.CarsDAO;
import com.xhg.studyelement.shiro.domain.Cars;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 16033
 */
@Service
public class CarsServiceImpl implements CarsService {

    @Resource
    private CarsDAO carsDAO;

    /**
     * 无条件查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Cars> selectAllPaging(Integer pageNum,Integer pageSize) {

        PageHelper.startPage(pageNum,pageSize);

        List<Cars> cars = carsDAO.selectAll();

        return new PageInfo<>(cars);
    }

    /**
     * 有条件查询
     * @param pageNum
     * @param pageSize
     * @param driver
     * @return
     */
    @Override
    public PageInfo<Cars> selectByDriverPaging(Integer pageNum,Integer pageSize, String driver) {

        PageHelper.startPage(pageNum,pageSize);

        Map<String,Object> params = new HashMap<>(8);
        params.put("driver",driver);
        List<Cars> cars = carsDAO.selectByDriver(params);

        return new PageInfo<>(cars);
    }
}
