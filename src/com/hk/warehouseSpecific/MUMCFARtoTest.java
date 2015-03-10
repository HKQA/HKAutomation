package com.hk.warehouseSpecific;

import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.LoginPage;
import com.hk.orderCheckoutDto.SoDetails;
import com.hk.recorder.Browse;
import com.hk.recorder.MultipleVariant;
import com.hk.scaledupOrderPlacement.AppSpecificReusableMethods;
import com.hk.scaledupOrderPlacement.DoRTO;
import com.hk.util.TestUtil;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 3/4/15
 * Time: 4:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class MUMCFARtoTest {

    Browse browse = new Browse();
    MultipleVariant multipleVariant = new MultipleVariant();
    LoginPage loginPage = new LoginPage();
    AppSpecificReusableMethods reusableMethods = new AppSpecificReusableMethods();
    SoDetails soDetails = new SoDetails();
    DoRTO doRTO = new DoRTO();


    @Test(enabled = false)
    public void testMUMCFARto() throws Exception {

        SharedProperties.openBrowser(TestUtil.getURL(), TestUtil.getBrowser());
        String variant = null;
        variant = TestUtil.excel.getCellData("MUM_CFA", "Test Scenario", 18);
        SharedProperties.driver.navigate().to(TestUtil.getURL()+"/sv/oh-yeah!-isolate/SP-29982?navKey=VRNT-"+ variant);
        Thread.sleep(2000);
        SharedProperties.driver.findElement(By.xpath("//*[@value = 'Buy Now']")).click();
        SharedProperties.driver.findElement(By.xpath("//*[@id='cartPop']/div/div/div/div[5]/a")).click();
        browse.proceedToCheckoutMultiVariant();
        SharedProperties.Click(loginPage.getSignInCheckbox(), SharedProperties.driver);
        reusableMethods.setUserCredentials();
        reusableMethods.selectDeliveryAddress();
        reusableMethods.doCODPayment();
        String orderId =   SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[7]/div/div[1]/p[2]")).getText();
        System.out.println(orderId);

        String finalOrderId = orderId.substring(10);
        soDetails.orderIdSoDetails = finalOrderId;
        Thread.sleep(2000);
        DoRTO.gatewayOrderId = finalOrderId;
        doRTO.doRTO();



    }
}
