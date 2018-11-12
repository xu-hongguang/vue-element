package com.xhg.studyelement.shiro.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class User implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String status;
    private Date createDate;
}
