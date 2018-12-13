package com.xhg.studyelement.serivce.Impl;

import com.xhg.studyelement.dao.User1Repository;
import com.xhg.studyelement.pojo.User1;
import com.xhg.studyelement.serivce.User1Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * @author 16033
 */
@Service(value = "user1Service")
@CacheConfig(cacheNames = "user1")
public class User1ServiceImpl implements User1Service {

    Logger logger = LoggerFactory.getLogger(User1ServiceImpl.class);

    @Autowired
    private User1Repository user1Repository;

    @Override
    public List<User1> findAll(){
        return user1Repository.findAll();
    }

    @Override
    public Page<User1> findAll(Integer pageNo, Integer pageSize) {
        if (pageNo < 1) {
            pageNo = 1;
        }

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.Direction.ASC, "id");

        return user1Repository.findAll(pageable);
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
    public Page<User1> findAllByUsername(Integer pageNo, Integer pageSize, String username) {
        if (pageNo < 1) {
            pageNo = 1;
        }

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.Direction.ASC, "id");

        return user1Repository.findAll((Specification<User1>) (Root<User1> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate p1 = criteriaBuilder.like(root.get("username"), "%" + username + "%");
            query.where(criteriaBuilder.and(p1));
            return query.getRestriction();
        }, pageable);
    }

    @Override
    @Cacheable(key = "#p0")
    public User1 findByUsername(String username) {
        return user1Repository.findByUsername(username);
    }

    @Override
//    删除所有缓存
    @CacheEvict(allEntries = true)
    public boolean saveUser(User1 user) {
        User1 user1 = user1Repository.findByUsername(user.getUsername());
        logger.info("对比：" + user1);
        boolean isSave = false;
        if (user1 == null) {
            user.setCreateDate(new Date());
            user1Repository.save(user);
            isSave = true;
        }
        return isSave;
    }

    @Override
    @CachePut(key = "#p0.id")
    public boolean updateUser(User1 user){
        if (user != null) {
            user1Repository.save(user);
            return true;
        }
        return false;
    }

    @Override
    @CacheEvict(key = "#p0")
    public void deleteUser(Integer id) {
        user1Repository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMap(Integer[] ids) {
        for (Integer id : ids) {
            deleteUser(id);
        }
    }


}
