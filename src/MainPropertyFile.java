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
 * To change this template use File | Settings | File Templates.
 */
public class MainPropertyFile {

    public String readProperty(){
    Properties prop = new Properties();
    InputStream input = null;
    try {

        input = new FileInputStream("E:\\Healthkart\\MainAutoQa\\Automation_testing_v4\\url.properties");
        prop.load(input);

        return prop.getProperty("url");

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
    public String readPropertyLoginExcelPath(){
        Properties prop = new Properties();
        InputStream input = null;
        try {

            input = new FileInputStream("E:\\Healthkart\\MainAutoQa\\Automation_testing_v4\\url.properties");
            prop.load(input);

            return prop.getProperty("LoginExcel");

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

    public String readPropertySignUpExcelPath(){
        Properties prop = new Properties();
        InputStream input = null;
        try {

            input = new FileInputStream("E:\\Healthkart\\MainAutoQa\\Automation_testing_v4\\url.properties");
            prop.load(input);

            return prop.getProperty("SignUpExcel");

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

    public String readPropertyProductIdExcelPath(){
        Properties prop = new Properties();
        InputStream input = null;
        try {

            input = new FileInputStream("E:\\Healthkart\\MainAutoQa\\Automation_testing_v4\\url.properties");
            prop.load(input);

            return prop.getProperty("productIdExcel");

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