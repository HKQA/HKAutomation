package com.hk.aquaElementLocators;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 8/5/14
 * Time: 3:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class AdminHome {

    private String searchBo="a[href*='SearchOrder.action']";
    private String searchSo="a[href*='SearchShippingOrder.action']";
    private String selectWh ="//*[@id=\"selectWHForm\"]/select";

    public String getSearchBo() {
        return searchBo;
    }

    public String getSearchSo() {
        return searchSo;
    }

    public String getSelectWh() {
        return selectWh;
    }
}
