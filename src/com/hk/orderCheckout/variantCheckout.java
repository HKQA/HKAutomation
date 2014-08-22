package com.hk.orderCheckout;


import com.hk.aquaElementLocators.*;
import com.hk.brightElementLocators.BrightHome;
import com.hk.brightElementLocators.CheckoutOrders;
import com.hk.orderCheckoutDto.BrightDetails;
import com.hk.commonProperties.SharedProperties;

import com.hk.orderCheckoutDto.SoDetails;
import com.hk.orderCheckoutDto.SoDetailsDTO;
import com.hk.property.PropertyHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

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
        SharedProperties.sendKeys(loginpage.getUserName(), "nitin.kukna@healthkart.com", SharedProperties.driver);
        SharedProperties.sendKeys(loginpage.getPassword(), "23031988", SharedProperties.driver);
        SharedProperties.Click(loginpage.getLoginbtn(), SharedProperties.driver);

        //Select WareHouse according to your order from database or with text
        for (String shippingOrderId : SoDetails.Sodetails().getShippingOrderIdList()) {


            SharedProperties.clickWithCss(printprick.getPrintPrickLink(), SharedProperties.driver);
            SharedProperties.Click(printprick.getOrderFilters(), SharedProperties.driver);
            Thread.sleep(2000);
            SharedProperties.sendKeys(printprick.getSoGatewayOrderIdTxt(), shippingOrderId, SharedProperties.driver);
            SharedProperties.Click(printprick.getBoGatewaySearchBtn(), SharedProperties.driver);
            Thread.sleep(3000);
            /*String SoId = SharedProperties.driver.findElement(By.xpath("/*//*[@id=\"shippingOrderDetail-2313803\"]/div[5]/strong")).getText();
            Thread.sleep(3000);
            */SharedProperties.Class(printprick.getCheckboxBo(), SharedProperties.driver);
            SharedProperties.Click(printprick.getBatchPrintBtn(), SharedProperties.driver);
            Thread.sleep(5000);
            sharedproperties.pressEnterSafe();
            Thread.sleep(3000);
            SharedProperties.Class(printprick.getCheckboxBo(), SharedProperties.driver);
            Thread.sleep(2000);
            SharedProperties.Click(printprick.getJobDoneClearQueBtn(), SharedProperties.driver);
            Thread.sleep(4000);


            System.out.print("SO again: - " + SoDetails.soDetailsdto.getShippingOrderIdList());
            //check for warehouse first
            for (String foreignSiCli : BrightDetails.ForeignSiCli().getForeignSoGatewayIdList()) {

                SharedProperties.driver.navigate().to(PropertyHelper.readProperty("brightUrl"));

                SharedProperties.sendKeys(loginpage.getUserName(), "nitin.kukna@healthkart.com", SharedProperties.driver);
                SharedProperties.sendKeys(loginpage.getPassword(), "23031988", SharedProperties.driver);
                SharedProperties.Click(brighthome.getLoginBtn(), SharedProperties.driver);
                SharedProperties.Click(checkoutorders.getCheckoutOrder(), SharedProperties.driver);
                SharedProperties.sendKeys(checkoutorders.getCheckoutOrderTxt(), foreignSiCli, SharedProperties.driver);

                for (String barcode : BrightDetails.ForeignSiCli().getForeignBarcodeList()) {
                    System.out.print("Using Barcode : " + barcode);
                    SharedProperties.sendKeys(checkoutorders.getCheckoutOrderBar(), barcode, SharedProperties.driver);
                    //Press Enter
                }
            }

            SharedProperties.driver.navigate().to(PropertyHelper.readProperty("adminUrl"));
            SharedProperties.sendKeys(loginpage.getUserName(), "saurabh.nagpal@healthkart.com", SharedProperties.driver);
            SharedProperties.sendKeys(brighthome.getPassWd(), "abcde12", SharedProperties.driver);
            SharedProperties.Click(createupdateshipment.getCreateUpdateShipmentLink(), SharedProperties.driver);
            SharedProperties.sendKeys(createupdateshipment.getSoGatewayIdTxt(), shippingOrderId, SharedProperties.driver);
            SharedProperties.Click(createupdateshipment.getSearchBtn(), SharedProperties.driver);
            new Select(SharedProperties.driver.findElement(By.xpath("//*[@id=\"boxSize\"]"))).selectByVisibleText("L");
            new Select(SharedProperties.driver.findElement(By.xpath("//*[@id=\"packer\"]"))).selectByVisibleText("L");
            new Select(SharedProperties.driver.findElement(By.xpath("//*[@id=\"picker\"]"))).selectByVisibleText("10");
            SharedProperties.clickWithCss(createupdateshipment.getSaveCreateUpdateShipmentBtn(), SharedProperties.driver);

            SharedProperties.Click(shipmentawaitingueue.getShipmentAwaitingQueueLink(), SharedProperties.driver);
            SharedProperties.sendKeys(shipmentawaitingueue.getGatewayId(), shippingOrderId, SharedProperties.driver);
            SharedProperties.Click(shipmentawaitingueue.getSearchBtn(), SharedProperties.driver);
            SharedProperties.Class(shipmentawaitingueue.getCheckBox(), SharedProperties.driver);
            SharedProperties.Click(shipmentawaitingueue.getMarkedOrdersAsShipped(), SharedProperties.driver);

            SharedProperties.Click(deliveryawaitingqueue.getDeliveryAwaitingQueueLink(), SharedProperties.driver);
            SharedProperties.sendKeys(deliveryawaitingqueue.getGatewayOrderIdTxt(), shippingOrderId, SharedProperties.driver);
            SharedProperties.Click(deliveryawaitingqueue.getSearchBtn(), SharedProperties.driver);
            SharedProperties.Class(deliveryawaitingqueue.getCheckBox(), SharedProperties.driver);
            SharedProperties.Click(deliveryawaitingqueue.getMarkOrdersAsDelivered(), SharedProperties.driver);


        }
    }

}

