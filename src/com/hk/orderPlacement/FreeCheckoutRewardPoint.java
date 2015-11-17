package com.hk.orderPlacement;

import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.*;
import com.hk.excel.TestDetailsExcelService;
import com.hk.excel.dto.TestDetailsDTO;
import com.hk.jdbc.OrderDetailsVerify;
import com.hk.orderCheckout.variantCheckout;
import com.hk.orderCheckoutDto.SoDetails;
import com.hk.property.PropertyHelper;
import com.hk.recorder.Browse;
import com.hk.recorder.VideoRecorder;
import com.hk.reportAndMailGenerator.SendMail;
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
 * User: anuj.sharma
 * Date: 10/7/15
 * Time: 5:28 PM
 * To change this template use File | Settings | File Templates.
 */

public class FreeCheckoutRewardPoint extends SharedProperties  {
    String baseUrl;
    public String browser;
    LoginPage loginPage = new LoginPage();
    ProductPage productpage = new ProductPage();
    CartPage cartpage = new CartPage();
    AddressPage addresspage = new AddressPage();
    PaymentPage paymentpage = new PaymentPage();
    ITestResult result = Reporter.getCurrentTestResult();
    variantCheckout varCheckout = new variantCheckout();
    SoDetails soDetails = new SoDetails();
    VideoRecorder recorder = new VideoRecorder();
    Browse browse = new Browse();



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

