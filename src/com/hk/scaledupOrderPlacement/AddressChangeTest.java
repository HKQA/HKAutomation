package com.hk.scaledupOrderPlacement;

import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.LoginPage;
import com.hk.orderCheckoutDto.SoDetails;
import com.hk.recorder.Browse;
import com.hk.recorder.MultipleVariant;
import com.hk.util.TestUtil;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 1/29/15
 * Time: 4:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddressChangeTest {

    AppSpecificReusableMethods reusableMethods = new AppSpecificReusableMethods();
    MultipleVariant multipleVariant = new MultipleVariant();

    Browse browse = new Browse();
    LoginPage loginPage = new LoginPage();

    SoDetails soDetails = new SoDetails();


    public void testAddressChange() throws Exception {

        SharedProperties.openBrowser(TestUtil.getURL(), TestUtil.getBrowser());

        multipleVariant.testMultipleVariant();

        browse.proceedToCheckoutMultiVariant();

        SharedProperties.Click(loginPage.getSignInCheckbox(), SharedProperties.driver);

        reusableMethods.setUserCredentials();

        reusableMethods.selectDeliveryAddress();

        reusableMethods.doCODPayment();




    }

    public static void main(String[] args) throws Exception {

        AddressChangeTest addressChangeTest = new AddressChangeTest();

        addressChangeTest.testAddressChange();

    }
}
