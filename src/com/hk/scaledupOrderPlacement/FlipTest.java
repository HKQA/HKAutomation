package com.hk.scaledupOrderPlacement;

import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.LoginPage;
import com.hk.excel.TestDetailsExcelService;
import com.hk.excel.dto.TestDetailsDTO;
import com.hk.orderCheckoutDto.SoDetails;
import com.hk.property.PropertyHelper;
import com.hk.recorder.Browse;
import com.hk.recorder.MultipleVariant;
import com.hk.recorder.VideoRecorder;
import com.hk.util.TestUtil;
import com.hk.variants.GetVariants;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 1/7/15
 * Time: 11:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class FlipTest {

    List<String> variants = new ArrayList<String>();

    GetVariants getVariants = new GetVariants();
    MultipleVariant multipleVariant = new MultipleVariant();
    Browse browse = new Browse();
    LoginPage loginPage = new LoginPage();
    AppSpecificReusableMethods reusableMethods = new AppSpecificReusableMethods();
    SoDetails soDetails = new SoDetails();
    MultiVariantOrderCheckout orderCheckout = new MultiVariantOrderCheckout();
    DoFlip doFlip = new DoFlip();
    VideoRecorder recorder = new VideoRecorder();




    @BeforeMethod
    public void isSkip()
    {

        if(!TestUtil.isExecutable("FlipTest"))
        {

            System.out.println("TestCase would be skipped");
            throw new SkipException("Skipping the test case as RunMode is No");

        }

    }

    @AfterMethod
    public void doAfter(ITestResult result) throws IOException {

        System.out.println("Inside doAfter method having AfterMethod annotation");

        if (result.getStatus() == ITestResult.FAILURE) {
            File screenshot = ((TakesScreenshot) SharedProperties.driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir") + PropertyHelper.readProperty("screenshotFolder") + "\\FlipTest.jpg"));
        }

        recorder.stopRecording();
        SharedProperties.driver.quit();
    }


    @Test(enabled = true)
    public void doFlipTest() throws Exception {

        recorder.startRecording();

        if(TestUtil.excel.getCellData("Config", 1, 18 ).equalsIgnoreCase("Y"))
        {
            variants = getVariants.getVariants();
            for(String value : variants)
            {
                SharedProperties.openBrowser(TestUtil.getURL(), TestUtil.getBrowser());
                SharedProperties.driver.navigate().to(TestUtil.getURL()+"/sv/oh-yeah!-isolate/SP-29982?navKey=VRNT-"+ value);
                SharedProperties.driver.findElement(By.xpath("//*[@value = 'Buy Now']")).click();
                SharedProperties.driver.findElement(By.xpath("//*[@id='cartPop']/div/div/div/div[5]/a")).click();
                browse.proceedToCheckoutMultiVariant();
                SharedProperties.Click(loginPage.getSignInCheckbox(), SharedProperties.driver);
                reusableMethods.setUserCredentials();
                reusableMethods.selectDeliveryAddress();
                //reusableMethods.doCODPayment();
                //reusableMethods.doOnlinePayment();
                reusableMethods.doDummyPayment();
                int lineItemCount= reusableMethods.verifyLineItems();
                System.out.println("Number of line items = " + lineItemCount);
                String orderId =   SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[6]/div/div[1]/p[2]")).getText();
                System.out.println(orderId);

                String finalOrderId = orderId.substring(10);
                soDetails.orderIdSoDetails = finalOrderId;



                doFlip.doFlip();



                SharedProperties.driver.close();



            }


        } else
        {

            TestDetailsDTO testDetailsDTO = null;

            testDetailsDTO = TestDetailsExcelService.getTestDetails();

            for(Long variantId : testDetailsDTO.getVariantIdList())
            {

                SharedProperties.openBrowser(TestUtil.getURL(), TestUtil.getBrowser());
                SharedProperties.driver.navigate().to(TestUtil.getURL()+"/sv/oh-yeah!-isolate/SP-29982?navKey=VRNT-"+ variantId);
                SharedProperties.driver.findElement(By.xpath("//*[@value = 'Buy Now']")).click();
                SharedProperties.driver.findElement(By.xpath("//*[@id='cartPop']/div/div/div/div[5]/a")).click();
                browse.proceedToCheckoutMultiVariant();
                SharedProperties.Click(loginPage.getSignInCheckbox(), SharedProperties.driver);
                reusableMethods.setUserCredentials();
                reusableMethods.selectDeliveryAddress();
                //reusableMethods.doCODPayment();
                //reusableMethods.doOnlinePayment();
                int lineItemCount= reusableMethods.verifyLineItems();
                System.out.println("Number of line items = " + lineItemCount);
                String orderId =   SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[6]/div/div[1]/p[2]")).getText();
                System.out.println(orderId);
                String finalOrderId = orderId.substring(10);
                soDetails.orderIdSoDetails = finalOrderId;


                doFlip.doFlip();
                //SharedProperties.driver.close();






            }

        }

        //Code that needs to be optimized
        /*variants = getVariants.getVariants();
        for(String values : variants)
        {
        SharedProperties.openBrowser(TestUtil.getURL(), TestUtil.getBrowser());

        if(TestUtil.excel.getCellData("Config", 1, 18 ).equalsIgnoreCase("Y"))
        {

            //variants = getVariants.getVariants();
            //for(String values : variants)


                System.out.println(values);
                SharedProperties.driver.navigate().to(TestUtil.getURL()+"/sv/oh-yeah!-isolate/SP-29982?navKey=VRNT-"+ values);
                Thread.sleep(2000);
                SharedProperties.driver.findElement(By.xpath("//*[@value = 'Buy Now']")).click();
            Thread.sleep(3000);

            SharedProperties.driver.findElement(By.xpath("//*[@id='cartPop']/div/div/div/div[5]/a")).click();



        }
        if(TestUtil.excel.getCellData("Config", 1, 18).equalsIgnoreCase("N"))
        {
            multipleVariant.testMultipleVariant();

        }

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
        //orderCheckout.variantCheckout();

        doFlip.doFlip();






        }*/


    }

    public static void main(String[] args) throws Exception {

        FlipTest flip = new FlipTest();

        flip.doFlipTest();



    }


}
