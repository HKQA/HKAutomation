package rough;

import com.hk.util.TestUtil;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 2/12/15
 * Time: 1:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class CFAExcelTest {


    public void cfaExcelTest()
    {

        System.out.println(TestUtil.excel.isSheetExist("GGN"));




    }


    public static void main(String[] args)
    {

        CFAExcelTest test = new CFAExcelTest();

        test.cfaExcelTest();



    }



}
