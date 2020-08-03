package com.xdsty.api.controller;

import com.xdsty.api.controller.param.OrderAddParam;
import com.xdsty.api.controller.param.OrderProductAddParam;
import com.xdsty.orderclient.dto.OrderAddDto;
import com.xdsty.orderclient.dto.OrderProductAddDto;
import com.xdsty.productclient.dto.ProductStorageDto;
import com.xdsty.txclient.service.OrderTransactionService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("order")
@RestController
public class OrderController {

    @DubboReference(version = "1.0")
    private OrderTransactionService orderTransactionService;

    @RequestMapping("placeOrder")
    @GlobalTransactional
    public void placeOrder() {
        OrderAddParam param = new OrderAddParam();
        param.setTotalPrice(BigDecimal.valueOf(10.0));
        param.setUserId(1L);

        List<OrderProductAddParam> orderProductAddParams = new ArrayList<>();
        OrderProductAddParam productAddParam = new OrderProductAddParam();
        productAddParam.setProductId(1L);
        productAddParam.setProductNum(1);
        productAddParam.setProductPrice(BigDecimal.valueOf(10.0));
        orderProductAddParams.add(productAddParam);
        param.setOrderProductAdds(orderProductAddParams);

        OrderAddDto dto = convert2OrderAddDto(param);

        orderTransactionService.placeOrder(dto);

    }

    private ProductStorageDto convert2ProductStorageDto(OrderProductAddDto orderProductAddDto) {
        ProductStorageDto dto = new ProductStorageDto();
        dto.setProductId(orderProductAddDto.getProductId());
        dto.setProductNum(orderProductAddDto.getProductNum());
        return dto;
    }

    private OrderAddDto convert2OrderAddDto(OrderAddParam param) {
        OrderAddDto dto = new OrderAddDto();
        dto.setUserId(param.getUserId());
        dto.setTotalPrice(param.getTotalPrice());
        dto.setProductDtos(param.getOrderProductAdds().stream().map(this::convert2OrderProductAddDto).collect(Collectors.toList()));
        return dto;
    }

    private OrderProductAddDto convert2OrderProductAddDto(OrderProductAddParam param) {
        OrderProductAddDto dto = new OrderProductAddDto();
        dto.setProductId(param.getProductId());
        dto.setProductNum(param.getProductNum());
        dto.setProductPrice(param.getProductPrice());
        return dto;
    }

}
