package com.xdsty.userservice.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserIntegral {

    private Long id;

    private Integer type;

    private Long userId;

    private Long orderId;

    private Integer integral;

    private Integer status;


}
