package com.xhg.studyelement.shiro.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 权限类
 * @author 16033
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Permission implements Serializable {
    private Long id;
    /**
     * 权限名称
     */
    private String name;

    /**
     * 资源表达式xx:xx  比如：employee:list
     */
    private String resource;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 父级id
     */
    private String parientId;

    /**
     * 菜单URL
     */
    private String url;

    /**
     * 权限类型  0 按钮  1 菜单
     */
    private String type;

    /**
     * 子菜单
     */
    private List<Permission> childList;
}
