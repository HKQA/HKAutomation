package com.hk.scaledupOrderPlacement;

import com.hk.aquaElementLocators.AdminHome;
import com.hk.aquaElementLocators.LoginPageAdmin;
import com.hk.brightElementLocators.BrightHome;
import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.LoginPage;
import com.hk.orderCheckoutDto.SoDetails;
import com.hk.orderCheckoutDto.SoDetailsDTO;
import com.hk.recorder.Browse;
import com.hk.recorder.MultipleVariant;
import com.hk.util.TestUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 2/2/15
 * Time: 7:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestMRPChange {

    MultipleVariant multipleVariant = new MultipleVariant();

    Browse browse = new Browse();

    LoginPage loginPage = new LoginPage();

    SoDetails soDetails = new SoDetails();

    GetBatchNumber getBatchNumber = new GetBatchNumber();

    AppSpecificReusableMethods reusableMethods = new AppSpecificReusableMethods();
    MultiVariantOrderCheckout orderCheckout = new MultiVariantOrderCheckout();
    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    AdminHome adminHome = new AdminHome();
    BrightHome brightHome = new BrightHome();

    public static String variantId ;

    public void testMRPChange() throws Exception {

        String batchnumber = null;
        String productVariantId = null;
        String shippingOrderId = null;
        int warehouseId = 0 ;
        int count = 1;

        List<String> getList = new ArrayList<String>()  ;


        SharedProperties.openBrowser(TestUtil.getURL(), TestUtil.getBrowser());

        multipleVariant.testMultipleVariant();

        browse.proceedToCheckoutMultiVariant();

        SharedProperties.Click(loginPage.getSignInCheckbox(), SharedProperties.driver);

        reusableMethods.setUserCredentials();

        reusableMethods.selectDeliveryAddress();

        //reusableMethods.doOnlinePayment();

        reusableMethods.doCODPayment();
        int lineItemCount= reusableMethods.verifyLineItems();

        System.out.println("Number of line items = " + lineItemCount);

        String orderId =   SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[6]/div/div[1]/p[2]")).getText();

        System.out.println(orderId);

        String finalOrderId = orderId.substring(10);

        soDetails.orderIdSoDetails = finalOrderId;

        getList = getBatchNumber.getBatchNumber(finalOrderId, variantId );

        System.out.println(getList.get(0));

        batchnumber =   getList.get(0);

        System.out.println(getList.get(1));

        productVariantId = getList.get(1);

        System.out.println(getList.get(2));

        warehouseId = Integer.valueOf(getList.get(2));

        SharedProperties.openBrowser(TestUtil.getBright_URL(), TestUtil.getBrowser());
        Thread.sleep(3000);

        SharedProperties.sendKeys(loginPageAdmin.getUserName(), TestUtil.getAdmin_User(), SharedProperties.driver);
        Thread.sleep(2000);
        SharedProperties.sendKeys(brightHome.getPassWd(), TestUtil.getBright_Password(), SharedProperties.driver );
        SharedProperties.Click(brightHome.getLoginBtn(), SharedProperties.driver);
        //SharedProperties.sendKeys(loginPageAdmin.getPassword(), TestUtil.getAdmin_Password(), SharedProperties.driver);
        //SharedProperties.Click(loginPageAdmin.getLoginbtn(), SharedProperties.driver);




            //shippingOrderId = soDetailsDTO.getSoGatewayOrderId();
            //warehouseId = soDetailsDTO.getWarehouseId();

            //System.out.println("Shipping Order Id = "+shippingOrderId);

            //System.out.println("Warehouse Id = " + warehouseId);

            //SharedProperties.driver.findElement(By.linkText("Admin Home")).click();
            Thread.sleep(3000);

            if(warehouseId == 10)
            {
                System.out.print("\n Selected GGN Bright Warehouse");
                WebElement WarehouseDropDownList = SharedProperties.driver.findElement(By.xpath("//*[@id=\"selectWHForm\"]/select"));
                Select clickWarehouse = new Select(WarehouseDropDownList);
                clickWarehouse.selectByVisibleText("GGN Bright Warehouse");
                SharedProperties.Click(adminHome.getSaveBtn(), SharedProperties.driver);
                Thread.sleep(2000);



            }

            if(warehouseId == 20)
            {
                System.out.print("\n MUM Bright Warehouse");
                Thread.sleep(2000);
                WebElement WarehouseDropDownList = SharedProperties.driver.findElement(By.xpath("//*[@id=\"selectWHForm\"]/select"));
                Select clickWarehouse = new Select(WarehouseDropDownList);
                clickWarehouse.selectByVisibleText("MUM Bright Warehouse");
                SharedProperties.Click(adminHome.getSaveBtn(), SharedProperties.driver);
                Thread.sleep(2000);


            }

            SharedProperties.driver.findElement(By.linkText("Search Available Batches")).click();
            SharedProperties.driver.findElement(By.xpath("//*[@name = 'upc']")).sendKeys(productVariantId);
            SharedProperties.driver.findElement(By.xpath("//*[@value = 'Show Available Batches']")).click();
            Thread.sleep(2000);

            while(SharedProperties.isElementPresent("/html/body/div/div[2]/div[3]/table/tbody/tr["+count+"]/td[3]"))
            {

                String text = SharedProperties.driver.findElement(By.xpath("/html/body/div/div[2]/div[3]/table/tbody/tr[\"+count+\"]/td[3]")).getText();

                if(SharedProperties.driver.findElement(By.xpath("/html/body/div/div[2]/div[3]/table/tbody/tr[\"+count+\"]/td[3]")).getText().equalsIgnoreCase(batchnumber))
                {


                    SharedProperties.driver.findElement(By.xpath("/html/body/div/div[2]/div[3]/table/tbody/tr["+count+"]/td[12]/a/img")).click();

                }

                count++;


            }

            SharedProperties.driver.findElement(By.xpath("//*[@name = 'skuGroup.mrp']")).clear();
            SharedProperties.driver.findElement(By.xpath("//*[@name = 'skuGroup.mrp']")).sendKeys("1001");
            SharedProperties.driver.findElement(By.xpath("//*[@value = 'Save']")).click();
            Thread.sleep(3000);
















    }


    public static void main(String[] args) throws Exception {
        TestMRPChange mrpChange = new TestMRPChange();

        mrpChange.testMRPChange();


    }
}
