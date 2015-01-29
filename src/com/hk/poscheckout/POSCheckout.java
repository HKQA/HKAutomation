package com.hk.poscheckout;

import com.hk.aquaElementLocators.AdminHome;
import com.hk.aquaElementLocators.LoginPageAdmin;
import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.LoginPage;
import com.hk.property.PropertyHelper;
import com.hk.util.TestUtil;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 1/2/15
 * Time: 3:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class POSCheckout {

    LoginPageAdmin loginPage = new LoginPageAdmin();
    AdminHome adminHome = new AdminHome();
    BarcodeStatus barcodeStatus = new BarcodeStatus();

    @AfterMethod
    public void doAfter(ITestResult result) throws IOException {

        System.out.println("Inside doAfter method having AfterMethod annotation");

        if (result.getStatus() == ITestResult.FAILURE) {
            File screenshot = ((TakesScreenshot) SharedProperties.driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir") + PropertyHelper.readProperty("screenshotFolder") + "\\ExistingOnlineOrder.jpg"));
        }

        //recorder.stopRecording();
        //SharedProperties.driver.quit();
    }

    @Test
    public void posCheckout() throws Exception {

        int beforeCheckinStatus = 0;
        int afterCheckinStatus = 0;
        String bcode = null;
        String orderCheckoutStatus = null;
        int count = 0;
        Thread.sleep(2000);
        SharedProperties.openBrowser(TestUtil.getAdminURL(), TestUtil.getBrowser());
        Thread.sleep(3000);
        SharedProperties.sendKeys(loginPage.getUserName(), TestUtil.getAdmin_User(), SharedProperties.driver);
        SharedProperties.sendKeys(loginPage.getPassword(), TestUtil.getAdmin_Password(), SharedProperties.driver);
        SharedProperties.Click(loginPage.getLoginbtn(), SharedProperties.driver);
        Thread.sleep(2000);
        WebElement WarehouseDropDownList = SharedProperties.driver.findElement(By.xpath("//*[@id=\"selectWHForm\"]/select"));
        Select clickWarehouse = new Select(WarehouseDropDownList);
        clickWarehouse.selectByVisibleText("Hyderabad Aqua CFA");
        SharedProperties.Click(adminHome.getSaveBtn(), SharedProperties.driver);
        Thread.sleep(2000);
        SharedProperties.driver.findElement(By.linkText("Store")).click();
        Thread.sleep(2000);
        SharedProperties.driver.findElement(By.xpath("//*[@id = 'email']")).sendKeys("manish.gupta@healthkart.com");
        Thread.sleep(2000);
        SharedProperties.driver.findElement(By.xpath("//*[@id = 'email']")).sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        for(String barcode : TestUtil.excel.getAllCellDataFromColumn("barcode", 3))
        {
            System.out.println(barcode);
            beforeCheckinStatus = barcodeStatus.getBarcodeStatus(barcode);
            bcode = barcode;
            SharedProperties.driver.findElement(By.xpath("//*[@id = 'productVariantBarcode']")).sendKeys(barcode);
            Thread.sleep(1000);
            SharedProperties.driver.findElement(By.xpath("//*[@id = 'productVariantBarcode']")).sendKeys(Keys.ENTER);
            Thread.sleep(1000);

            count++;



        }

        System.out.println("Barcode status before placing order = "+ beforeCheckinStatus);

        System.out.println("Barcode status after placing order = "+ afterCheckinStatus);

        new Select(SharedProperties.driver.findElement(By.xpath("//*[@id=\"paymentMode\"]"))).selectByVisibleText("Counter Cash");
        SharedProperties.driver.findElement(By.xpath("//*[@value = 'Receive Payment']")).click();
        if(SharedProperties.isElementPresent("html/body/div/div[2]/div[1]/ul/li[2]/div/label/label"))
        {
          orderCheckoutStatus = SharedProperties.driver.findElement(By.xpath("html/body/div/div[2]/div[1]/ul/li[2]/div/label/label")).getText();
            if(orderCheckoutStatus.contains("shipping order has been delivered"))
            {
                System.out.println("Order has been successfully delivered");
            }
        }
        else
        {

            System.out.println("Order can not be delivered");
            Assert.fail();

        }










    }




}
