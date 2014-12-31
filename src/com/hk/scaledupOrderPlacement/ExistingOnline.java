package com.hk.scaledupOrderPlacement;

import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.LoginPage;
import com.hk.orderCheckoutDto.SoDetails;
import com.hk.recorder.Browse;
import com.hk.recorder.MultipleVariant;
import com.hk.util.TestUtil;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 12/26/14
 * Time: 4:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExistingOnline {


    MultipleVariant multipleVariant = new MultipleVariant();

    Browse browse = new Browse();

    LoginPage loginPage = new LoginPage();

    SoDetails soDetails = new SoDetails();

    AppSpecificReusableMethods reusableMethods = new AppSpecificReusableMethods();
    MultiVariantOrderCheckout orderCheckout = new MultiVariantOrderCheckout();

    @Test
    public void test() throws Exception {

        SharedProperties.openBrowser(TestUtil.getURL(), TestUtil.getBrowser());

        multipleVariant.testMultipleVariant();

        browse.proceedToCheckoutMultiVariant();

        SharedProperties.Click(loginPage.getSignInCheckbox(), SharedProperties.driver);

        reusableMethods.setUserCredentials();

        reusableMethods.selectDeliveryAddress();

        reusableMethods.doOnlinePayment();

        int lineItemCount= reusableMethods.verifyLineItems();

        System.out.println("Number of line items = " + lineItemCount);

        String orderId =   SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[6]/div/div[1]/p[2]")).getText();

        System.out.println(orderId);

        String finalOrderId = orderId.substring(10);

        soDetails.orderIdSoDetails = finalOrderId;

        orderCheckout.variantCheckout();








    }
}
