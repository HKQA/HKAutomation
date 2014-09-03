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

}
