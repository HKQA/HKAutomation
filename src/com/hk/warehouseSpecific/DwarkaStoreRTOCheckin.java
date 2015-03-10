package com.hk.warehouseSpecific;

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
import com.hk.recorder.MultipleVariant;
import com.hk.scaledupOrderPlacement.AppSpecificReusableMethods;
import com.hk.scaledupOrderPlacement.FetchBarcodeRTO;
import com.hk.scaledupOrderPlacement.StoreCheckoutBarcodes;
import com.hk.util.TestUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 2/20/15
 * Time: 3:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class DwarkaStoreRTOCheckin {

    Browse browse = new Browse();
    MultipleVariant multipleVariant = new MultipleVariant();
    LoginPage loginPage = new LoginPage();
    AppSpecificReusableMethods reusableMethods = new AppSpecificReusableMethods();
    SoDetails soDetails = new SoDetails();
    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    AdminHome adminHome = new AdminHome();
    StoreCheckoutBarcodes storeBarcodes = new StoreCheckoutBarcodes();
    ShipmentAwaitingQueue shipmentAwaitingQueue = new ShipmentAwaitingQueue();
    DeliveryAwaitingQueue deliveryAwaitingQueue = new DeliveryAwaitingQueue();
    FetchBarcodeRTO fetchBarcodeRTO = new FetchBarcodeRTO();
    FetchPinCodeAndVariantId fetchPinCodeAndVariantId = new FetchPinCodeAndVariantId();


    @AfterMethod
    public void doAfter(ITestResult result) throws IOException {

        System.out.println("Inside doAfter method having AfterMethod annotation");

        if (result.getStatus() == ITestResult.FAILURE) {
            File screenshot = ((TakesScreenshot) SharedProperties.driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir") + PropertyHelper.readProperty("screenshotFolder") + "\\DwarkaRTOCheckin.jpg"));
        }


        SharedProperties.driver.quit();
    }


    @BeforeMethod
    public void isSkip()
    {

        if(!(TestUtil.isExecutable("DwarkaStoreTest") && TestUtil.excel.getCellData("DWARKA_Store", "RunMode", 10).equalsIgnoreCase("Y") ) )
        {

            System.out.println("DwarkaRTOCheckin test would be skipped");
            throw new SkipException("Skipping the DwarkaRTOCheckin test case as RunMode is No");

        }

    }


    public void testDwarkaStoreRTOCheckin() throws Exception {

        String shippingOrderId = null;
        int warehouseId = 0;
        String rtoBarcode = null;

        List<String> storeCheckoutProductIds = new ArrayList<String>();
        List<String> storeCheckoutBarcodes = new ArrayList<String>();
        int rowCount = 1;

        SharedProperties.openBrowser(TestUtil.getURL(), TestUtil.getBrowser());
        String variant = null;
        fetchPinCodeAndVariantId.getPinCodeAndVariantId(1003);
        Thread.sleep(3000);
        variant = TestUtil.excel.getCellData("DWARKA_Store", "Test Scenario", 12);
        SharedProperties.driver.navigate().to(TestUtil.getURL()+"/sv/oh-yeah!-isolate/SP-29982?navKey=VRNT-"+ variant);
        Thread.sleep(2000);
        SharedProperties.driver.findElement(By.xpath("//*[@value = 'Buy Now']")).click();
        SharedProperties.driver.findElement(By.xpath("//*[@id='cartPop']/div/div/div/div[5]/a")).click();
        browse.proceedToCheckoutMultiVariant();
        SharedProperties.Click(loginPage.getSignInCheckbox(), SharedProperties.driver);
        reusableMethods.setUserCredentials();
        SharedProperties.driver.findElement(By.linkText("Edit")).click();
        SharedProperties.driver.findElement(By.xpath("//*[@id = 'pincode']")).clear();
        SharedProperties.driver.findElement(By.xpath("//*[@id = 'pincode']")).sendKeys(TestUtil.excel.getCellData("DWARKA_Store", "Test Scenario", 19));
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

            if(warehouseId == 1003)
            {
                System.out.println("Selected Dwarka Aqua Store");
                WebElement WarehouseDropDownList = SharedProperties.driver.findElement(By.xpath("//*[@id=\"selectWHForm\"]/select"));
                Select clickWarehouse = new Select(WarehouseDropDownList);
                clickWarehouse.selectByVisibleText("Dwarka Aqua Store");
                SharedProperties.Click(adminHome.getSaveBtn(), SharedProperties.driver);
                Thread.sleep(2000);
                SharedProperties.driver.findElement(By.linkText("Search SO")).click();
                Thread.sleep(2000);
                SharedProperties.driver.findElement(By.xpath("//*[@name = 'shippingOrderGatewayId']")).sendKeys(shippingOrderId);
                Thread.sleep(2000);
                SharedProperties.driver.findElement(By.xpath("//*[@value = 'Search Orders']")).click();
                Thread.sleep(2000);

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
                //String parentWindowId = SharedProperties.driver.getWindowHandle();
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


                }

                SharedProperties.driver.findElement(By.linkText("Store Manager")).click();
                SharedProperties.driver.navigate().to(TestUtil.getAdminURL()+ "/queue/ShipmentAwaitingQueue.action");
                SharedProperties.sendKeys(shipmentAwaitingQueue.getGatewayId(), shippingOrderId, SharedProperties.driver);
                SharedProperties.Click(shipmentAwaitingQueue.getSearchBtn(), SharedProperties.driver);
                SharedProperties.Class(shipmentAwaitingQueue.getCheckBox(), SharedProperties.driver);
                Thread.sleep(3000);
                SharedProperties.Click(shipmentAwaitingQueue.getMarkedOrdersAsShipped(), SharedProperties.driver);
                Thread.sleep(2000);
                System.out.print("\n ************* SO Shipped *************");

                SharedProperties.driver.findElement(By.linkText("Search SO")).click();
                Thread.sleep(2000);
                SharedProperties.driver.findElement(By.xpath("//*[@name = 'shippingOrderGatewayId']")).sendKeys(shippingOrderId);
                SharedProperties.driver.findElement(By.xpath("//*[@value = 'Search Orders']")).click();
                Thread.sleep(2000);
                WebElement rtoReason = SharedProperties.driver.findElement(By.xpath("//*[@id = 'rto-reason']"));
                Select selectRTOReason = new Select(rtoReason);
                selectRTOReason.selectByIndex(1);
                SharedProperties.driver.findElement(By.xpath("//*[@value = 'Initiate RTO']")).click();
                Alert alert = SharedProperties.driver.switchTo().alert();
                alert.accept();
                Thread.sleep(2000);
                alert.accept();
                SharedProperties.driver.findElement(By.linkText("Search SO")).click();
                Thread.sleep(2000);
                SharedProperties.driver.findElement(By.xpath("//*[@name = 'shippingOrderGatewayId']")).sendKeys(shippingOrderId);
                SharedProperties.driver.findElement(By.xpath("//*[@value = 'Search Orders']")).click();
                String parentWindowId = SharedProperties.driver.getWindowHandle();
                SharedProperties.driver.findElement(By.linkText("Create Auto RTO Booking")).click();
                Set<String> windowIds = SharedProperties.driver.getWindowHandles();
                for(String window : windowIds)
                {

                    if(window.equalsIgnoreCase(parentWindowId))
                    {
                        continue;

                    }

                    SharedProperties.driver.switchTo().window(window);
                    Thread.sleep(3000);
                    SharedProperties.driver.findElement(By.xpath("//*[@value = 'Search']")).click();
                    String rpIdText = SharedProperties.driver.findElement(By.xpath("/html/body/div/div[2]/table/tbody/tr/td[2]")).getText();

                    System.out.println(rpIdText.substring(0, 15));
                    String rpIdValue =  rpIdText.substring(0, 15);
                    String winId = SharedProperties.driver.getWindowHandle();
                    SharedProperties.driver.findElement(By.xpath("//*[@class = 'approve']")).click();

                    SharedProperties.driver.findElement(By.linkText("Warehouse")).click();
                    SharedProperties.driver.findElement(By.linkText("RPWarehouse Checkedin")) .click();
                    SharedProperties.driver.findElement(By.xpath("//*[@name = 'reversePickupId']")).sendKeys(rpIdValue);
                    SharedProperties.driver.findElement(By.xpath("//*[@value = 'search']")).click();
                    rtoBarcode = fetchBarcodeRTO.getBarcode(shippingOrderId.substring(0, 8), null);
                    SharedProperties.driver.findElement(By.xpath("//*[@class = 'barcode-text']")).sendKeys(rtoBarcode);
                    WebElement condition = SharedProperties.driver.findElement(By.xpath("//*[@name = 'rpLineItems[0].warehouseReceivedCondition']"));
                    Select selectCondition = new Select(condition);
                    selectCondition.selectByVisibleText("Good");
                    SharedProperties.driver.findElement(By.xpath("//*[@class = 'save']")).click();
                    Thread.sleep(2000);
                    SharedProperties.driver.findElement(By.xpath("/html/body/div/div[2]/div[5]/form/div[2]/a/span")).click();
                    fetchBarcodeRTO.getBarcode(null, rtoBarcode);





                }







            }




        }


    }


    public static void main(String[] args) throws Exception {

        DwarkaStoreRTOCheckin test = new DwarkaStoreRTOCheckin();

        test.testDwarkaStoreRTOCheckin();


    }

}
