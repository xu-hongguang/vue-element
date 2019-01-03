package com.xhg.studyelement;

import com.xhg.studyelement.common.utils.PageUtils;
import com.xhg.studyelement.dao.User1Repository;
import com.xhg.studyelement.pojo.User1;
import com.xhg.studyelement.serivce.User1Service;
import com.xhg.studyelement.shiro.dao.IUserDAO;
import com.xhg.studyelement.shiro.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudyelementApplication.class)
public class StudyelementApplicationTests {

	@Autowired
	User1Repository userRepository;

	@Autowired
	private User1Service user1Service;

	@Test
	public void contextLoads() {
		List<User1> users = userRepository.findAll();
		System.out.println(users);
		Assert.assertEquals(2,users.size());

		PageUtils<User1> userPageUtils = new PageUtils<>(users,(int)userRepository.count(),1,1);
		System.out.println(userPageUtils.getList());
		System.out.println(userPageUtils.getTotalPage());
	}

	@Test
	public void test(){
		User1 user = userRepository.findById(2).get();

		System.out.println(user.toString());

        Page<User1> userPage = user1Service.findAllByUsername(1, 1, "");

        System.out.println(userPage.getTotalElements());

    }

    @Test
    public void  test2(){
		user1Service.deleteUser(5);
	}

	@Autowired
	private IUserDAO userDAO;

	@Test
	public void getUserByUsername() {
		System.out.println(userDAO.getUserByUsername("zhangsan"));

		User user1 = new User();
		user1.setUsername("sdf");
		user1.setPassword("132234sdf");
		System.out.println(user1);
	}

}
