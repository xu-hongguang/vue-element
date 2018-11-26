package com.xhg.studyelement;

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

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudyelementApplication.class)
public class EhcacheTest {

	@Autowired
	User1Repository userRepository;

	@Autowired
	private User1Service user1Service;

	@Test
	public void test(){

		System.out.println(user1Service.findAllByUsername(0,20,"").getTotalElements());

		user1Service.saveUser(new User1("水天2","123swwq",new Date()));

		System.out.println(user1Service.findAllByUsername(0,20,"").getTotalElements());
	}

}