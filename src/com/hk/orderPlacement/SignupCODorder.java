package com.hk.orderPlacement;

import com.google.common.collect.Lists;
import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.*;
import com.hk.excelService.ExcelServiceImpl;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
    public  String browser;
    /*com.hk.elementLocators.LoginPage loginPage = new com.hk.elementLocators.LoginPage();*/
    SignupPage signupage = new SignupPage();
    ProductPage productpage = new ProductPage();
    CartPage cartpage = new CartPage();
    AddressPage addresspage = new AddressPage();
    PaymentPage paymentpage = new PaymentPage();
    ExcelServiceImpl readexcel =new ExcelServiceImpl();
    //com.hk.property.PropertyHelper mainproperty = new com.hk.property.PropertyHelper();


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

        try{


            finalObjectString.addAll(readexcel.mainReadFromExcelIterator(PropertyHelper.readProperty("SignUpExcel")));
            finalObjectString.addAll(readexcel.mainReadFromExcelIterator(PropertyHelper.readProperty("productIdExcel")));
            result.add(new Object[]{finalObjectString});

        }
        catch(FileNotFoundException fex){
            System.out.println(fex.getMessage());
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        return result.iterator();
    }





    @Parameters("BaseURL")
    @Test(dataProvider = "CombinedData", enabled = true)
    public void login(List<String> dataArray)  throws InterruptedException, IOException {
        SharedProperties.openBrowser(baseUrl, browser);

        Thread.sleep(7000);

        /*Click(loginPage.getSignInBtn(), "Create an account button", "Sign in page", driver);
        Thread.sleep(3000);

        sendKeys(loginPage.getEmailIdTextBox(), "Login page", "Enter username", dataArray.get(3), driver);
        sendKeys(loginPage.getPasswordTextBox(), "Login page", "Enter password", dataArray.get(2), driver);
        Click(loginPage.getSignInBtn(), "Login page", "Sign in Button", driver);
        sendKeys(loginPage.getEmailIdTextBox(), "Login page", "Enter username", dataArray.get(1), driver);
        sendKeys(loginPage.getPasswordTextBox(), "Login page", "Enter password", dataArray.get(1), driver);
        Click(loginPage.getSignInBtn(), "Login page", "Sign in page", driver);
*/

        for(int i=4;i<dataArray.size();i++){
            SharedProperties.driver.navigate().to(PropertyHelper.readProperty("url")+dataArray.get(i));
            WebElement buyNow = SharedProperties.driver.findElement(By.cssSelector("input[class='addToCart btn btn-blue btn2 mrgn-b-5 disp-inln']"));
            buyNow.click();

        }

        //WebElement exp = driver.findElement(By.cssSelector("a[href*='Cart.action']"));
        SharedProperties.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Wait<WebDriver> wait = new FluentWait<WebDriver>(SharedProperties.driver)
                .withTimeout( 30, TimeUnit.SECONDS )
                .pollingEvery( 5, TimeUnit.SECONDS )
                .ignoring( NoSuchElementException.class, StaleElementReferenceException.class );
        WebElement cartLink = wait.until( ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href*='Cart.action']")));
        cartLink.click();



        //Code to add more quantity
        //code to redeem reward points
        //code to add coupons

        SharedProperties.Click(cartpage.proceedToCheckout(), SharedProperties.driver);
        Thread.sleep(2000);
        SharedProperties.Click(signupage.signupPage(), SharedProperties.driver);
        SharedProperties.sendKeys(signupage.name(), dataArray.get(0), SharedProperties.driver);
        SharedProperties.sendKeys(signupage.emailid(), dataArray.get(1), SharedProperties.driver);
        SharedProperties.sendKeys(signupage.password(), dataArray.get(2), SharedProperties.driver);
        SharedProperties.sendKeys(signupage.confirmpassword(), dataArray.get(3), SharedProperties.driver);
        SharedProperties.Click(signupage.createaccount(), SharedProperties.driver);
        ExcelServiceImpl.updateCellContent(PropertyHelper.readProperty("SignUpExcel"), "1", 0, 1);

        Thread.sleep(2000);
        SharedProperties.Click(cartpage.proceedToCheckout(), SharedProperties.driver);
        Thread.sleep(2000);
        SharedProperties.sendKeys(addresspage.name(), "Nitin", SharedProperties.driver);
        SharedProperties.sendKeys(addresspage.mobile(), "9999999999", SharedProperties.driver);
        SharedProperties.sendKeys(addresspage.address(), "Test", SharedProperties.driver);
        SharedProperties.sendKeys(addresspage.pincode(), "122001", SharedProperties.driver);
        Thread.sleep(2000);
        SharedProperties.Click(addresspage.delivertoaddress(), SharedProperties.driver);
        Thread.sleep(5000);


        SharedProperties.Click(paymentpage.cashOnDelivery(), SharedProperties.driver);
        Thread.sleep(5000);
        SharedProperties.Click(paymentpage.payOnDelivery(), SharedProperties.driver);
    }
}