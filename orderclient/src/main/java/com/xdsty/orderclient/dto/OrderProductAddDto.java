package com.xdsty.orderclient.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 张富华
 * @date 2020/7/28 9:49
 */
public class OrderProductAddDto implements Serializable {

    private Long productId;

    private Integer productNum;

    private BigDecimal productPrice;

    private List<OrderProductAdditionalDto> orderProductAdditionals;

    public List<OrderProductAdditionalDto> getOrderProductAdditionals() {
        return orderProductAdditionals;
    }

    public void setOrderProductAdditionals(List<OrderProductAdditionalDto> orderProductAdditionals) {
        this.orderProductAdditionals = orderProductAdditionals;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }
}
