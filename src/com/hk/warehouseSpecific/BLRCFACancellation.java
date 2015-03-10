package com.hk.warehouseSpecific;

import com.hk.aquaElementLocators.AdminHome;
import com.hk.aquaElementLocators.LoginPageAdmin;
import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.LoginPage;
import com.hk.orderCheckoutDto.SoDetails;
import com.hk.orderCheckoutDto.SoDetailsDTO;
import com.hk.recorder.Browse;
import com.hk.scaledupOrderPlacement.AppSpecificReusableMethods;
import com.hk.util.TestUtil;
import org.openqa.selenium.By;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 3/4/15
 * Time: 4:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class BLRCFACancellation {

    Browse browse = new Browse();
    AppSpecificReusableMethods reusableMethods = new AppSpecificReusableMethods();
    LoginPage loginPage = new LoginPage();

    SoDetails soDetails = new SoDetails();
    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    AdminHome adminHome = new AdminHome();


    public void testBLRCFACancellation() throws Exception {


        String shippingOrderId = null;
        int warehouseId = 0;

        SharedProperties.openBrowser(TestUtil.getURL(), TestUtil.getBrowser());
        String variant = null;
        variant = TestUtil.excel.getCellData("MUM_CFA", "Test Scenario", 4);
        SharedProperties.driver.navigate().to(TestUtil.getURL()+"/sv/oh-yeah!-isolate/SP-29982?navKey=VRNT-"+ variant);
        Thread.sleep(2000);
        SharedProperties.driver.findElement(By.xpath("//*[@value = 'Buy Now']")).click();
        SharedProperties.driver.findElement(By.xpath("//*[@id='cartPop']/div/div/div/div[5]/a")).click();
        browse.proceedToCheckoutMultiVariant();
        SharedProperties.Click(loginPage.getSignInCheckbox(), SharedProperties.driver);
        reusableMethods.setUserCredentials();
        reusableMethods.selectDeliveryAddress();
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




        }


    }
}
