package com.hk.orderCheckout;

import com.hk.aquaElementLocators.aquaElementLocators.AdminHome;
import com.hk.aquaElementLocators.aquaElementLocators.SearchBo;
import com.hk.commonProperties.SharedProperties;
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
    AdminHome adminhome = new AdminHome();
    SearchBo searchbo = new SearchBo();

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
    /*EOO.login(1L);
*/
        SharedProperties.openBrowser(AdminBaseURL,browser);
/*
        System.out.print("GateWayOrderId :-  " + OrderDetailsUtil.GatewayOrderId());
*/
        SharedProperties.clickWithCss(adminhome.baseOrderSearch(), SharedProperties.driver);
        SharedProperties.sendKeys(searchbo.boGatewayId(), "HK13801-570655", SharedProperties.driver);




    }
}
