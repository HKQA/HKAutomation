package com.hk.orderPlacement;


import com.hk.codConfirmation.CodConfirmNavigation;
import com.hk.orderCheckout.variantCheckout;
import com.hk.orderCheckoutDto.SoDetails;
import com.hk.reportAndMailGenerator.SendMail;
import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.*;
import com.hk.excel.TestDetailsExcelService;
import com.hk.excel.dto.TestDetailsDTO;
import com.hk.jdbc.OrderDetailsVerify;
import com.hk.property.PropertyHelper;
import com.hk.util.TestUtil;
import org.apache.commons.io.FileUtils;
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
public class ExistingCodPlacement extends SharedProperties {

    public String browser;
    String baseUrl;
    LoginPage loginPage = new LoginPage();
    ProductPage productpage = new ProductPage();
    CartPage cartpage = new CartPage();
    AddressPage addresspage = new AddressPage();
    PaymentPage paymentpage = new PaymentPage();
    ITestResult result = Reporter.getCurrentTestResult();
    variantCheckout varCheckout = new variantCheckout();
    SoDetails soDetails = new SoDetails();
    CodConfirmNavigation codNavigation = new CodConfirmNavigation();

    //@Parameters({"BaseURL", "Browser"})
    /*@BeforeClass
    public void g(String baseUrl, String browser) {
        this.baseUrl = baseUrl;
        this.browser = browser;
    }*/

    @BeforeClass
    public void Config()
    {
        this.baseUrl = TestUtil.getURL();
        this.browser = TestUtil.getBrowser();
    }

    @BeforeMethod
    public void isSkip() throws InterruptedException
    {
        if(!(TestUtil.isExecutable("ExistingCodPlacement") && TestUtil.isExecutable("OrderPlacement")))
        {
            System.out.println("ExistingCodPlacement would be skipped");
            throw new SkipException("Skipping the ExistingCodPlacement test case as RunMode is No");
        }
    }

    @AfterMethod
    public void doAfter(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir") + PropertyHelper.readProperty("screenshotFolder") + "\\ExistingCodPlacement.jpg"));
        }

        driver.quit();
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
                //SharedProperties.driver.navigate().to(PropertyHelper.readProperty("url") + variantId);
                //SharedProperties.driver.navigate().to(TestUtil.getURL()+ PropertyHelper.readProperty("url") + TestUtil.getVariantId());
                SharedProperties.driver.navigate().to(TestUtil.getURL()+ PropertyHelper.readProperty("url") + variantId);
                Thread.sleep(3000);
                if(SharedProperties.isElementPresent("//*[@id='variant-page']/div/div/div[2]/div[2]/div[2]/a"))
                {
                    System.out.println("Add to cart button is present");
                    Thread.sleep(10000);
                    SharedProperties.driver.findElement(By.xpath("//*[@id='variant-page']/div/div/div[2]/div[2]/div[2]/a")).click();
                    Thread.sleep(3000);
                }
                else
                {
                WebElement buyNow = SharedProperties.driver.findElement(By.cssSelector("input[class='addToCart btn btn-red btn2 mrgn-b-5 disp-inln']"));
                buyNow.click();
                }
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
        Thread.sleep(3000);
        SharedProperties.Click(cartpage.proceedToCheckout(), SharedProperties.driver);
        Thread.sleep(3000);
        SharedProperties.Click(loginPage.getSignInCheckbox(), SharedProperties.driver);
        SharedProperties.Click(loginPage.getSignInBtn(), SharedProperties.driver);
        Thread.sleep(3000);

        SharedProperties.clear(loginPage.getEmailIdTextBox(), SharedProperties.driver);
        SharedProperties.sendKeys(loginPage.getEmailIdTextBox(), testDetailsDTO.getLoginList(), SharedProperties.driver);
        SharedProperties.clear(loginPage.getPasswordTextBox(), SharedProperties.driver);
        SharedProperties.sendKeys(loginPage.getPasswordTextBox(), testDetailsDTO.getPasswordList(), SharedProperties.driver);
        SharedProperties.Click(loginPage.getSignInBtn(), SharedProperties.driver);
        Thread.sleep(5000);

