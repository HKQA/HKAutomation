package com.hk.warehouseSpecific;

import com.hk.aquaElementLocators.AdminHome;
import com.hk.aquaElementLocators.LoginPageAdmin;
import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.LoginPage;
import com.hk.orderCheckoutDto.SoDetails;
import com.hk.orderCheckoutDto.SoDetailsDTO;
import com.hk.property.PropertyHelper;
import com.hk.recorder.Browse;
import com.hk.scaledupOrderPlacement.AppSpecificReusableMethods;
import com.hk.util.TestUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 2/19/15
 * Time: 4:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class HYDCFACancellation {

    Browse browse = new Browse();
    AppSpecificReusableMethods reusableMethods = new AppSpecificReusableMethods();
    LoginPage loginPage = new LoginPage();

    SoDetails soDetails = new SoDetails();
    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    AdminHome adminHome = new AdminHome();

    @BeforeMethod
    public void isSkip()
    {

        if(!(TestUtil.isExecutable("HYD_CFA") && TestUtil.excel.getCellData("HYD_CFA", "RunMode", 2).equalsIgnoreCase("Y") ) )
        {

            System.out.println("HYDCFA Cancellation test would be skipped");
            throw new SkipException("Skipping the HYDCFA Cancellation test case as RunMode is No");

        }

    }


    @AfterMethod
    public void doAfter(ITestResult result) throws IOException {

        System.out.println("Inside doAfter method having AfterMethod annotation");

        if (result.getStatus() == ITestResult.FAILURE) {
            File screenshot = ((TakesScreenshot) SharedProperties.driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir") + PropertyHelper.readProperty("screenshotFolder") + "\\HYDCFACancellationTest.jpg"));
        }


        SharedProperties.driver.quit();
    }


    @Test
    public void testHYDCFACancellation() throws Exception {

        String shippingOrderId = null;
        int warehouseId = 0;

        SharedProperties.openBrowser(TestUtil.getURL(), TestUtil.getBrowser());
        String variant = null;
        variant = TestUtil.excel.getCellData("HYD_CFA", "Test Scenario", 4);
        SharedProperties.driver.navigate().to(TestUtil.getURL()+"/sv/oh-yeah!-isolate/SP-29982?navKey=VRNT-"+ variant);
        Thread.sleep(2000);
        SharedProperties.driver.findElement(By.xpath("//*[@value = 'Buy Now']")).click();
        SharedProperties.driver.findElement(By.xpath("//*[@id='cartPop']/div/div/div/div[5]/a")).click();
        browse.proceedToCheckoutMultiVariant();
        SharedProperties.Click(loginPage.getSignInCheckbox(), SharedProperties.driver);
        reusableMethods.setUserCredentials();
        //reusableMethods.selectDeliveryAddress();
        SharedProperties.driver.findElement(By.linkText("Edit")).click();
        SharedProperties.driver.findElement(By.xpath("//*[@id = 'pincode']")).clear();
        SharedProperties.driver.findElement(By.xpath("//*[@id = 'pincode']")).sendKeys("500081");
        Thread.sleep(3000);
        SharedProperties.driver.findElement(By.xpath("//*[@name = 'updateAddressForUser']")).click();
        reusableMethods.doCODPayment();
        String orderId =   SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[7]/div/div[1]/p[2]")).getText();
        System.out.println(orderId);
        String finalOrderId = orderId.substring(10);
        soDetails.orderIdSoDetails = finalOrderId;
        Thread.sleep(2000);
        SharedProperties.driver.navigate().to(TestUtil.getAdminURL());
        Thread.sleep(3000);
        SharedProperties.sendKeys(loginPageAdmin.getUserName(), TestUtil.getAdmin_User(), SharedProperties.driver);
        SharedProperties.sendKeys(loginPageAdmin.getPassword(), TestUtil.getAdmin_Password(), SharedProperties.driver);
        SharedProperties.Click(loginPageAdmin.getLoginbtn(), SharedProperties.driver);
        for(SoDetailsDTO soDetailsDTO : soDetails.Sodetails())
        {

            shippingOrderId = soDetailsDTO.getSoGatewayOrderId();
            warehouseId = soDetailsDTO.getWarehouseId();

            System.out.println("Shipping Order Id = "+shippingOrderId);
            System.out.println("Warehouse Id = " + warehouseId);

            SharedProperties.driver.findElement(By.linkText("Admin Home")).click();
            Thread.sleep(3000);

            if(warehouseId == 1005)
            {
                System.out.print("\n Selected GGN Aqua Warehouse");
                WebElement WarehouseDropDownList = SharedProperties.driver.findElement(By.xpath("//*[@id=\"selectWHForm\"]/select"));
                Select clickWarehouse = new Select(WarehouseDropDownList);
                clickWarehouse.selectByVisibleText("Hyderabad Aqua CFA");
                SharedProperties.Click(adminHome.getSaveBtn(), SharedProperties.driver);
                Thread.sleep(2000);



            }


            SharedProperties.driver.findElement(By.linkText("Search SO")).click();
            SharedProperties.driver.findElement(By.xpath("//*[@name = 'shippingOrderGatewayId']")).sendKeys(shippingOrderId);
            SharedProperties.driver.findElement(By.xpath("//*[@value = 'Search Orders']")).click();
            Thread.sleep(2000);

            WebElement cancelReason = SharedProperties.driver.findElement(By.xpath("//*[@id = 'soReason']"));
            Select selectCancelReason = new Select(cancelReason);
            selectCancelReason.selectByIndex(1);
            SharedProperties.driver.findElement(By.xpath("//*[@value = 'Cancel SO']")).click();
            Alert alert = SharedProperties.driver.switchTo().alert();
            alert.accept();
            if(SharedProperties.driver.findElement(By.xpath("html/body/div[2]/div[2]/div/ul/li")).getText().contains("Shipping Order Cancelled Successfully"))
            {

                System.out.println("Cancellation done successfully");

            }

        }



    }

    public static void main(String[] args) throws Exception {
        HYDCFACancellation test = new HYDCFACancellation();
        test.testHYDCFACancellation();



    }


}
