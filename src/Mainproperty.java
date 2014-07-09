import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 7/9/14
 * Time: 2:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class Mainproperty{
        public static String readProperty(String key){
            Properties prop = new Properties();
            InputStream input = null;
            try {

                input = new FileInputStream("E:\\Healthkart\\MainAutoQa\\Automation_testing_v4\\other.properties");
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
