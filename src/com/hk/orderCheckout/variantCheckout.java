package com.hk.orderCheckout;


import com.hk.aquaElementLocators.LoginPageAdmin;
import com.hk.aquaElementLocators.PrintPrickOrders;
import com.hk.commonProperties.SharedProperties;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Nitin Kukna
 * Date: 8/1/14
 * Time: 6:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class variantCheckout /*extends ExistingOnlineOrder*/{
    String AdminBaseURL;
    String browser;
    private int delay ;

    LoginPageAdmin loginpage=new LoginPageAdmin();
    PrintPrickOrders printprick = new PrintPrickOrders();

    @Parameters({"AdminBaseURL", "Browser"})
    @BeforeClass
    public void g(String AdminBaseURL, String Browser) {
        this.AdminBaseURL = AdminBaseURL;
        this.browser = Browser;
    }

    /*ExistingOnlineOrder EOO = new ExistingOnlineOrder();
    *//*ExcelServiceImplOld readexcel = new ExcelServiceImplOld();*/



/*    @DataProvider(name = "VariantCheckoutData")
    public List<String> variantCheckoutDataProviderCombined() {

        List<String> finalObjectString = new ArrayList<String>();

        try {
            finalObjectString.addAll(readexcel.mainReadFromExcelIterator(PropertyHelper.readProperty("LoginExcel")));
            finalObjectString.addAll(readexcel.mainReadFromExcelIterator(PropertyHelper.readProperty("productIdExcel")));
        } catch (FileNotFoundException fex) {
            System.out.println(fex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return finalObjectString;
    }*/
    /*@Test(dataProvider = "VariantCheckoutData", enabled = true)*/
    @Test(enabled = true)
    public void variantCheckout() throws InterruptedException, IOException, Exception {
//    EOO.login(1L);

        SharedProperties.openBrowser(AdminBaseURL,browser);
        SharedProperties.sendKeys(loginpage.getUserName(), "saurabh.nagpal@healthkart.com", SharedProperties.driver);
        SharedProperties.sendKeys(loginpage.getPassword(), "abcde12", SharedProperties.driver);
        SharedProperties.Click(loginpage.getLoginbtn(), SharedProperties.driver);

        //Select WareHouse according to your order from database or with text

        SharedProperties.clickWithCss(printprick.getPrintPrickLink(), SharedProperties.driver);
        SharedProperties.Click(printprick.getOrderFilters(), SharedProperties.driver);
        Thread.sleep(2000);
        SharedProperties.sendKeys(printprick.getBoGatewayOrderIdTxt(), "HK54942-392044", SharedProperties.driver);
        SharedProperties.Click(printprick.getBoGatewaySearchBtn(), SharedProperties.driver);
        Thread.sleep(3000);
        SharedProperties.Class(printprick.getCheckboxBo(), SharedProperties.driver);
        SharedProperties.Click(printprick.getBatchPrintBtn(), SharedProperties.driver);
        Thread.sleep(5000);

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

