package com.hk.warehouseSpecific;

import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.LoginPage;
import com.hk.orderCheckoutDto.SoDetails;
import com.hk.recorder.Browse;
import com.hk.scaledupOrderPlacement.AppSpecificReusableMethods;
import com.hk.scaledupOrderPlacement.DoSplitSO;
import com.hk.util.TestUtil;
import org.openqa.selenium.By;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 2/18/15
 * Time: 6:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class GGNSplitSOTest {

    Browse browse = new Browse();
    AppSpecificReusableMethods reusableMethods = new AppSpecificReusableMethods();
    LoginPage loginPage = new LoginPage();
    SoDetails soDetails = new SoDetails();
    DoSplitSO doSplitSO = new DoSplitSO();

    public void testGGNSplitSO() throws Exception {

        SharedProperties.openBrowser(TestUtil.getURL(), TestUtil.getBrowser());
        SharedProperties.driver.navigate().to(TestUtil.getURL() + "/sv/oh-yeah!-isolate/SP-29982?navKey=VRNT-" + TestUtil.excel.getCellData("GGN", "Test Scenario", 13));
        SharedProperties.driver.findElement(By.xpath("//*[@value = 'Buy Now']")).click();
        SharedProperties.driver.navigate().to(TestUtil.getURL() + "/sv/oh-yeah!-isolate/SP-29982?navKey=VRNT-" + TestUtil.excel.getCellData("GGN", "Test Scenario", 14));
        SharedProperties.driver.findElement(By.xpath("//*[@value = 'Buy Now']")).click();
        SharedProperties.driver.findElement(By.xpath("//*[@id='cartPop']/div/div/div/div[5]/a")).click();
        browse.proceedToCheckoutMultiVariant();
        SharedProperties.Click(loginPage.getSignInCheckbox(), SharedProperties.driver);
        reusableMethods.setUserCredentials();
        reusableMethods.selectDeliveryAddress();
        //reusableMethods.doOnlinePayment();
        reusableMethods.doCODPayment();
        int lineItemCount= reusableMethods.verifyLineItems();
        System.out.println("Number of line items = " + lineItemCount);
        String orderId =   SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[6]/div/div[1]/p[2]")).getText();
        System.out.println(orderId);
        String finalOrderId = orderId.substring(10);
        soDetails.orderIdSoDetails = finalOrderId;
        doSplitSO.doSplitSO();




    }

    public static void main(String[] args) throws Exception {
        GGNSplitSOTest test = new GGNSplitSOTest();
        test.testGGNSplitSO();



    }

}
