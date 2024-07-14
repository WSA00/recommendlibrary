package com.library.response;

import com.library.pojo.Order;
import lombok.Data;

import java.util.Date;

@Data
public class updateOrderResponse {
    private Integer id;

    private Integer productId;

    private Integer userId;

    private Integer warehouseId;

    private Date createTime;

    public updateOrderResponse(Order order) {
        this.id = order.getId();
        this.productId = order.getProductId();
        this.userId = order.getUserId();
        this.warehouseId = order.getWarehouseId();
    }
}
