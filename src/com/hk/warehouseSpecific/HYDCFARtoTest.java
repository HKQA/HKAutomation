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
 * Date: 2/19/15
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class HYDCFARtoTest {

    Browse browse = new Browse();
    MultipleVariant multipleVariant = new MultipleVariant();
    LoginPage loginPage = new LoginPage();
    AppSpecificReusableMethods reusableMethods = new AppSpecificReusableMethods();
    SoDetails soDetails = new SoDetails();
    DoRTO doRTO = new DoRTO();


    @Test(enabled = false)
    public void testHYDCFARto() throws Exception {
        SharedProperties.openBrowser(TestUtil.getURL(), TestUtil.getBrowser());
        String variant = null;
        variant = TestUtil.excel.getCellData("HYD_CFA", "Test Scenario", 18);
        SharedProperties.driver.navigate().to(TestUtil.getURL()+"/sv/oh-yeah!-isolate/SP-29982?navKey=VRNT-"+ variant);
        Thread.sleep(2000);
        SharedProperties.driver.findElement(By.xpath("//*[@value = 'Buy Now']")).click();
        SharedProperties.driver.findElement(By.xpath("//*[@id='cartPop']/div/div/div/div[5]/a")).click();
        browse.proceedToCheckoutMultiVariant();
        SharedProperties.Click(loginPage.getSignInCheckbox(), SharedProperties.driver);
        reusableMethods.setUserCredentials();
        //reusableMethods.selectDeliveryAddress();
        SharedProperties.driver.findElement(By.linkText("Edit")).click();
        SharedProperties.driver.findElement(By.xpath("//*[@id = 'pincode']")).clear();
        SharedProperties.driver.findElement(By.xpath("//*[@id = 'pincode']")).sendKeys("500081");
        Thread.sleep(3000);
        SharedProperties.driver.findElement(By.xpath("//*[@name = 'updateAddressForUser']")).click();
        reusableMethods.doCODPayment();
        String orderId =   SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[7]/div/div[1]/p[2]")).getText();
        System.out.println(orderId);
        String finalOrderId = orderId.substring(10);
        soDetails.orderIdSoDetails = finalOrderId;
        Thread.sleep(2000);
        DoRTO.gatewayOrderId = finalOrderId;
        doRTO.doRTO();



    }

    public static void main(String[] args) throws Exception {
        HYDCFARtoTest test = new HYDCFARtoTest();

        test.testHYDCFARto();


    }


}
