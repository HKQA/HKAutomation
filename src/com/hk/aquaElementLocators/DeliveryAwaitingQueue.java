package com.hk.aquaElementLocators;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 8/21/14
 * Time: 7:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class DeliveryAwaitingQueue {
    private String gatewayOrderIdTxt="/html/body/div/div[2]/fieldset/form/ul/div/li[2]/input";
    private String searchBtn="/html/body/div/div[2]/fieldset/form/ul/div/li[9]/div/input";
    private String checkBox="shippingOrderDetailCheckbox";
    private String markOrdersAsDelivered="//*[@id=\"markShippingOrdersAsDelivered\"]";
    private String deliveryAwaitingQueueLink="/html/body/div/div[2]/div[2]/h3[6]/a";

    public String getDeliveryAwaitingQueueLink() {
        return deliveryAwaitingQueueLink;
    }

    public String getGatewayOrderIdTxt() {
        return gatewayOrderIdTxt;
    }

    public String getSearchBtn() {
        return searchBtn;
    }

    public String getCheckBox() {
        return checkBox;
    }

    public String getMarkOrdersAsDelivered() {
        return markOrdersAsDelivered;
    }



}
