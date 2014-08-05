package com.hk.orderCheckout;

import com.hk.excel.ExcelServiceImplOld;
import com.hk.orderPlacement.ExistingOnlineOrder;
import com.hk.property.PropertyHelper;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nitin Kukna
 * Date: 8/1/14
 * Time: 6:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class variantCheckout extends ExistingOnlineOrder{
    String baseUrl;
    String browser;
    ExistingOnlineOrder EOO = new ExistingOnlineOrder();
    /*ExcelServiceImplOld readexcel = new ExcelServiceImplOld();*/



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
    EOO.login(1L);
    }
}
