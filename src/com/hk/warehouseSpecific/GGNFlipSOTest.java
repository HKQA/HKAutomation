package com.hk.warehouseSpecific;

import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.LoginPage;
import com.hk.orderCheckoutDto.SoDetails;
import com.hk.recorder.Browse;
import com.hk.recorder.MultipleVariant;
import com.hk.scaledupOrderPlacement.AppSpecificReusableMethods;
import com.hk.scaledupOrderPlacement.DoFlip;
import com.hk.util.TestUtil;
import org.openqa.selenium.By;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 2/16/15
 * Time: 5:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class GGNFlipSOTest {

    MultipleVariant multipleVariant = new MultipleVariant();
    Browse browse = new Browse();
    LoginPage loginPage = new LoginPage();
    AppSpecificReusableMethods reusableMethods = new AppSpecificReusableMethods();
    SoDetails soDetails = new SoDetails();
    DoFlip doFlip = new DoFlip();

    public void testGGNFlipSO() throws Exception {
        int count = 0;
        int rowVal = 8;


        while (count < 2)
        {
            count++;
            SharedProperties.openBrowser(TestUtil.getURL(), TestUtil.getBrowser());
            SharedProperties.driver.navigate().to(TestUtil.getURL() + "/sv/oh-yeah!-isolate/SP-29982?navKey=VRNT-" + TestUtil.excel.getCellData("GGN", "Test Scenario", rowVal));
            SharedProperties.driver.findElement(By.xpath("//*[@value = 'Buy Now']")).click();
            SharedProperties.driver.findElement(By.xpath("//*[@id='cartPop']/div/div/div/div[5]/a")).click();
            rowVal++;
            browse.proceedToCheckoutMultiVariant();
            SharedProperties.Click(loginPage.getSignInCheckbox(), SharedProperties.driver);
            reusableMethods.setUserCredentials();
            reusableMethods.selectDeliveryAddress();
            reusableMethods.doCODPayment();
            int lineItemCount= reusableMethods.verifyLineItems();
            System.out.println("Number of line items = " + lineItemCount);
            String orderId =   SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[6]/div/div[1]/p[2]")).getText();
            System.out.println(orderId);
            String finalOrderId = orderId.substring(10);
            soDetails.orderIdSoDetails = finalOrderId;
            doFlip.doFlip();
            SharedProperties.driver.close();
        }



    }


    public static void main(String[] args) throws Exception {

        GGNFlipSOTest test = new GGNFlipSOTest();
        test.testGGNFlipSO();


    }



}
