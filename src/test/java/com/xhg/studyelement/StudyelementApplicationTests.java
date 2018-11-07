package com.xhg.studyelement;

import com.xhg.studyelement.common.utils.PageUtils;
import com.xhg.studyelement.dao.UserRepository;
import com.xhg.studyelement.pojo.User;
import com.xhg.studyelement.serivce.UserService;
import com.xhg.studyelement.shiro.dao.IUserDAO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudyelementApplication.class)
public class StudyelementApplicationTests {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Test
	public void contextLoads() {
		List<User> users = userRepository.findAll();
		System.out.println(users);
		Assert.assertEquals(2,users.size());

		PageUtils<User> userPageUtils = new PageUtils<>(users,(int)userRepository.count(),1,1);
		System.out.println(userPageUtils.getList());
		System.out.println(userPageUtils.getTotalPage());
	}

	@Test
	public void test(){
		User user = userRepository.findById(2).get();

		System.out.println(user.toString());

        Page<User> userPage = userService.findAllByUsername(1, 1, "xh");

        System.out.println(userPage.getTotalElements());

    }

    @Test
    public void  test2(){
		userService.deleteUser(5);

	}

	@Autowired
	private IUserDAO userDAO;

	@Test
	public void getUserByUsername() {
		System.out.println(userDAO.getUserByUsername("zhangsan"));
	}

}
