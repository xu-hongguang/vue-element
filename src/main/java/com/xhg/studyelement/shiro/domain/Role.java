package com.xhg.studyelement.shiro.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Role implements Serializable {
    private Long id;
    private String name;  //角色名称
    private String sn;  //角色表达式： empMgr
}
