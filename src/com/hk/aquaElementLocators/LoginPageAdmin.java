package com.hk.aquaElementLocators;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 8/5/14
 * Time: 11:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginPageAdmin {
    private String userName=".//*[@id='loginName']";
    private String password="/html/body/div[3]/div[1]/div[2]/div/form/input[2]";
    private String loginbtn="/html/body/div[3]/div[1]/div[2]/div/form/div[3]/input";

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getLoginbtn() {
        return loginbtn;
    }
}
