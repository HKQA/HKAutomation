package com.hk.recorder;

import com.hk.commonProperties.SharedProperties;
import com.hk.util.TestUtil;
import org.openqa.selenium.By;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 12/26/14
 * Time: 1:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class CategoryTree {

    public void testCategoryTree() throws Exception {

        SharedProperties.driver.findElement(By.xpath("//*[@id='dropDownbox1']/ul/li[4]/a")).click();

        Thread.sleep(3000);

        SharedProperties.driver.findElement(By.xpath("//*[@id='flexiCarousel-0']/div[2]/div[2]/a/span")).click();

        Thread.sleep(3000);

        SharedProperties.driver.findElement(By.xpath("//*[@id='variant-page']/div[2]/div[3]/div[1]/div/div[2]/div[2]/div/div[1]/input")).click();

        Thread.sleep(3000);

        SharedProperties.driver.findElement(By.xpath("//*[@id='cartPop']/div/div/div/div[5]/a")).click();

    }

    public static void main(String[] args) throws Exception {
        Browse browse = new Browse();

        CategoryTree category = new CategoryTree();

        SharedProperties.openBrowser(TestUtil.getURL(), TestUtil.getBrowser());

        category.testCategoryTree();

        browse.proceedToCheckout();





    }
}
