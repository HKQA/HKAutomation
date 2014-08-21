package com.hk.aquaElementLocators;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 8/21/14
 * Time: 7:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShipmentAwaitingQueue {
    private String gatewayId="//*[@id=\"gatewayOrderId\"]";
    private String searchBtn="/html/body/div/div[2]/fieldset[1]/ul/div/form/label[3]/label/input";
    private String checkBox="shippingOrderDetailCheckbox";
    private String markedOrdersAsShipped="//*[@id=\"markShippingOrdersAsShipped\"]";

    public String getGatewayId() {
        return gatewayId;
    }

    public String getSearchBtn() {
        return searchBtn;
    }

    public String getCheckBox() {
        return checkBox;
    }

    public String getMarkedOrdersAsShipped() {
        return markedOrdersAsShipped;
    }



}
