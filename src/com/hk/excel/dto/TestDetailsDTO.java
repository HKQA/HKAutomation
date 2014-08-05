package com.hk.excel.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nitin Kukna
 * Date: 8/1/14
 * Time: 8:18 PM
 */
public class TestDetailsDTO {

    private List<Long> variantIdList = new ArrayList<Long>(100);
    private String loginList;
    private String signUpList;
    private String PasswordList;

    public List<Long> getVariantIdList() {
        return variantIdList;
    }

    public void setVariantIdList(List<Long> variantIdList) {
        this.variantIdList = variantIdList;
    }

    public String getLoginList() {
        return loginList;
    }

    public void setLoginList(String loginList) {
        this.loginList = loginList;
    }

    public String getSignUpList() {
        return signUpList;
    }

    public void setSignUpList(String signUpList) {
        this.signUpList = signUpList;
    }

    public String getPasswordList() {
        return PasswordList;
    }

    public void setPasswordList(String passwordList) {
        PasswordList = passwordList;
    }
}
