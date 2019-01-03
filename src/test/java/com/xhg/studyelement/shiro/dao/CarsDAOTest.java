package com.xhg.studyelement.shiro.dao;

import com.xhg.studyelement.StudyelementApplication;
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
    UserRoleDAO userRoleDAO;
    @Test
    public void insertSelective() {
        UserRole cars = new UserRole();
        cars.setUserId(2L);
        cars.setRoleId(2L);
        int cars1 = userRoleDAO.insertSelective(cars);

        System.out.println(cars1);
    }
}