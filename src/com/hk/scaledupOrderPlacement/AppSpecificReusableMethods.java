package com.hk.scaledupOrderPlacement;

import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.AddressPage;
import com.hk.elementLocators.LoginPage;
import com.hk.elementLocators.PaymentPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 12/26/14
 * Time: 4:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class AppSpecificReusableMethods {
    LoginPage loginpage = new LoginPage();
    AddressPage addressPage = new AddressPage();
    PaymentPage paymentPage = new PaymentPage();


    public void setUserCredentials()
    {
        SharedProperties.sendKeys(loginpage.getEmailIdTextBox(), "vipul.j@healthkart.com", SharedProperties.driver);

        SharedProperties.sendKeys(loginpage.getPasswordTextBox(), "Vipul.jain", SharedProperties.driver);

        SharedProperties.Click(loginpage.getSignInBtn(), SharedProperties.driver);


    }

    public void selectDeliveryAddress()
    {

        SharedProperties.Click(addressPage.addressPage(), SharedProperties.driver);

    }

    public void doOnlinePayment() throws Exception {
        SharedProperties.Click(paymentPage.paymentPageDummy(), SharedProperties.driver);
        Thread.sleep(3000);
        new Select(SharedProperties.driver.findElement(By.xpath("//*[@id='tab1']/div/div[5]/select"))).selectByVisibleText("Dummy");
        Thread.sleep(2000);
        SharedProperties.Click(paymentPage.proceedToPayment(), SharedProperties.driver);
        Thread.sleep(5000);
        SharedProperties.Click(paymentPage.paymentY(), SharedProperties.driver);
        Thread.sleep(5000);
        SharedProperties.Click(paymentPage.proceedPayment(), SharedProperties.driver);
        Thread.sleep(3000);


    }

    public void doCODPayment() throws Exception {
        SharedProperties.driver.findElement(By.xpath("//*[@class = 'last']")).click();
        Thread.sleep(2000);
        SharedProperties.driver.findElement(By.xpath("//*[@value = 'Place order']")).click();



    }



    public int verifyLineItems()
    {
        int init = 3;
        int count = 0;

        if (SharedProperties.isElementPresent("/html/body/div[1]/div[2]/div/div[7]"))
        {

            if(SharedProperties.driver.findElement(By.xpath("html/body/div[1]/div[2]/div/div[7]")).getText().contains("your"))
            {

                while (SharedProperties.isElementPresent("/html/body/div[1]/div[2]/div/div[8]/div[1]/div[1]/div["+init+"]"))
                {
                    count++;
                    init++;

                }

            } else
            {  while (SharedProperties.isElementPresent("/html/body/div[1]/div[2]/div/div[7]/div[1]/div[1]/div["+init+"]"))
            {

            count++;
            init++;
            }
            }
        }

       return count;
    }



}
