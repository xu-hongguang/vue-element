package com.xhg.studyelement.serivce;

import com.xhg.studyelement.pojo.Invoice;
import org.springframework.data.domain.Page;

public interface InvoiceService {
    boolean saveInvoiceWithUser1Id(Invoice invoice);

    Page<Invoice> findAllByInvoiceName(Integer pageNo,Integer pageSize,String username);
}
