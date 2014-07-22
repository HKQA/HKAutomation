import com.beust.jcommander.JCommander;
import com.hk.property.PropertyHelper;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 7/4/14
 * Time: 12:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class BootStrap {

    public void runTestNGTest(Map<String, String > testngParams) {

//Create an instance on TestNG
        TestNG myTestNG = new TestNG();

//Create an instance of XML Suite and assign a name for it.
        XmlSuite mySuite = new XmlSuite();
        mySuite.setName("TestNG Test");

//Create an instance of XmlTest and assign a name for it.
        XmlTest myTest = new XmlTest(mySuite);
        myTest.setName("ASQA Sample Test");

//Add any parameters that you want to set to the Test.
        myTest.setParameters(testngParams);
        Map<String,String> suiteParam = new HashMap<String,String>();
        suiteParam.put("BaseURL", PropertyHelper.readProperty("baseUrl"));
        suiteParam.put("Browser","chrome");
        mySuite.setParameters(suiteParam);


//Create a list which can contain the classes that you want to run.
        List<XmlClass> myClasses = new ArrayList<XmlClass> ();
        myClasses.add(new XmlClass("com.hk.orderPlacement.ExistingOnlineOrder"));
        myClasses.add(new XmlClass("com.hk.orderPlacement.ExistingCodPlacement"));
        myClasses.add(new XmlClass("com.hk.orderPlacement.SignupOrderOnline"));
        myClasses.add(new XmlClass("com.hk.orderPlacement.SignupCodOrder"));
        myClasses.add(new XmlClass("com.hk.orderPlacement.CouponOnlineOrder"));

//Assign that to the XmlTest Object created earlier.
        myTest.setXmlClasses(myClasses);

//Create a list of XmlTests and add the Xmltest you created earlier to it.
        List<XmlTest> myTests = new ArrayList<XmlTest>();
        myTests.add(myTest);

//add the list of tests to your Suite.
        mySuite.setTests(myTests);

//Add the suite to the list of suites.
        List<XmlSuite> mySuites = new ArrayList<XmlSuite>();
        mySuites.add(mySuite);

//Set the list of Suites to the testNG object you created earlier.
        myTestNG.setXmlSuites(mySuites);

//invoke run() - this will run your class.
        myTestNG.run();
    }

    public static void main(String args[]) {

        BootStrap BootStrapMain = new BootStrap();

        //This Map can hold your testng Parameters.
        Map<String,String> testngParams = new HashMap<String,String> ();

        BootStrapMain.runTestNGTest(testngParams);

    }










}

