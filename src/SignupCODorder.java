import com.google.common.collect.Lists;
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
public class SignupCODorder extends SharedProperties{
    String baseUrl;
    public  String browser;
    /*LoginPage loginPage = new LoginPage();*/
    SignupPage signupage = new SignupPage();
    ProductPage productpage = new ProductPage();
    CartPage cartpage = new CartPage();
    AddressPage addresspage = new AddressPage();
    PaymentPage paymentpage = new PaymentPage();
    ExcelServiceImpl readexcel =new ExcelServiceImpl();
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


            finalObjectString.addAll(readexcel.mainReadFromExcelIterator(mainproperty.readPropertySignUpExcelPath()));
            finalObjectString.addAll(readexcel.mainReadFromExcelIterator(mainproperty.readPropertyProductIdExcelPath()));
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
            driver.navigate().to(mainproperty.readProperty()+dataArray.get(i));
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



        //Code to add more quantity
        //code to redeem reward points
        //code to add coupons

        Click(cartpage.proceedToCheckout(), driver);
        Thread.sleep(2000);
        Click(signupage.signupPage(),  driver);
        sendKeys(signupage.name(), dataArray.get(0), driver);
        sendKeys(signupage.emailid(),  dataArray.get(1), driver);
        sendKeys(signupage.password(),  dataArray.get(2), driver);
        sendKeys(signupage.confirmpassword(),  dataArray.get(3), driver);
        Click(signupage.createaccount(),  driver);
        ExcelServiceImpl.updateCellContent(mainproperty.readPropertySignUpExcelPath(),"1",0,1);

        Thread.sleep(2000);
        Click(cartpage.proceedToCheckout(),  driver);
        Thread.sleep(2000);
        sendKeys(addresspage.name(),"Nitin" , driver);
        sendKeys(addresspage.mobile(),  "9999999999" , driver);
        sendKeys(addresspage.address(),  "Test" , driver);
        sendKeys(addresspage.pincode(),  "122001" , driver);
        Thread.sleep(2000);
        Click(addresspage.delivertoaddress(),  driver);
        Thread.sleep(5000);


       /* private boolean isElementPresent(By by) {
            try {
                WebElement wb = driver.findElement(by);
                return wb == null? false:true;
            } catch (NoSuchElementException e) {
                return false;
            }
        }*/

        Click(paymentpage.cashOnDelivery(),  driver);
        Thread.sleep(5000);
        Click(paymentpage.payOnDelivery(),  driver);
    }
}
