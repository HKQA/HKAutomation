package com.hk.scaledupOrderPlacement;

import com.hk.aquaElementLocators.AdminHome;
import com.hk.aquaElementLocators.LoginPageAdmin;
import com.hk.commonProperties.SharedProperties;
import com.hk.orderCheckoutDto.BrightDetails;
import com.hk.orderCheckoutDto.SoDetails;
import com.hk.orderCheckoutDto.SoDetailsDTO;
import com.hk.util.TestUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import sun.nio.cs.ext.SJIS;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 1/7/15
 * Time: 4:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class DoSplitSO {

    LoginPageAdmin loginPage = new LoginPageAdmin();
    SoDetails soDetails = new SoDetails();
    AdminHome adminHome = new AdminHome();

    public void doSplitSO() throws InterruptedException {
        int count = 1;
        int countSO = 0;
        String shippingOrderId = null;
        String soValue = null;
        int warehouseId = 0;
        String boBeforeSplit = null;
        String barcode1beforeSplit = null;
        String barcode2beforeSplit = null;
        SharedProperties.openBrowser(TestUtil.getAdminURL(), TestUtil.getBrowser());
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
            String getBOId = SharedProperties.driver.findElement(By.xpath("//*[contains(@id, 'shippingOrderDetail')]/div[6]/strong")).getText();
            System.out.println("BO Id = " + getBOId);
            String parentWindow = SharedProperties.driver.getWindowHandle();
            SharedProperties.driver.findElement(By.linkText("Foreign Booking Status")).click();
            Set<String> tabs = SharedProperties.driver.getWindowHandles();
            System.out.println(tabs.size());
            for(String windowId : tabs)
            {

                if(windowId.equalsIgnoreCase(parentWindow))
                {
                    continue;
                }
                SharedProperties.driver.switchTo().window(windowId);
                //Saving BO before split
                boBeforeSplit = SharedProperties.driver.findElement(By.xpath("//*[@id='skuItemLineItemTable']/tbody/tr[2]/td[7]")).getText();
                barcode1beforeSplit = SharedProperties.driver.findElement(By.xpath("//*[@id='skuItemLineItemTable']/tbody/tr[2]/td[6]")).getText();
                barcode2beforeSplit = SharedProperties.driver.findElement(By.xpath("//*[@id='skuItemLineItemTable']/tbody/tr[3]/td[6]")).getText();
                SharedProperties.driver.close();




            }
            SharedProperties.driver.switchTo().window(parentWindow);
            if(SharedProperties.driver.findElement(By.xpath("//*[contains(@id, 'shippingOrder')]/td[5]/span/strong")).getText().contains("SO Ready for Process"))
            {
                WebElement chooseReasonDropdown = SharedProperties.driver.findElement(By.xpath("//*[@id = 'shippingOrderMoveBackReason']"));
                Select chooseReason = new Select(chooseReasonDropdown);
                chooseReason.selectByIndex(2);
                SharedProperties.driver.findElement(By.xpath("//*[@value = 'Move Back']")).click();
                Thread.sleep(3000);
                SharedProperties.driver.findElement(By.xpath("//*[@alt = 'Unhold Shipping Order']")).click();
                String soStatus = SharedProperties.driver.findElement(By.xpath("//*[contains(@id, 'shippingOrder')]/td[5]/span/strong")).getText();
                System.out.println(soStatus);

                SharedProperties.driver.findElement(By.linkText("Split SO")).click();
                SharedProperties.driver.findElement(By.xpath("//*[@name = 'lineItems[0]']")).click();
                SharedProperties.driver.findElement(By.xpath("//*[@value = 'Split Shipping Order']")).click();
                SharedProperties.driver.findElement(By.linkText("Search BO")).click();
                SharedProperties.driver.findElement(By.xpath("//*[@name = 'gatewayOrderId']")).sendKeys(getBOId);
                SharedProperties.driver.findElement(By.xpath("//*[@value = 'Search Orders']")).click();
                Thread.sleep(2000);
                while (SharedProperties.isElementPresent("html/body/div[2]/div[2]/table/tbody/tr/td[6]/table/tbody/tr["+count+"]/td/a[1]"))
                {
                    soValue = SharedProperties.driver.findElement(By.xpath("html/body/div[2]/div[2]/table/tbody/tr/td[6]/table/tbody/tr["+count+"]/td/a[1]")).getText();
                    System.out.println(soValue);
                    countSO++;
                    count++;


                }

                SharedProperties.driver.navigate().to(TestUtil.getBright_URL());
                Thread.sleep(2000);
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
                    SharedProperties.Click(adminHome.getSaveBtn(), SharedProperties.driver);
                    Thread.sleep(2000);

                } else
                {
                    WebElement WarehouseDropDownList = SharedProperties.driver.findElement(By.xpath("/*//*[@id=\"selectWHForm\"]/select"));
                    Select clickWarehouse = new Select(WarehouseDropDownList);
                    clickWarehouse.selectByVisibleText("MUM Bright Warehouse");
                    SharedProperties.Click(adminHome.getSaveBtn(), SharedProperties.driver);
                    Thread.sleep(2000);


                }
                SharedProperties.driver.findElement(By.linkText("Search BO")).click();
                SharedProperties.driver.findElement( By.xpath("//*[@name = 'orderId']")).sendKeys(boBeforeSplit);
                SharedProperties.driver.findElement(By.xpath("//*[@value = 'Search Orders']")).click();
                String afterSplitStatus = SharedProperties.driver.findElement(By.xpath("html/body/div[2]/div[2]/table/tbody/tr/td[6]/table/tbody/tr/td")).getText();
                System.out.println(afterSplitStatus);
                if(afterSplitStatus.contains("SO Cancelled"))
                {

                    System.out.println("Split done successfully");

                }
                else
                {

                    System.out.println("Split not done successfully");
                    Assert.fail();

                }






            }

        }


    }
}
