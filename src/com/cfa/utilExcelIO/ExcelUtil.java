package com.cfa.utilExcelIO;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: vikas.dhull
 * Date: 9/17/15
 * Time: 3:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExcelUtil {

    public static String getBrowser() throws IOException {

        String browser = ExcelReading.fn_FetchData(System.getProperty("user.dir")+ "\\POM_TestData.xls","CONF",3,"Value");
        return browser;
    }

    public static String getURL() throws IOException{

        String url = ExcelReading.fn_FetchData(System.getProperty("user.dir")+ "\\POM_TestData.xls","CONF",3,"Value");
        return url;
    }

    public static String getUserName() throws IOException{

        String username = ExcelReading.fn_FetchData(System.getProperty("user.dir")+ "\\POM_TestData.xls","CONF",3,"Value");
        return username;
    }

    public static String getPassword() throws IOException{

        String password = ExcelReading.fn_FetchData(System.getProperty("user.dir")+ "\\POM_TestData.xls","CONF",3,"Value");
        return password;
    }

    public static String getStoreName() throws IOException{

        String store = ExcelReading.fn_FetchData(System.getProperty("user.dir")+ "\\POM_TestData.xls","CONF",3,"Value");
        return store;
    }
}
