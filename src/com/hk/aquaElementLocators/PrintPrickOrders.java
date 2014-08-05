package com.hk.aquaElementLocators;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 8/5/14
 * Time: 11:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class PrintPrickOrders {
    private String searchSo="a[href*='ChooseOrdersForPrintPick.action']";
    private String orderFilters="/html/body/div/div[2]/form[1]/div[2]/span[2]";
    private String BoGatewayOrderId="//*[@id=\"orderFilters\"]/div[2]/input[2]";
    private String BoGatewaySearchbtn="//*[@id=\"orderFilters\"]/div[2]/input[3]";
    private String checkboxBo="//*[@id=\"shippingOrder-2296888\"]/td[5]/input";
    private String batchPrintbtn="//*[@id=\"orderForm\"]/div[2]/input[2]";

    public String getOrderFilters() {
        return orderFilters;
    }

    public String getBoGatewayOrderId() {
        return BoGatewayOrderId;
    }

    public String getBoGatewaySearchbtn() {
        return BoGatewaySearchbtn;
    }

    public String getCheckboxBo() {
        return checkboxBo;
    }

    public String getBatchPrintbtn() {
        return batchPrintbtn;
    }

    public String getSearchSo() {
        return searchSo;
    }
}