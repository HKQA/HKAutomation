package com.cfa.automationScripts.orderPlacement;

import com.cfa.genericMethods.UIGenericMethods;
import com.cfa.pageObjects.HomePage;
import com.cfa.pageObjects.LoginPage;
import com.cfa.utilExcelIO.ExcelUtil;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: vikas.dhull
 * Date: 9/17/15
 * Time: 12:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlaceOrderCOD extends UIGenericMethods {

    public void placeOrder() throws IOException {

        LoginPage login = fn_OpenLoginPage(ExcelUtil.getBrowser(),ExcelUtil.getURL());
        HomePage home = login.fn_ValidLogin();
    }


}
