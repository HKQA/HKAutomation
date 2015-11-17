package com.hk.elementLocators;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 6/30/14
 * Time: 7:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginPage {
    private String guestEmailIdTextBox = "//*[@id=\"guestSignInForm\"]/input[3]";
    private String emailIdTextBox = "//*[@id=\"loginForm\"]/input[3]";
    //private String emailIdTextBox = "//*[@name = 'email']";
    private String oldEmailIdTextBox = "//*[@id=\"signInForm\"]/input[4]";
    /*private String passwordTextBox="/*//*[@id=\"loginForm\"]/input[5]";*/
    private String passwordTextBox = "//*[@id=\"password\"]";
    //private String passwordTextBox = "//*[@name = 'password']";
    private String guestSigninBtn = "//*[@id=\"guestSignInForm\"]/input[4]";
    private String signInBtn = "//*[@id=\"loginForm\"]/input[5]";
    private String oldSignInBtn = "//*[@id=\"signInForm\"]/input[6]";
    private String signInCheckbox = "//*[@id=\"guestSignInForm\"]/label[2]/input";
    private String loginUrl = "auth/Login.action";


    public String getEmailIdTextBox() {
        return emailIdTextBox;
    }

    public String getPasswordTextBox() {
        return passwordTextBox;
    }

    public String getGuestEmailIdTextBox() {
        return guestEmailIdTextBox;
    }

    public String getSignInBtn() {
        return signInBtn;
    }

    public String getSignInCheckbox() {
        return signInCheckbox;
    }

    public String getOldEmailIdTextBox() {
        return oldEmailIdTextBox;
    }

    public String getOldSignInBtn() {
        return oldSignInBtn;
    }
    public String getGuestSigninBtn() {
        return guestSigninBtn;
    }
    public String getLoginUrl() {
        return loginUrl;
    }

}

