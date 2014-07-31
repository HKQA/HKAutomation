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
public class OrderDetailsUtil {

    private static final String xpathGatewayOrderId = "/html/body/div[1]/div[2]/div/div[4]/div[1]/p[2]";
    private static final String orderAmount = "/html/body/div[1]/div[2]/div/div[4]/div[1]/p[3]";
    private static final String totalItem = "/html/body/div[1]/div[2]/div/div[4]/div[1]/p[4]";
    private static final String userName = "/html/body/div[1]/div[2]/div/div[4]/div[3]/p[1]";

    public static String GatewayOrderId() {
        String fullOrderId = SharedProperties.driver.findElement(By.xpath(xpathGatewayOrderId)).getText();
        int index = fullOrderId.indexOf(":");
        return fullOrderId.substring(index + 2, fullOrderId.length());
    }

    public static Double getOrderAmount() {
        String fullOrderAmount = SharedProperties.driver.findElement(By.xpath(orderAmount)).getText();
        int index = fullOrderAmount.indexOf(".");
        return Double.valueOf(fullOrderAmount.substring(index + 2, fullOrderAmount.length()));
    }

    public static int TotalItem() {
        String stringTotalItems = SharedProperties.driver.findElement(By.xpath(totalItem)).getText();
        int index = stringTotalItems.indexOf(":");
        String newStringTotalItems = stringTotalItems.substring(index + 2, stringTotalItems.length());
        return Integer.parseInt(newStringTotalItems);
    }

    public static List<Long> getItems() {
        int orderedItems = 3;
        int exists = SharedProperties.driver.findElements(By.xpath("/html/body/div[1]/div[2]/div/div[6]/div[1]/div[" + orderedItems + "]/div[2]/div/div[1]/a")).size();
        List<Long> Items = new ArrayList<Long>();
        for (int i = 1; i <= TotalItem(); i++) {
            /*try
            {
                Items.add(SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[6]/div[1]/div[" + orderedItems + "]/div[2]/div/div[1]/a")).getText());
            } catch (Exception e){
                Items.add(SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[7]/div[1]/div[" + orderedItems + "]/div[2]/div/div[1]/a")).getText());
            }*/
            String itemHref;
            if (exists > 1) {
                itemHref = SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[6]/div[1]/div[" + orderedItems + "]/div[2]/div/div[1]/a")).getAttribute("href");
            } else {
                itemHref = SharedProperties.driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[7]/div[1]/div[" + orderedItems + "]/div[2]/div/div[1]/a")).getAttribute("href");
            }

            int index = itemHref.indexOf("VRNT-");
            Long variantId = Long.valueOf(itemHref.substring(index + 5, itemHref.length()));
            System.out.print(variantId);
            Items.add(variantId);
            orderedItems++;
        }
        return Items;
    }

    public static String getUserName() {
        return SharedProperties.driver.findElement(By.xpath(userName)).getText();
    }

}