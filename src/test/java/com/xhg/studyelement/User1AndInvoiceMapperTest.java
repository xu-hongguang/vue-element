package com.xhg.studyelement;

import com.xhg.studyelement.dao.InvoiceRepository;
import com.xhg.studyelement.dao.User1Repository;
import com.xhg.studyelement.pojo.Invoice;
import com.xhg.studyelement.pojo.User1;
import com.xhg.studyelement.serivce.User1Service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudyelementApplication.class)
public class User1AndInvoiceMapperTest {

    @Resource
    private User1Repository user1Repository;

    @Autowired
    private User1Service user1Service;

    @Autowired
    private InvoiceRepository invoiceRepository;


    /**
     * 保存货品关联
     */
    @Test
    public void testSave() {
//        创建客户
        User1 user1 = new User1("一对多3", "123456");
        user1.setId(41);

//        创建货品
        Invoice invoice = new Invoice("电视机8", 2180.3,
                new GregorianCalendar(2018, 11 - 1, 10).getTime());

//        关联
        Set<Invoice> invoices = user1.getInvoices();
        invoices.add(invoice);
        invoice.setUser1(user1);

//        保存数据
        invoiceRepository.save(invoice);

    }

    /**
     * 关联查询
     */
    @Test
    public void testFind(){
        User1 user1 = user1Repository.findById(41).get();
        System.out.println(user1);

        for (Invoice invoice : user1.getInvoices()) {
            System.out.println(invoice);
        }

        Invoice invoice = invoiceRepository.findById(10).get();

        System.out.println(invoice.getUser1());
    }

    @Test
    public void testUser1Page(){
        System.out.println(user1Service.findAllByUsername(4,5,"").getContent());
    }

    @Test
    public void testPage(){

        String username = "测试服";

        Pageable pageable = PageRequest.of(0,2, Sort.Direction.DESC,"no");

        Page<Invoice> user1Page = invoiceRepository.findAll((Specification<Invoice>) (root, query, criteriaBuilder) -> {

            /*
             * 两种方式
             */
//            第一种（可以指定使用哪种关联查询）
//            指定关联属性
            root.join("user1", JoinType.INNER);
            List<Predicate> predicateList = new ArrayList<>();
            predicateList.add(criteriaBuilder.equal(root.get("user1").get("id"),41));
            predicateList.add(criteriaBuilder.like(root.get("user1").get("username"),"%" + username + "%"));
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("invoicePrice"),500));

            Predicate[] predicates = new Predicate[predicateList.size()];
            return criteriaBuilder.and(predicateList.toArray(predicates));

//            第二种
            /*Predicate p1 = criteriaBuilder.equal(root.get("user1").get("id"),41);
            Predicate p2 = criteriaBuilder.like(root.get("user1").get("username"),"%" + username + "%");
            Predicate p3 = criteriaBuilder.greaterThanOrEqualTo(root.get("invoicePrice"),500);
            query.where(criteriaBuilder.and(p1,p2,p3));
            return query.getRestriction();*/
        },pageable);


        System.out.println("查询总条数：" + user1Page.getTotalElements());
        System.out.println("总页数：" + user1Page.getTotalPages());
        System.out.println("当页记录数：" + user1Page.getNumberOfElements());
        System.out.println("查到的数据：" + user1Page.getContent());

    }
}