       /*if (SharedProperties.driver.findElements(By.xpath("//*[@id=\"signInForm\"]/input[3]")).size() > 0) {
            SharedProperties.clear(loginPage.getOldEmailIdTextBox(), SharedProperties.driver);
            SharedProperties.sendKeys(loginPage.getOldEmailIdTextBox(), testDetailsDTO.getLoginList(), SharedProperties.driver);
            SharedProperties.sendKeys(loginPage.getPasswordTextBox(), testDetailsDTO.getPasswordList(), SharedProperties.driver);
            SharedProperties.Click(loginPage.getOldSignInBtn(), SharedProperties.driver);
            Thread.sleep(5000);
        }
        else
        {
            SharedProperties.clear(loginPage.getEmailIdTextBox(), SharedProperties.driver);
            SharedProperties.sendKeys(loginPage.getEmailIdTextBox(), testDetailsDTO.getLoginList(), SharedProperties.driver);
            SharedProperties.sendKeys(loginPage.getPasswordTextBox(), testDetailsDTO.getPasswordList(), SharedProperties.driver);
            SharedProperties.Click(loginPage.getSignInBtn(), SharedProperties.driver);
            Thread.sleep(5000);
        }
          */
        //Code to add more quantity
        //code to redeem reward points
        //code to add coupons

        SharedProperties.Click(cartpage.proceedToCheckout(), SharedProperties.driver);
        Thread.sleep(5000);
        SharedProperties.Click(addresspage.addressPage(), SharedProperties.driver);
        Thread.sleep(5000);
        /*if (SharedProperties.driver.findElement(By.xpath("/*//*[@id=\"nav\"]/li[5]")).getText() == "CASH ON DELIVERY")
        {
           SharedProperties.Click(paymentpage.cashOnDelivery(), SharedProperties.driver);
        }
        else if (SharedProperties.driver.findElement(By.xpath("*//*//**//*[@id=\"nav\"]/li[5]")).getText() == "CASH ON DELIVERY")
        {
            SharedProperties.Click(paymentpage.getCod1stDiv(), SharedProperties.driver);
        }
        else {
            SharedProperties.Click(paymentpage.getCod2ndDiv(), SharedProperties.driver);
        }*/

        //  SharedProperties.driver.findElement(By.xpath("//ul[@id='nav']/li[6]")).click();
        //SharedProperties.Click(paymentpage.cashOnDelivery(), SharedProperties.driver);
        SharedProperties.Click(paymentpage.cashOnDelivery(), SharedProperties.driver);
        Thread.sleep(5000);
        SharedProperties.Click(paymentpage.payOnDelivery(), SharedProperties.driver);

        Thread.sleep(5000);


        String orderId =   SharedProperties.driver.findElement(By.xpath("//p[@class='order-id mrgn-b-5']")).getText();
        System.out.println(orderId);

        String finalOrderId = orderId.substring(10);
        soDetails.orderIdSoDetails = finalOrderId;
        //TestUtil.excel.setCellData("test_suite","OrderId_Generated",8, orderId );
        String getText = SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[7]")).getText();

        if(SharedProperties.isElementPresent("/html/body/div[1]/div[2]/div/div[7]") && getText.contains("your"))
        {
            OrderDetailsUtil.flagLoyalty = true  ;
        }
        else
        {
            OrderDetailsUtil.flagNoLoyalty = true;
        }
        String codStatus = SharedProperties.driver.findElement(By.xpath("html/body/div[1]/div[2]/div/div[6]/div/div[1]/p[1]/span[2]")).getText();
        System.out.println(codStatus);
        Thread.sleep(3000);
        //varCheckout.variantCheckout();

        if (OrderDetailsVerify.orderDetails() == true)
        {
            System.out.print("DB verification Successful");
            Thread.sleep(3000);
            if(codStatus.equalsIgnoreCase("Authorization Pending"))
            {
                codNavigation.codConfirmNavigation(finalOrderId);
            }
            Thread.sleep(5000);
            if(TestUtil.getExecuteVariantCheckoutRunMode(8).equalsIgnoreCase("Y"))
            {
                varCheckout.variantCheckout();
            }
            Thread.sleep(3000);

            OrderDetailsUtil.flagLoyalty = false;
            OrderDetailsUtil.flagNoLoyalty = false;
        } else {
            if(codStatus.equalsIgnoreCase("Authorization Pending"))
            {
                codNavigation.codConfirmNavigation(finalOrderId);
            }
            Thread.sleep(5000);
            if(TestUtil.getExecuteVariantCheckoutRunMode(8).equalsIgnoreCase("Y"))
            {
            varCheckout.variantCheckout();
            }
            Thread.sleep(3000);
            OrderDetailsUtil.flagLoyalty = false;
            OrderDetailsUtil.flagNoLoyalty = false;
            System.out.println("DB verification failed but Order ID is generated. So please refer DB");
            SendMail.sendmail("DB Verification failed for Existing Cod order");
            result.setStatus(ITestResult.FAILURE);
            Thread.sleep(5000);
        }
    }
}
