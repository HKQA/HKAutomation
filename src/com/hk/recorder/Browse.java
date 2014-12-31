package com.hk.recorder;

import com.hk.commonProperties.SharedProperties;
import com.hk.util.TestUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 12/23/14
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class Browse {



    public void doBrowsing() throws Exception {

        //SharedProperties.openBrowser(TestUtil.getURL(), TestUtil.getBrowser());

        WebElement search = SharedProperties.driver.findElement(By.xpath("//*[@id='globalSearch']/input"));
        if(TestUtil.excel.getCellData("Browsing", 1, 2).equalsIgnoreCase("Y"))
        {

        String brandName = TestUtil.getBrandName();

        search.sendKeys(brandName);
        }

        if(TestUtil.excel.getCellData("Browsing", 1, 3).equalsIgnoreCase("Y"))
        {

            String category = TestUtil.getCategory();

            search.sendKeys(category);



        }

        SharedProperties.driver.findElement(By.xpath("//*[@id='search']/span")).click();

        SharedProperties.driver.findElement(By.xpath("//*[@id='variantResultView']/div[1]/div[3]/a/span")).click();

        Thread.sleep(3000);

        SharedProperties.driver.findElement(By.xpath("//*[@id='variant-page']/div[2]/div[4]/div[1]/div/div[2]/div[2]/div/div[1]/input")).click();

        Thread.sleep(3000);

        SharedProperties.driver.findElement(By.xpath("//*[@id='cartPop']/div/div/div/div[5]/a")).click();



    }

    public void proceedToCheckoutMultiVariant() throws Exception {
        WebElement proceedToCheckout = SharedProperties.driver.findElement(By.xpath("html/body/div[1]/div[2]/div/div[5]/div/div[2]/div/div[2]/div[1]/div[4]/a"));
        Thread.sleep(3000);
        proceedToCheckout.click();

    }

    public void proceedToCheckout() throws Exception {

        WebElement proceedToCheckout = SharedProperties.driver.findElement(By.xpath("html/body/div[1]/div[2]/div/div[5]/div/div[2]/div/div[2]/div[1]/div[4]/a"));

        Thread.sleep(3000);

        SharedProperties.driver.findElement(By.xpath("html/body/div[1]/div[2]/div/div[5]/div/div[1]/div[2]/div[2]/div/div[2]/input")).clear();

        if(TestUtil.excel.getCellData("Browsing",1, 4 ).equalsIgnoreCase("Y"))
        {

        String quantity = TestUtil.getQuantity();

        SharedProperties.driver.findElement(By.xpath("html/body/div[1]/div[2]/div/div[5]/div/div[1]/div[2]/div[2]/div/div[2]/input")).sendKeys(quantity);
        }

        SharedProperties.driver.findElement(By.xpath("/html/body")).click();
        Thread.sleep(3000);

        proceedToCheckout.click();

    }

    public void clickClearance()
    {

        WebElement clearance = SharedProperties.driver.findElement(By.xpath("//*[@id='dropDownbox1']/ul/li[14]/a"));



        clearance.click();

    }

    public void enterBrand()
    {

        WebElement inputBrand = SharedProperties.driver.findElement(By.xpath("//*[@id='globalSearch']/input"));

        inputBrand.sendKeys("on");


    }

    public int getQuantity()
    {



        return 0;
    }

    public static void main(String[] args) throws Exception {
        Browse browse = new Browse();

        SharedProperties.openBrowser(TestUtil.getURL(), TestUtil.getBrowser());

        browse.doBrowsing();

        browse.proceedToCheckout();



    }

}
