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
import org.testng.Assert;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 1/15/15
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class DoRTO {

    LoginPageAdmin loginPage = new LoginPageAdmin();
    SoDetails soDetails = new SoDetails();
    AdminHome adminHome = new AdminHome();
    PrintPrickOrders printPrick = new PrintPrickOrders();
    SharedProperties sharedProperties = new SharedProperties();
    CheckoutOrders checkoutOrders = new CheckoutOrders();
    CreateUpdateShipment createUpdateShipment = new CreateUpdateShipment();
    ShipmentAwaitingQueue shipmentAwaitingQueue = new ShipmentAwaitingQueue();
    FetchBarcodeRTO fetchBarcodeRTO = new FetchBarcodeRTO();
    public static String gatewayOrderId ;
    DeliveryAwaitingQueue deliveryAwaitingQueue = new DeliveryAwaitingQueue();
    CodConfirmation codconfirmation = new CodConfirmation();

    public void doRTO() throws Exception {
        String shippingOrderId = null;
        String rtoBarcode = null;
        int warehouseId = 0;

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

            if(warehouseId == 1005)
            {
                System.out.print("\n Selected GGN Aqua Warehouse");
                WebElement WarehouseDropDownList = SharedProperties.driver.findElement(By.xpath("//*[@id=\"selectWHForm\"]/select"));
                Select clickWarehouse = new Select(WarehouseDropDownList);
                clickWarehouse.selectByVisibleText("Hyderabad Aqua CFA");
                SharedProperties.Click(adminHome.getSaveBtn(), SharedProperties.driver);
                Thread.sleep(2000);



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

            SharedProperties.clickWithCss(printPrick.getPrintPrickLink(), SharedProperties.driver);
            SharedProperties.Click(printPrick.getOrderFilters(), SharedProperties.driver);
            Thread.sleep(2000);
            SharedProperties.sendKeys(printPrick.getSoGatewayOrderIdTxt(), shippingOrderId, SharedProperties.driver);
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
            else if (ForeignWarehouseId.equals("Hyderabad Bright CFA"))
            {

                System.out.print("\n Hyderabad Bright CFA");
                WebElement WarehouseDropDownList = SharedProperties.driver.findElement(By.xpath("/*//*[@id=\"selectWHForm\"]/select"));
                Select clickWarehouse = new Select(WarehouseDropDownList);
                clickWarehouse.selectByVisibleText("Hyderabad Bright CFA");
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
                //SharedProperties.driver.findElement(By.linkText("Edit RPU")).click();

                /*
                 code for RTO needs to be entered here
                */
                String rpIdText = SharedProperties.driver.findElement(By.xpath("/html/body/div/div[2]/table/tbody/tr/td[2]")).getText();

                System.out.println(rpIdText.substring(0, 15));
                String rpIdValue =  rpIdText.substring(0, 15);
                String winId = SharedProperties.driver.getWindowHandle();
                SharedProperties.driver.findElement(By.xpath("//*[@class = 'approve']")).click();
                SharedProperties.driver.findElement(By.linkText("Master CRM")).click();
                Thread.sleep(2000);
                Set<String> winHandles = SharedProperties.driver.getWindowHandles();
                for(String winHandle : winHandles)
                {

                    if(winHandle.equalsIgnoreCase(parentWindowId) || winHandle.equalsIgnoreCase(winId))
                    {
                        continue;

                    }

                     SharedProperties.driver.switchTo().window(winHandle);
                    WebElement action = SharedProperties.driver.findElement(By.xpath("//*[@id=\"actionType\"]"));
                    Select selectRO = new Select(action);
                    selectRO.selectByVisibleText("Replacement Order");
                    SharedProperties.driver.findElement(By.xpath("//*[@value = 'Search']")).click();
                    WebElement roReason = SharedProperties.driver.findElement(By.xpath("//*[@name = 'replacementOrderReason']"));
                    Select selectROReason = new Select(roReason);
                    selectROReason.selectByVisibleText("Courier - RTO due to service unavailability of the courier.");
                    SharedProperties.driver.findElement(By.xpath("//*[@value = 'Generate Replacement Order']")).click();


                }
                //WebElement action = SharedProperties.driver.findElement(By.xpath("//*[@id=\"actionType\"]"));
                //Select selectRO = new Select(action);
                //selectRO.selectByVisibleText("Replacement Order");
                //SharedProperties.driver.findElement(By.xpath("//*[@value = 'Search']")).click();
                //WebElement roReason = SharedProperties.driver.findElement(By.xpath("//*[@name = 'replacementOrderReason']"));
                //Select selectROReason = new Select(roReason);
                //selectROReason.selectByVisibleText("Courier - RTO due to service unavailability of the courier.");
                //SharedProperties.driver.findElement(By.xpath("//*[@value = 'Generate Replacement Order']")).click();
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
                Thread.sleep(3000);
                SharedProperties.driver.findElement(By.linkText("Search BO")).click();
                SharedProperties.driver.findElement(By.xpath("//*[@name = 'gatewayOrderId']")).clear();
                SharedProperties.driver.findElement(By.xpath("//*[@name = 'gatewayOrderId']")).sendKeys(gatewayOrderId);
                SharedProperties.driver.findElement(By.xpath("//*[@value = 'Search Orders']")).click();
                Thread.sleep(2000);
                String newSOId= SharedProperties.driver.findElement(By.xpath("html/body/div[2]/div[2]/table[2]/tbody/tr/td[6]/table/tbody/tr/td/a[1]")).getText();
                System.out.println(newSOId);
                Thread.sleep(3000);
                SharedProperties.clickWithCss(printPrick.getPrintPrickLink(), SharedProperties.driver);
                SharedProperties.Click(printPrick.getOrderFilters(), SharedProperties.driver);
                Thread.sleep(2000);
                SharedProperties.sendKeys(printPrick.getSoGatewayOrderIdTxt(), newSOId, SharedProperties.driver);
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
                System.out.println(BrightDetails.getForeignSiCliDTO(newSOId).getForeignBarcodeList().size() );
                SharedProperties.driver.navigate().to(TestUtil.getBright_URL());
                Thread.sleep(2000);
                SharedProperties.Click("/html/body/div/div[1]/div/ul/li[8]/a", SharedProperties.driver);
                SharedProperties.sendKeys("/html/body/div[2]/div[2]/form/fieldset/ul/div/li[1]/input", BrightDetails.getForeignSiCliDTO(newSOId).getForeignSoGatewayId(), SharedProperties.driver);
                SharedProperties.Click("/html/body/div[2]/div[2]/form/fieldset/div/input", SharedProperties.driver);
                String ForeignWarehouseId1 = SharedProperties.driver.findElement(By.xpath("/*//*[@id=\"shippingOrderDetail-" + BrightDetails.getForeignSiCliDTO(newSOId).getForeignSoId() + "\"]/div[1]/strong[2]")).getText();
                SharedProperties.Click("/html/body/div[2]/div[1]/div/ul/li[1]/a", SharedProperties.driver);
                if (ForeignWarehouseId1.equals("GGN Bright Warehouse"))
                {
                    System.out.print("\n GGN Bright Warehouse");
                    WebElement WarehouseDropDownList = SharedProperties.driver.findElement(By.xpath("/*//*[@id=\"selectWHForm\"]/select"));
                    Select clickWarehouse = new Select(WarehouseDropDownList);
                    clickWarehouse.selectByVisibleText("GGN Bright Warehouse");
                    SharedProperties.Click(adminHome.getSaveBtn(), SharedProperties.driver);
                    Thread.sleep(2000);


                }
                else if (ForeignWarehouseId.equals("Hyderabad Bright CFA"))
                {

                    System.out.print("\n Hyderabad Bright CFA");
                    WebElement WarehouseDropDownList = SharedProperties.driver.findElement(By.xpath("/*//*[@id=\"selectWHForm\"]/select"));
                    Select clickWarehouse = new Select(WarehouseDropDownList);
                    clickWarehouse.selectByVisibleText("Hyderabad Bright CFA");
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
                SharedProperties.sendKeys(checkoutOrders.getCheckoutOrderTxt(), BrightDetails.getForeignSiCliDTO(newSOId).getForeignSoGatewayId(), SharedProperties.driver);
                SharedProperties.Click(checkoutOrders.getCheckoutOrderBtn(), SharedProperties.driver);

                for (String barcode : BrightDetails.getForeignSiCliDTO(newSOId).getForeignBarcodeList())
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
                SharedProperties.sendKeys(createUpdateShipment.getSoGatewayIdTxt(), newSOId, SharedProperties.driver);
                Thread.sleep(3000);
                SharedProperties.Click(createUpdateShipment.getSearchBtn(), SharedProperties.driver);
                new Select(SharedProperties.driver.findElement(By.xpath("//*[@id=\"boxSize\"]"))).selectByVisibleText("L");
                new Select(SharedProperties.driver.findElement(By.xpath("//*[@id=\"packer\"]"))).selectByVisibleText("L");
                new Select(SharedProperties.driver.findElement(By.xpath("//*[@id=\"picker\"]"))).selectByVisibleText("10");
                Thread.sleep(2000);
                SharedProperties.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                Wait<WebDriver> wait1 = new FluentWait<WebDriver>(SharedProperties.driver)
                        .withTimeout(30, TimeUnit.SECONDS)
                        .pollingEvery(5, TimeUnit.SECONDS)
                        .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
                WebElement saveLink1 = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"validate\"]")));
                saveLink1.click();

                System.out.print("\n ************* SO Packed *************");

                SharedProperties.Click(adminHome.getWareHouseLink(), SharedProperties.driver);
                SharedProperties.Click(shipmentAwaitingQueue.getShipmentAwaitingQueueLink(), SharedProperties.driver);
                SharedProperties.sendKeys(shipmentAwaitingQueue.getGatewayId(), newSOId, SharedProperties.driver);
                SharedProperties.Click(shipmentAwaitingQueue.getSearchBtn(), SharedProperties.driver);
                SharedProperties.Class(shipmentAwaitingQueue.getCheckBox(), SharedProperties.driver);
                Thread.sleep(3000);
                SharedProperties.Click(shipmentAwaitingQueue.getMarkedOrdersAsShipped(), SharedProperties.driver);

                System.out.print("\n ************* SO Shipped *************");

                SharedProperties.Click(adminHome.getWareHouseLink(), SharedProperties.driver);
                SharedProperties.Click(deliveryAwaitingQueue.getDeliveryAwaitingQueueLink(), SharedProperties.driver);
                SharedProperties.sendKeys(deliveryAwaitingQueue.getGatewayOrderIdTxt(), newSOId, SharedProperties.driver);
                SharedProperties.Click(deliveryAwaitingQueue.getSearchBtn(), SharedProperties.driver);
                SharedProperties.Class(deliveryAwaitingQueue.getCheckBox(), SharedProperties.driver);
                Thread.sleep(3000);
                SharedProperties.Click(deliveryAwaitingQueue.getMarkOrdersAsDelivered(), SharedProperties.driver);
                System.out.print("\n ************* SO Delivered *************");
                SharedProperties.driver.navigate().to(TestUtil.getCOD_Confirm_URL());
                SharedProperties.sendKeys(codconfirmation.getEmail(), TestUtil.getCOD_Confirm_User(), SharedProperties.driver);
                SharedProperties.sendKeys(codconfirmation.getPassword(), TestUtil.getCOD_Confirm_Password(), SharedProperties.driver);
                WebElement store = SharedProperties.driver.findElement(By.xpath("//*[@name = 'storeId']"));
                Select selectStore = new Select(store);
                selectStore.selectByVisibleText("Healthkart");
                SharedProperties.Click(codconfirmation.getLogin(), SharedProperties.driver);
                Thread.sleep(3000);
                SharedProperties.mouseHoverAndClick("//*[@id=\"cssmenu\"]/ul/li[6]/a/span", "//*[@id='cssmenu']/ul/li[6]/ul/li[1]/a/span", SharedProperties.driver );
                SharedProperties.driver.findElement(By.xpath("//*[@name = 'gatewayOrderId']")).sendKeys(gatewayOrderId);
                SharedProperties.driver.findElement(By.xpath("//*[@value = 'Search']")).click();
                boolean catalogStatusDelivered = SharedProperties.driver.findElement(By.xpath("html/body/div[5]/div/div/div[4]/div[2]/div/div[2]/div/div/div[2]/div[2]/div[2]/div/div/div[1]")).getText().contains("Delivered on");
                System.out.println(catalogStatusDelivered);
                boolean catalogStatusReturned = SharedProperties.driver.findElement(By.xpath("html/body/div[5]/div/div/div[4]/div[2]/div/div[2]/div/div/div[4]/div[2]/div[2]/div/div/div")).getText().contains("Returned");
                System.out.println(catalogStatusReturned);
                Thread.sleep(50000);
                //Assert.assertEquals(true, catalogStatusReturned);




                /*WebElement returnReason = SharedProperties.driver.findElement(By.xpath("//*[@class = 'reason-entered-select']"));
                Select selectReturnReason = new Select(returnReason);
                selectReturnReason.selectByIndex(2);
                WebElement csAction = SharedProperties.driver.findElement(By.xpath("//*[@name = 'rpLineItems[0].actionTaken']"));
                Select selectCSAction = new Select(csAction);
                selectCSAction.selectByIndex(1); */





            }




        }


    }

}
