package com.xhg.studyelement.dao;

import com.xhg.studyelement.pojo.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * invoice数据库操作接口
 *
 * @author 16033
 */
public interface InvoiceRepository extends JpaRepository<Invoice,Integer>,JpaSpecificationExecutor<Invoice> {
    Invoice findByNo(Integer no);
}
