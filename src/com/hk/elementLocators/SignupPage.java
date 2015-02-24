package com.hk.elementLocators;

/**
 * Created with IntelliJ IDEA.
 * User: Nitin Kukna
 * Date: 7/4/14
 * Time: 6:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class SignupPage {
    private String signupPage="/html/body/div[2]/div[2]/div[1]/div[4]/h2/span";
    private String name="//*[@id='signUpForm']/input[3]";
    private String emailid="//*[@id='signUpForm']/input[4]";
    private String password="//*[@id='signUpForm']/input[5]";
    private String confirmpassword="//*[@id='signUpForm']/input[6]";
    private String createaccount="//*[@value = 'Create Account']";

    public String signupPage() {
        return signupPage;
    }
    public String name() {
        return name;
    }
    public String emailid() {
        return emailid;
    }
    public String password() {
        return password;
    }
    public String confirmpassword() {
        return confirmpassword;
    }
    public String createaccount() {
        return createaccount;
    }
}
