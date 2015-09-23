package com.hk.commonProperties; /**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 6/30/14
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */
import com.hk.recorder.VideoRecorder;
import com.hk.util.TestUtil;
import org.apache.xmlbeans.impl.jam.visitor.TraversingJVisitor;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Keys;

import com.hk.property.PropertyHelper;





import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class SharedProperties
{
    public static WebDriver driver;
    public int delay = 5000 ;
    VideoRecorder recorder = new VideoRecorder();





    public static void openBrowser(String AppURL, String BrowserName) {
        if (BrowserName.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            driver.manage().window().maximize();
            driver.get(AppURL);

        } else if (BrowserName.equalsIgnoreCase("IE")) {
            System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+ PropertyHelper.readProperty("ieDriver"));
            DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
            cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            driver = new InternetExplorerDriver(cap);
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            driver.manage().window().maximize();
            driver.get(AppURL);
        } else if (BrowserName.equals("chrome")) {
            //System.setProperty("webdriver.chrome.driver", PropertyHelper.readProperty("chromeDriver"));
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + PropertyHelper.readProperty("chromeDriver"));
            if(TestUtil.getMobileSiteStatus().equalsIgnoreCase("Yes") )
            {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--user-agent=Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");
                options.addArguments("--start-maximized");
                driver = new ChromeDriver(options);

            }
            else if(TestUtil.getMobileSiteStatus().equalsIgnoreCase("No"))
            {
            driver = new ChromeDriver();
            }
            driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
            driver.manage().window().maximize();
            driver.get(AppURL);
        } else {
            driver = new FirefoxDriver();
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            driver.manage().window().maximize();
            driver.get(AppURL);
        }
    }
    public static void sendKeys(String elementXpath, String value, WebDriver driver) {
        driver.findElement(By.xpath(elementXpath)).sendKeys(value);

    }
    public static void sendKeysWithName(String name, String value, WebDriver driver) {
        driver.findElement(By.name(name)).sendKeys(value);

    }
    public static void Click(String elementXpath, WebDriver driver)  {
        driver.findElement(By.xpath(elementXpath)).click();

    }

    public static void clickWithCss(String elementCss, WebDriver driver)  {
        driver.findElement(By.cssSelector(elementCss)).click();

    }
    public static void clear(String elementXpath,  WebDriver driver)  {
        driver.findElement(By.xpath(elementXpath)).clear();

    }

    public static void Class(String className,  WebDriver driver)  {
        driver.findElement(By.className(className)).click();

    }

    public static void ClickWithName(String name,  WebDriver driver)  {
        driver.findElement(By.name(name)).click();

    }

    public  void pressEnterSafe() throws InterruptedException, IOException, Exception{

        Robot robot = new Robot() ;

        robot.delay(delay) ;
        robot.keyPress(KeyEvent.VK_ESCAPE) ;
        robot.keyRelease(KeyEvent.VK_ESCAPE) ;
        Thread.sleep(2000);
        robot.delay(delay) ;

        robot.keyPress(KeyEvent.VK_ESCAPE) ;
        robot.keyRelease(KeyEvent.VK_ESCAPE) ;
        Thread.sleep(3000);

        robot.delay(delay) ;
        Thread.sleep(3000);
        robot.keyPress(KeyEvent.VK_ESCAPE) ;
        robot.keyRelease(KeyEvent.VK_ESCAPE) ;
        robot.delay(delay) ;

    }

    public static boolean isElementPresent(String xpath)
    {
        try {
            driver.findElement(By.xpath(xpath));
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public static void mouseHoverAndClick(String mouseHoverElementXpath, String targetElementXpath, WebDriver driver)
    {
        Actions action = new Actions(driver);
        WebElement signupHover = driver.findElement(By.xpath(mouseHoverElementXpath));
        //action.moveToElement(signupHover).moveToElement(driver.findElement(By.xpath(targetElementXpath))).click().perform();
        action.moveToElement(signupHover).build().perform();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement signupClick = driver.findElement(By.xpath(targetElementXpath));
        action.moveToElement(signupClick).click().perform();
    }
}



