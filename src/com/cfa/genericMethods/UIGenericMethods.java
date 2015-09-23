package com.cfa.genericMethods;

import com.cfa.pageObjects.LoginPage;
import com.hk.property.PropertyHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: vikas.dhull
 * Date: 9/17/15
 * Time: 9:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class UIGenericMethods {

    public static WebDriver DRIVER_OBJ;
    public void fn_LaunchBrowser(String BrowserName){
        WebDriver  driverObj=null;

        if(("IE").equalsIgnoreCase(BrowserName)){

            System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+ PropertyHelper.readProperty("ieDriver"));
            DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
            cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            driverObj = new InternetExplorerDriver(cap);

        } else if(("chrome").equalsIgnoreCase(BrowserName)){

            System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+ PropertyHelper.readProperty("ieDriver"));
            DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
            cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            driverObj = new InternetExplorerDriver(cap);

        } else if(("firefox").equalsIgnoreCase(BrowserName)){
            driverObj = new FirefoxDriver();

        }
        driverObj.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driverObj.manage().window().maximize();
        DRIVER_OBJ=driverObj;
    }

    public  void fn_OpenURL(String url){

        DRIVER_OBJ.get(url);

    }

    public static void fn_SelectRightClickOption(WebElement we, int OptionIndex) throws InterruptedException{
        Actions actObj=new Actions(DRIVER_OBJ);
        actObj.contextClick(we).build().perform();
        Thread.sleep(500);
        for(int i=1; i<=OptionIndex;i++){
            actObj.sendKeys(Keys.ARROW_DOWN);
        }
        actObj.sendKeys(Keys.ENTER);
    }

    public LoginPage fn_OpenLoginPage(String browserName, String URL){
        fn_LaunchBrowser(browserName);
        fn_OpenURL(URL);
        LoginPage LPObj= PageFactory.initElements(DRIVER_OBJ, LoginPage.class);
        return LPObj;
    }

    public void fn_Click(WebElement weObj){
        boolean visiblestatus=fn_IsVisiible(weObj);
        boolean enablestatus=fn_IsEnable(weObj);
        if(enablestatus==true && visiblestatus==true){
            weObj.click();
        }else{

        }

    }

    public void fn_MouseOver(WebElement weObj){
        boolean visiblestatus=fn_IsVisiible(weObj);
        boolean enablestatus=fn_IsEnable(weObj);
        if(enablestatus==true && visiblestatus==true){
            new Actions(DRIVER_OBJ).moveToElement(weObj).build().perform();
        }else{

        }

    }

    public void fn_MouseClick(WebElement weObj){
        boolean visiblestatus=fn_IsVisiible(weObj);
        boolean enablestatus=fn_IsEnable(weObj);
        if(enablestatus==true && visiblestatus==true){
            new Actions(DRIVER_OBJ).click(weObj).build().perform();
        }else{

        }
    }

    public static void fn_JavaScriptClick(WebElement we){

        JavascriptExecutor executor = (JavascriptExecutor)DRIVER_OBJ;
        executor.executeScript("arguments[0].click();", we);
    }

    public static void fn_WindowScroll(){

        JavascriptExecutor executor = (JavascriptExecutor)DRIVER_OBJ;
        executor.executeScript("window.scrollTo(0,150);");
    }

    public static void fn_WindowScrollToWebElement(WebElement we){
        Point p= we.getLocation();
        JavascriptExecutor executor = (JavascriptExecutor)DRIVER_OBJ;
        executor.executeScript("window.scrollTo("+p.getX()+","+p.getY()+");");
    }

    public void fn_InputStatic(WebElement weObj, String valToInput){
        boolean visiblestatus=fn_IsVisiible(weObj);
        boolean enablestatus=fn_IsEnable(weObj);
        if(enablestatus==true && visiblestatus==true){
            weObj.sendKeys(valToInput);
        }else{

        }
    }

    public boolean fn_IsVisiible(WebElement eleObj){
        int height=eleObj.getSize().getHeight();
        if(height>0){
            return true;
        }else{
            return false;
        }
    }

    public boolean fn_IsEnable(WebElement eleObj){
        boolean status=eleObj.isEnabled();
        return status;
    }

    public void fn_ExplicitWait_Visibility(WebElement we, int TimeOut){
        WebDriverWait WWT=new WebDriverWait(DRIVER_OBJ, TimeOut);
        WWT.until(ExpectedConditions.visibilityOf(we));

    }
    public void fn_ExplicitWait_ClickAble(WebElement we, int TimeOut){
        WebDriverWait WWT=new WebDriverWait(DRIVER_OBJ, TimeOut);
        WWT.until(ExpectedConditions.elementToBeClickable(we));

    }

    public void fn_ValidateText_Static(WebElement we, String ExpectedValue){
        String ActualText= we.getText();
        if(ExpectedValue.equalsIgnoreCase(ActualText)){
            System.out.println("Passed");
        }else{
            System.out.println("Failed");
        }
    }

    public void fn_ValidateTextContains_Static(WebElement we, String ExpectedValue){
        boolean ActualStatus= we.getText().contains(ExpectedValue);
        if(ActualStatus==true){
            System.out.println("Passed");
        }else{
            System.out.println("Failed");
        }
    }

    public void fn_ValidateAttribute_Static(WebElement we, String ExpectedValue){
        boolean ActualStatus= we.getText().contains(ExpectedValue);
        if(ActualStatus==true){
            System.out.println("Passed");
        }else{
            System.out.println("Failed");
        }
    }
}
