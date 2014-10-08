package com.hk.orderCheckout;


import com.hk.aquaElementLocators.*;
import com.hk.brightElementLocators.BrightHome;
import com.hk.brightElementLocators.CheckoutOrders;
import com.hk.commonProperties.SharedProperties;
import com.hk.excel.ExcelServiceImplOld;
import com.hk.orderCheckoutDto.BrightDetails;
import com.hk.orderCheckoutDto.SoDetails;
import com.hk.orderCheckoutDto.SoDetailsDTO;
import com.hk.orderPlacement.ExistingOnlineOrder;
import com.hk.property.PropertyHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Nitin Kukna
 * Date: 8/1/14
 * Time: 6:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class variantCheckout /*extends ExistingOnlineOrder */{
    String AdminBaseURL;
    String browser;

    LoginPageAdmin loginpage = new LoginPageAdmin();
    PrintPrickOrders printprick = new PrintPrickOrders();
    CheckoutOrders checkoutorders = new CheckoutOrders();
    SharedProperties sharedproperties = new SharedProperties();
    BrightHome brighthome = new BrightHome();
    CreateUpdateShipment createupdateshipment = new CreateUpdateShipment();
    ShipmentAwaitingQueue shipmentawaitingueue = new ShipmentAwaitingQueue();
    DeliveryAwaitingQueue deliveryawaitingqueue = new DeliveryAwaitingQueue();
    AdminHome adminhome = new AdminHome();
    ExistingOnlineOrder EOO = new ExistingOnlineOrder();
    ExcelServiceImplOld readexcel = new ExcelServiceImplOld();


    @Parameters({"AdminBaseURL", "Browser"})
    @BeforeClass
    public void AdminUrlService(String AdminBaseURL, String Browser) {
        this.AdminBaseURL = AdminBaseURL;
        this.browser = Browser;
    }


    /*@DataProvider(name = "VariantCheckoutData")
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

    @Test(/*dataProvider = "VariantCheckoutData", */enabled = true)
    public void variantCheckout() throws InterruptedException, IOException, Exception {
        /*EOO.login(1L);*/


        for (SoDetailsDTO soDetailsDTO : SoDetails.Sodetails()) {
            SharedProperties.openBrowser(AdminBaseURL, browser);
            SharedProperties.sendKeys(loginpage.getUserName(), "saurabh.nagpal@healthkart.com", SharedProperties.driver);
            SharedProperties.sendKeys(loginpage.getPassword(), "abcde12", SharedProperties.driver);
            SharedProperties.Click(loginpage.getLoginbtn(), SharedProperties.driver);

            String shippingOrderId = soDetailsDTO.getSoGatewayOrderId();
            Integer warehouseId = soDetailsDTO.getWarehouseId();
            System.out.print("\n Warehouse id:- " + warehouseId);


            if (warehouseId.equals(10)) {
                System.out.print("\n Selected GGN Aqua Warehouse");

                WebElement WarehouseDropDownList = SharedProperties.driver.findElement(By.xpath("//*[@id=\"selectWHForm\"]/select"));
                Select clickWarehouse = new Select(WarehouseDropDownList);
                clickWarehouse.selectByVisibleText("GGN Aqua Warehouse");
                SharedProperties.Click(adminhome.getSaveBtn(), SharedProperties.driver);
                Thread.sleep(2000);

            } else {
                System.out.print("\n MUM Aqua Warehouse");
                // SharedProperties.clickWithCss(adminhome.getAdminHomeLink(), SharedProperties.driver);
                Thread.sleep(2000);

                WebElement WarehouseDropDownList = SharedProperties.driver.findElement(By.xpath("//*[@id=\"selectWHForm\"]/select"));
                Select clickWarehouse = new Select(WarehouseDropDownList);
                clickWarehouse.selectByVisibleText("MUM Aqua Warehouse");
                SharedProperties.Click(adminhome.getSaveBtn(), SharedProperties.driver);
                Thread.sleep(2000);

            }


            SharedProperties.clickWithCss(printprick.getPrintPrickLink(), SharedProperties.driver);
            SharedProperties.Click(printprick.getOrderFilters(), SharedProperties.driver);
            Thread.sleep(2000);
            SharedProperties.sendKeys(printprick.getSoGatewayOrderIdTxt(), shippingOrderId, SharedProperties.driver);
            SharedProperties.Click(printprick.getBoGatewaySearchBtn(), SharedProperties.driver);
            Thread.sleep(3000);


            SharedProperties.Class(printprick.getCheckboxBo(), SharedProperties.driver);
            SharedProperties.Click(printprick.getBatchPrintBtn(), SharedProperties.driver);
            Thread.sleep(5000);
            sharedproperties.pressEnterSafe();
            Thread.sleep(3000);
            SharedProperties.Class(printprick.getCheckboxBo(), SharedProperties.driver);
            Thread.sleep(2000);
            SharedProperties.Click(printprick.getJobDoneClearQueBtn(), SharedProperties.driver);
            Thread.sleep(4000);

            System.out.print("\n Aqua using SO1: " + shippingOrderId);

            //check for warehouse first
            SharedProperties.driver.navigate().to(PropertyHelper.readProperty("brightUrl"));
            /*SharedProperties.sendKeys(loginpage.getUserName(), "saurabh.nagpal@healthkart.com", SharedProperties.driver);
            SharedProperties.sendKeys(brighthome.getPassWd(), "abcde12", SharedProperties.driver);
            SharedProperties.Click(brighthome.getLoginBtn(), SharedProperties.driver);
            */
            SharedProperties.Click("/html/body/div/div[1]/div/ul/li[8]/a", SharedProperties.driver);

            SharedProperties.sendKeys("/html/body/div[2]/div[2]/form/fieldset/ul/div/li[1]/input", BrightDetails.getForeignSiCliDTO(shippingOrderId).getForeignSoGatewayId(), SharedProperties.driver);
            SharedProperties.Click("/html/body/div[2]/div[2]/form/fieldset/div/input", SharedProperties.driver);
            String ForeignWarehouseId = SharedProperties.driver.findElement(By.xpath("/*//*[@id=\"shippingOrderDetail-" + BrightDetails.getForeignSiCliDTO(shippingOrderId).getForeignSoId() + "\"]/div[1]/strong[2]")).getText();
            SharedProperties.Click("/html/body/div[2]/div[1]/div/ul/li[1]/a", SharedProperties.driver);


            if (ForeignWarehouseId.equals("GGN Bright Warehouse")) {
                System.out.print("\n GGN Bright Warehouse");

                WebElement WarehouseDropDownList = SharedProperties.driver.findElement(By.xpath("/*//*[@id=\"selectWHForm\"]/select"));
                Select clickWarehouse = new Select(WarehouseDropDownList);
                clickWarehouse.selectByVisibleText("GGN Bright Warehouse");
                SharedProperties.Click(adminhome.getSaveBtn(), SharedProperties.driver);
                Thread.sleep(2000);

            } else {
                System.out.print("\n MUM Bright Warehouse");

                WebElement WarehouseDropDownList = SharedProperties.driver.findElement(By.xpath("/*//*[@id=\"selectWHForm\"]/select"));
                Select clickWarehouse = new Select(WarehouseDropDownList);
                clickWarehouse.selectByVisibleText("MUM Bright Warehouse");
                SharedProperties.Click(adminhome.getSaveBtn(), SharedProperties.driver);
                Thread.sleep(2000);
            }
            SharedProperties.clickWithCss(checkoutorders.getCheckoutOrder(), SharedProperties.driver);
            SharedProperties.sendKeys(checkoutorders.getCheckoutOrderTxt(), BrightDetails.getForeignSiCliDTO(shippingOrderId).getForeignSoGatewayId(), SharedProperties.driver);
            SharedProperties.Click(checkoutorders.getCheckoutOrderBtn(), SharedProperties.driver);

            //WebElement freebeeButton = SharedProperties.driver.findElement(By.xpath("//*[@id=\"freeCartLineItemTable\"]/tbody/tr/td[5]/form/input[5]"));
            if (SharedProperties.driver.findElements(By.xpath("//*[@id=\"freeCartLineItemTable\"]/tbody/tr/td[5]/form/input[5]")).size() == 0) {
                for (String barcode : BrightDetails.getForeignSiCliDTO(shippingOrderId).getForeignBarcodeList()) {
                    System.out.print("\n Foreign SO selected:- " + BrightDetails.getForeignSiCliDTO(shippingOrderId).getForeignSoId());
                    System.out.print("\n ForeignGatewayID selected:- " + BrightDetails.getForeignSiCliDTO(shippingOrderId).getForeignSoGatewayId());
                    System.out.print("\n Barcode selected:- " + barcode);

                    SharedProperties.sendKeys(checkoutorders.getCheckoutOrderBar(), barcode, SharedProperties.driver);
                    Thread.sleep(2000);
                    SharedProperties.driver.findElement(By.xpath("//*[@id=\"upc\"]")).sendKeys(Keys.ENTER);
                }
            } else {
                SharedProperties.driver.findElement(By.xpath("//*[@id=\"freeCartLineItemTable\"]/tbody/tr/td[5]/form/input[5]")).click();
                Thread.sleep(3000);
                for (String barcode : BrightDetails.getForeignSiCliDTO(shippingOrderId).getForeignBarcodeList()) {

                    SharedProperties.sendKeys(checkoutorders.getCheckoutOrderBar(), barcode, SharedProperties.driver);
                    Thread.sleep(2000);
                    SharedProperties.driver.findElement(By.xpath("//*[@id=\"upc\"]")).sendKeys(Keys.ENTER);
                }


            }

            SharedProperties.driver.navigate().to(PropertyHelper.readProperty("adminUrl"));
            SharedProperties.Click(adminhome.getWareHouseLink(), SharedProperties.driver);
            Thread.sleep(2000);
            SharedProperties.Click(createupdateshipment.getCreateUpdateShipmentLink(), SharedProperties.driver);
            SharedProperties.sendKeys(createupdateshipment.getSoGatewayIdTxt(), shippingOrderId, SharedProperties.driver);
            Thread.sleep(3000);
            SharedProperties.Click(createupdateshipment.getSearchBtn(), SharedProperties.driver);
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

            //SharedProperties.clickWithCss(createupdateshipment.getSaveCreateUpdateShipmentBtn(), SharedProperties.driver);

            SharedProperties.Click(adminhome.getWareHouseLink(), SharedProperties.driver);
            SharedProperties.Click(shipmentawaitingueue.getShipmentAwaitingQueueLink(), SharedProperties.driver);
            SharedProperties.sendKeys(shipmentawaitingueue.getGatewayId(), shippingOrderId, SharedProperties.driver);
            SharedProperties.Click(shipmentawaitingueue.getSearchBtn(), SharedProperties.driver);
            SharedProperties.Class(shipmentawaitingueue.getCheckBox(), SharedProperties.driver);
            Thread.sleep(3000);
            SharedProperties.Click(shipmentawaitingueue.getMarkedOrdersAsShipped(), SharedProperties.driver);

            System.out.print("\n ************* SO Shipped *************");


            SharedProperties.Click(adminhome.getWareHouseLink(), SharedProperties.driver);
            SharedProperties.Click(deliveryawaitingqueue.getDeliveryAwaitingQueueLink(), SharedProperties.driver);
            SharedProperties.sendKeys(deliveryawaitingqueue.getGatewayOrderIdTxt(), shippingOrderId, SharedProperties.driver);
            SharedProperties.Click(deliveryawaitingqueue.getSearchBtn(), SharedProperties.driver);
            SharedProperties.Class(deliveryawaitingqueue.getCheckBox(), SharedProperties.driver);
            Thread.sleep(3000);
            SharedProperties.Click(deliveryawaitingqueue.getMarkOrdersAsDelivered(), SharedProperties.driver);

            System.out.print("\n ************* SO Delivered *************");
        }
    }


}

