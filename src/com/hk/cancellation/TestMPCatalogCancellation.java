package com.hk.cancellation;

import com.hk.aquaElementLocators.CodConfirmation;
import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.LoginPage;
import com.hk.property.PropertyHelper;
import com.hk.recorder.Browse;
import com.hk.recorder.VideoRecorder;
import com.hk.scaledupOrderPlacement.AppSpecificReusableMethods;
import com.hk.util.TestUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 1/12/15
 * Time: 11:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestMPCatalogCancellation {

    Browse browse = new Browse();
    LoginPage loginPage = new LoginPage();
    AppSpecificReusableMethods reusableMethods = new AppSpecificReusableMethods();
    CodConfirmation codconfirmation = new CodConfirmation();
    FileWriter fw = null;
    VideoRecorder recorder = new VideoRecorder();

    @BeforeMethod
    public void isSkip()
    {

        if(!TestUtil.isExecutable("TestCancellation"))
        {

            System.out.println("Test case would be skipped");
            throw new SkipException("Skipping the test case as RunMode is No");

        }

    }

    @AfterMethod
    public void doAfter(ITestResult result) throws IOException {

        System.out.println("Inside doAfter method having AfterMethod annotation");
        if(SharedProperties.driver.findElement(By.xpath("html/body/div[4]/ul/li")).getText().contains("Opr cancelled successfully"))
        {

            System.out.println("MP Order Cancelled Successfully");
            fw.append("\n"+"Opr cancelled successfully");



        }else
        {

            File screenshot = ((TakesScreenshot) SharedProperties.driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir") + PropertyHelper.readProperty("screenshotFolder") + "\\ExistingOnlineOrder.jpg"));

        }

        recorder.stopRecording();
        fw.close();
        SharedProperties.driver.quit();
    }

    @Test(enabled = false)
    public void testMPCatalogCancellation() throws Exception {

        recorder.startRecording();

        System.out.println("Inside testMPCatalogCancellation method");
        fw = new FileWriter("C:\\Workspace\\Automation_testing_v4_Vipul\\logs\\" + new SimpleDateFormat("ddMMyyyy").format(new Date())+"_MPCatalogCancellation" + ".txt", true);
        fw.append("\n"+"\n"+new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(new Date()) );
        SharedProperties.openBrowser(TestUtil.getURL(), TestUtil.getBrowser());
        String variant = null;
        variant = TestUtil.excel.getCellData("Cancellation", "Variant", 2);
        SharedProperties.driver.navigate().to(TestUtil.getURL()+"/sv/oh-yeah!-isolate/SP-29982?navKey=VRNT-"+ variant);
        Thread.sleep(2000);
        SharedProperties.driver.findElement(By.xpath("//*[@value = 'Buy Now']")).click();
        SharedProperties.driver.findElement(By.xpath("//*[@id='cartPop']/div/div/div/div[5]/a")).click();
        browse.proceedToCheckoutMultiVariant();
        SharedProperties.Click(loginPage.getSignInCheckbox(), SharedProperties.driver);
        reusableMethods.setUserCredentials();
        reusableMethods.selectDeliveryAddress();
        reusableMethods.doCODPayment();
        //reusableMethods.doOnlinePayment();
        Thread.sleep(3000);
        String orderId =   SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[6]/div/div[1]/p[2]")).getText();

        System.out.println(orderId);

        fw.append("\n"+ "Order Id for Market Place Cancellation from catalog = " + orderId);

        String finalOrderId = orderId.substring(10);
        //SharedProperties.openBrowser(TestUtil.getCOD_Confirm_URL(), TestUtil.getBrowser());
        SharedProperties.driver.navigate().to(TestUtil.getCOD_Confirm_URL());
        SharedProperties.sendKeys(codconfirmation.getEmail(), TestUtil.getCOD_Confirm_User(), SharedProperties.driver);
        SharedProperties.sendKeys(codconfirmation.getPassword(), TestUtil.getCOD_Confirm_Password(), SharedProperties.driver);
        SharedProperties.Click(codconfirmation.getLogin(), SharedProperties.driver);
        Thread.sleep(5000);
        SharedProperties.mouseHoverAndClick("//*[@id='cssmenu']/ul/li[6]/a/span", "//*[@id='cssmenu']/ul/li[6]/ul/li[1]/a/span", SharedProperties.driver);
        SharedProperties.driver.findElement(By.xpath("//*[@name = 'gatewayOrderId']")).sendKeys(finalOrderId);
        SharedProperties.driver.findElement(By.xpath("//*[@value = 'Search']")).click();
        Thread.sleep(2000);
        String parentWindowId = SharedProperties.driver.getWindowHandle();
        SharedProperties.driver.findElement(By.xpath("//*[@title = 'Cancel Opr']")).click();
        Thread.sleep(2000);
        Set<String> windowsFetched = SharedProperties.driver.getWindowHandles();
        System.out.println(windowsFetched.size());
        WebElement cancelReason = SharedProperties.driver.findElement(By.xpath("//*[@name = 'orderCancellationTypeId']"));
        Select selectCancelReason = new Select(cancelReason);
        selectCancelReason.selectByIndex(1);
        //WebElement paymentResolutionMode = SharedProperties.driver.findElement(By.xpath("//*[@value = 'paymentResolutionModeId']"));
        //Select selectPaymentResolutionMode = new Select(paymentResolutionMode);
        //selectPaymentResolutionMode.selectByIndex(1);
        Thread.sleep(2000);
        SharedProperties.driver.findElement(By.xpath("//*[@value = 'Cancel Opr']")).click();
        Alert alert = SharedProperties.driver.switchTo().alert();
        alert.accept();
        Thread.sleep(10000);


        /*if(SharedProperties.driver.findElement(By.xpath("html/body/div[4]/ul/li")).getText().contains("Opr cancelled successfully"))

        {

            System.out.println("MP Order Cancelled Successfully");


        } */




    }

    public static void main(String[] args) throws Exception {

        TestMPCatalogCancellation mpCatCancellation = new TestMPCatalogCancellation();

        mpCatCancellation.testMPCatalogCancellation();

    }

}
