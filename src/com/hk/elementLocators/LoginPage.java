package com.hk.elementLocators;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 6/30/14
 * Time: 7:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginPage {
    private String emailIdTextBox="//*[@id=\"signInForm\"]/input[3]";
    private String passwordTextBox="//*[@id=\"password\"]";
    private String signInBtn="//*[@id=\"signInForm\"]/input[5]";


    public String getEmailIdTextBox() {
        return emailIdTextBox;
    }

    public String getPasswordTextBox() {
        return passwordTextBox;
    }

    public String getSignInBtn() {
        return signInBtn;
    }
}

