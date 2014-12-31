package com.hk.recorder;

import com.hk.commonProperties.SharedProperties;
import com.hk.excel.TestDetailsExcelService;
import com.hk.excel.dto.TestDetailsDTO;
import com.hk.util.TestUtil;
import org.openqa.selenium.By;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 12/26/14
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class MultipleVariant {

    public void testMultipleVariant() throws Exception {

        TestDetailsDTO testDetailsDTO = null;

        testDetailsDTO = TestDetailsExcelService.getTestDetails();

        for(Long variantId : testDetailsDTO.getVariantIdList())
        {
            SharedProperties.driver.navigate().to("http://192.168.70.51:9090/sv/oh-yeah!-isolate/SP-29982?navKey=VRNT-"+ variantId);

            Thread.sleep(5000);


           Boolean elementStatus = SharedProperties.isElementPresent("//*[@id='variant-page']/div[2]/div[4]/div[1]/div/div[2]/div[2]/div/div[1]/input");

            System.out.println(elementStatus);

            //SharedProperties.driver.findElement(By.xpath("//*[@id='variant-page']/div[2]/div[3]/div[1]/div/div[2]/div[2]/div/div[1]/input")).click();
            SharedProperties.driver.findElement(By.xpath("//*[@value = 'Buy Now']")).click();
        }


        Thread.sleep(3000);

        SharedProperties.driver.findElement(By.xpath("//*[@id='cartPop']/div/div/div/div[5]/a")).click();



    }

    public static void main(String[] args) throws Exception {
        MultipleVariant multipleVariant = new MultipleVariant();

        SharedProperties.openBrowser(TestUtil.getURL(), TestUtil.getBrowser());

        multipleVariant.testMultipleVariant();





    }
}
