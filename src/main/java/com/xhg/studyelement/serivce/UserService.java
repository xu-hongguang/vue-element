package com.xhg.studyelement.serivce;

import com.xhg.studyelement.pojo.User;
import org.springframework.data.domain.Page;

/**
 * @author 16033
 */
public interface UserService {
    Page<User> findAll(Integer pageNo,Integer pageSize);

    Page<User> findAllByUsername(Integer pageNo, Integer pageSize,String username);
}
