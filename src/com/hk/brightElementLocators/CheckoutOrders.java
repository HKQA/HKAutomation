package com.hk.brightElementLocators;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 8/6/14
 * Time: 12:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class CheckoutOrders {

    private String checkoutOrder="a[href*='BrightInventoryCheckout.action']";
    private String checkoutOrderTxt= "//*[@id=\"orderGatewayId\"]";
    private String checkoutOrderBtn= "//*[@id=\"invBarcodeCheckinForm\"]/input[2]";
    private String checkoutOrderBar="//*[@id=\"upc\"]";
    private  String freeBeeRadioutButton="//*[@id=\"freeCartLineItemTable\"]/tbody/tr/td[5]/form/input[5]";

    public String getFreeBeeRadioutButton() {
        return freeBeeRadioutButton;
    }

    public String getCheckoutOrderBar() {
        return checkoutOrderBar;
    }


    public String getCheckoutOrder() {
        return checkoutOrder;
    }

    public String getCheckoutOrderTxt() {
        return checkoutOrderTxt;
    }

    public String getCheckoutOrderBtn() {
        return checkoutOrderBtn;
    }
}
