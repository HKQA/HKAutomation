package com.hk.scaledupOrderPlacement;

import com.hk.aquaElementLocators.*;
import com.hk.brightElementLocators.CheckoutOrders;
import com.hk.commonProperties.SharedProperties;
import com.hk.orderCheckoutDto.BrightDetails;
import com.hk.orderCheckoutDto.SoDetails;
import com.hk.orderCheckoutDto.SoDetailsDTO;
import com.hk.util.TestUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 12/30/14
 * Time: 11:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class MultiVariantOrderCheckout {

    SoDetails soDetails = new SoDetails();
    LoginPageAdmin loginPage = new LoginPageAdmin();
    AdminHome adminHome = new AdminHome();
    PrintPrickOrders printPrick = new PrintPrickOrders();
    SharedProperties sharedProperties = new SharedProperties();
    CheckoutOrders checkoutOrders = new CheckoutOrders();
    AppSpecificReusableMethods reusableMethods = new AppSpecificReusableMethods();
    CreateUpdateShipment createUpdateShipment = new CreateUpdateShipment();
    ShipmentAwaitingQueue shipmentAwaitingQueue = new ShipmentAwaitingQueue();
    DeliveryAwaitingQueue deliveryAwaitingQueue = new DeliveryAwaitingQueue();
    StoreCheckoutBarcodes storeBarcodes = new StoreCheckoutBarcodes();
    DoFlip doFlip = new DoFlip();

    public void variantCheckout() throws Exception {

        List<String> storeCheckoutProductIds = new ArrayList<String>();
        List<String> storeCheckoutBarcodes = new ArrayList<String>();
        int rowCount = 1;

        String shippingOrderId = null;
        int warehouseId = 0;
        /*for(SoDetailsDTO soDetailsDTO : soDetails.Sodetails())
        {

            shippingOrderId = soDetailsDTO.getSoGatewayOrderId();
            warehouseId = soDetailsDTO.getWarehouseId();
            //break;

        } */


        //SharedProperties.openBrowser(TestUtil.getAdminURL(), TestUtil.getBrowser());
        SharedProperties.driver.navigate().to(TestUtil.getAdminURL());
        Thread.sleep(3000);
        SharedProperties.sendKeys(loginPage.getUserName(), TestUtil.getAdmin_User(), SharedProperties.driver);
        SharedProperties.sendKeys(loginPage.getPassword(), TestUtil.getAdmin_Password(), SharedProperties.driver);
        SharedProperties.Click(loginPage.getLoginbtn(), SharedProperties.driver);

        for(SoDetailsDTO soDetailsDTO : soDetails.Sodetails())
        {

            shippingOrderId = soDetailsDTO.getSoGatewayOrderId();
            warehouseId = soDetailsDTO.getWarehouseId();

            System.out.println("Shipping Order Id = "+shippingOrderId);

            System.out.println("Warehouse Id = " + warehouseId);

            SharedProperties.driver.findElement(By.linkText("Admin Home")).click();
            Thread.sleep(3000);

        if(warehouseId == 301)
        {
            System.out.println("Selected Punjabi Bagh Store");
            WebElement WarehouseDropDownList = SharedProperties.driver.findElement(By.xpath("//*[@id=\"selectWHForm\"]/select"));
            Select clickWarehouse = new Select(WarehouseDropDownList);
            clickWarehouse.selectByVisibleText("DEL Punjabi Bagh Aqua Store");
            SharedProperties.Click(adminHome.getSaveBtn(), SharedProperties.driver);
            Thread.sleep(2000);
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
            continue;









        }

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

            /*if(TestUtil.getExecuteFlip().equalsIgnoreCase("Y"))
            {
               doFlip.doFlip(shippingOrderId);

            } */


            SharedProperties.clickWithCss(printPrick.getPrintPrickLink(), SharedProperties.driver);
            Thread.sleep(3000);
            SharedProperties.Click(printPrick.getOrderFilters(), SharedProperties.driver);
            Thread.sleep(2000);
            SharedProperties.sendKeys(printPrick.getSoGatewayOrderIdTxt(), shippingOrderId, SharedProperties.driver);
            Thread.sleep(3000);
            SharedProperties.Click(printPrick.getBoGatewaySearchBtn(), SharedProperties.driver);
            Thread.sleep(3000);

            SharedProperties.Class(printPrick.getCheckboxBo(), SharedProperties.driver);
            SharedProperties.Click(printPrick.getBatchPrintBtn(), SharedProperties.driver);
            Thread.sleep(5000);
            sharedProperties.pressEnterSafe();
            Thread.sleep(3000);
            SharedProperties.Class(printPrick.getCheckboxBo(), SharedProperties.driver);
            Thread.sleep(2000);
            SharedProperties.Click(printPrick.getJobDoneClearQueBtn(), SharedProperties.driver);
            Thread.sleep(4000);
            System.out.print("\n Aqua using SO1: " + shippingOrderId);

        System.out.println(BrightDetails.getForeignSiCliDTO(shippingOrderId).getForeignBarcodeList().size() );






        SharedProperties.driver.navigate().to(TestUtil.getBright_URL());
        Thread.sleep(2000);
        SharedProperties.Click("/html/body/div/div[1]/div/ul/li[8]/a", SharedProperties.driver);
        SharedProperties.sendKeys("/html/body/div[2]/div[2]/form/fieldset/ul/div/li[1]/input", BrightDetails.getForeignSiCliDTO(shippingOrderId).getForeignSoGatewayId(), SharedProperties.driver);
        SharedProperties.Click("/html/body/div[2]/div[2]/form/fieldset/div/input", SharedProperties.driver);
        String ForeignWarehouseId = SharedProperties.driver.findElement(By.xpath("/*//*[@id=\"shippingOrderDetail-" + BrightDetails.getForeignSiCliDTO(shippingOrderId).getForeignSoId() + "\"]/div[1]/strong[2]")).getText();
        SharedProperties.Click("/html/body/div[2]/div[1]/div/ul/li[1]/a", SharedProperties.driver);
        if (ForeignWarehouseId.equals("GGN Bright Warehouse"))
        {
            System.out.print("\n GGN Bright Warehouse");
            WebElement WarehouseDropDownList = SharedProperties.driver.findElement(By.xpath("/*//*[@id=\"selectWHForm\"]/select"));
            Select clickWarehouse = new Select(WarehouseDropDownList);
            clickWarehouse.selectByVisibleText("GGN Bright Warehouse");
            SharedProperties.Click(adminHome.getSaveBtn(), SharedProperties.driver);
            Thread.sleep(2000);


        }
        else
        {
            System.out.print("\n MUM Bright Warehouse");
            WebElement WarehouseDropDownList = SharedProperties.driver.findElement(By.xpath("/*//*[@id=\"selectWHForm\"]/select"));
            Select clickWarehouse = new Select(WarehouseDropDownList);
            clickWarehouse.selectByVisibleText("MUM Bright Warehouse");
            SharedProperties.Click(adminHome.getSaveBtn(), SharedProperties.driver);
            Thread.sleep(2000);


        }

        SharedProperties.clickWithCss(checkoutOrders.getCheckoutOrder(), SharedProperties.driver);
        SharedProperties.sendKeys(checkoutOrders.getCheckoutOrderTxt(), BrightDetails.getForeignSiCliDTO(shippingOrderId).getForeignSoGatewayId(), SharedProperties.driver);
        SharedProperties.Click(checkoutOrders.getCheckoutOrderBtn(), SharedProperties.driver);

        for (String barcode : BrightDetails.getForeignSiCliDTO(shippingOrderId).getForeignBarcodeList())
        {

            System.out.print("\n Barcode selected:- " + barcode);
            SharedProperties.sendKeys(checkoutOrders.getCheckoutOrderBar(), barcode, SharedProperties.driver);
            Thread.sleep(2000);
            SharedProperties.driver.findElement(By.xpath("//*[@id=\"upc\"]")).sendKeys(Keys.ENTER);

        }

        SharedProperties.driver.navigate().to(TestUtil.getAdminURL());
        SharedProperties.Click(adminHome.getWareHouseLink(), SharedProperties.driver);
        Thread.sleep(2000);
        SharedProperties.Click(createUpdateShipment.getCreateUpdateShipmentLink(), SharedProperties.driver);
        SharedProperties.sendKeys(createUpdateShipment.getSoGatewayIdTxt(), shippingOrderId, SharedProperties.driver);
        Thread.sleep(3000);
        SharedProperties.Click(createUpdateShipment.getSearchBtn(), SharedProperties.driver);
        new Select(SharedProperties.driver.findElement(By.xpath("//*[@id=\"boxSize\"]"))).selectByVisibleText("L");
        new Select(SharedProperties.driver.findElement(By.xpath("//*[@id=\"packer\"]"))).selectByVisibleText("L");
        new Select(SharedProperties.driver.findElement(By.xpath("//*[@id=\"picker\"]"))).selectByVisibleText("10");
        Thread.sleep(2000);
        SharedProperties.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Wait<WebDriver> wait = new FluentWait<WebDriver>(SharedProperties.driver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(5, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
        WebElement saveLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"validate\"]")));
        saveLink.click();

        System.out.print("\n ************* SO Packed *************");

        SharedProperties.Click(adminHome.getWareHouseLink(), SharedProperties.driver);
        SharedProperties.Click(shipmentAwaitingQueue.getShipmentAwaitingQueueLink(), SharedProperties.driver);
        SharedProperties.sendKeys(shipmentAwaitingQueue.getGatewayId(), shippingOrderId, SharedProperties.driver);
        SharedProperties.Click(shipmentAwaitingQueue.getSearchBtn(), SharedProperties.driver);
        SharedProperties.Class(shipmentAwaitingQueue.getCheckBox(), SharedProperties.driver);
        Thread.sleep(3000);
        SharedProperties.Click(shipmentAwaitingQueue.getMarkedOrdersAsShipped(), SharedProperties.driver);

        System.out.print("\n ************* SO Shipped *************");

        SharedProperties.Click(adminHome.getWareHouseLink(), SharedProperties.driver);
        SharedProperties.Click(deliveryAwaitingQueue.getDeliveryAwaitingQueueLink(), SharedProperties.driver);
        SharedProperties.sendKeys(deliveryAwaitingQueue.getGatewayOrderIdTxt(), shippingOrderId, SharedProperties.driver);
        SharedProperties.Click(deliveryAwaitingQueue.getSearchBtn(), SharedProperties.driver);
        SharedProperties.Class(deliveryAwaitingQueue.getCheckBox(), SharedProperties.driver);
        Thread.sleep(3000);
        SharedProperties.Click(deliveryAwaitingQueue.getMarkOrdersAsDelivered(), SharedProperties.driver);
        System.out.print("\n ************* SO Delivered *************");
        Thread.sleep(5000);



        }




    }


}
