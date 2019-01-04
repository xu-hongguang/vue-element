package com.xhg.studyelement.serivce.Impl;

import com.xhg.studyelement.dao.InvoiceRepository;
import com.xhg.studyelement.pojo.Invoice;
import com.xhg.studyelement.serivce.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {


    private Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    @Autowired
    private InvoiceRepository invoiceRepository;

    /**
     * 保存货单
     *
     * @param invoice 关联了用户id(需要设置用户id)
     * @return boolean
     */
    @Override
    public boolean saveInvoiceWithUser1Id(Invoice invoice) {
        Invoice invoice1 = invoiceRepository.findByNo(invoice.getNo());
        logger.info("对比: " + invoice1);
        boolean isSave = false;
        if (invoice1 == null){
            invoiceRepository.save(invoice);
            isSave = true;
        }
        return isSave;
    }

    /**
     * 通过用户名查询对应的货单
     *
     * @param pageNo
     * @param pageSize
     * @param username
     * @return
     */
    @Override
    public Page<Invoice> findAllByInvoiceName(Integer pageNo, Integer pageSize, String username) {

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.Direction.DESC, "no");

        Page<Invoice> invoicePage = invoiceRepository.findAll((Specification<Invoice>) (root, query, criteriaBuilder) -> {

            /*
             * 两种方式
             */
//            第一种（可以指定使用哪种关联查询）
//            指定关联属性
            root.join("user1", JoinType.INNER);
            List<Predicate> predicateList = new ArrayList<>();
            predicateList.add(criteriaBuilder.equal(root.get("user1").get("id"), 48));
            predicateList.add(criteriaBuilder.like(root.get("user1").get("username"),  "%" + username + "%"));
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("invoicePrice"), 500));

            Predicate[] predicates = new Predicate[predicateList.size()];
            return criteriaBuilder.and(predicateList.toArray(predicates));

//            第二种
            /*Predicate p1 = criteriaBuilder.equal(root.get("user1").get("id"),41);
            Predicate p2 = criteriaBuilder.like(root.get("user1").get("username"),"%" + username + "%");
            Predicate p3 = criteriaBuilder.greaterThanOrEqualTo(root.get("invoicePrice"),500);
            query.where(criteriaBuilder.and(p1,p2,p3));
            return query.getRestriction();*/
        }, pageable);


        return invoicePage;
    }
}
