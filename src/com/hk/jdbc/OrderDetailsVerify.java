package com.hk.jdbc;

import com.hk.orderPlacement.OrderDetailsUtil;

public class OrderDetailsVerify {

    public static boolean orderDetails() throws InterruptedException {
        OrderDetailsDTO orderDetailsDTO = OrderDetailsReturn.orderDetail();
        if (!orderDetailsDTO.getAmount().equals(OrderDetailsUtil.getOrderAmount())) {
            return false;
        }

        if (!orderDetailsDTO.getUserName().equalsIgnoreCase(OrderDetailsUtil.getUserName())) {
            return false;
        }

        if(OrderDetailsUtil.flagLoyalty)
        {

        if (!orderDetailsDTO.getProductList().containsAll(OrderDetailsUtil.getItems_Loyalty()))
            {
            return false;
            }
        }
        if (OrderDetailsUtil.flagNoLoyalty)
        {

        if (!orderDetailsDTO.getProductList().containsAll(OrderDetailsUtil.getItems_NoLoyalty()))
            {
            return false;
            }
        }
        return true;
    }
}