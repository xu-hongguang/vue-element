package com.xhg.studyelement.shiro.dao;

import com.xhg.studyelement.StudyelementApplication;
import com.xhg.studyelement.shiro.domain.Cars;
import com.xhg.studyelement.shiro.domain.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudyelementApplication.class)
public class CarsDAOTest {

    @Autowired
    CarsDAO carsDAO;

    @Test
    public void insertSelective() {
        Cars cars = new Cars();
//        cars.setId(8);
        cars.setDriver("萨达6");
        cars.setCarnumber("苏H3456");
        carsDAO.insertSelective(cars);

        //此时主键值已经赋给对象cars
        System.out.println(cars);
    }

    @Test
    public void insertBatich() {
        Cars car1 = new Cars();
//        cars.setId(8);
        car1.setDriver("嘿嘿7");
        car1.setCarnumber("苏H3456");

        Cars car2 = new Cars();
        car2.setDriver("嘿嘿8");
        car2.setCarnumber("苏H3456");

        Cars car3 = new Cars();
        car3.setDriver("嘿嘿9");
        car3.setCarnumber("苏H3456");

        List<Cars> cars = Arrays.asList(car1,car2,car3);

        HashMap<String,Object> map = new HashMap<>(16);
        map.put("cars",cars);

        carsDAO.batchInsert(cars);

        //此时主键值已经赋给对象cars
        System.out.println(map);
    }
}