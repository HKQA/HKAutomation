package com.hk.orderCheckout;


import com.hk.aquaElementLocators.LoginPageAdmin;
import com.hk.aquaElementLocators.PrintPrickOrders;
import com.hk.brightElementLocators.BrightHome;
import com.hk.brightElementLocators.CheckoutOrders;
import com.hk.orderCheckoutDto.BrightDetails;
import com.hk.orderCheckoutDto.ForeignSiCliDTO;
import com.hk.commonProperties.SharedProperties;

import com.hk.orderCheckoutDto.SoDetails;
import com.hk.orderCheckoutDto.SoDetailsDTO;
import com.hk.property.PropertyHelper;
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
public class variantCheckout /*extends ExistingOnlineOrder*/{
    String AdminBaseURL;
    String browser;
    private int delay ;

    LoginPageAdmin loginpage=new LoginPageAdmin();
    PrintPrickOrders printprick = new PrintPrickOrders();
    CheckoutOrders checkoutorders = new CheckoutOrders();
    SharedProperties sharedproperties = new SharedProperties();
    BrightHome brighthome = new BrightHome();



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
            finalObjectString.addAll(readexcel.mainReadFromExcelIterator(PropertyHelper.readProperty("LoginExcel")));
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

        SharedProperties.openBrowser(AdminBaseURL,browser);
        SharedProperties.sendKeys(loginpage.getUserName(), "saurabh.nagpal@healthkart.com", SharedProperties.driver);
        SharedProperties.sendKeys(loginpage.getPassword(), "abcde12", SharedProperties.driver);
        SharedProperties.Click(loginpage.getLoginbtn(), SharedProperties.driver);

        //Select WareHouse according to your order from database or with text
        for(Long shippingOrderId: SoDetails.Sodetails().getShippingOrderIdList()){

        SharedProperties.clickWithCss(printprick.getPrintPrickLink(), SharedProperties.driver);
        SharedProperties.Click(printprick.getOrderFilters(), SharedProperties.driver);
        Thread.sleep(2000);
        SharedProperties.sendKeys(printprick.getSoGatewayOrderIdTxt(),"2326372-S47809", SharedProperties.driver);

        SharedProperties.Click(printprick.getBoGatewaySearchBtn(), SharedProperties.driver);
        Thread.sleep(3000);
        SharedProperties.Class(printprick.getCheckboxBo(), SharedProperties.driver);
        SharedProperties.Click(printprick.getBatchPrintBtn(), SharedProperties.driver);
        Thread.sleep(5000);

        sharedproperties.pressEnterSafe();
        Thread.sleep(3000);

        SharedProperties.Class(printprick.getCheckboxBo(), SharedProperties.driver);
        Thread.sleep(2000);
        SharedProperties.Click(printprick.getJobDoneClearQueBtn(),SharedProperties.driver);
        Thread.sleep(4000);
        SharedProperties.driver.navigate().to(PropertyHelper.readProperty("brightUrl"));
        SharedProperties.sendKeys(loginpage.getUserName(), "saurabh.nagpal@healthkart.com", SharedProperties.driver);
        SharedProperties.sendKeys(brighthome.getPassWd(), "abcde12", SharedProperties.driver);
        SharedProperties.Click(brighthome.getLoginBtn(),SharedProperties.driver);
        //check for warehouse first
            for(String foreignSiCli:BrightDetails.ForeignSiCli().getForeignSoGatewayIdList())  {
        SharedProperties.Click(checkoutorders.getCheckoutOrder(),SharedProperties.driver);
        SharedProperties.sendKeys(checkoutorders.getCheckoutOrderTxt(),foreignSiCli,SharedProperties.driver);

            }


        //find Foreign Booking status BO with queries
        //find SO of that BO
        //get to checkout order
        //Enter SO of Bright






        }
    }

    }

