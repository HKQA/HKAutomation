package com.hk.elementLocators;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 7/2/14
 * Time: 3:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddressPage {
    private String addressPage="html/body/div[1]/div[2]/div/div[4]/div/div[1]/div/div[3]/a";
    private String name="//*[@id=\"user-name\"]";
    private String mobile="//*[@id=\"contactNo\"]";
    private String address="//*[@id=\"line1\"]";
    private String pincode="//*[@id=\"pincode\"]";
    private String delivertoaddress="//*[@id=\"addAddressForUser\"]";
    public String addressPage() {
        return addressPage;
    }
    public String name() {
        return name;
    }
    public String mobile() {
        return mobile;
    }
    public String address() {
        return address;
    }
    public String pincode() {
        return pincode;
    }
    public String delivertoaddress() {
        return delivertoaddress;
    }

}
