package com.xhg.studyelement.shiro.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 权限类
 * @author 16033
 */
@Getter
@Setter
@EqualsAndHashCode
public class Permission {
    private Long id;
    private String name;  //权限名称
    private String resource; //资源表达式xx:xx  比如：employee:list
}
