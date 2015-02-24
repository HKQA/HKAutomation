package com.hk.scaledupOrderPlacement;

import com.hk.aquaElementLocators.AdminHome;
import com.hk.aquaElementLocators.CodConfirmation;
import com.hk.aquaElementLocators.LoginPageAdmin;
import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.LoginPage;
import com.hk.orderCheckoutDto.SoDetails;
import com.hk.orderCheckoutDto.SoDetailsDTO;
import com.hk.property.PropertyHelper;
import com.hk.recorder.Browse;
import com.hk.recorder.MultipleVariant;
import com.hk.recorder.VideoRecorder;
import com.hk.util.TestUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 1/29/15
 * Time: 4:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddressChangeTest {

    AppSpecificReusableMethods reusableMethods = new AppSpecificReusableMethods();
    MultipleVariant multipleVariant = new MultipleVariant();

    Browse browse = new Browse();
    LoginPage loginPage = new LoginPage();

    SoDetails soDetails = new SoDetails();
    CodConfirmation codconfirmation = new CodConfirmation();

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    AdminHome adminHome = new AdminHome();

    FetchBarcode getBarcode = new FetchBarcode();

    VideoRecorder recorder = new VideoRecorder();

    FileWriter fw = null;

    @BeforeMethod
    public void isSkip()
    {

        if(!TestUtil.isExecutable("AddressChangeTest"))
        {

            System.out.println("TestCase would be skipped");
            throw new SkipException("Skipping the test case as RunMode is No");

        }

    }

    @AfterMethod
    public void doAfter(ITestResult result) throws IOException {

        System.out.println("Inside doAfter method having AfterMethod annotation");

        if (result.getStatus() == ITestResult.FAILURE) {
            File screenshot = ((TakesScreenshot) SharedProperties.driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir") + PropertyHelper.readProperty("screenshotFolder") + "\\FlipTest.jpg"));
        }

        recorder.stopRecording();
        SharedProperties.driver.quit();
    }



    @Test
    public void testAddressChange() throws Exception {

        recorder.startRecording();

        fw = new FileWriter("C:\\Workspace\\Automation_testing_v4_Vipul\\logs\\" + new SimpleDateFormat("ddMMyyyy").format(new Date())+"_AddressChangeTest" + ".txt", true);
        fw.append("\n"+"\n"+new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(new Date()) );

        List<Object> barcodeDetails = null;

        String shippingOrderId = null;

        int warehouseId = 0;

        SharedProperties.openBrowser(TestUtil.getURL(), TestUtil.getBrowser());

        multipleVariant.testMultipleVariant();

        browse.proceedToCheckoutMultiVariant();

        SharedProperties.Click(loginPage.getSignInCheckbox(), SharedProperties.driver);

        reusableMethods.setUserCredentials();

        reusableMethods.selectDeliveryAddress();

        //reusableMethods.doOnlinePayment();
        reusableMethods.doCODPayment();

        Thread.sleep(3000);
        String orderId =   SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[6]/div/div[1]/p[2]")).getText();

        System.out.println(orderId);

        String finalOrderId = orderId.substring(10);

        soDetails.orderIdSoDetails = finalOrderId;

        TestUtil.excel.setCellData("test_suite","OrderId_Generated",16, orderId );

        fw.append("\n"+ "Order id = " + finalOrderId);

        SharedProperties.driver.navigate().to(TestUtil.getCOD_Confirm_URL());

        SharedProperties.sendKeys(codconfirmation.getEmail(), TestUtil.getCOD_Confirm_User(), SharedProperties.driver);
        SharedProperties.sendKeys(codconfirmation.getPassword(), TestUtil.getCOD_Confirm_Password(), SharedProperties.driver);
        WebElement store = SharedProperties.driver.findElement(By.xpath("//*[@name = 'storeId']"));
        Select selectStore = new Select(store);
        selectStore.selectByVisibleText("Healthkart");
        SharedProperties.Click(codconfirmation.getLogin(), SharedProperties.driver);
        Thread.sleep(5000);

        SharedProperties.mouseHoverAndClick("//*[@id='cssmenu']/ul/li[6]/a/span", "//*[@id='cssmenu']/ul/li[6]/ul/li[1]/a/span", SharedProperties.driver);
        SharedProperties.driver.findElement(By.xpath("//*[@name = 'gatewayOrderId']")).sendKeys(finalOrderId);
        SharedProperties.driver.findElement(By.xpath("//*[@value = 'Search']")).click();
        SharedProperties.driver.findElement(By.linkText("Change Address")).click();
        Thread.sleep(2000);
        SharedProperties.driver.findElement(By.xpath("//*[@name = 'addressDTO.pincodeDTO.pincode']")).clear();
        SharedProperties.driver.findElement(By.xpath("//*[@name = 'addressDTO.pincodeDTO.pincode']")).sendKeys("110024");
        SharedProperties.driver.findElement(By.xpath("//*[@value = 'Save Changes']")).click();
        Thread.sleep(5000);

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

            System.out.println("Warehouse Id = " + warehouseId);

            fw.append("\n"+ "New Shipping order id = " + shippingOrderId +"and warehouse id =" + warehouseId);


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

            SharedProperties.driver.findElement(By.linkText("Search BO")).click();
            SharedProperties.driver.findElement(By.xpath("//*[@name = 'gatewayOrderId']")).sendKeys(finalOrderId);
            SharedProperties.driver.findElement(By.xpath("//*[@value = 'Search Orders']")).click();

            //Get xpath of SOs
            if(SharedProperties.isElementPresent("html/body/div[2]/div[2]/table[1]/tbody/tr/td[6]/table/tbody/tr/td/a"))
            {

                 String shippingOrder = SharedProperties.driver.findElement(By.xpath("html/body/div[2]/div[2]/table[1]/tbody/tr/td[6]/table/tbody/tr/td/a")).getText() ;
                 fw.append("\n"+"Old shipping order id = "+ shippingOrder);
            }

            barcodeDetails = getBarcode.fetchBarcode(shippingOrderId.substring(0,7));

            for(Object values : barcodeDetails)
            {

                System.out.println(values);
                fw.append("\n"+values);

            }




             fw.close();


        }






    }



    public static void main(String[] args) throws Exception {

        AddressChangeTest addressChangeTest = new AddressChangeTest();

        addressChangeTest.testAddressChange();

    }
}
