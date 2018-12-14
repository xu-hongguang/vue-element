package com.xhg.studyelement.shiro.dao;

import com.xhg.studyelement.shiro.domain.Cars;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CarsDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(Cars record);

    int insertSelective(Cars record);

    Cars selectByPrimaryKey(Integer id);

    /**
     * 无条件
     * @return
     */
    List<Cars> selectAll();

    /**
     * 有条件
     * @return
     */
    List<Cars> selectByDriver(Map<String,Object> params);

    int updateByPrimaryKeySelective(Cars record);

    int updateByPrimaryKey(Cars record);
}