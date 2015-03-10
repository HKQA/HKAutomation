package com.hk.warehouseSpecific;

import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.LoginPage;
import com.hk.orderCheckoutDto.SoDetails;
import com.hk.property.PropertyHelper;
import com.hk.recorder.Browse;
import com.hk.scaledupOrderPlacement.AppSpecificReusableMethods;
import com.hk.scaledupOrderPlacement.DoSplitSO;
import com.hk.util.TestUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 2/18/15
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class MUMSplitSOTest {

    Browse browse = new Browse();
    AppSpecificReusableMethods reusableMethods = new AppSpecificReusableMethods();
    LoginPage loginPage = new LoginPage();
    SoDetails soDetails = new SoDetails();
    DoSplitSO doSplitSO = new DoSplitSO();


    @BeforeMethod
    public void isSkip()
    {

        if(!(TestUtil.isExecutable("MUM") && TestUtil.excel.getCellData("MUM", "RunMode", 11).equalsIgnoreCase("Y") ) )
        {

            System.out.println("MUM Split SO test would be skipped");
            throw new SkipException("Skipping the MUM Split SO test case as RunMode is No");

        }

    }




    @AfterMethod
    public void doAfter(ITestResult result) throws IOException {

        System.out.println("Inside doAfter method having AfterMethod annotation");

        if (result.getStatus() == ITestResult.FAILURE) {
            File screenshot = ((TakesScreenshot) SharedProperties.driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir") + PropertyHelper.readProperty("screenshotFolder") + "\\MUMSplitSOTest.jpg"));
        }


        SharedProperties.driver.quit();
    }


    @Test
    public void testMUMSplitSO() throws Exception {
        SharedProperties.openBrowser(TestUtil.getURL(), TestUtil.getBrowser());
        SharedProperties.driver.navigate().to(TestUtil.getURL() + "/sv/oh-yeah!-isolate/SP-29982?navKey=VRNT-" + TestUtil.excel.getCellData("MUM", "Test Scenario", 13));
        SharedProperties.driver.findElement(By.xpath("//*[@value = 'Buy Now']")).click();
        SharedProperties.driver.navigate().to(TestUtil.getURL() + "/sv/oh-yeah!-isolate/SP-29982?navKey=VRNT-" + TestUtil.excel.getCellData("MUM", "Test Scenario", 14));
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
        String orderId =   SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[7]/div/div[1]/p[2]")).getText();
        System.out.println(orderId);
        String finalOrderId = orderId.substring(10);
        soDetails.orderIdSoDetails = finalOrderId;
        doSplitSO.doSplitSO();




    }

    public static void main(String[] args) throws Exception {
        MUMSplitSOTest test = new MUMSplitSOTest();
        test.testMUMSplitSO();


    }

}
