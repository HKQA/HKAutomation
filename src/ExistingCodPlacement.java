import com.google.common.collect.Lists;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 7/3/14
 * Time: 6:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExistingCodPlacement extends SharedProperties {

    String baseUrl;
    public  String browser;
    LoginPage loginPage = new LoginPage();
    ProductPage productpage = new ProductPage();
    CartPage cartpage = new CartPage();
    AddressPage addresspage = new AddressPage();
    PaymentPage paymentpage = new PaymentPage();
    ExcelServiceImpl readexcel = new ExcelServiceImpl();
    MainPropertyFile mainproperty = new MainPropertyFile();
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


            finalObjectString.addAll(readexcel.mainReadFromExcelIterator(mainproperty.readProperty("LoginExcel")));
            finalObjectString.addAll(readexcel.mainReadFromExcelIterator(mainproperty.readProperty("productIdExcel")));
            result.add(new Object[]{finalObjectString});

            System.out.println("List iterator : "+result.iterator());
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
        openBrowser(baseUrl, browser);
        Thread.sleep(3000);

        for(int i=4;i<dataArray.size();i++){

            driver.navigate().to(mainproperty.readProperty("url")+dataArray.get(i));
            WebElement buyNow = driver.findElement(By.cssSelector("input[class='addToCart btn btn-blue btn2 mrgn-b-5 disp-inln']"));
            buyNow.click();

        }

        //WebElement exp = driver.findElement(By.cssSelector("a[href*='Cart.action']"));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout( 30, TimeUnit.SECONDS )
                .pollingEvery( 5, TimeUnit.SECONDS )
                .ignoring( NoSuchElementException.class, StaleElementReferenceException.class );
        WebElement cartLink = wait.until( ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href*='Cart.action']")));
        cartLink.click();

        Click(cartpage.proceedToCheckout(), driver);
        Click(loginPage.getSignInBtn(),driver);
        Thread.sleep(3000);

        sendKeys(loginPage.getEmailIdTextBox(), dataArray.get(0), driver);
        sendKeys(loginPage.getPasswordTextBox(), dataArray.get(1), driver);
        Click(loginPage.getSignInBtn(), driver);
        Thread.sleep(5000);
        clear(loginPage.getEmailIdTextBox(),driver);

        sendKeys(loginPage.getEmailIdTextBox(), dataArray.get(2), driver);
        sendKeys(loginPage.getPasswordTextBox(), dataArray.get(3), driver);
        Click(loginPage.getSignInBtn(), driver);
        Thread.sleep(5000);

        //Code to add more quantity
        //code to redeem reward points
        //code to add coupons

        Click(cartpage.proceedToCheckout(), driver);
        Thread.sleep(2000);
        Click(addresspage.addressPage(), driver);
        Thread.sleep(5000);
        Click(paymentpage.cashOnDelivery(), driver);
        Thread.sleep(5000);
        Click(paymentpage.payOnDelivery(), driver);




    }


}
