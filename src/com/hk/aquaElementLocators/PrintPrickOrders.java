package com.hk.aquaElementLocators;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 8/5/14
 * Time: 11:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class PrintPrickOrders {

    private String printPrickLink="a[href*='ChooseOrdersForPrintPick.action']";
    private String orderFilters="/html/body/div/div[2]/form[1]/div[2]/span[2]";
    private String BoGatewayOrderIdTxt="//*[@id=\"orderFilters\"]/div[2]/input[2]";
    private String BoGatewaySearchBtn="//*[@id=\"orderFilters\"]/div[2]/input[3]";
    private String checkboxBo="shippingOrderDetailCheckbox";
    private String batchPrintBtn="//*[@id=\"orderForm\"]/div[2]/input[2]";
    private String cancelBtn="//*[text()='Cancel']";
    private String jobDoneClearQueBtn ="//*[@id=\"orderForm\"]/div[2]/input[2]";

    public String getJobDoneClearQueBtn() {
        return jobDoneClearQueBtn;
    }

    public String getPrintPrickLink() {
        return printPrickLink;
    }

    public String getOrderFilters() {
        return orderFilters;
    }

    public String getBoGatewayOrderIdTxt() {
        return BoGatewayOrderIdTxt;
    }

    public String getBoGatewaySearchBtn() {
        return BoGatewaySearchBtn;
    }

    public String getCheckboxBo() {
        return checkboxBo;
    }

    public String getBatchPrintBtn() {
        return batchPrintBtn;
    }

    public String getCancelBtn() {
        return cancelBtn;
    }
}