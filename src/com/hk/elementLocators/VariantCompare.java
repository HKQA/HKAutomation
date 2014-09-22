package com.hk.elementLocators;

/**
 * Created with IntelliJ IDEA.
 * User: saurabh.nagpal
 * Date: 9/22/14
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class VariantCompare {
    private String crossButton="/html/body/div[1]/div[2]/div/div[2]/table[2]/thead/tr[1]/th[2]/a";
    private String buyNowButton="/html/body/div[1]/div[2]/div/div[2]/table[2]/thead/tr[3]/th[2]/div/input";
    private String compareButton="//*[@id=\"compButton1\"]";
    private String clearButton="//*[@id=\"variantCompareView\"]/div[3]/p[2]";
    private String clearAllButton="//*[@id=\"variantCompareView\"]/div[1]/p";


    public String getClearButton() {
        return clearButton;
    }

    public String getCrossButton() {
        return crossButton;
    }

    public String getBuyNowButton() {
        return buyNowButton;
    }

    public String getCompareButton() {
        return compareButton;
    }

    public String getClearAllButton() {
        return clearAllButton;
    }
}
