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

    public void setOrderId_Generated(String UseCase, String data)
    {


        excel.setCellData(UseCase, "OrderId_Generated", 3, data );





    }

    public static String getVariantId()
    {

        String variantId = excel.getCellData("Config", 1, 6)  ;

        return variantId;

    }

    public static String getCoupon(String UseCase)
    {


        String coupon = excel.getCellData(UseCase, "Coupon", 2);

        return coupon;

    }

    public static String getDBURL()
    {

        String db_URL = excel.getCellData("Config", 1, 3)  ;

        return db_URL;


    }

    public static String getDBPassword()
    {

        String db_Password = excel.getCellData("Config", 1, 5);

        return db_Password;

    }

    public static String getDBUser()
    {

        String db_User = excel.getCellData("Config", 1, 4);

        return db_User;

    }

    public static String getAdminURL()
    {

        String adminURL =  excel.getCellData("Config", 1, 7);

        return adminURL;

    }

    public static String getAdmin_User()
    {

        String adminUser = excel.getCellData("Config", 1, 8);

        return adminUser;


    }

    public static String getAdmin_Password()
    {

        String adminPassword = excel.getCellData("Config", 1, 9);

        return adminPassword;

    }

    public  static String getBright_URL()
    {

        String brightURL = excel.getCellData("Config", 1, 10)   ;

        return brightURL;

    }

    public static String getBright_User()
    {

        String brightUser = excel.getCellData("Config", 1, 11);

        return brightUser;


    }


    public static String getBright_Password()
    {

        String brightPassword = excel.getCellData("Config", 1, 12);

        return brightPassword;

    }

    public static String getCOD_Confirm_URL()
    {

        String codConfirmURL = excel.getCellData("Config", 1, 13);

        return codConfirmURL;

    }

    public static String getCOD_Confirm_User()
    {

        String codConfirmUser = excel.getCellData("Config", 1, 14);

        return codConfirmUser;

    }

    public static String getCOD_Confirm_Password()
    {

        String codConfirmPassword = excel.getCellData("Config", 1, 15);

        return codConfirmPassword;

    }

}
