package com.hk.commonProperties; /**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 6/30/14
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
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

import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class SharedProperties {
    public static WebDriver driver;
    public int delay ;


    public static void openBrowser(String AppURL, String BrowserName) {
        if (BrowserName.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            driver.manage().window().maximize();
            driver.get(AppURL);

        } else if (BrowserName.equalsIgnoreCase("IE")) {
            System.setProperty("webdriver.ie.driver", PropertyHelper.readProperty("ieDriver"));
            DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
            cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            driver = new InternetExplorerDriver(cap);
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            driver.manage().window().maximize();
            driver.get(AppURL);
        } else if (BrowserName.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", PropertyHelper.readProperty("chromeDriver"));
            driver = new ChromeDriver();
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

    public  void pressEnterSafe() throws InterruptedException, IOException, Exception{
        /*Set<String> windowId = SharedProperties.driver.getWindowHandles();    // get  window id of current window
        Iterator<String> itererator = windowId.iterator();

        String mainWinID = itererator.next();
        String  newAdwinID = itererator.next();

        SharedProperties.driver.switchTo().window(newAdwinID);
        System.out.println(SharedProperties.driver.findElement(By.xpath("*//*//**//*[@id=\"print-header\"]/div/button[2]")));
        Thread.sleep(3000);
        SharedProperties.driver.close();

        SharedProperties.driver.switchTo().window(mainWinID);
        System.out.println(SharedProperties.driver.getTitle());
        Thread.sleep(2000);*/
        /*Alert alert = SharedProperties.driver.switchTo().alert();
        //alert is present
        System.out.println(SharedProperties.driver.findElement(By.xpath("*//*//**//*[@id=\"print-header\"]/div/button[2]")));
        alert.accept();*/

       /* windowids = driver.getWindowHandles();
        iter= windowids.iterator();
        String mainWindowId=iter.next();
        String popupWindowId=iter.next();
        driver.switchTo().window(popupwindowid);*/

        Robot robot = new Robot() ;

        robot.delay(delay) ;

        robot.keyPress(KeyEvent.VK_ENTER) ;
        robot.keyRelease(KeyEvent.VK_ENTER) ;
        Thread.sleep(2000);
        robot.delay(delay) ;

        robot.keyPress(KeyEvent.VK_ENTER) ;
        robot.keyRelease(KeyEvent.VK_ENTER) ;
        Thread.sleep(2000);
        robot.delay(delay) ;
        Thread.sleep(2000);

        robot.keyPress(KeyEvent.VK_ENTER) ;
        robot.keyRelease(KeyEvent.VK_ENTER) ;

        System.out.print("test");



}
}