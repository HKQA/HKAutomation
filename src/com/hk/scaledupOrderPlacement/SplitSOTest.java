package com.hk.scaledupOrderPlacement;

import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.LoginPage;
import com.hk.orderCheckoutDto.SoDetails;
import com.hk.property.PropertyHelper;
import com.hk.recorder.Browse;
import com.hk.recorder.MultipleVariant;
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
 * Date: 1/7/15
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class SplitSOTest {
    MultipleVariant multipleVariant = new MultipleVariant();
    LoginPage loginPage = new LoginPage();
    AppSpecificReusableMethods reusableMethods = new AppSpecificReusableMethods();
    SoDetails soDetails = new SoDetails();
    DoSplitSO doSplitSO = new DoSplitSO();
    Browse browse = new Browse();

    @BeforeMethod
    public void isSkip()
    {

        if(!TestUtil.isExecutable("SplitSOTest"))
        {

            System.out.println("ExistingOnlineOrder would be skipped");
            throw new SkipException("Skipping the ExistingOnlineOrder test case as RunMode is No");

        }

    }

    @AfterMethod
    public void doAfter(ITestResult result) throws IOException {

        System.out.println("Inside doAfter method having AfterMethod annotation");

        if (result.getStatus() == ITestResult.FAILURE) {
            File screenshot = ((TakesScreenshot) SharedProperties.driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir") + PropertyHelper.readProperty("screenshotFolder") + "\\ExistingOnlineOrder.jpg"));
        }


        SharedProperties.driver.quit();
    }

    @Test(enabled = false)
    public void doSplitSOTest() throws Exception {

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

        TestUtil.excel.setCellData("test_suite","OrderId_Generated",10, orderId );

        doSplitSO.doSplitSO();


    }

    public static void main(String[] args) throws Exception {
           SplitSOTest splitSOTest = new SplitSOTest();

        splitSOTest.doSplitSOTest();


    }
}
