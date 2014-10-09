package com.hk.codConfirmation;

import com.hk.aquaElementLocators.CodConfirmation;
import com.hk.commonProperties.SharedProperties;
import com.hk.util.TestUtil;
import org.openqa.selenium.Alert;

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

        SharedProperties.openBrowser(TestUtil.getCOD_Confirm_URL(), TestUtil.getBrowser());
        SharedProperties.sendKeys(codconfirmation.getEmail(), TestUtil.getCOD_Confirm_User(), SharedProperties.driver);
        SharedProperties.sendKeys(codconfirmation.getPassword(), TestUtil.getCOD_Confirm_Password(), SharedProperties.driver);
        SharedProperties.Click(codconfirmation.getLogin(), SharedProperties.driver);
        Thread.sleep(3000);
        SharedProperties.sendKeys(codconfirmation.getInputOrderId(), finalOrderId, SharedProperties.driver);
        Thread.sleep(3000);
        SharedProperties.Click(codconfirmation.getFilter(), SharedProperties.driver);
        Thread.sleep(3000);
        SharedProperties.Click(codconfirmation.getConfirmOrder(), SharedProperties.driver);
        Thread.sleep(3000);
        Alert alert = SharedProperties.driver.switchTo().alert();
        Thread.sleep(3000);
        alert.accept();
    }
}
