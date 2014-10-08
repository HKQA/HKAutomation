package com.hk.codConfirmation;

import com.hk.aquaElementLocators.CodConfirmation;
import com.hk.commonProperties.SharedProperties;
import com.hk.util.TestUtil;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 9/29/14
 * Time: 9:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class CodConfirmNavigation extends SharedProperties {

    CodConfirmation codconfirmation = new CodConfirmation();

    public void codConfirmNavigation(String finalOrderId) throws InterruptedException {

        //SharedProperties.openBrowser("http://192.168.70.27:6060/", "chrome");

        //SharedProperties.openBrowser("http://192.168.70.27:6060/admin/search/payment/CodPaymentSearch.action", "chrome");

        SharedProperties.openBrowser(TestUtil.getCOD_Confirm_URL(), TestUtil.getBrowser());

        //SharedProperties.sendKeys(codconfirmation.getEmail(), "nitin.kukna@healthkart.com", SharedProperties.driver);

        SharedProperties.sendKeys(codconfirmation.getEmail(), TestUtil.getCOD_Confirm_User(), SharedProperties.driver);

        //SharedProperties.sendKeys(codconfirmation.getPassword(), "23031988", SharedProperties.driver);

        SharedProperties.sendKeys(codconfirmation.getPassword(), TestUtil.getCOD_Confirm_Password(), SharedProperties.driver);

        SharedProperties.Click(codconfirmation.getLogin(), SharedProperties.driver);

        //Thread.sleep(5000);

        //SharedProperties.mouseHoverAndClick(codconfirmation.getPaymentHover(), codconfirmation.getCodPayment(), SharedProperties.driver);

        Thread.sleep(3000);

        SharedProperties.sendKeys(codconfirmation.getInputOrderId(), finalOrderId, SharedProperties.driver);

        Thread.sleep(3000);

        SharedProperties.Click(codconfirmation.getFilter(), SharedProperties.driver);

        Thread.sleep(3000);

        SharedProperties.Click(codconfirmation.getConfirmOrder(), SharedProperties.driver);



    }
}
