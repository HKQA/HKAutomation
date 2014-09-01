package com.hk.orderCheckout;


import com.hk.aquaElementLocators.*;
import com.hk.brightElementLocators.BrightHome;
import com.hk.brightElementLocators.CheckoutOrders;
import com.hk.orderCheckoutDto.BrightDetails;
import com.hk.commonProperties.SharedProperties;

import com.hk.orderCheckoutDto.SoDetails;
import com.hk.orderCheckoutDto.SoDetailsDTO;
import com.hk.property.PropertyHelper;
import com.hk.util.AutoStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Nitin Kukna
 * Date: 8/1/14
 * Time: 6:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class variantCheckout /*extends ExistingOnlineOrder*/ {
    String AdminBaseURL;
    String browser;
    private int delay;

    LoginPageAdmin loginpage = new LoginPageAdmin();
    PrintPrickOrders printprick = new PrintPrickOrders();
    CheckoutOrders checkoutorders = new CheckoutOrders();
    SharedProperties sharedproperties = new SharedProperties();
    BrightHome brighthome = new BrightHome();
    CreateUpdateShipment createupdateshipment = new CreateUpdateShipment();
    ShipmentAwaitingQueue shipmentawaitingueue = new ShipmentAwaitingQueue();
    DeliveryAwaitingQueue deliveryawaitingqueue = new DeliveryAwaitingQueue();
    AdminHome adminhome = new AdminHome();

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
                finalObjectString.addAll(readexcel.mainReadFromExcelIterator(PropertyH
                elper.readProperty("LoginExcel")));
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

        SharedProperties.openBrowser(AdminBaseURL, browser);
        SharedProperties.sendKeys(loginpage.getUserName(), PropertyHelper.readProperty("email_id"), SharedProperties.driver);
        SharedProperties.sendKeys(loginpage.getPassword(), PropertyHelper.readProperty("password"), SharedProperties.driver);
        SharedProperties.Click(loginpage.getLoginbtn(), SharedProperties.driver);

        for (String shippingOrderId : SoDetails.Sodetails().getShippingOrderIdList()) {

            if (SoDetails.soDetailsdto.getWarehouseId().contains("10")) {
                System.out.print("\n Selected GGN Aqua Warehouse");

                ((JavascriptExecutor) SharedProperties.driver).executeScript("$('select[id=\"selectWHForm\"]').click();");
                new Select(SharedProperties.driver.findElement(By.id("//*[@id=\"selectWHForm\"]/select"))).selectByVisibleText("GGN Aqua Warehouse");
                SharedProperties.Click(adminhome.getSaveBtn(), SharedProperties.driver);

            } else {
                System.out.print("\n MUM Aqua Warehouse");

                ((JavascriptExecutor) SharedProperties.driver).executeScript("$('select[id=\"selectWHForm\"]').click();");
                new Select(SharedProperties.driver.findElement(By.xpath("//*[@id=\"selectWHForm\"]/select"))).selectByVisibleText("MUM Aqua Warehouse");
                SharedProperties.Click(adminhome.getSaveBtn(), SharedProperties.driver);

            }


            SharedProperties.clickWithCss(printprick.getPrintPrickLink(), SharedProperties.driver);
            SharedProperties.Click(printprick.getOrderFilters(), SharedProperties.driver);
            Thread.sleep(2000);
            SharedProperties.sendKeys(printprick.getSoGatewayOrderIdTxt(), shippingOrderId, SharedProperties.driver);
            SharedProperties.Click(printprick.getBoGatewaySearchBtn(), SharedProperties.driver);
            Thread.sleep(3000);
            /*String SoId = SharedProperties.driver.findElement(By.xpath("/*//*[@id=\"shippingOrderDetail-2313803\"]/div[5]/strong")).getText();
            Thread.sleep(3000);

            */
            SharedProperties.Class(printprick.getCheckboxBo(), SharedProperties.driver);
            SharedProperties.Click(printprick.getBatchPrintBtn(), SharedProperties.driver);
            Thread.sleep(5000);
            sharedproperties.pressEnterSafe();
            Thread.sleep(3000);
            SharedProperties.Class(printprick.getCheckboxBo(), SharedProperties.driver);
            Thread.sleep(2000);
            SharedProperties.Click(printprick.getJobDoneClearQueBtn(), SharedProperties.driver);
            Thread.sleep(4000);

            System.out.print("\n Aqua using SO1: " + SoDetails.soDetailsdto.getShippingOrderIdList());
            System.out.print("\n Aqua using SO2: " + SoDetails.Sodetails().getShippingOrderIdList());

            //check for warehouse first
            for (String foreignSiCli : BrightDetails.ForeignSiCli().getForeignSoGatewayIdList()) {

                System.out.print("\n Bright Foreign SO2: " + BrightDetails.foreignSiCliDTO.getForeignSoGatewayIdList());
                System.out.print("\n using barcode2: " + BrightDetails.foreignSiCliDTO.getForeignBarcodeList());
                SharedProperties.driver.navigate().to(PropertyHelper.readProperty("brightUrl"));

                SharedProperties.sendKeys(loginpage.getUserName(), PropertyHelper.readProperty("email_id"), SharedProperties.driver);
                SharedProperties.sendKeys(brighthome.getPassWd(), PropertyHelper.readProperty("password"), SharedProperties.driver);
                SharedProperties.Click(brighthome.getLoginBtn(), SharedProperties.driver);
                if (SoDetails.soDetailsdto.getWarehouseId().contains("10")) {
                    System.out.print("\n GGN Bright Warehouse");

                    ((JavascriptExecutor) SharedProperties.driver).executeScript("$('select[id=\"selectWHForm\"]').click();");
                    new Select(SharedProperties.driver.findElement(By.id("//*[@id=\"selectWHForm\"]/select"))).selectByVisibleText("GGN Bright Warehouse");
                    SharedProperties.Click(adminhome.getSaveBtn(), SharedProperties.driver);

                } else {
                    System.out.print("\n MUM Bright Warehouse");

                    ((JavascriptExecutor) SharedProperties.driver).executeScript("$('select[id=\"selectWHForm\"]').click();");
                    new Select(SharedProperties.driver.findElement(By.xpath("//*[@id=\"selectWHForm\"]/select"))).selectByVisibleText("MUM Bright Warehouse");
                    SharedProperties.Click(adminhome.getSaveBtn(), SharedProperties.driver);

                }
                SharedProperties.clickWithCss(checkoutorders.getCheckoutOrder(), SharedProperties.driver);
                SharedProperties.sendKeys(checkoutorders.getCheckoutOrderTxt(), foreignSiCli, SharedProperties.driver);
                SharedProperties.Click(checkoutorders.getCheckoutOrderBtn(), SharedProperties.driver);


                for (String barcode : BrightDetails.foreignSiCliDTO.getForeignBarcodeList()) {

                    SharedProperties.sendKeys(checkoutorders.getCheckoutOrderBar(), barcode, SharedProperties.driver);
                    Thread.sleep(2000);
                    SharedProperties.driver.findElement(By.xpath("//*[@id=\"upc\"]")).sendKeys(Keys.ENTER);
                }
            }

            SharedProperties.driver.navigate().to(PropertyHelper.readProperty("adminUrl"));
            SharedProperties.sendKeys(loginpage.getUserName(), PropertyHelper.readProperty("email_id"), SharedProperties.driver);
            SharedProperties.sendKeys(loginpage.getPassword(), PropertyHelper.readProperty("password"), SharedProperties.driver);
            Thread.sleep(3000);
            SharedProperties.Click(loginpage.getLoginbtn(), SharedProperties.driver);
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

