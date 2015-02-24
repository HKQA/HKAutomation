package com.hk.orderPlacement;



import com.hk.codConfirmation.CodConfirmNavigation;
import com.hk.excel.ExcelServiceImplOld;
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
import org.testng.Assert;
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
 * Date: 7/7/14
 * Time: 6:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class SignupCodOrder extends SharedProperties {
    String baseUrl;
    public String browser;
    /*com.hk.elementLocators.LoginPage loginPage = new com.hk.elementLocators.LoginPage();*/
    SignupPage signupage = new SignupPage();
    ProductPage productpage = new ProductPage();
    CartPage cartpage = new CartPage();
    AddressPage addresspage = new AddressPage();
    PaymentPage paymentpage = new PaymentPage();
    ITestResult result = Reporter.getCurrentTestResult();
    SoDetails soDetails = new SoDetails();
    variantCheckout varCheckout = new variantCheckout();
    CodConfirmNavigation codNavigation = new CodConfirmNavigation();


    //@Parameters({"BaseURL", "Browser"})
    /*@BeforeClass
    public void g(String baseUrl, String browser) {
        this.baseUrl = baseUrl;
        this.browser = browser;


    }*/

    @BeforeClass
    public void config()
    {
        this.baseUrl = TestUtil.getURL();

        this.browser = TestUtil.getBrowser();

    }

    @BeforeMethod
    public void isSkip()
    {

        if(!(TestUtil.isExecutable("SignupCodOrder") && TestUtil.isExecutable("OrderPlacement")))
        {

            System.out.println("SignupCodOrder would be skipped");
            throw new SkipException("Skipping the SignUpCodOrder test case as RunMode is No");

        }

    }

    @AfterMethod
    public void doAfter(ITestResult result) throws IOException {



        if (result.getStatus() == ITestResult.FAILURE) {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir") + PropertyHelper.readProperty("screenshotFolder") + "\\SignupCODFailure.jpg"));
        }

        driver.quit();
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
                //SharedProperties.driver.navigate().to(PropertyHelper.readProperty("url") + variantId );
                SharedProperties.driver.navigate().to(TestUtil.getURL()+ PropertyHelper.readProperty("url") + TestUtil.getVariantId());
                Thread.sleep(5000);
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

        //Code to add more quantity
        //code to redeem reward points
        //code to add coupons

        //SharedProperties.Click(cartpage.getSigninLink(), SharedProperties.driver);
        SharedProperties.mouseHoverAndClick(cartpage.getSignupHover(), cartpage.getSigninLink(), SharedProperties.driver);
        Thread.sleep(4000);
        //SharedProperties.Click(signupage.signupPage(), SharedProperties.driver);
        //Thread.sleep(2000);
        //SharedProperties.sendKeys(signupage.name(), "Test", SharedProperties.driver);
        SharedProperties.sendKeys(signupage.name(), TestUtil.getUserName("SignupCodOrder"), SharedProperties.driver);
        //SharedProperties.sendKeys(signupage.emailid(), testDetailsDTO.getSignUpList(), SharedProperties.driver);
        //SharedProperties.sendKeys(signupage.emailid(), "nitin.kukna+905@healthkart.com", SharedProperties.driver);
        SharedProperties.sendKeys(signupage.emailid(), TestUtil.getEmailId("SignupCodOrder"), SharedProperties.driver);
        //SharedProperties.sendKeys(signupage.password(), "123456", SharedProperties.driver);
        SharedProperties.sendKeys(signupage.password(), TestUtil.getPassword("SignupCodOrder"), SharedProperties.driver);
        //SharedProperties.sendKeys(signupage.confirmpassword(), "123456", SharedProperties.driver);
        SharedProperties.sendKeys(signupage.confirmpassword(), TestUtil.getConfirmPassword("SignupCodOrder"), SharedProperties.driver );
        SharedProperties.Click(signupage.createaccount(), SharedProperties.driver);

        //ExcelServiceImplOld.updateCellContent(System.getProperty("user.dir") + PropertyHelper.readProperty("productIdExcel"), "1", 1, 3);
        Thread.sleep(2000);
        SharedProperties.Click(cartpage.proceedToCheckout(), SharedProperties.driver);
        Thread.sleep(2000);
        //SharedProperties.sendKeys(addresspage.name(), "Test", SharedProperties.driver);
        SharedProperties.sendKeys(addresspage.name(), TestUtil.getAddressName("SignupCodOrder"), SharedProperties.driver);
        //SharedProperties.sendKeys(addresspage.mobile(), "9999999999", SharedProperties.driver);
        SharedProperties.sendKeys(addresspage.mobile(), TestUtil.getMobile_Number("SignupCodOrder"), SharedProperties.driver);
        //SharedProperties.sendKeys(addresspage.address(), "Test", SharedProperties.driver);
        SharedProperties.sendKeys(addresspage.address(), TestUtil.getAddress("SignupCodOrder"), SharedProperties.driver);
        //SharedProperties.sendKeys(addresspage.pincode(), "122001", SharedProperties.driver);
        SharedProperties.sendKeys(addresspage.pincode(), TestUtil.getPincode("SignupCodOrder"), SharedProperties.driver);
        Thread.sleep(2000);
        SharedProperties.Click(addresspage.delivertoaddress(), SharedProperties.driver);
        Thread.sleep(5000);

        SharedProperties.driver.findElement(By.xpath("//*[@class='last']")).click();


        //SharedProperties.Click(paymentpage.cashOnDelivery(), SharedProperties.driver);
        Thread.sleep(5000);
        SharedProperties.Click(paymentpage.payOnDelivery(), SharedProperties.driver);

        String orderId =   SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[6]/div/div[1]/p[2]")).getText();

        System.out.println(orderId);

        String finalOrderId = orderId.substring(10);

        soDetails.orderIdSoDetails = finalOrderId;

        TestUtil.excel.setCellData("test_suite","OrderId_Generated",2, orderId )       ;

        String getText = SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[6]")).getText();

        if(SharedProperties.isElementPresent("/html/body/div[1]/div[2]/div/div[6]") && (getText.contains("your")))
        {
            OrderDetailsUtil.flagLoyalty = true  ;

        }
        else
        {

            OrderDetailsUtil.flagNoLoyalty = true;


        }

        String codStatus = SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[6]/div/div[1]/p[1]/span[2]")).getText();




        //codNavigation.codConfirmNavigation(finalOrderId);



        //varCheckout.variantCheckout();


        Assert.assertTrue(true, "SignupCodOrder is passed");
        if (OrderDetailsVerify.orderDetails()) {
            System.out.print("DB verification Successful");
            if(codStatus.equalsIgnoreCase("Authorization Pending"))
            {
            codNavigation.codConfirmNavigation(finalOrderId);
            }
            Thread.sleep(7000);
            if(TestUtil.getExecuteVariantCheckoutRunMode(2).equalsIgnoreCase("Y"))
            {
            varCheckout.variantCheckout();
            }
            OrderDetailsUtil.flagLoyalty = false  ;
            OrderDetailsUtil.flagNoLoyalty = false;


        } else {
            if(codStatus.equalsIgnoreCase("Authorization Pending"))
            {
            codNavigation.codConfirmNavigation(finalOrderId);
            }
            Thread.sleep(7000);
            if(TestUtil.getExecuteVariantCheckoutRunMode(2).equalsIgnoreCase("Y"))
            {
            varCheckout.variantCheckout();
            }
            Thread.sleep(3000);
            OrderDetailsUtil.flagLoyalty = false;
            OrderDetailsUtil.flagNoLoyalty = false;
            System.out.println("DB verification failed but Order ID is generated. So please refer DB");
            SendMail.sendmail("DB verification failed for Signup COD order");
            result.setStatus(ITestResult.FAILURE);
            Thread.sleep(5000);
        }

    }
}