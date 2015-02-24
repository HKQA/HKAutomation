package com.hk.scaledupOrderPlacement;

import com.hk.aquaElementLocators.AdminHome;
import com.hk.aquaElementLocators.DeliveryAwaitingQueue;
import com.hk.aquaElementLocators.LoginPageAdmin;
import com.hk.aquaElementLocators.ShipmentAwaitingQueue;
import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.LoginPage;
import com.hk.orderCheckoutDto.SoDetails;
import com.hk.orderCheckoutDto.SoDetailsDTO;
import com.hk.property.PropertyHelper;
import com.hk.recorder.Browse;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 2/4/15
 * Time: 6:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class StoreCheckoutTest {

    Browse browse = new Browse();
    LoginPage loginPage = new LoginPage();
    AppSpecificReusableMethods reusableMethods = new AppSpecificReusableMethods();
    SoDetails soDetails = new SoDetails();
    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    AdminHome adminHome = new AdminHome();
    StoreCheckoutBarcodes storeBarcodes = new StoreCheckoutBarcodes();
    ShipmentAwaitingQueue shipmentAwaitingQueue = new ShipmentAwaitingQueue();
    DeliveryAwaitingQueue deliveryAwaitingQueue = new DeliveryAwaitingQueue();



    @BeforeMethod
    public void isSkip()
    {

        if(!(TestUtil.isExecutable("StoreCheckoutTest")) )
        {

            System.out.println("StoreCheckoutTest would be skipped");
            throw new SkipException("Skipping the StoreCheckoutTest test case as RunMode is No");

        }

    }

    @AfterMethod
    public void doAfter(ITestResult result) throws IOException {

        System.out.println("Inside doAfter method having AfterMethod annotation");

        if (result.getStatus() == ITestResult.FAILURE) {
            File screenshot = ((TakesScreenshot) SharedProperties.driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir") + PropertyHelper.readProperty("screenshotFolder") + "\\StoreCheckoutTest.jpg"));
        }

        //recorder.stopRecording();
        SharedProperties.driver.quit();
    }


    @Test
    public void testStoreCheckout() throws Exception {

        String shippingOrderId = null;
        int warehouseId = 0;
        List<String> storeCheckoutProductIds = new ArrayList<String>();
        List<String> storeCheckoutBarcodes = new ArrayList<String>();
        int rowCount = 1;

        SharedProperties.openBrowser(TestUtil.getURL(), TestUtil.getBrowser());

        String variant = TestUtil.excel.getCellData("StoreCheckout", "Variant", 2);

        SharedProperties.driver.navigate().to(TestUtil.getURL()+"/sv/oh-yeah!-isolate/SP-29982?navKey=VRNT-"+ variant);
        SharedProperties.driver.findElement(By.xpath("//*[@value = 'Buy Now']")).click();
        SharedProperties.driver.findElement(By.xpath("//*[@id='cartPop']/div/div/div/div[5]/a")).click();

        browse.proceedToCheckoutMultiVariant();
        SharedProperties.Click(loginPage.getSignInCheckbox(), SharedProperties.driver);
        reusableMethods.setUserCredentials();
        reusableMethods.selectDeliveryAddress();
        reusableMethods.doCODPayment();
        int lineItemCount= reusableMethods.verifyLineItems();
        System.out.println("Number of line items = " + lineItemCount);

        String orderId =   SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[6]/div/div[1]/p[2]")).getText();

        System.out.println(orderId);

        String finalOrderId = orderId.substring(10);
        soDetails.orderIdSoDetails = finalOrderId;

        SharedProperties.openBrowser(TestUtil.getAdminURL(), TestUtil.getBrowser());
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

            if(warehouseId == 301)
            {
                System.out.println("Selected Punjabi Bagh Store");
                WebElement WarehouseDropDownList = SharedProperties.driver.findElement(By.xpath("//*[@id=\"selectWHForm\"]/select"));
                Select clickWarehouse = new Select(WarehouseDropDownList);
                clickWarehouse.selectByVisibleText("DEL Punjabi Bagh Aqua Store");
                SharedProperties.Click(adminHome.getSaveBtn(), SharedProperties.driver);
                Thread.sleep(2000);



            }

            if(warehouseId == 1003)
            {
                System.out.println("Selected Dwarka Store");
                WebElement WarehouseDropDownList = SharedProperties.driver.findElement(By.xpath("//*[@id=\"selectWHForm\"]/select"));
                Select clickWarehouse = new Select(WarehouseDropDownList);
                clickWarehouse.selectByVisibleText("Dwarka Aqua Store");
                SharedProperties.Click(adminHome.getSaveBtn(), SharedProperties.driver);
                Thread.sleep(2000);




            }

            SharedProperties.driver.findElement(By.linkText("Search SO")).click();
            Thread.sleep(2000);
            SharedProperties.driver.findElement(By.xpath("//*[@name = 'shippingOrderGatewayId']")).sendKeys(shippingOrderId);
            Thread.sleep(2000);
            SharedProperties.driver.findElement(By.xpath("//*[@value = 'Search Orders']")).click();
            Thread.sleep(2000);
            //Code to fetch product variant ids of the line items
            while (SharedProperties.isElementPresent("//*[contains(@id, 'shippingOrderLineItems')]/table/tbody/tr["+rowCount+"]/td[1]/a"))
            {
                String productIds =  SharedProperties.driver.findElement(By.xpath("//*[contains(@id, 'shippingOrderLineItems')]/table/tbody/tr["+rowCount+"]/td[1]/a")).getText();
                storeCheckoutProductIds.add(productIds);
                rowCount++;

            }

            System.out.println("Product Variant size for Store Checkout = "+ storeCheckoutProductIds.size() );
            for(String productVariantId : storeCheckoutProductIds)
            {

                //storeBarcodes.getStoreCheckoutBarcodes();
                String barcode = storeBarcodes.getStoreCheckoutBarcodes(warehouseId, productVariantId);
                storeCheckoutBarcodes.add(barcode);


            }

            SharedProperties.driver.findElement(By.linkText("Store Manager")).click();
            String parentWindowId = SharedProperties.driver.getWindowHandle();
            //SharedProperties.driver.findElement(By.linkText("Checkout Order")).click();
            SharedProperties.driver.navigate().to(TestUtil.getAdminURL()+ "/inventory/StoreInventoryCheckout.action");
            Thread.sleep(3000);
            SharedProperties.driver.findElement(By.xpath("//*[@id= 'orderGatewayId']")).sendKeys(shippingOrderId);
            Thread.sleep(3000);
            SharedProperties.driver.findElement(By.xpath("//*[@value = 'Proceed to Checkout']")).click();
            Thread.sleep(3000);
            System.out.println(storeCheckoutBarcodes.size());

            for(String barcode : storeCheckoutBarcodes)
            {
                System.out.println("Inside for loop for entering barcodes");
                SharedProperties.driver.findElement(By.xpath("//*[@id = 'upc']")).sendKeys(barcode);
                Thread.sleep(3000);
                SharedProperties.driver.findElement(By.xpath("//*[@id=\"upc\"]")).sendKeys(Keys.ENTER);
                //SharedProperties.driver.findElement(By.xpath("[@value = 'Back']")).click();



            }

            SharedProperties.driver.findElement(By.linkText("Store Manager")).click();
            SharedProperties.driver.navigate().to(TestUtil.getAdminURL()+ "/courier/CreateUpdateShipment.action");
            Thread.sleep(2000);
            SharedProperties.driver.findElement(By.xpath("//*[@id = 'gatewayOrderId']")).sendKeys(shippingOrderId);
            SharedProperties.driver.findElement(By.xpath("//*[@value = 'Search']")).click();
            new Select(SharedProperties.driver.findElement(By.xpath("//*[@id=\"boxSize\"]"))).selectByVisibleText("L");
            new Select(SharedProperties.driver.findElement(By.xpath("//*[@id=\"packer\"]"))).selectByVisibleText("L");
            new Select(SharedProperties.driver.findElement(By.xpath("//*[@id=\"picker\"]"))).selectByVisibleText("10");
            Thread.sleep(2000);
            SharedProperties.driver.findElement(By.xpath("//*[@id = 'validate']")).click();
            Thread.sleep(2000);
            System.out.print("\n ************* SO Packed *************");
            SharedProperties.driver.findElement(By.linkText("Store Manager")).click();
            SharedProperties.driver.navigate().to(TestUtil.getAdminURL()+ "/queue/ShipmentAwaitingQueue.action");
            SharedProperties.sendKeys(shipmentAwaitingQueue.getGatewayId(), shippingOrderId, SharedProperties.driver);
            SharedProperties.Click(shipmentAwaitingQueue.getSearchBtn(), SharedProperties.driver);
            SharedProperties.Class(shipmentAwaitingQueue.getCheckBox(), SharedProperties.driver);
            Thread.sleep(3000);
            SharedProperties.Click(shipmentAwaitingQueue.getMarkedOrdersAsShipped(), SharedProperties.driver);
            Thread.sleep(2000);
            System.out.print("\n ************* SO Shipped *************");
            SharedProperties.driver.findElement(By.linkText("Store Manager")).click();
            Thread.sleep(2000);
            SharedProperties.driver.navigate().to(TestUtil.getAdminURL()+"/queue/DeliveryAwaitingQueue.action");
            SharedProperties.sendKeys(deliveryAwaitingQueue.getGatewayOrderIdTxt(), shippingOrderId, SharedProperties.driver);
            SharedProperties.Click(deliveryAwaitingQueue.getSearchBtn(), SharedProperties.driver);
            SharedProperties.Class(deliveryAwaitingQueue.getCheckBox(), SharedProperties.driver);
            Thread.sleep(3000);
            SharedProperties.Click(deliveryAwaitingQueue.getMarkOrdersAsDelivered(), SharedProperties.driver);
            System.out.print("\n ************* SO Delivered *************");





        }






    }

    public static void main(String[] args) throws Exception {


        StoreCheckoutTest storeCheckoutTest = new StoreCheckoutTest();

        storeCheckoutTest.testStoreCheckout();

    }
}
