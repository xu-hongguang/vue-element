package com.xhg.studyelement.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 16033
 */
@Entity
@Table(name = "invoice")
@EqualsAndHashCode
public class Invoice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;

    private String invoiceName;

    private Double invoicePrice;

    private Date date;

    /**
     * 货品和客户是多对一关系
     */
//    @ManyToOne(cascade = CascadeType.PERSIST)
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER,targetEntity = User1.class)
    @JoinColumn(name = "user1_Id",referencedColumnName = "id")
//    @JsonIgnore
    private User1 user1;

    public Invoice() {
    }

    public Invoice(String invoiceName, Double invoicePrice, Date date) {
        this.invoiceName = invoiceName;
        this.invoicePrice = invoicePrice;
        this.date = date;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    public Double getInvoicePrice() {
        return invoicePrice;
    }

    public void setInvoicePrice(Double invoicePrice) {
        this.invoicePrice = invoicePrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User1 getUser1() {
        return user1;
    }

    public void setUser1(User1 user1) {
        this.user1 = user1;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "no=" + no +
                ", invoiceName='" + invoiceName + '\'' +
                ", invoicePrice=" + invoicePrice +
                ", date=" + date +
                ", user1=" + user1 +
                '}';
    }
}
