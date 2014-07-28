package com.hk.jdbc;
import com.hk.orderPlacement.OrderDetailsUtil;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetailsVerify {

    public static boolean orderDetails(){

        Map<String,Boolean> totalresult = new HashMap<String,Boolean>();
        String testcase = "all";

        Boolean testresult = false;
        if(OrderDetailsReturn.orderDetail().contains(OrderDetailsUtil.OrderAmount())){
            testresult = true;
        }

        else {

            testcase = "order amount";
            testresult = false;
            totalresult.put(testcase, testresult );
            return testresult;
        }
        if(OrderDetailsReturn.orderDetail().contains(OrderDetailsUtil.UserName())){

            testresult = true;

        }

        else {
            testcase = "username";
            testresult = false;
            totalresult.put(testcase,testresult);
            return testresult;
        }
        if(OrderDetailsReturn.orderDetail().contains(OrderDetailsUtil.Item())){

            testresult = true;

        }

        else {
            testcase = "items";
            testresult = false;
            totalresult.put(testcase, testresult );
            return testresult;
        }
        totalresult.put(testcase,testresult);
        return testresult;
    }
}