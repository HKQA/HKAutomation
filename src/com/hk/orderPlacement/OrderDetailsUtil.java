package com.hk.orderPlacement;

import com.hk.commonProperties.SharedProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

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

    public static List<String> getItems() {
        int orderedItems = 3;
        int exists = SharedProperties.driver.findElements(By.xpath("/html/body/div[1]/div[2]/div/div[6]/div[1]/div[" + orderedItems + "]/div[2]/div/div[1]/a")).size();
        List<String> Items = new ArrayList<String>();
        for (int i = 1; i <= TotalItem(); i++) {
            /*try
            {
                Items.add(SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[6]/div[1]/div[" + orderedItems + "]/div[2]/div/div[1]/a")).getText());
            } catch (Exception e){
                Items.add(SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[7]/div[1]/div[" + orderedItems + "]/div[2]/div/div[1]/a")).getText());
            }*/
            if (exists > 1)

            {
                Items.add(SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[6]/div[1]/div[" + orderedItems + "]/div[2]/div/div[1]/a")).getText());
            }
            else {
                Items.add(SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[7]/div[1]/div[" + orderedItems + "]/div[2]/div/div[1]/a")).getText());
            }
            orderedItems++;
            System.out.print(Items);
        }
        return Items;
    }

    public static String getUserName() {
        return SharedProperties.driver.findElement(By.xpath(userName)).getText();
    }

}

