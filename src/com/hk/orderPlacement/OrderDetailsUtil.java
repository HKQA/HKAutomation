package com.hk.orderPlacement;

import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.PaymentPage;
import org.openqa.selenium.By;

/**
 * Created with IntelliJ IDEA.
 * User: Nitin Kukna
 * Date: 7/10/14
 * Time: 2:53 PM
 */
public class OrderDetailsUtil {

    private static String xpathGatewayOrderId = "/html/body/div[1]/div[2]/div/div[4]/div[1]/p[2]";

    public static String gatewayOrderId() {
        return SharedProperties.driver.findElement(By.xpath(xpathGatewayOrderId)).getText();
    }

}
