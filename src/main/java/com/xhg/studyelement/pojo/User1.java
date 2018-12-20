package com.xhg.studyelement.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 16033
 */
@Entity
@Table(name = "user1")
public class User1 implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    @Column(name = "create_date")
    private Date createDate;
    private String remark;

    /**
     * 客户和货品是一对多关系
     */
//    @OneToMany(fetch = FetchType.EAGER,targetEntity = Invoice.class,mappedBy = "user1")
    @OneToMany(targetEntity = Invoice.class, fetch = FetchType.EAGER,mappedBy = "user1")
    @JsonIgnore
    private Set<Invoice> invoices = new HashSet<>();

    public User1(String username, String pwd, Date createDate) {
        this.username = username;
        this.password = pwd;
        this.createDate = createDate;
    }

    public User1() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "User1{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", createDate='" + createDate + '\'' +
                ", remark='" + remark + '\'' +
                ", invoices's size =" + invoices.size() +
                '}';
    }
}
