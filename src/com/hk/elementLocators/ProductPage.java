package com.hk.elementLocators;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 6/30/14
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductPage {
    private String buyNowbtn="html/body/div[1]/div[2]/div[2]/div[4]/div[2]/div[1]/div[2]/input";
    private String addToCompareButton="//*[@id=\"variant-page\"]/div[2]/div[3]/div[2]/ul/li[2]/span[2]/a";

    public String pressBuyNow() {
        return buyNowbtn;
    }

    public String getAddToCompareButton(){
        return addToCompareButton;
    }
}
