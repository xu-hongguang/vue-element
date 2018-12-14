package com.xhg.studyelement;

import com.xhg.studyelement.common.utils.RedisUtils;
import com.xhg.studyelement.dao.User1Repository;
import com.xhg.studyelement.pojo.User1;
import com.xhg.studyelement.serivce.User1Service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudyelementApplication.class)
public class RedisTest {

	@Autowired
	private User1Repository user1Repository;

	@Autowired
	private User1Service user1Service;

	@Autowired
	private RedisUtils redisUtils;

	/**
	 * 保存对象
	 */
	@Test
	public void testSet(){
		redisUtils.setValue("user3",user1Repository.findById(41).get());
	}

	/**
	 * 获取对象
	 */
	@Test
	public void testGet(){
		User1 user1 = redisUtils.getValue("user3",User1.class);

		System.out.println(user1);
	}

	/**
	 * 删除对象
	 */
	@Test
	public void testDelete(){
		redisUtils.delete("user1");
	}


}
