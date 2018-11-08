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
public class User implements Serializable {
    private Long id;
    private String username;
    private String password;
}
