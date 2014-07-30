package com.hk.jdbc;

import com.hk.orderPlacement.OrderDetailsUtil;

public class OrderDetailsVerify {

    public static boolean orderDetails() {

//        Map<String,Boolean> totalresult = new HashMap<String,Boolean>();
        String testcase = "all";

        Boolean testresult = false;
        /*if(OrderDetailsReturn.orderDetail().contains(OrderDetailsUtil.OrderAmount())){
            testresult = true;
        }

        else {

            testcase = "order amount";
            testresult = false;
            totalresult.put(testcase, testresult );
            return testresult;
        }*/
        if (!OrderDetailsReturn.orderDetail().contains(OrderDetailsUtil.getUserName())) {
//            testcase = "username";
//            testresult = false;
//            totalresult.put(testcase,testresult);
            return false;
        }


        if (!OrderDetailsReturn.orderDetail().containsAll(OrderDetailsUtil.getItems())) {

            /*testcase = "items";
            testresult = false;
            totalresult.put(testcase, testresult );*/
            return false;
        }
//        totalresult.put(testcase,testresult);
        return true;
    }
}