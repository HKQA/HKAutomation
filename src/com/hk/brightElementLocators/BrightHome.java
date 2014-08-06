package com.hk.brightElementLocators;

import com.hk.commonProperties.SharedProperties;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 8/6/14
 * Time: 11:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class BrightHome extends SharedProperties {

    private String selectWh="//*[@id=\"selectWHForm\"]/select";
    private String saveBtn="//*[@id=\"selectWHForm\"]/input";

    public String getSelectWh() {
        return selectWh;
    }

    public String getSaveBtn() {
        return saveBtn;
    }
}
