package com.hk.property;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 7/8/14
 * Time: 3:03 PM
 */
public class PropertyHelper {

    public static String readProperty;

    public static String readProperty(String key){
    Properties prop = new Properties();
    InputStream input = null;
    try {

        input = new FileInputStream("C:\\selenium\\Automation_testing_v4\\url.properties");
        prop.load(input);

        return prop.getProperty(key);

    } catch (IOException ex) {
        ex.printStackTrace();
    } finally {
        if (input != null) {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    return null;
}

}