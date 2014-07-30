package com.hk.jdbc;

import com.hk.orderPlacement.OrderDetailsUtil;

public class OrderDetailsVerify {

    public static boolean orderDetails() {
        OrderDetailsDTO orderDetailsDTO = OrderDetailsReturn.orderDetail();
        if (!orderDetailsDTO.getAmount().equals(OrderDetailsUtil.getOrderAmount())) {
            return false;
        }

        if (!orderDetailsDTO.getUserName().equals(OrderDetailsUtil.getUserName())) {
            return false;
        }

        if (!orderDetailsDTO.getProductList().containsAll(OrderDetailsUtil.getItems())) {
            return false;
        }
        return true;
    }
}