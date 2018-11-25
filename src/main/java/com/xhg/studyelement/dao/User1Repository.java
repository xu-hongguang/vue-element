package com.xhg.studyelement.dao;

import com.xhg.studyelement.pojo.User1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author 16033
 */
public interface User1Repository extends JpaRepository<User1, Integer>,
        JpaSpecificationExecutor<User1> {

    User1 findByUsername(String username);

}
