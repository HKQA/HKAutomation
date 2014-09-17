package com.hk.util;



public class TestUtil  {
	
public static Xls_Reader excel = new Xls_Reader(System.getProperty("user.dir")+ "\\testData.xls");
	
	
	public static Object[][] getData(String sheetName){
		
		 /*if(excel == null){
				// load the Excel sheet
				excel = new Xls_Reader("c://selenium//data.xlsx");
					
			}*/
			//String sheetName="Login";
			int rows = excel.getRowCount(sheetName);  // Get Row Count
			int cols = excel.getColumnCount(sheetName);  // Get Col Count
			Object data[][] = new Object[rows-1][cols]; //-1
			
			for(int rowNum = 2 ; rowNum <= rows ; rowNum++){ //2
				
				for(int colNum=0 ; colNum< cols; colNum++){
					data[rowNum-2][colNum]=excel.getCellData(sheetName, colNum, rowNum); //-2
				}
			}
		
		return data;
		
	}
	
	
	public static boolean isExecutable(String tcid)
	{
		
		for(int rowNum = 2; rowNum <= excel.getRowCount("test_suite"); rowNum++ )
		{
			
			if(excel.getCellData("test_suite", "TCID", rowNum).equals(tcid))
			{
				
				
				if (excel.getCellData("test_suite", "RunMode", rowNum).equals("Y"))
				{
					
					return true;
					
				}else if (excel.getCellData("test_suite", "RunMode", rowNum).equals("N"))
				{
					
					return false;
					
				}
			}
			
		}
		
		return false;
	}

    public static String getBrowser()
    {

        String browser = excel.getCellData("Config", 1, 1 )    ;

        return  browser;



    }

    public static String getURL()
    {

        String URL = excel.getCellData("Config", 1, 2);

        return URL;



    }

    public static String getEmailId(String UseCase )
    {

        String emailId = excel.getCellData(UseCase,"Email", 2 )   ;

        return emailId;

    }

    public static String getUserName()
    {

        String userName =  excel.getCellData("SignupCodOrder", "Name_User", 2);

        return userName;


    }

    public static String getPassword(String UseCase)
    {

        String password = excel.getCellData(UseCase, "Password", 2);

        return password;

    }

    public static String getConfirmPassword(String UseCase)
    {

        String confirmPassword = excel.getCellData(UseCase, "Confirm_Password", 2);

        return confirmPassword;



    }

    public static String getAddressName(String UseCase)
    {
        String sheetName = UseCase;

        String addressName = excel.getCellData(sheetName, "Name_Address", 2);

        return addressName;

    }

    public static String getMobile_Number(String UseCase)
    {

        String mobileNumber = excel.getCellData(UseCase, "Mobile_Number", 2);

        return mobileNumber;

    }

    public static String getAlternateNumber(String UseCase)
    {

        String alternateNumber = excel.getCellData(UseCase, "Alternate_Number", 2);

        return alternateNumber;


    }

    public static String getAddress(String UseCase)
    {

        String address = excel.getCellData(UseCase, "Address", 2);

        return address;

    }

    public static String getPincode(String UseCase)
    {


        String pincode = excel.getCellData(UseCase, "Pincode", 2);

        return pincode;

    }

    public static String getUserName(String UseCase)
    {

        String userName =  excel.getCellData(UseCase, "Name_User", 2);

        return userName;


    }

    public static String getGuestEmail(String UseCase)
    {

        String guestEmail = excel.getCellData(UseCase, "Guest_Email", 2 ) ;

        return guestEmail;


    }

}
