package com.hk.orderPlacement;

import com.hk.reportAndMailGenerator.SendMail;
import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.*;
import com.hk.excel.TestDetailsExcelService;
import com.hk.excel.dto.TestDetailsDTO;
import com.hk.jdbc.OrderDetailsVerify;
import com.hk.property.PropertyHelper;
import com.hk.util.TestUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
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
 * User: Nitin Kukna
 * Date: 7/9/14
 * Time: 4:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class CouponOnlineOrder extends SharedProperties {
    String baseUrl;
    public String browser;
    LoginPage loginPage = new LoginPage();
    ProductPage productpage = new ProductPage();
    CartPage cartpage = new CartPage();
    AddressPage addresspage = new AddressPage();
    PaymentPage paymentpage = new PaymentPage();
    ITestResult result = Reporter.getCurrentTestResult();

    //@Parameters({"BaseURL", "Browser"})
    /*@BeforeClass
    public void g(String baseUrl, String browser) {
        this.baseUrl = baseUrl;

        this.browser = browser;
    }*/

    @BeforeClass
    public void Config() {
        this.baseUrl = TestUtil.getURL();
        this.browser = TestUtil.getBrowser();
    }

    @BeforeMethod
    public void isSkip() {

        if (!TestUtil.isExecutable("CouponOnlineOrder")) {
            System.out.println("CouponOnlineOrder would be skipped");
            throw new SkipException("Skipping the CouponOnlineOrder test case as RunMode is No");
        }

    }

    @AfterMethod
    public void doAfter(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(PropertyHelper.readProperty("screenshotFolder") + "\\CouponOnlineOrder.jpg"));
        }
    }


    @Parameters("specificVariantIndex")
    @Test(enabled = true)
    public void login(@Optional Long specificVariantIndex) throws InterruptedException, IOException, Exception {
        SharedProperties.openBrowser(baseUrl, browser);
        TestDetailsDTO testDetailsDTO = null;

        try {
            testDetailsDTO = TestDetailsExcelService.getTestDetails();
        } catch (RuntimeException re) {
            System.out.println(re.getMessage());
        }

        if (specificVariantIndex == null) {
            for (Long variantId : testDetailsDTO.getVariantIdList()) {
                //SharedProperties.driver.navigate().to(PropertyHelper.readProperty("url") + variantId);
                SharedProperties.driver.navigate().to(TestUtil.getURL() + PropertyHelper.readProperty("url") + TestUtil.getVariantId());
                Thread.sleep(3000);
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

        if (StringUtils.contains(SharedProperties.driver.findElement(By.xpath(cartpage.IsCouponApplied())).getText(), "Coupon Applied")) {
            SharedProperties.driver.findElement(By.cssSelector("a[href*='removeOffer']")).click();
        }
        //SharedProperties.sendKeys(cartpage.addCouponTextBox(), "HKROCKS", SharedProperties.driver);
        SharedProperties.sendKeys(cartpage.addCouponTextBox(), TestUtil.getCoupon("CouponOnlineOrder"), SharedProperties.driver);
        SharedProperties.Click(cartpage.ClickCouponTextBox(), SharedProperties.driver);
        Thread.sleep(2000);
        SharedProperties.Click(cartpage.CouponProceedToCheckout(), SharedProperties.driver);
        Thread.sleep(3000);
        SharedProperties.Click(loginPage.getSignInCheckbox(), SharedProperties.driver);
        if (SharedProperties.driver.findElements(By.xpath("//*[@id=\"signInForm\"]/input[3]")).size() > 0) {
            SharedProperties.clear(loginPage.getOldEmailIdTextBox(), SharedProperties.driver);
            SharedProperties.sendKeys(loginPage.getOldEmailIdTextBox(), testDetailsDTO.getLoginList(), SharedProperties.driver);
            SharedProperties.sendKeys(loginPage.getPasswordTextBox(), testDetailsDTO.getPasswordList(), SharedProperties.driver);
            SharedProperties.Click(loginPage.getOldSignInBtn(), SharedProperties.driver);
            Thread.sleep(5000);
        } else {
            SharedProperties.clear(loginPage.getEmailIdTextBox(), SharedProperties.driver);
            SharedProperties.sendKeys(loginPage.getEmailIdTextBox(), testDetailsDTO.getLoginList(), SharedProperties.driver);
            SharedProperties.sendKeys(loginPage.getPasswordTextBox(), testDetailsDTO.getPasswordList(), SharedProperties.driver);
            SharedProperties.Click(loginPage.getSignInBtn(), SharedProperties.driver);
            Thread.sleep(5000);
        }
        SharedProperties.Click(addresspage.addressPage(), SharedProperties.driver);
        Thread.sleep(5000);
        SharedProperties.Click(paymentpage.paymentPageDummy(), SharedProperties.driver);
        Thread.sleep(2000);

        new Select(SharedProperties.driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/div[5]/div[2]/div/div[2]/form[1]/div[1]/div/div[5]/select"))).selectByVisibleText("Dummy");
        Thread.sleep(2000);
        SharedProperties.Click(paymentpage.proceedToPayment(), SharedProperties.driver);
        Thread.sleep(2000);
        SharedProperties.Click(paymentpage.paymentY(), SharedProperties.driver);
        Thread.sleep(2000);
        SharedProperties.Click(paymentpage.proceedPayment(), SharedProperties.driver);
        Thread.sleep(5000);

        String orderId = SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[4]/div[1]/p[2]")).getText();
        System.out.println(orderId);
        TestUtil.excel.setCellData("test_suite", "OrderId_Generated", 9, orderId);
        OrderDetailsUtil.flag = true;

        if (OrderDetailsVerify.orderDetails() == true) {
            System.out.print("DB verification Successful");
            OrderDetailsUtil.flag = false;
        } else {
            SendMail.sendmail("DB Verification failed for Coupon online order");
            result.setStatus(ITestResult.FAILURE);
            Thread.sleep(5000);
        }
    }
}