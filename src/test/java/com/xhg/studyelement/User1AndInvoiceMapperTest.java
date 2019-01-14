package com.xhg.studyelement;

import com.xhg.studyelement.common.utils.PageUtils;
import com.xhg.studyelement.dao.InvoiceRepository;
import com.xhg.studyelement.dao.User1Repository;
import com.xhg.studyelement.pojo.Invoice;
import com.xhg.studyelement.pojo.User1;
import com.xhg.studyelement.serivce.InvoiceService;
import com.xhg.studyelement.serivce.User1Service;
import com.xhg.studyelement.shiro.domain.User;
import com.xhg.studyelement.shiro.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudyelementApplication.class)
public class User1AndInvoiceMapperTest {

    @Resource
    private User1Repository user1Repository;

    @Autowired
    private User1Service user1Service;

    @Autowired
    private UserService userService;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceService invoiceService;


    /**
     * 保存货品关联
     */
    @Test
    public void testSave() {
//        创建客户
       /* User1 user1 = new User1("一对多3", "123456",new Date());
        user1.setId(4);

//        创建货品
        Invoice invoice = new Invoice("电视机2", 2180.3,
                new GregorianCalendar(2018, 11 - 1, 10).getTime());

//        关联
        Set<Invoice> invoices = user1.getInvoices();
        invoices.add(invoice);
        invoice.setUser1(user1);

//        保存数据
        invoiceRepository.save(invoice);*/

       Invoice invoice = new Invoice("冰箱",3000.0,new Date());
       User1 user1 = user1Repository.findById(1).get();
       invoice.setUser1(user1);

       invoiceService.saveInvoiceWithUser1Id(invoice);

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
        System.out.println(user1Service.findAllByUsername(0,5,"").getContent());
    }

    @Test
    public void testPage(){

        Page<Invoice> invoicePage = invoiceService.findAllByInvoiceName(1,2,"新店");


        System.out.println("查询总条数：" + invoicePage.getTotalElements());
        System.out.println("总页数：" + invoicePage.getTotalPages());
        System.out.println("当页记录数：" + invoicePage.getNumberOfElements());
        System.out.println("查到的数据：" + invoicePage.getContent());

    }

    @Test
    public void testUsers(){
        Map<String,Object> map = new HashMap<>(16);
        map.put("page",1);
        map.put("limit",2);
        PageUtils<User> userAllPaging = userService.findUserAllPaging(map);
        System.out.println("当前页数据:" + userAllPaging.getList());
        System.out.println("总记录:" + userAllPaging.getTotalCount());
        System.out.println("总页数:" + userAllPaging.getTotalPage());
        System.out.println("页码:" + userAllPaging.getCurrPage());
    }
}
