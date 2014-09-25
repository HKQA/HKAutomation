package com.hk.orderPlacement; /**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 6/30/14
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */

import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.*;
import com.hk.excel.TestDetailsExcelService;
import com.hk.excel.dto.TestDetailsDTO;
import com.hk.jdbc.OrderDetailsVerify;
import com.hk.orderCheckout.variantCheckout;
import com.hk.property.PropertyHelper;
import com.hk.reportAndMailGenerator.SendMail;
import com.hk.util.TestUtil;
import org.apache.commons.io.FileUtils;
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


public class ExistingOnlineOrder extends SharedProperties {
    String baseUrl;
    public String browser;
    LoginPage loginPage = new LoginPage();
    ProductPage productpage = new ProductPage();
    CartPage cartpage = new CartPage();
    AddressPage addresspage = new AddressPage();
    PaymentPage paymentpage = new PaymentPage();
    ITestResult result = Reporter.getCurrentTestResult();
    variantCheckout varCheckout = new variantCheckout();



    /*ExcelServiceImplOld readexcel = new ExcelServiceImplOld();*/

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
    public void isSkip()
    {

        if(!TestUtil.isExecutable("ExistingOnlineOrder"))
        {

            System.out.println("ExistingOnlineOrder would be skipped");
            throw new SkipException("Skipping the ExistingOnlineOrder test case as RunMode is No");

        }

    }

    @AfterMethod
    public void doAfter(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(PropertyHelper.readProperty("screenshotFolder") + "\\ExistingOnlineOrder.jpg"));
        }
    }

 /*   @DataProvider(name = "CombinedData")
    public Iterator<Object[]> dataProviderCombined() {
        List<Object[]> result = Lists.newArrayList();
        List<String> finalObjectString = new ArrayList<String>();

        try {

            finalObjectString.addAll(readexcel.mainReadFromExcelIterator(PropertyHelper.readProperty("LoginExcel")));
            finalObjectString.addAll(readexcel.mainReadFromExcelIterator(PropertyHelper.readProperty("productIdExcel")));
            result.add(new Object[]{finalObjectString});

        } catch (FileNotFoundException fex) {
            System.out.println(fex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return result.iterator();
    }
      @AfterSuite
    public static void SuiteReport() throws IOException {
        SendMail.sendmail("Please find the attached report of test cases", PropertyHelper.readProperty("screenshotFolder"), PropertyHelper.readProperty("reportFolder") + "report.zip");

    }*/


    /*@Test(enabled = true,groups = {"init"})
    public void TestMethod()
    {

        System.out.println("Inside test method");
        varCheckout.dummyMethod();


    }*/


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
                SharedProperties.driver.navigate().to(TestUtil.getURL()+ PropertyHelper.readProperty("url") + TestUtil.getVariantId());
                Thread.sleep(3000);
                WebElement buyNow = SharedProperties.driver.findElement(By.cssSelector("input[class='addToCart btn btn-red btn2 mrgn-b-5 disp-inln']"));
                buyNow.click();
            }
        } else {
            SharedProperties.driver.navigate().to(PropertyHelper.readProperty("url") + testDetailsDTO.getVariantIdList().get(specificVariantIndex.intValue()));
            WebElement buyNow = SharedProperties.driver.findElement(By.cssSelector("input[class='addToCart btn btn-blue btn2 mrgn-b-5 disp-inln']"));
            buyNow.click();
        }

        /*for (int i = 4; i < dataArray.size(); i++) {
            SharedProperties.driver.navigate().to(PropertyHelper.readProperty("url") + dataArray.get(i));
            WebElement buyNow = SharedProperties.driver.findElement(By.cssSelector("input[class='addToCart btn btn-blue btn2 mrgn-b-5 disp-inln']"));
            buyNow.click();
        }*/


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
        SharedProperties.sendKeys(loginPage.getEmailIdTextBox(), testDetailsDTO.getLoginList(), SharedProperties.driver);
        SharedProperties.sendKeys(loginPage.getPasswordTextBox(), "lklsdk", SharedProperties.driver);
        SharedProperties.Click(loginPage.getSignInBtn(), SharedProperties.driver);
        Thread.sleep(5000);

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

        String orderId =   SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[5]/div[1]/p[2]")).getText();

        System.out.println(orderId);

        TestUtil.excel.setCellData("test_suite","OrderId_Generated",7, orderId );

        OrderDetailsUtil.flag = true;

        varCheckout.variantCheckout();



        /*if (OrderDetailsVerify.orderDetails() == true) {
            System.out.print("DB verification Successful");

            OrderDetailsUtil.flag = false;


        } else {
            System.out.println("DB verification failed but Order ID is generated. So please refer DB");
            SendMail.sendmail("DB Verification failed for Online Order");
            result.setStatus(ITestResult.FAILURE);
            Thread.sleep(5000);
        }*/
    }

}
