package com.hk.scaledupOrderPlacement;

import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.LoginPage;
import com.hk.orderCheckoutDto.SoDetails;
import com.hk.property.PropertyHelper;
import com.hk.recorder.Browse;
import com.hk.recorder.MultipleVariant;
import com.hk.recorder.VideoRecorder;
import com.hk.util.TestUtil;
import com.hk.variants.GetSplitSOVariants;
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
import java.util.ArrayList;
import java.util.List;

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
    GetSplitSOVariants getSplitSOVariants = new GetSplitSOVariants();
    List<String> variants = new ArrayList<String>();
    VideoRecorder recorder = new VideoRecorder();

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
            FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir") + PropertyHelper.readProperty("screenshotFolder") + "\\SplitSOTest.jpg"));
        }

        recorder.stopRecording();

        SharedProperties.driver.quit();
    }

    @Test(enabled = true)
    public void doSplitSOTest() throws Exception {

        recorder.startRecording();

        SharedProperties.openBrowser(TestUtil.getURL(), TestUtil.getBrowser());

        if(TestUtil.excel.getCellData("Config", 1, 18 ).equalsIgnoreCase("Y"))
        {

        variants = getSplitSOVariants.getSplitSOVariants();

        for(String values : variants)
        {

            SharedProperties.driver.navigate().to(TestUtil.getURL()+"/sv/oh-yeah!-isolate/SP-29982?navKey=VRNT-"+ values);
            SharedProperties.driver.findElement(By.xpath("//*[@value = 'Buy Now']")).click();
            SharedProperties.driver.findElement(By.xpath("//*[@id='cartPop']/div/div/div/div[5]/a")).click();


        }
        }
        else
        {

            SharedProperties.driver.navigate().to(TestUtil.getURL()+"/sv/oh-yeah!-isolate/SP-29982?navKey=VRNT-"+ TestUtil.excel.getCellData("SplitSO", "VariantId", 2));
            SharedProperties.driver.findElement(By.xpath("//*[@value = 'Buy Now']")).click();
            SharedProperties.driver.navigate().to(TestUtil.getURL()+"/sv/oh-yeah!-isolate/SP-29982?navKey=VRNT-"+ TestUtil.excel.getCellData("SplitSO", "VariantId", 3));
            SharedProperties.driver.findElement(By.xpath("//*[@value = 'Buy Now']")).click();
            SharedProperties.driver.findElement(By.xpath("//*[@id='cartPop']/div/div/div/div[5]/a")).click();
        }

        //multipleVariant.testMultipleVariant();
        //multipleVariant.testMultipleVariant();

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

        TestUtil.excel.setCellData("test_suite","OrderId_Generated",10, orderId );

        doSplitSO.doSplitSO();


    }

    public static void main(String[] args) throws Exception {
           SplitSOTest splitSOTest = new SplitSOTest();

        splitSOTest.doSplitSOTest();


    }
}
