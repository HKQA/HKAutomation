package com.hk.orderPlacement;

import com.hk.reportAndMailGenerator.SendMail;
import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.*;
import com.hk.excel.ExcelServiceImplOld;
import com.hk.excel.TestDetailsExcelService;
import com.hk.excel.dto.TestDetailsDTO;
import com.hk.jdbc.OrderDetailsVerify;
import com.hk.property.PropertyHelper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Nitin Kukna
 * Date: 7/4/14
 * Time: 5:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class SignupOrderOnline extends SharedProperties {
    String baseUrl;
    public String browser;
    SignupPage signupage = new SignupPage();
    ProductPage productpage = new ProductPage();
    CartPage cartpage = new CartPage();
    AddressPage addresspage = new AddressPage();
    PaymentPage paymentpage = new PaymentPage();
    ITestResult result = Reporter.getCurrentTestResult();

    @Parameters({"BaseURL", "Browser"})
    @BeforeClass
    public void g(String baseUrl, String browser) {
        this.baseUrl = baseUrl;
        this.browser = browser;
    }

    @AfterMethod
    public void doAfter(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(PropertyHelper.readProperty("screenshotFolder") + "\\SignupOrderOnline.jpg"));
        }
    }


    @Parameters("specificVariantIndex")
    @Test(enabled = true)
    public void login(@Optional Long specificVariantIndex) throws InterruptedException, IOException, Exception {
        SharedProperties.openBrowser(baseUrl, browser);

        Thread.sleep(7000);
        TestDetailsDTO testDetailsDTO = null;

        try {
            testDetailsDTO = TestDetailsExcelService.getTestDetails();
        } catch (RuntimeException re) {
            System.out.println(re.getMessage());
        }

        if (specificVariantIndex == null) {
            for (Long variantId : testDetailsDTO.getVariantIdList()) {
                SharedProperties.driver.navigate().to(PropertyHelper.readProperty("url") + variantId);
                WebElement buyNow = SharedProperties.driver.findElement(By.cssSelector("input[class='addToCart btn btn-blue btn2 mrgn-b-5 disp-inln']"));
                buyNow.click();
            }
        }else {
            SharedProperties.driver.navigate().to(PropertyHelper.readProperty("url") + testDetailsDTO.getVariantIdList().get(specificVariantIndex.intValue()));
            WebElement buyNow = SharedProperties.driver.findElement(By.cssSelector("input[class='addToCart btn btn-blue btn2 mrgn-b-5 disp-inln']"));
            buyNow.click();
        }
        SharedProperties.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Wait<WebDriver> wait = new FluentWait<WebDriver>(SharedProperties.driver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(5, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
        WebElement cartLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href*='Cart.action']")));
        cartLink.click();


        //Code to add more quantity
        //code to redeem reward points
        //code to add coupons

        SharedProperties.Click(cartpage.getSigninLink(), SharedProperties.driver);
        Thread.sleep(2000);
        SharedProperties.Click(signupage.signupPage(), SharedProperties.driver);
        SharedProperties.sendKeys(signupage.name(), "Test", SharedProperties.driver);
        SharedProperties.sendKeys(signupage.emailid(), testDetailsDTO.getSignUpList(), SharedProperties.driver);
        SharedProperties.sendKeys(signupage.password(), "123456", SharedProperties.driver);
        SharedProperties.sendKeys(signupage.confirmpassword(), "123456", SharedProperties.driver);
        SharedProperties.Click(signupage.createaccount(), SharedProperties.driver);
        ExcelServiceImplOld.updateCellContent(PropertyHelper.readProperty("productIdExcel"), "1", 1, 3);
        SharedProperties.Click(cartpage.proceedToCheckout(), SharedProperties.driver);
        Thread.sleep(2000);
        SharedProperties.sendKeys(addresspage.name(), "Test", SharedProperties.driver);
        SharedProperties.sendKeys(addresspage.mobile(), "9999999999", SharedProperties.driver);
        SharedProperties.sendKeys(addresspage.address(), "Test", SharedProperties.driver);
        SharedProperties.sendKeys(addresspage.pincode(), "122001", SharedProperties.driver);
        Thread.sleep(2000);
        SharedProperties.Click(addresspage.delivertoaddress(), SharedProperties.driver);
        Thread.sleep(5000);

        WebElement dummypayment = SharedProperties.driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/div[5]/div[2]/div/div[2]/form[1]/div[1]/div/div[4]/input"));
        if (dummypayment == null) {
            SharedProperties.Click(paymentpage.paymentPageDummy(), SharedProperties.driver);
            new Select(SharedProperties.driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/div[5]/div[2]/div/div[2]/form[1]/div[1]/div/div[5]/select"))).selectByVisibleText("Dummy");
            Thread.sleep(2000);
        } else {
            SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[5]/div[2]/div/div[2]/form[1]/div[1]/div/div[2]/input")).click();
        }
        Thread.sleep(2000);
        SharedProperties.Click(paymentpage.proceedToPayment(), SharedProperties.driver);
        Thread.sleep(2000);
        SharedProperties.Click(paymentpage.paymentY(), SharedProperties.driver);
        Thread.sleep(2000);
        SharedProperties.Click(paymentpage.proceedPayment(), SharedProperties.driver);
        if (OrderDetailsVerify.orderDetails() == true) {
            System.out.print("DB verification Successful");
        } else {
            SendMail.sendmail("DB verification failed for Signup online order");
            result.setStatus(ITestResult.FAILURE);
            Thread.sleep(5000);
        }
    }
}
