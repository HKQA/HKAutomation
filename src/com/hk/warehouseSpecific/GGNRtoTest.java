package com.hk.warehouseSpecific;

import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.LoginPage;
import com.hk.orderCheckoutDto.SoDetails;
import com.hk.property.PropertyHelper;
import com.hk.recorder.Browse;
import com.hk.recorder.MultipleVariant;
import com.hk.scaledupOrderPlacement.AppSpecificReusableMethods;
import com.hk.scaledupOrderPlacement.DoRTO;
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
 * Date: 2/16/15
 * Time: 11:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class GGNRtoTest {


    Browse browse = new Browse();
    MultipleVariant multipleVariant = new MultipleVariant();
    LoginPage loginPage = new LoginPage();
    AppSpecificReusableMethods reusableMethods = new AppSpecificReusableMethods();
    SoDetails soDetails = new SoDetails();
    DoRTO doRTO = new DoRTO();


    @AfterMethod
    public void doAfter(ITestResult result) throws IOException {

        System.out.println("Inside doAfter method having AfterMethod annotation");

        if (result.getStatus() == ITestResult.FAILURE) {
            File screenshot = ((TakesScreenshot) SharedProperties.driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir") + PropertyHelper.readProperty("screenshotFolder") + "\\GGNRTOTest.jpg"));
        }


        SharedProperties.driver.quit();
    }


    @BeforeMethod
    public void isSkip()
    {

        if(!(TestUtil.isExecutable("GGN") && TestUtil.excel.getCellData("GGN", "RunMode", 16).equalsIgnoreCase("Y") ) )
        {

            System.out.println("RPU test would be skipped");
            throw new SkipException("Skipping the RPUTest test case as RunMode is No");

        }

    }


    @Test
    public void testGGNRto() throws Exception {

        SharedProperties.openBrowser(TestUtil.getURL(), TestUtil.getBrowser());
        String variant = null;
        variant = TestUtil.excel.getCellData("GGN", "Test Scenario", 18);
        SharedProperties.driver.navigate().to(TestUtil.getURL()+"/sv/oh-yeah!-isolate/SP-29982?navKey=VRNT-"+ variant);
        Thread.sleep(2000);
        SharedProperties.driver.findElement(By.xpath("//*[@value = 'Buy Now']")).click();
        SharedProperties.driver.findElement(By.xpath("//*[@id='cartPop']/div/div/div/div[5]/a")).click();
        browse.proceedToCheckoutMultiVariant();
        SharedProperties.Click(loginPage.getSignInCheckbox(), SharedProperties.driver);
        reusableMethods.setUserCredentials();
        reusableMethods.selectDeliveryAddress();
        reusableMethods.doCODPayment();
        String orderId =   SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[6]/div/div[1]/p[2]")).getText();
        System.out.println(orderId);

        String finalOrderId = orderId.substring(10);
        soDetails.orderIdSoDetails = finalOrderId;
        Thread.sleep(2000);
        DoRTO.gatewayOrderId = finalOrderId;
        doRTO.doRTO();




    }

    public static void main(String[] args) throws Exception {

        GGNRtoTest test = new GGNRtoTest();

        test.testGGNRto();


    }

}
