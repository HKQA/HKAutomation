package com.hk.orderCheckout;

import com.hk.orderPlacement.ExistingOnlineOrder;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Nitin Kukna
 * Date: 8/5/14
 * Time: 3:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class variantWithFreebieCheckout extends ExistingOnlineOrder {
    String baseUrl;
    String browser;
    ExistingOnlineOrder EOO = new ExistingOnlineOrder();

    @Test(enabled = true)
    public void variantWithFreebieCheckout() throws InterruptedException, IOException, Exception {
        EOO.login(2L);
    }
}
