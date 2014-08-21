package com.hk.orderCheckoutDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 8/20/14
 * Time: 1:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class SoDetailsDTO {
    private List<String> shippingOrderIdList = new ArrayList<String>();

    public List<String> getShippingOrderIdList() {
        return shippingOrderIdList;
    }

    public void setShippingOrderIdList(List<String> shippingOrderIdList) {
        this.shippingOrderIdList = shippingOrderIdList;
    }
}
