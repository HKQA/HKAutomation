package com.hk.orderPlacement;


import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.*;
import com.hk.excel.TestDetailsExcelService;
import com.hk.excel.dto.TestDetailsDTO;
import com.hk.jdbc.OrderDetailsVerify;
import com.hk.property.PropertyHelper;
import com.hk.reportAndMailGenerator.SendMail;
import com.hk.util.TestUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 7/3/14
 * Time: 6:14 PM         -
 * To change this template use File | Settings | File Templates.
 */
public class GuestCheckoutCod extends SharedProperties {

    public String browser;
    String baseUrl;
    LoginPage loginPage = new LoginPage();
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

    @BeforeMethod
    public void isSkip()
    {

        if(!TestUtil.isExecutable("GuestCheckoutCod"))
        {

            System.out.println("GuestCheckoutCod would be skipped");
            throw new SkipException("Skipping the GuestCheckOutCod test case as RunMode is No");

        }

    }

    @AfterMethod
    public void doAfter(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(PropertyHelper.readProperty("screenshotFolder") + "\\GuestCheckoutCod.jpg"));
        }
    }


    @Parameters("specificVariantIndex")
    @Test(enabled = true)
    public void login(@Optional Long specificVariantIndex) throws InterruptedException, IOException, Exception {
        SharedProperties.openBrowser(baseUrl, browser);
        Thread.sleep(3000);
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
        } else {
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
        Thread.sleep(3000);
        SharedProperties.Click(cartpage.proceedToCheckout(), SharedProperties.driver);
        Thread.sleep(3000);
        SharedProperties.sendKeys(loginPage.getGuestEmailIdTextBox(), testDetailsDTO.getLoginList(), SharedProperties.driver);
        SharedProperties.Click(loginPage.getGuestSigninBtn(), SharedProperties.driver);
        Thread.sleep(5000);

        //Code to add more quantity
        //code to redeem reward points
        //code to add coupons

        SharedProperties.sendKeys(addresspage.name(), "Test", SharedProperties.driver);
        SharedProperties.sendKeys(addresspage.mobile(), "9999999999", SharedProperties.driver);
        SharedProperties.sendKeys(addresspage.address(), "Test", SharedProperties.driver);
        SharedProperties.sendKeys(addresspage.pincode(), "122001", SharedProperties.driver);
        Thread.sleep(2000);
        SharedProperties.Click(addresspage.delivertoaddress(), SharedProperties.driver);
        Thread.sleep(5000);
        if (StringUtils.equals(SharedProperties.driver.findElement(By.xpath("//*[@id=\"nav\"]/li[5]")).getText(), "CASH ON DELIVERY")) {
            SharedProperties.Click(paymentpage.cashOnDelivery(), SharedProperties.driver);
        } else if (StringUtils.equals(SharedProperties.driver.findElement(By.xpath("//*[@id=\"nav\"]/li[6]")).getText(), "CASH ON DELIVERY")) {
            SharedProperties.Click(paymentpage.getCod1stDiv(), SharedProperties.driver);
        } else {
            SharedProperties.Click(paymentpage.getCod2ndDiv(), SharedProperties.driver);
        }
        Thread.sleep(5000);
        SharedProperties.Click(paymentpage.payOnDelivery(), SharedProperties.driver);

        if (OrderDetailsVerify.orderDetails() == true) {
            System.out.print("DB verification Successful");
        } else {
            SendMail.sendmail("DB Verification failed for Existing Cod order");
            result.setStatus(ITestResult.FAILURE);
            Thread.sleep(5000);
        }
    }
}
