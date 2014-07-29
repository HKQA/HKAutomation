package com.hk.orderPlacement;

import com.hk.commonProperties.SharedProperties;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nitin Kukna
 * Date: 7/10/14
 * Time: 2:53 PM
 */
public class OrderDetailsUtil extends SharedProperties {

    private static String xpathGatewayOrderId = "/html/body/div[1]/div[2]/div/div[4]/div[1]/p[2]";
    private static String orderAmount = "/html/body/div[1]/div[2]/div/div[4]/div[1]/p[3]";
    private static String totalItem = "/html/body/div[1]/div[2]/div/div[4]/div[1]/p[4]";
    private static String userName = "/html/body/div[1]/div[2]/div/div[4]/div[3]/p[1]";

    public static String GatewayOrderId() {
        String fullOrderId = SharedProperties.driver.findElement(By.xpath(xpathGatewayOrderId)).getText();
        int index = fullOrderId.indexOf(":");
        String orderId = fullOrderId.substring(index + 2, fullOrderId.length());
        return orderId;
    }

    public static String OrderAmount() {
        String fullOrderAmount = SharedProperties.driver.findElement(By.xpath(orderAmount)).getText();
        int index = fullOrderAmount.indexOf(".");
        String orderAmount = fullOrderAmount.substring(index + 2, fullOrderAmount.length());
        return orderAmount;
    }

    public static int TotalItem() {
        String stringTotalItems = SharedProperties.driver.findElement(By.xpath(totalItem)).getText();
        int index = stringTotalItems.indexOf(":");
        String newStringTotalItems = stringTotalItems.substring(index + 2, stringTotalItems.length());
        int totalItems = Integer.parseInt(newStringTotalItems);
        System.out.print(totalItems);
        return totalItems;
    }

    public static List<String> Item() {
        int orderSuccessItems = 3 ;
        List<String> Items = new ArrayList<String>();
        for (int i = 1; i <= TotalItem(); i++) {
            Items.add(SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[6]/div[1]/div[" + orderSuccessItems + "]/div[2]/div/div[1]/a")).getText());
            orderSuccessItems++;
            System.out.print(Items);
        }
        return Items;
    }

    public static String UserName() {
        return SharedProperties.driver.findElement(By.xpath(userName)).getText();
    }

}

