package com.xhg.studyelement.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
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
    private int id;
    private String username;
    private String password;

    /**
     * 客户和货品是一对多关系
     */
//    @OneToMany(fetch = FetchType.EAGER,targetEntity = Invoice.class,mappedBy = "user1")
    @OneToMany(targetEntity = Invoice.class, fetch = FetchType.EAGER,mappedBy = "user1",orphanRemoval = true)
    private Set<Invoice> invoices = new HashSet<>();

    public User1(String username, String pwd) {
        this.username = username;
        this.password = pwd;
    }

    public User1() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public String toString() {
        return "User1{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", invoices's size =" + invoices.size() +
                '}';
    }
}