        if(!(TestUtil.isExecutable("FreeCheckoutRewardPoint") && TestUtil.isExecutable("OrderPlacement")) )
        {

            System.out.println("FreeCheckoutRewardPoint would be skipped");
            throw new SkipException("Skipping the FreeCheckoutRewardPoint test case as RunMode is No");

        }

    }

    @AfterMethod
    public void doAfter(ITestResult result) throws IOException {

        System.out.println("Inside doAfter method having AfterMethod annotation");

        if (result.getStatus() == ITestResult.FAILURE) {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir") + PropertyHelper.readProperty("screenshotFolder") + "\\FreeCheckoutRewardPoint.jpg"));
        }

        recorder.stopRecording();
        driver.quit();
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

    @Parameters("specificVariantIndex")
    @Test(enabled = true)
    public void login(@Optional Long specificVariantIndex) throws InterruptedException, IOException, Exception {

        recorder.startRecording();

        SharedProperties.openBrowser(baseUrl + loginPage.getLoginUrl(), browser);
     //   SharedProperties.openBrowser("http://192.168.70.27:9090/auth/Login.action",browser);
        Thread.sleep(3000);
        TestDetailsDTO testDetailsDTO = null;

        try {
            testDetailsDTO = TestDetailsExcelService.getTestDetails();
        } catch (RuntimeException re) {
            System.out.println(re.getMessage());
        }

        if (specificVariantIndex == null) {
            SharedProperties.clear(loginPage.getOldEmailIdTextBox(),driver);
            SharedProperties.sendKeys(loginPage.getOldEmailIdTextBox(), testDetailsDTO.getLoginList(), SharedProperties.driver);
            SharedProperties.clear(loginPage.getPasswordTextBox(),driver);
            SharedProperties.sendKeys(loginPage.getPasswordTextBox(), testDetailsDTO.getPasswordList(), SharedProperties.driver);
            SharedProperties.Click(loginPage.getOldSignInBtn(), SharedProperties.driver);
            Thread.sleep(5000);
            for (Long variantId : testDetailsDTO.getVariantIdList()) {
                //SharedProperties.driver.navigate().to(PropertyHelper.readProperty("url") + variantId);
                //browse.doBrowsing();
                SharedProperties.driver.navigate().to(TestUtil.getURL()+ PropertyHelper.readProperty("url") + TestUtil.getVariantId());
                //SharedProperties.driver.navigate().to(TestUtil.getURL()+ PropertyHelper.readProperty("url") + variantId);
                Thread.sleep(3000);
                if(SharedProperties.isElementPresent("//*[@id='variant-page']/div/div/div[2]/div[2]/div[2]/a") )
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

        }      else {
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
        //      SharedProperties.Click(cartpage.proceedToCheckout(), SharedProperties.driver);
        //      Thread.sleep(3000);
        /*SharedProperties.Click(loginPage.getSignInCheckbox(), SharedProperties.driver);
        SharedProperties.Click(loginPage.getSignInBtn(), SharedProperties.driver);
        Thread.sleep(3000);
        SharedProperties.sendKeys(loginPage.getEmailIdTextBox(), testDetailsDTO.getLoginList(), SharedProperties.driver);
        SharedProperties.sendKeys(loginPage.getPasswordTextBox(), testDetailsDTO.getPasswordList(), SharedProperties.driver);
        SharedProperties.Click(loginPage.getSignInBtn(), SharedProperties.driver);
        Thread.sleep(5000);
*/
 /*       if (SharedProperties.driver.findElements(By.xpath("/*//*[@id=\"signInForm\"]/input[3]")).size() > 0) {
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

*/
      //  boolean removeRewardPtPresent = SharedProperties.isElementPresent(cartpage.getRemoveRewardPoint());
  //      String reward = SharedProperties.driver.findElement(By.xpath(cartpage.getRemoveRewardPoint())).getText();
  //      System.out.println("rp locator value gettext is: " + reward);
        boolean removeRewardPtPresent = SharedProperties.driver.findElement(By.xpath(cartpage.getRemoveRewardPoint())).getText().equalsIgnoreCase("remove");
        int lengthRemoveRp = SharedProperties.driver.findElement(By.xpath(cartpage.getRemoveRewardPoint())).getText().length();
    //    boolean applyRewardPointPresent = SharedProperties.driver.findElement(By.xpath(cartpage.getApplyRewardPoint())).getText().equalsIgnoreCase("redeem");
        boolean applyRewardPointPresent = SharedProperties.isElementPresent(cartpage.getApplyRewardPoint());
        if (!(removeRewardPtPresent || applyRewardPointPresent))
    //    if(!applyRewardPointPresent)
        {
            System.out.println("User has zero reward points");
            result.setStatus(ITestResult.FAILURE);
        }


        else
        {
            if(removeRewardPtPresent || lengthRemoveRp > 0)
            {
                System.out.println("Removing already applied reward points");
                SharedProperties.Click(cartpage.getRemoveRewardPoint(),SharedProperties.driver);
            }
   //         String x = SharedProperties.driver.findElement(By.xpath(cartpage.getYouPay())).getText();
            SharedProperties.Click(cartpage.getApplyRewardPoint(),SharedProperties.driver);
  //          boolean v = SharedProperties.driver.findElement(By.xpath(cartpage.getYouPay())).getText().equals("0");
            Thread.sleep(2000);
            String a = SharedProperties.driver.findElement(By.xpath(cartpage.getYouPay())).getText();
             Double b = Double.parseDouble(a);


           if(!b.equals(0.0))
            {
                System.out.println("Total payable amount is not zero after applying reward points");
                result.setStatus(ITestResult.FAILURE);

            }
            else
            {
                SharedProperties.Click(cartpage.proceedToCheckout(),SharedProperties.driver);
                SharedProperties.Click(addresspage.addressPage(), SharedProperties.driver);
                Thread.sleep(5000);
//                SharedProperties.driver.findElement(By.xpath("//*[@tab = 'tab3']")).click();
//                Thread.sleep(2000);
                SharedProperties.Click(paymentpage.getConfirmFreeCheckoutButton(), SharedProperties.driver);
                Thread.sleep(2000);

 /*       new Select(SharedProperties.driver.findElement(By.xpath("/*//*[@id='tab3']/div/div[8]/select"))).selectByVisibleText("Dummy");
        Thread.sleep(2000);
        SharedProperties.Click(paymentpage.proceedToPayment(), SharedProperties.driver);
        Thread.sleep(5000);
        SharedProperties.Click(paymentpage.paymentY(), SharedProperties.driver);
        Thread.sleep(5000);
        SharedProperties.Click(paymentpage.proceedPayment(), SharedProperties.driver);

        Thread.sleep(5000);*/

                String orderId =   SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[6]/div/div[1]/p[2]")).getText();

                System.out.println(orderId);

                String finalOrderId = orderId.substring(10);

                soDetails.orderIdSoDetails = finalOrderId;

                TestUtil.excel.setCellData("test_suite","OrderId_Generated",25, orderId );

                String getText = SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[7]")).getText();

                if(SharedProperties.isElementPresent("/html/body/div[1]/div[2]/div/div[6]") && (getText.contains("your")))
                {
                    OrderDetailsUtil.flagLoyalty = true  ;

                }
                else
                {

                    OrderDetailsUtil.flagNoLoyalty = true;


                }

                //OrderDetailsUtil.flag = true;

                Thread.sleep(5000);




                //varCheckout.variantCheckout();

                //Thread.sleep(10000);



                if (OrderDetailsVerify.orderDetails()) {
                    System.out.print("DB verification Successful");
                    //        Thread.sleep(5000);
                    if(TestUtil.getExecuteVariantCheckoutRunMode(25).equalsIgnoreCase("Y"))
                    {
                        varCheckout.variantCheckout();
                    }

                    OrderDetailsUtil.flagLoyalty = false;

                    OrderDetailsUtil.flagNoLoyalty = false;



                    //OrderDetailsUtil.flag = false;


                } else {
/*            Thread.sleep(5000);
            if(TestUtil.getExecuteVariantCheckoutRunMode(7).equalsIgnoreCase("Y"))
            {
                varCheckout.variantCheckout();
            }*/
                    System.out.println("DB verification failed but Order ID is generated. So please refer DB");
                    SendMail.sendmail("DB Verification failed for Free checkout using reward points");
                    result.setStatus(ITestResult.FAILURE);
                    Thread.sleep(5000);
                }
            }
        }
    }


}
