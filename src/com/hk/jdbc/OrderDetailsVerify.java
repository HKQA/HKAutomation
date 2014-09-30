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

        if(OrderDetailsUtil.flag_signup)
        {

        if (!orderDetailsDTO.getProductList().containsAll(OrderDetailsUtil.getItems()))
            {
            return false;
            }
        }
        if (OrderDetailsUtil.flag)
        {

        if (!orderDetailsDTO.getProductList().containsAll(OrderDetailsUtil.getItems_existing()))
            {

            return false;
            }
        }


        return true;


    }
}