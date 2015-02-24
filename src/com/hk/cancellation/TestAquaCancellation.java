package com.hk.cancellation;

import com.hk.aquaElementLocators.AdminHome;
import com.hk.aquaElementLocators.LoginPageAdmin;
import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.LoginPage;
import com.hk.orderCheckoutDto.SoDetails;
import com.hk.orderCheckoutDto.SoDetailsDTO;
import com.hk.property.PropertyHelper;
import com.hk.recorder.Browse;
import com.hk.recorder.VideoRecorder;
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
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 1/9/15
 * Time: 3:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestAquaCancellation {

    Browse browse = new Browse();
    AppSpecificReusableMethods reusableMethods = new AppSpecificReusableMethods();
    LoginPage loginPage = new LoginPage();
    SoDetails soDetails = new SoDetails();
    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    AdminHome adminHome = new AdminHome();
    FileWriter fw = null;
    VideoRecorder recorder = new VideoRecorder();


    @BeforeMethod
    public void isSkip()
    {

        if(!TestUtil.isExecutable("TestCancellation"))
        {

            System.out.println("Test case would be skipped");
            throw new SkipException("Skipping the test case as RunMode is No");

        }

    }

    @AfterMethod
    public void doAfter(ITestResult result) throws IOException {

        System.out.println("Inside doAfter method having AfterMethod annotation");

        if (result.getStatus() == ITestResult.FAILURE) {
            File screenshot = ((TakesScreenshot) SharedProperties.driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir") + PropertyHelper.readProperty("screenshotFolder") + "\\ExistingOnlineOrder.jpg"));
        }

        recorder.stopRecording();
        SharedProperties.driver.quit();
    }

    @Test(enabled = true)
    public void testAquaCancellation() throws Exception {
        recorder.startRecording();
        String shippingOrderId = null;
        int warehouseId = 0;
        fw = new FileWriter("C:\\Workspace\\Automation_testing_v4_Vipul\\logs\\" + new SimpleDateFormat("ddMMyyyy").format(new Date())+"_AquaCancellation" + ".txt", true);
        fw.append("\n"+"\n"+new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(new Date()) );

        System.out.println("Inside testAquaCancellation method");
        SharedProperties.openBrowser(TestUtil.getURL(), TestUtil.getBrowser());
        String variant = null;
        variant = TestUtil.excel.getCellData("Cancellation", "Variant", 3);
        //SharedProperties.driver.navigate().to("http://182.74.46.183:9090/sv/oh-yeah!-isolate/SP-29982?navKey=VRNT-"+ variant);
        SharedProperties.driver.navigate().to(TestUtil.getURL()+"/sv/oh-yeah!-isolate/SP-29982?navKey=VRNT-"+ variant);
        Thread.sleep(2000);
        SharedProperties.driver.findElement(By.xpath("//*[@value = 'Buy Now']")).click();
        SharedProperties.driver.findElement(By.xpath("//*[@id='cartPop']/div/div/div/div[5]/a")).click();
        browse.proceedToCheckoutMultiVariant();
        SharedProperties.Click(loginPage.getSignInCheckbox(), SharedProperties.driver);
        reusableMethods.setUserCredentials();
        reusableMethods.selectDeliveryAddress();
        reusableMethods.doCODPayment();
        //reusableMethods.doOnlinePayment();
        String orderId =   SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[6]/div/div[1]/p[2]")).getText();
        System.out.println(orderId);
        fw.append("\n"+ "Order id for Aqua cancellation = " + orderId);
        String finalOrderId = orderId.substring(10);
        soDetails.orderIdSoDetails = finalOrderId;
        Thread.sleep(2000);
        //SharedProperties.openBrowser(TestUtil.getAdminURL(), TestUtil.getBrowser());
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

            fw.append("\n"+ "Shipping Order id for the corresponding gateway order is = "+ shippingOrderId);

            System.out.println("Warehouse Id = " + warehouseId);

            SharedProperties.driver.findElement(By.linkText("Admin Home")).click();
            Thread.sleep(3000);

            if(warehouseId == 10)
            {
                System.out.print("\n Selected GGN Aqua Warehouse");
                WebElement WarehouseDropDownList = SharedProperties.driver.findElement(By.xpath("//*[@id=\"selectWHForm\"]/select"));
                Select clickWarehouse = new Select(WarehouseDropDownList);
                clickWarehouse.selectByVisibleText("GGN Aqua Warehouse");
                SharedProperties.Click(adminHome.getSaveBtn(), SharedProperties.driver);
                Thread.sleep(2000);



            }

            if(warehouseId == 20)
            {
                System.out.print("\n MUM Aqua Warehouse");
                Thread.sleep(2000);
                WebElement WarehouseDropDownList = SharedProperties.driver.findElement(By.xpath("//*[@id=\"selectWHForm\"]/select"));
                Select clickWarehouse = new Select(WarehouseDropDownList);
                clickWarehouse.selectByVisibleText("MUM Aqua Warehouse");
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
                fw.append("\n"+ "Cancellation done successfully");

            }



        }


        fw.close();

    }

    public static void main(String[] args) throws Exception {

        TestAquaCancellation aquaCancellation = new TestAquaCancellation();

        aquaCancellation.testAquaCancellation();


    }

}
