package com.hk.warehouseSpecific;

import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.LoginPage;
import com.hk.orderCheckoutDto.SoDetails;
import com.hk.property.PropertyHelper;
import com.hk.recorder.Browse;
import com.hk.recorder.MultipleVariant;
import com.hk.scaledupOrderPlacement.AppSpecificReusableMethods;
import com.hk.scaledupOrderPlacement.DoFlip;
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
 * Time: 11:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class MUMFlipSOTest {

    MultipleVariant multipleVariant = new MultipleVariant();
    Browse browse = new Browse();
    LoginPage loginPage = new LoginPage();
    AppSpecificReusableMethods reusableMethods = new AppSpecificReusableMethods();
    SoDetails soDetails = new SoDetails();
    DoFlip doFlip = new DoFlip();


    @BeforeMethod
    public void isSkip()
    {

        if(!(TestUtil.isExecutable("MUM") && TestUtil.excel.getCellData("MUM", "RunMode", 6).equalsIgnoreCase("Y") ) )
        {

            System.out.println("MUM Flip SO test would be skipped");
            throw new SkipException("Skipping the MUM Flip SO test case as RunMode is No");

        }

    }


    @AfterMethod
    public void doAfter(ITestResult result) throws IOException {

        System.out.println("Inside doAfter method having AfterMethod annotation");

        if (result.getStatus() == ITestResult.FAILURE) {
            File screenshot = ((TakesScreenshot) SharedProperties.driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir") + PropertyHelper.readProperty("screenshotFolder") + "\\MUMFlipSOTest.jpg"));
        }


        SharedProperties.driver.quit();
    }



    @Test
    public void testMUMFlipSO() throws Exception {
        int count = 0;
        int rowVal = 8;

        while(count < 2)
        {

            count++;
            SharedProperties.openBrowser(TestUtil.getURL(), TestUtil.getBrowser());
            SharedProperties.driver.navigate().to(TestUtil.getURL() + "/sv/oh-yeah!-isolate/SP-29982?navKey=VRNT-" + TestUtil.excel.getCellData("MUM", "Test Scenario", rowVal));
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
            String orderId =   SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[7]/div/div[1]/p[2]")).getText();
            System.out.println(orderId);
            String finalOrderId = orderId.substring(10);
            soDetails.orderIdSoDetails = finalOrderId;
            doFlip.doFlip();
            SharedProperties.driver.close();


        }


    }

    public static void main(String[] args) throws Exception {
        MUMFlipSOTest test = new MUMFlipSOTest();

        test.testMUMFlipSO();




    }


}
