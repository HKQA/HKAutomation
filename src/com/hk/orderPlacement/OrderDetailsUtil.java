package com.hk.orderPlacement;

import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.PaymentPage;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nitin Kukna
 * Date: 7/10/14
 * Time: 2:53 PM
 */
public class OrderDetailsUtil {

    private static String xpathGatewayOrderId = "/html/body/div[1]/div[2]/div/div[4]/div[1]/p[2]";
    private static String orderAmount = "/html/body/div[1]/div[2]/div/div[4]/div[1]/p[3]";
    private static String totalItem = "/html/body/div[1]/div[2]/div/div[4]/div[1]/p[4]";
    private static String userName = "/html/body/div[1]/div[2]/div/div[4]/div[3]/p[1]";

    public static String GatewayOrderId() {
        String fullOrderId = SharedProperties.driver.findElement(By.xpath(xpathGatewayOrderId)).getText();
        int index = fullOrderId.indexOf(":");
        String orderId= fullOrderId.substring(index+2,fullOrderId.length());
        return orderId;
    }
    public static String OrderAmount() {
        String fullOrderAmount = SharedProperties.driver.findElement(By.xpath(orderAmount)).getText();
        int index = fullOrderAmount.indexOf(".");
        String orderAmount= fullOrderAmount.substring(index+2,fullOrderAmount.length());
        return orderAmount;
    }
    public static String TotalItem() {
        return SharedProperties.driver.findElement(By.xpath(totalItem)).getText();
    }
    public static String Item(){
        return SharedProperties.driver.findElements(By.className("col-sm-5 col-xs-10 item-name")).toString();
    }
    public static String UserName(){
        return SharedProperties.driver.findElement(By.xpath(userName)).getText();
    }
}
