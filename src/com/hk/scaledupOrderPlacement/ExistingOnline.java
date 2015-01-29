package com.hk.scaledupOrderPlacement;

import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.LoginPage;
import com.hk.orderCheckoutDto.SoDetails;
import com.hk.property.PropertyHelper;
import com.hk.recorder.Browse;
import com.hk.recorder.MultipleVariant;
import com.hk.recorder.VideoRecorder;
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
    VideoRecorder recorder = new VideoRecorder();

    @BeforeMethod
    public void isSkip()
    {

        if(!TestUtil.isExecutable("ExistingOnline"))
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

        recorder.stopRecording();
        SharedProperties.driver.quit();
    }

    @Test(enabled = true)
    public void test() throws Exception {

        recorder.startRecording();

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

        orderCheckout.variantCheckout();








    }
}
