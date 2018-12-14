package com.xhg.studyelement;

import com.github.pagehelper.PageInfo;
import com.xhg.studyelement.dao.User1Repository;
import com.xhg.studyelement.pojo.User1;
import com.xhg.studyelement.serivce.User1Service;
import com.xhg.studyelement.shiro.domain.Cars;
import com.xhg.studyelement.shiro.service.CarsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudyelementApplication.class)
public class EhcacheTest {

    @Autowired
    User1Repository userRepository;

    @Autowired
    private User1Service user1Service;

    @Autowired
    private CarsService carsService;

    @Test
    public void test() {

        System.out.println(user1Service.findAllByUsername(0, 20, "").getTotalElements());

        user1Service.saveUser(new User1("水天2", "123swwq", new Date()));

        System.out.println(user1Service.findAllByUsername(0, 20, "").getTotalElements());
    }


    /**
     * 无条件查询
     */
    @Test
    public void testPaging() {

        PageInfo<Cars> carsPageInfo = carsService.selectAllPaging(3, 2);
        List<Cars> cars = carsPageInfo.getList();
        for (Cars car : cars) {
            System.out.println(car);

        }

        System.out.println("当前页：" + carsPageInfo.getPageNum());
        System.out.println("每页记录数：" + carsPageInfo.getPageSize());
        System.out.println("当前页记录数：" + carsPageInfo.getSize());
        System.out.println("总记录数：" + carsPageInfo.getTotal());
        System.out.println("总页数：" + carsPageInfo.getPages());
        System.out.println("当前页所有数据：" + cars.toString());

    }

    /**
     * 有条件查询
     */
    @Test
    public void testDriverPaging() {

        PageInfo<Cars> carsPageInfo = carsService.selectByDriverPaging(1, 2, "司机1");
        List<Cars> cars = carsPageInfo.getList();
        for (Cars car : cars) {
            System.out.println(car.getCarnumber() + " = " + car.getDriver());
        }

        System.out.println("当前页：" + carsPageInfo.getPageNum());
        System.out.println("每页记录数：" + carsPageInfo.getPageSize());
        System.out.println("当前页记录数：" + carsPageInfo.getSize());
        System.out.println("总记录数：" + carsPageInfo.getTotal());
        System.out.println("总页数：" + carsPageInfo.getPages());
        System.out.println("当前页所有数据：" + cars.toString());

    }

}
