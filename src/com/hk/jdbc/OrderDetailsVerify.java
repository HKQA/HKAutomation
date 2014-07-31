package com.hk.jdbc;

import com.hk.orderPlacement.OrderDetailsUtil;

public class OrderDetailsVerify {

    public static boolean orderDetails() throws Exception {
        OrderDetailsDTO orderDetailsDTO = OrderDetailsReturn.orderDetail();
        if (!orderDetailsDTO.getAmount().equals(OrderDetailsUtil.getOrderAmount())) {
            throw new Exception("Amount mismatch");
//            return false;
        }

        if (!orderDetailsDTO.getUserName().equals(OrderDetailsUtil.getUserName())) {
            throw new Exception("User name mismatch");
//            return false;
        }

        if (!orderDetailsDTO.getProductList().containsAll(OrderDetailsUtil.getItems())) {
            throw new Exception("Variant List mismatch");
//            return false;
        }
        return true;
    }
}