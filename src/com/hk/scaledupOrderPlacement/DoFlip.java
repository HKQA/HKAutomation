package com.hk.scaledupOrderPlacement;

import com.hk.aquaElementLocators.AdminHome;
import com.hk.aquaElementLocators.LoginPageAdmin;
import com.hk.commonProperties.SharedProperties;
import com.hk.orderCheckoutDto.SoDetails;
import com.hk.orderCheckoutDto.SoDetailsDTO;
import com.hk.util.TestUtil;
//import jdk.internal.jfr.events.FileWriteEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 1/7/15
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class DoFlip {

    LoginPageAdmin loginPage = new LoginPageAdmin();
    SoDetails soDetails = new SoDetails();
    AdminHome adminHome = new AdminHome();
    FileWriter fw = null;


    public void doFlip() throws Exception {

        fw = new FileWriter("C:\\Workspace\\Automation_testing_v4_Vipul\\logs\\" + new SimpleDateFormat("ddMMyyyy").format(new Date())+"_FlipTest" + ".txt", true);
        fw.append("\n"+"\n"+new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(new Date()) );
        String shippingOrderId = null;
        String barcodeBeforeFlip = null;
        String barcodeAfterFlip = null;
        String boBeforeFlip = null;
        String boAfterFlip = null;
        String soBeforeFlip = null;
        String soAfterFlip = null;
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

            fw.append("\n"+ "Order id for FlipTest = "+ SoDetails.orderIdSoDetails);
            fw.append("\n"+ "Shipping order id for the corresponding gateway order id = "+ shippingOrderId);

            System.out.println("Shipping Order Id = "+shippingOrderId);



            System.out.println("Warehouse Id = " + warehouseId);
            fw.append("\n"+ "Warehouse id before flipping is done = "+ warehouseId);
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


            SharedProperties.driver.findElement(By.cssSelector("a[href*='SearchShippingOrder.action']")).click();
            //SharedProperties.driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/ul/li[6]/a")).click();
            SharedProperties.driver.findElement(By.xpath("html/body/div[2]/div[2]/form/fieldset/ul/div/li[1]/input")).sendKeys(shippingOrderId);
            SharedProperties.driver.findElement(By.xpath("html/body/div[2]/div[2]/form/fieldset/div/input")).click();
            Thread.sleep(3000);
            String warehouseBeforeFlip = SharedProperties.driver.findElement(By.xpath("//*[contains(@id, 'shippingOrderDetail')]/div[8]/strong[2]")).getText();
            String parentWindow = SharedProperties.driver.getWindowHandle();
            SharedProperties.driver.findElement(By.linkText("Foreign Booking Status")).click();
            Thread.sleep(3000);
            //String barcodeBeforeFlip = SharedProperties.driver.findElement(By.xpath("//*[@id='skuItemLineItemTable']/tbody/tr[2]/td[6]")).getText();
            //SharedProperties.driver.close();

            Set<String> tabs = SharedProperties.driver.getWindowHandles();
            System.out.println(tabs.size());
            for(String windowId : tabs)
            {

                if(windowId.equalsIgnoreCase(parentWindow))
                {
                    continue;
                }
                SharedProperties.driver.switchTo().window(windowId);
            //Saving barcode
                barcodeBeforeFlip = SharedProperties.driver.findElement(By.xpath("//*[@id='skuItemLineItemTable']/tbody/tr[2]/td[6]")).getText();
                boBeforeFlip = SharedProperties.driver.findElement(By.xpath("//*[@id='skuItemLineItemTable']/tbody/tr[2]/td[7]")).getText();
                soBeforeFlip = SharedProperties.driver.findElement(By.xpath("//*[@id='skuItemLineItemTable']/tbody/tr[2]/td[8]")).getText();
                SharedProperties.driver.close();




            }

            fw.append("\n"+ "Barcode before flip = " + barcodeBeforeFlip);

            SharedProperties.driver.switchTo().window(parentWindow);
            SharedProperties.driver.findElement(By.linkText("Flip Warehouse")).click();
            WebElement dropDown = SharedProperties.driver.findElement(By.xpath("html/body/div[1]/div[2]/form/table/tfoot/tr/td[2]/select"));
            //SharedProperties.driver.findElement(By.xpath("html/body/div/div[2]/form/table/tfoot/tr/td[2]/select/option[2]"));
            Select clickWarehouse = new Select(dropDown);
            clickWarehouse.selectByIndex(1);
            //Thread.sleep(3000);
            SharedProperties.driver.findElement(By.xpath("//*[@value = 'Flip Warehouse']")).click();
            Thread.sleep(3000);
            SharedProperties.driver.findElement(By.cssSelector("a[href*='SearchShippingOrder.action']")).click();
            SharedProperties.driver.findElement(By.xpath("html/body/div[2]/div[2]/form/fieldset/ul/div/li[1]/input")).sendKeys(shippingOrderId);
            SharedProperties.driver.findElement(By.xpath("html/body/div[2]/div[2]/form/fieldset/div/input")).click();
            Thread.sleep(3000);
            String warehouseAfterFlip = SharedProperties.driver.findElement(By.xpath("//*[contains(@id, 'shippingOrderDetail')]/div[8]/strong[2]")).getText();
            SharedProperties.driver.findElement(By.linkText("Foreign Booking Status")).click();
            Set<String> tabs1 = SharedProperties.driver.getWindowHandles();
            System.out.println(tabs1.size());
            for(String windowId : tabs1)
            {

                if(windowId.equalsIgnoreCase(parentWindow))
                {
                    continue;
                }
                SharedProperties.driver.switchTo().window(windowId);
                //Saving barcode
                barcodeAfterFlip = SharedProperties.driver.findElement(By.xpath("//*[@id='skuItemLineItemTable']/tbody/tr[2]/td[6]")).getText();
                boAfterFlip = SharedProperties.driver.findElement(By.xpath("//*[@id='skuItemLineItemTable']/tbody/tr[2]/td[7]")).getText();
                soAfterFlip = SharedProperties.driver.findElement(By.xpath("//*[@id='skuItemLineItemTable']/tbody/tr[2]/td[8]")).getText();
                SharedProperties.driver.close();



            }
            fw.append("\n"+ "Barcode after flip = "+ barcodeAfterFlip);
            SharedProperties.driver.switchTo().window(parentWindow);
            if(warehouseBeforeFlip.equalsIgnoreCase(warehouseAfterFlip) )
            {
                System.out.println("Not possible to flip warehouse");
                TestUtil.excel.setCellData("Flip", "Result", 3, "Passed" );


                //Assert.fail("Not possible to switch warehouse");

            }
            else
            {

                System.out.println("Warehouse flipped successfully");
                TestUtil.excel.setCellData("Flip", "Result", 2, "Passed" );


            }

            fw.append("\n"+"\n");
            fw.close();
        }




    }





}
