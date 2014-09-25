package com.hk.brightElementLocators;

import com.hk.commonProperties.SharedProperties;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 8/6/14
 * Time: 11:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class BrightHome {

    private String selectWh="//*[@id=\"selectWHForm\"]/select";
    private String saveBtn="//*[@id=\"selectWHForm\"]/input";
    private  String passWd="//*[@id=\"container\"]/div[5]/div[1]/div[2]/div/form/input[2]";
    private String loginBtn="//*[@id=\"container\"]/div[5]/div[1]/div[2]/div/form/div[3]/input";


    public String getLoginBtn() {
        return loginBtn;
    }


    public String getPassWd() {
        return passWd;
    }


    public String getSelectWh() {
        return selectWh;
    }

    public String getSaveBtn() {
        return saveBtn;
    }
}
