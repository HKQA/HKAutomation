package com.hk.compareProducts;

import com.hk.commonProperties.SharedProperties;
import com.hk.elementLocators.CartPage;
import com.hk.elementLocators.ProductPage;
import com.hk.elementLocators.VariantCompare;
import com.hk.excel.TestDetailsExcelService;
import com.hk.excel.dto.TestDetailsDTO;
import com.hk.jdbc.OrderDetailsVerify;
import com.hk.property.PropertyHelper;
import com.hk.reportAndMailGenerator.SendMail;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: saurabh.nagpal
 * Date: 9/22/14
 * Time: 1:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class CompareProducts {
    String baseUrl;
    public String browser;

    CartPage cartpage = new CartPage();
    ProductPage productpage = new ProductPage();
    VariantCompare variantcompare = new VariantCompare();

    @Parameters({"BaseURL", "Browser"})
    @BeforeClass
    public void g(String baseUrl, String browser) {
        this.baseUrl = baseUrl;
        this.browser = browser;
    }

    @Parameters("specificVariantIndex")
    @Test(enabled = true)
    public void login(@Optional Long specificVariantIndex) throws InterruptedException, IOException, Exception {
        SharedProperties.openBrowser(baseUrl, browser);
        Thread.sleep(3000);
        TestDetailsDTO testDetailsDTO = null;

        try {
            testDetailsDTO = TestDetailsExcelService.getTestDetails();
        } catch (RuntimeException re) {
            System.out.println(re.getMessage());
        }

        if (specificVariantIndex == null) {
            for (Long variantId : testDetailsDTO.getVariantIdList()) {
                SharedProperties.driver.navigate().to(PropertyHelper.readProperty("url") + variantId);
                SharedProperties.Click(productpage.getAddToCompareButton(),SharedProperties.driver);            }
        } else {
            SharedProperties.driver.navigate().to(PropertyHelper.readProperty("url") + testDetailsDTO.getVariantIdList().get(specificVariantIndex.intValue()));
            SharedProperties.Click(productpage.getAddToCompareButton(),SharedProperties.driver);
        }

        SharedProperties.Click(variantcompare.getCompareButton(),SharedProperties.driver);


    }


}
