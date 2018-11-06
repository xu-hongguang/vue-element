package com.xhg.studyelement.serivce.Impl;

import com.xhg.studyelement.dao.UserRepository;
import com.xhg.studyelement.pojo.User;
import com.xhg.studyelement.serivce.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.stream.IntStream;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<User> findAll(Integer pageNo, Integer pageSize) {
        if (pageNo < 1) {
            pageNo = 1;
        }

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.Direction.ASC, "id");

        return userRepository.findAll(pageable);
    }

    /**
     * 根据用户名模糊查询
     *
     * @param pageNo
     * @param pageSize
     * @param username
     * @return
     */
    @Override
    public Page<User> findAllByUsername(Integer pageNo, Integer pageSize, String username) {
        if (pageNo < 1) {
            pageNo = 1;
        }

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.Direction.ASC, "id");

        return userRepository.findAll((Specification<User>) (root, query, criteriaBuilder) -> {
            Predicate p1 = criteriaBuilder.like(root.get("username"), "%" + username + "%");
            query.where(criteriaBuilder.and(p1));
            return query.getRestriction();
        }, pageable);
    }

    @Override
    public boolean saveUser(User user) {
        User user1 = userRepository.findByUsername(user.getUsername());
        logger.info("对比：" + user1);
        boolean isSave = false;
        if (user1 == null) {
            userRepository.save(user);
            isSave = true;
        }
        return isSave;
    }

    @Override
    public boolean updateUser(User user){
        if (user != null) {
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMap(Integer[] ids) {
        for (Integer id : ids) {
            deleteUser(id);
        }
    }


}
