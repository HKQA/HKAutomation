package com.hk.orderPlacement;

import com.google.common.collect.Lists;
import com.hk.commonProperties.SendMail;
import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.*;
import com.hk.excelService.ExcelServiceImpl;
import com.hk.jdbc.OrderDetailsReturn;
import com.hk.property.PropertyHelper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Nitin Kukna
 * Date: 7/9/14
 * Time: 4:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class CouponOnlineOrder extends SharedProperties{
    String baseUrl;
    public String browser;
    LoginPage loginPage = new LoginPage();
    ProductPage productpage = new ProductPage();
    CartPage cartpage = new CartPage();
    AddressPage addresspage = new AddressPage();
    PaymentPage paymentpage = new PaymentPage();
    ExcelServiceImpl readexcel = new ExcelServiceImpl();


    @Parameters({"BaseURL", "Browser"})
    @BeforeClass
    public void g(String baseUrl, String browser) {
        this.baseUrl = baseUrl;
        this.browser = browser;
    }

    @DataProvider(name = "CombinedData")
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


    @Parameters("BaseURL")
    @Test(dataProvider = "CombinedData", enabled = true)
    public void login(List<String> dataArray) throws InterruptedException, IOException, Exception {
        try {


            SharedProperties.openBrowser(baseUrl, browser);

            Thread.sleep(7000);
            for (int i = 4; i < dataArray.size(); i++) {
                SharedProperties.driver.navigate().to(PropertyHelper.readProperty("url") + dataArray.get(i));
                WebElement buyNow = SharedProperties.driver.findElement(By.cssSelector("input[class='addToCart btn btn-blue btn2 mrgn-b-5 disp-inln']"));
                buyNow.click();

            }

            //WebElement exp = driver.findElement(By.cssSelector("a[href*='Cart.action']"));
            SharedProperties.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            Wait<WebDriver> wait = new FluentWait<WebDriver>(SharedProperties.driver)
                    .withTimeout(30, TimeUnit.SECONDS)
                    .pollingEvery(5, TimeUnit.SECONDS)
                    .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
            WebElement cartLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href*='Cart.action']")));
            cartLink.click();

            SharedProperties.Click(cartpage.proceedToCheckout(), SharedProperties.driver);
        /*SharedProperties.Click(loginPage.getSignInBtn(), SharedProperties.driver);*/
            Thread.sleep(3000);

       /* SharedProperties.sendKeys(loginPage.getEmailIdTextBox(), dataArray.get(0), SharedProperties.driver);
        SharedProperties.sendKeys(loginPage.getPasswordTextBox(), dataArray.get(1), SharedProperties.driver);
        SharedProperties.Click(loginPage.getSignInBtn(), SharedProperties.driver);
        Thread.sleep(5000);
        SharedProperties.clear(loginPage.getEmailIdTextBox(), SharedProperties.driver);
*/
            SharedProperties.sendKeys(loginPage.getEmailIdTextBox(), dataArray.get(2), SharedProperties.driver);
            SharedProperties.sendKeys(loginPage.getPasswordTextBox(), dataArray.get(3), SharedProperties.driver);
            SharedProperties.Click(loginPage.getSignInBtn(), SharedProperties.driver);
            Thread.sleep(5000);


            //Code to add more quantity
            //code to redeem reward points
            //code to add coupons
            SharedProperties.sendKeys(cartpage.addCouponTextBox(), "HKROCKS", SharedProperties.driver);
            SharedProperties.Click(cartpage.ClickCouponTextBox(), SharedProperties.driver);
            Thread.sleep(2000);
            SharedProperties.Click(cartpage.CouponProceedToCheckout(), SharedProperties.driver);
            Thread.sleep(2000);
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
            OrderDetailsReturn.orderDetail(OrderDetailsUtil.GatewayOrderId());
            /*SendMail.sendmail(true, PropertyHelper.readProperty("screenshotFolder"));*/


        } catch (Exception e) {
            //Takes the screenshot  when test fails
            File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(PropertyHelper.readProperty("screenshotFolder") + "\\CouponOnlineOrderFailure.jpg"));
            /*SendMail.sendmail(false,PropertyHelper.readProperty("screenshotFolder"));*/
            throw new Exception() ;
        }
    }
}
