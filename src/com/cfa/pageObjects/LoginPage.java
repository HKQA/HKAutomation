package com.cfa.pageObjects;

import com.cfa.genericMethods.UIGenericMethods;
import com.cfa.utilExcelIO.ExcelReading;
import com.cfa.utilExcelIO.ExcelUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vikas.dhull
 * Date: 9/2/15
 * Time: 5:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginPage extends UIGenericMethods {

    @FindBy(name="email")
    public static WebElement userName;

    @FindBy(name="password")
    public static WebElement password;

    @FindBy(name="st")
    public static WebElement store;

    @FindBys(value={@FindBy(xpath="//select[@name='st']//option")})
    public static List<WebElement> coll_ddloption;

    @FindBy(xpath="//input[@value='Login']")
    public static WebElement loginBtn;

    public void setUserName(String strUserName){
        userName.sendKeys(strUserName);
    }

    public void setPassword(String strPassword){
        password.sendKeys(strPassword);
    }

    public void setStoreName(String strStoreName){
       store.sendKeys(strStoreName);
    }

   /* public void setStoreName(String strStoreName){
        coll_ddloption..sendKeys(strStoreName); //need code to handle drop down list for store
    } */

    public void clickLogin(){
        fn_Click(loginBtn);
    }

    public HomePage fn_ValidLogin() throws IOException {

     // WebElement item1=coll_ip.get(0);

        setUserName(ExcelUtil.getUserName());
        setPassword(ExcelUtil.getPassword());
        setStoreName(ExcelUtil.getStoreName());
        clickLogin();

     // initElements Method intialize not only page class but also returns object of that page


            HomePage HPObj=PageFactory.initElements(DRIVER_OBJ, HomePage.class);

        return HPObj;
    }

    public void fn_InValidLogin(){

        setUserName("testuser");
        setPassword("password");
        setStoreName("XYZ");
        clickLogin();
    }
}
