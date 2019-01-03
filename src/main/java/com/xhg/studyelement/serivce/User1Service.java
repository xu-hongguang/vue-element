package com.xhg.studyelement.serivce;

import com.xhg.studyelement.pojo.User1;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author 16033
 */
public interface User1Service {
    List<User1> findAll();

    Page<User1> findAll(Integer pageNo, Integer pageSize);

    Page<User1> findAllByUsername(Integer pageNo, Integer pageSize,String username);

    /**
     * 解析Excel
     * @param multipartFile 文件
     * @return map
     */
    Map<String,Object> parseExcel(MultipartFile multipartFile);

    User1 findByUsername(Integer id,String username);

    User1 saveUser(User1 user);

    User1 updateUser(User1 user);

    void deleteUser(Integer id);

    void deleteMap(Integer[] ids);

}
