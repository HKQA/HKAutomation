package rough;
import com.hk.util.*;
/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 9/2/14
 * Time: 1:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestRunMode extends TestUtil {

    public static void main(String[] args)
    {

         System.out.println(System.getProperty("user.dir"));

         System.out.println(excel.isSheetExist("test_suite"));

        System.out.println(TestUtil.isExecutable("SignupCodeOrder"));
        
        
        





    }

}
