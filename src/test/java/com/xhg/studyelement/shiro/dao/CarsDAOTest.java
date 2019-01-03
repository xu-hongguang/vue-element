package com.xhg.studyelement.shiro.dao;

import com.xhg.studyelement.StudyelementApplication;
import com.xhg.studyelement.shiro.domain.Cars;
import com.xhg.studyelement.shiro.domain.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
}