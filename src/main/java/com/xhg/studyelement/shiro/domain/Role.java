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
    /** 角色名称 */
    private String name;
    /** 角色表达式： empMgr */
    private String sn;
}
