package com.xhg.studyelement.serivce;

import com.xhg.studyelement.pojo.User1;
import org.springframework.data.domain.Page;

/**
 * @author 16033
 */
public interface User1Service {
    Page<User1> findAll(Integer pageNo, Integer pageSize);

    Page<User1> findAllByUsername(Integer pageNo, Integer pageSize,String username);

    User1 findByUsername(String username);

    boolean saveUser(User1 user);

    boolean updateUser(User1 user);

    void deleteUser(Integer id);

    void deleteMap(Integer[] ids);

}
