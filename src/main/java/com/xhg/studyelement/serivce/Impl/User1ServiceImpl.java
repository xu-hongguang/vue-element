package com.xhg.studyelement.serivce.Impl;

import com.xhg.studyelement.common.safesoft.User1ImportExcel;
import com.xhg.studyelement.dao.User1Repository;
import com.xhg.studyelement.pojo.User1;
import com.xhg.studyelement.serivce.User1Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

/**
 * @author 16033
 */
@Service(value = "user1Service")
@CacheConfig(cacheNames = "user1")
public class User1ServiceImpl implements User1Service {

    Logger logger = LoggerFactory.getLogger(User1ServiceImpl.class);

    // 错误条数
    private int errorCount;

    // 重复条数
    private int repeatCount;

    @Autowired
    private User1Repository user1Repository;

    @Override
    public List<User1> findAll() {
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

    /**
     * 解析Excel
     *
     * @param multipartFile 文件
     * @return
     */
    @Override
    public Map<String, Object> parseExcel(MultipartFile multipartFile) {
        //进入解析excel方法
        final User1ImportExcel redInvoiceImport = new User1ImportExcel(multipartFile);
        final Map<String, Object> map = newHashMap();
        try {
            //读取excel
            final List<User1> user1List = redInvoiceImport.analysisExcel();
            if (!user1List.isEmpty()) {
                map.put("success", Boolean.TRUE);
                Map<String, List<User1>> entityMap = user1ImportData(user1List);
                map.put("errorCount", errorCount);
                map.put("repeatCount", repeatCount);
                map.put("reason", entityMap.get("successEntityList"));
                map.put("errorEntityList", entityMap.get("errorEntityList"));
                map.put("repeatEntityList", entityMap.get("repeatEntityList"));

                logger.info("总数据：" + (entityMap.get("successEntityList").size() + entityMap.get("errorEntityList").size() + entityMap.get("repeatEntityList").size()));
            } else {
                // LOGGER.info("读取到excel无数据");
                map.put("success", Boolean.FALSE);
                map.put("reason", "excel中无数据！");
            }
        } catch (Exception e) {
            map.put("success", Boolean.FALSE);
            map.put("reason", "读取excel文件错误！");
        }
        return map;
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
    public boolean updateUser(User1 user) {
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


    private Map<String, List<User1>> user1ImportData(List<User1> user1List) {
        //返回值
        final Map<String, List<User1>> map = newHashMap();
        //导入成功的数据集
        final List<User1> successEntityList = newArrayList();
        //导入错误的数据集
        final List<User1> errorEntityList = newArrayList();
        //导入重复的数据集
        final List<User1> repeatEntityList = newArrayList();

        user1List.forEach(user1Data -> {
            Integer user1Id = user1Data.getId();
            String username = user1Data.getUsername();
            String password = user1Data.getPassword();

            User1 user;
            if (user1Id != null && !username.isEmpty() && !password.isEmpty()) {
                // 判断是否已经存在此用户id
                user = user1Repository.selectById(user1Id);
                if (user == null) {
                    // 判断是否已经有此用户名
                    user = user1Repository.findByUsername(username);
                    if (user != null) {
                        repeatCount++;
                        repeatEntityList.add(user1Data);
                    } else {
                        successEntityList.add(user1Data);
                        user1Repository.save(user1Data);
                    }
                } else {
                    repeatCount++;
                    repeatEntityList.add(user1Data);
                }
            } else {
                errorCount++;
                errorEntityList.add(user1Data);
            }
        });

        //去重
        /*for (int i = 0; i < successEntityList.size() - 1; i++) {
            for (int j = successEntityList.size() - 1; j > i; j--) {
                String str = "";
                String str2 = "";
                str = successEntityList.get(j).getUsername();
                str2 = successEntityList.get(j).getUsername();
                if (str.equals(str2)) {
                    repeatCount ++;
                    errorEntityList.add(successEntityList.get(j));
                    successEntityList.remove(j);
                }
            }
        }*/

        if (errorEntityList.size() == 0 && repeatEntityList.size() == 0) {
            //如果都校验通过，保存入库
            for (User1 user1 : successEntityList) {
                user1Repository.save(user1);
            }
        }

        map.put("successEntityList", successEntityList);
        map.put("errorEntityList", errorEntityList);
        map.put("repeatEntityList", repeatEntityList);
        return map;
    }

}
