package com.hk.recorder;

import com.hk.commonProperties.SharedProperties;
import com.hk.util.TestUtil;
import org.openqa.selenium.By;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 12/26/14
 * Time: 12:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClearanceTest {



    public void testClearance() throws Exception {
        SharedProperties.driver.findElement(By.xpath("//*[@id='dropDownbox1']/ul/li[14]/a")).click();

        Thread.sleep(3000);

        SharedProperties.driver.findElement(By.xpath("//*[@id='variantResultView']/div[1]/div[2]/a/span")).click();

        SharedProperties.driver.findElement(By.xpath("//*[@id='variant-page']/div[2]/div[4]/div[1]/div/div[2]/div[2]/div/div[1]/input")).click();

        SharedProperties.driver.findElement(By.xpath("//*[@id='cartPop']/div/div/div/div[5]/a")).click();


    }

    public static void main(String[] args) throws Exception {
        ClearanceTest clearance = new ClearanceTest();

        Browse browse = new Browse();

        SharedProperties.openBrowser(TestUtil.getURL(), TestUtil.getBrowser());

        clearance.testClearance();

        browse.proceedToCheckout();


    }

}
