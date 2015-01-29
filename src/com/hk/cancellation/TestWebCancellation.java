package com.hk.cancellation;

import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.LoginPage;
import com.hk.property.PropertyHelper;
import com.hk.recorder.Browse;
import com.hk.scaledupOrderPlacement.AppSpecificReusableMethods;
import com.hk.util.TestUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
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

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 1/8/15
 * Time: 5:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestWebCancellation {

    Browse browse = new Browse();
    LoginPage loginPage = new LoginPage();
    AppSpecificReusableMethods reusableMethods = new AppSpecificReusableMethods();
    FileWriter fw = null;


    @BeforeMethod
    public void isSkip()
    {

        if(!TestUtil.isExecutable("TestCancellation"))
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
            FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir") + PropertyHelper.readProperty("screenshotFolder") + "\\ExistingOnlineOrder.jpg"));
        }


        SharedProperties.driver.quit();
    }

    @Test(enabled = false)
    public void testWebCancellation() throws Exception {

        System.out.println("Inside testWebCancellation method");

        fw = new FileWriter("C:\\Workspace\\Automation_testing_v4_Vipul\\logs\\" + new SimpleDateFormat("ddMMyyyy").format(new Date())+"_WebCancellation" + ".txt", true);
        fw.append("\n"+"\n"+new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(new Date()) );

        SharedProperties.openBrowser(TestUtil.getURL(), TestUtil.getBrowser());

        String variant = null;

        variant = TestUtil.excel.getCellData("Cancellation", "Variant", 3);

        //SharedProperties.driver.navigate().to("http://182.74.46.183:9090/sv/oh-yeah!-isolate/SP-29982?navKey=VRNT-"+ variant);
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
        //System.out.println("Number of line items = " + lineItemCount);

        String orderId =   SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[6]/div/div[1]/p[2]")).getText();

        System.out.println(orderId);

        fw.append("\n"+ "Order Id for Web Cancellation = " + orderId);

        String finalOrderId = orderId.substring(10);
        //SharedProperties.driver.navigate().to(TestUtil.getURL()+ "/account/MyAccountOrderHistory.action");
        SharedProperties.driver.findElement(By.xpath("//*[@alt = 'HealthKart']")).click();
        Thread.sleep(2000);
        SharedProperties.mouseHoverAndClick("//*[@id='header']/div/div[2]/div[3]/div/div/span", "//*[@id='header']/div/div[2]/div[3]/div/ul/li[2]/a", SharedProperties.driver);
        Thread.sleep(2000);
        //SharedProperties.sendKeys(loginPage.getOldEmailIdTextBox(), "vipul.j@healthkart.com", SharedProperties.driver);
        //SharedProperties.sendKeys(loginPage.getPasswordTextBox(), "Vipul.jain", SharedProperties.driver);
        //SharedProperties.Click("//*[@value = 'Log in']", SharedProperties.driver);


        if(SharedProperties.driver.findElement(By.xpath("//*[@class = 'order-detail-hdr']")).getText().contains(finalOrderId))
        {
            System.out.println("Inside if condition");
            SharedProperties.driver.findElement(By.xpath("//*[@data-orderid = '"+finalOrderId+"']")).click();

        }

        Thread.sleep(3000);

        WebElement cancelReason = SharedProperties.driver.findElement(By.xpath("//*[@name = 'reason']"));
        Select selectReason = new Select(cancelReason);
        selectReason.selectByIndex(2);
        //WebElement refundMode = SharedProperties.driver.findElement(By.xpath("//*[@class = 'js-refund-type fnt-sz14 full-width']"));
        //Select selectRefundMode = new Select(refundMode);
        //selectRefundMode.selectByVisibleText("Reward Points");

        SharedProperties.driver.findElement(By.xpath("//*[@name = 'cancel']")).click();
        Thread.sleep(5000);
        System.out.println("Order id = "+finalOrderId+ "is successfully cancelled ");
        fw.append("\n"+ "Order id = "+finalOrderId+ " is successfully cancelled ");
        fw.close();


















    }

    public static void main(String[] args) throws Exception {
          TestWebCancellation mp = new TestWebCancellation();
          mp.testWebCancellation();



    }
}
