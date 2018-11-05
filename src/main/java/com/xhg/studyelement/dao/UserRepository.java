package com.xhg.studyelement.dao;

import com.xhg.studyelement.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User,Integer>,JpaSpecificationExecutor<User> {

    User findByUsername(String username);

}
