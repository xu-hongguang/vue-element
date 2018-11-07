package com.xhg.studyelement.shiro.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public class Role {
    private Long id;
    private String name;  //角色名称
    private String sn;  //角色表达式： empMgr
}
