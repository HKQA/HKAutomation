package com.hk.orderPlacement;


import com.google.common.collect.Lists;
import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.*;
import com.hk.excelService.ExcelServiceImpl;
import com.hk.jdbc.JdbcConnectionFile;
import com.hk.jdbc.ResultSetExtractor;
import com.hk.property.PropertyHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
            finalObjectString.addAll(readexcel.mainReadFromExcelIterator(PropertyHelper.readProperty("orderDetails")));
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
    public void login(List<String> dataArray) throws InterruptedException, IOException {
        SharedProperties.openBrowser(baseUrl, browser);
        Thread.sleep(3000);


        String email = (String) JdbcConnectionFile.readJdbcprop("select p.contact_name,p.email,p.base_order_id from payment p where'" + dataArray.get(5) + "'",(String)JdbcConnectionFile.readJdbcprop(""), new ResultSetExtractor<Object>() {
            String email = null;

            @Override
            public Object extract(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    email = rs.getString("email");
                }

                return email;
            }
        });

        System.out.print("email: " + email);

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
        SharedProperties.Click(loginPage.getSignInBtn(), SharedProperties.driver);
        Thread.sleep(3000);

        SharedProperties.sendKeys(loginPage.getEmailIdTextBox(), dataArray.get(0), SharedProperties.driver);
        SharedProperties.sendKeys(loginPage.getPasswordTextBox(), dataArray.get(1), SharedProperties.driver);
        SharedProperties.Click(loginPage.getSignInBtn(), SharedProperties.driver);
        Thread.sleep(5000);
        SharedProperties.clear(loginPage.getEmailIdTextBox(), SharedProperties.driver);

        SharedProperties.sendKeys(loginPage.getEmailIdTextBox(), dataArray.get(2), SharedProperties.driver);
        SharedProperties.sendKeys(loginPage.getPasswordTextBox(), dataArray.get(3), SharedProperties.driver);
        SharedProperties.Click(loginPage.getSignInBtn(), SharedProperties.driver);
        Thread.sleep(5000);

        //Code to add more quantity
        //code to redeem reward points
        //code to add coupons

        SharedProperties.Click(cartpage.proceedToCheckout(), SharedProperties.driver);
        Thread.sleep(2000);
        SharedProperties.Click(addresspage.addressPage(), SharedProperties.driver);
        Thread.sleep(5000);
        SharedProperties.Click(paymentpage.cashOnDelivery(), SharedProperties.driver);
        Thread.sleep(5000);
        SharedProperties.Click(paymentpage.payOnDelivery(), SharedProperties.driver);


    }


}
