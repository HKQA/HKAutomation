package com.hk.elementLocators;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 7/2/14
 * Time: 11:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class CartPage {
    private String addToCartBtn="a[href*='btn-mini']";
    private String addCouponTextBox="input[class='js-coupon-code']";
    private String addQuantityPlus=".//*[@id='plus']";
    private String addQuantityMinus=".//*[@id='minus']";
    private String proceedToCheckout="html/body/div/div/div/div/div[2]/div/div[2]/div[1]/div[4]/a";



    public String addToCartBtn() {
        return addToCartBtn;
    }

    public String addCouponTextBox() {
        return addCouponTextBox;
    }

    public String proceedToCheckout() {
        return proceedToCheckout;
    }
    public String addQuantityPlus() {
        return addQuantityPlus;
    }
    public String addQuantityMinus() {
        return addQuantityMinus;
    }

}
