/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 6/30/14
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */

import java.io.IOException;
import java.io.FileNotFoundException;

import com.google.common.collect.Lists;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.*;
import org.openqa.selenium.support.ui.Select;

import java.util.*;
import java.util.concurrent.TimeUnit;


public class ExistingOnlineOrder extends SharedProperties {
    String baseUrl;
    public  String browser;
    LoginPage loginPage = new LoginPage();
    ProductPage productpage = new ProductPage();
    CartPage cartpage = new CartPage();
    AddressPage addresspage = new AddressPage();
    PaymentPage paymentpage = new PaymentPage();
    ExcelServiceImpl readexcel =new ExcelServiceImpl();


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


            finalObjectString.addAll(readexcel.mainReadFromExcelIterator(MainPropertyFile.readProperty("LoginExcel")));
            finalObjectString.addAll(readexcel.mainReadFromExcelIterator(MainPropertyFile.readProperty("productIdExcel")));
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

        Thread.sleep(7000);
for(int i=4;i<dataArray.size();i++){
            driver.navigate().to(MainPropertyFile.readProperty("url")+dataArray.get(i));
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
        Click(loginPage.getSignInBtn(), driver);
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
        Click(paymentpage.paymentPageDummy(), driver);
        Thread.sleep(2000);

        new Select(driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/div[5]/div[2]/div/div[2]/form[1]/div[1]/div/div[5]/select"))).selectByVisibleText("Dummy");
        Thread.sleep(2000);
        Click(paymentpage.proceedToPayment(), driver);
        Thread.sleep(2000);
        Click(paymentpage.paymentY(), driver);
        Thread.sleep(2000);
        Click(paymentpage.proceedPayment(), driver);


    }


}
