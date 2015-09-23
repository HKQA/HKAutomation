package com.cfa.utilExcelIO;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: vikas.dhull
 * Date: 9/17/15
 * Time: 9:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class ExcelReading {


    public static int fn_GetRowCount(String workBookPath, String sheetName) throws IOException {
        Workbook wbookObj=fn_GetWorkBook(workBookPath);
        Sheet sheetObj=wbookObj.getSheet(sheetName);
        int rowcnt=sheetObj.getLastRowNum();
        return rowcnt;
    }

    //to run this class standalone.
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        //File fileObj= new File("DataSheets/DataWorkbook");



        Workbook wbookObj=fn_GetWorkBook("DataSheets/DataWorkbook.xlsx");
        Sheet sheetObj=wbookObj.getSheetAt(0);
        sheetObj.getSheetName();
        int rowcnt=sheetObj.getLastRowNum();
        Row.MissingCellPolicy MCP=Row.CREATE_NULL_AS_BLANK;
        int strnumber= Cell.CELL_TYPE_STRING;
        int intnumber=Cell.CELL_TYPE_NUMERIC;
        int blanknumber=Cell.CELL_TYPE_BLANK;

        for(int i=0; i<=rowcnt;i++){
            Row rowObj=sheetObj.getRow(i);
            int cellcnt=rowObj.getLastCellNum();
            for(int j=0; j<=cellcnt-1;j++){
                Cell cellObj=rowObj.getCell(j, MCP);
                int cellTypeNum=cellObj.getCellType();
                String cellval=null;
                if(cellTypeNum==intnumber){
                    Double cellval1=cellObj.getNumericCellValue();
                    Integer cellval2= cellval1.intValue();
                    cellval =cellval2.toString();
                }else if(cellTypeNum==strnumber){
                    cellval=cellObj.getStringCellValue();

                }else if(cellTypeNum==blanknumber){
                    cellval="";
                }
                System.out.println(cellval);
                if(cellval.equalsIgnoreCase("selenium")){
                    System.out.println("Row NUmber-"+(i+1)+"Cell Number-"+(j+1));
                }
            }
        }



    }

    public static Workbook fn_GetWorkBook(String filePath) throws IOException{
        String[] arrPath=filePath.split("\\.");
        String extension=arrPath[1];
        FileInputStream fisObj=new FileInputStream(filePath);
        Workbook wbookObj=null;
        if(extension.equalsIgnoreCase("xlsx")){
            wbookObj=new XSSFWorkbook(fisObj);
        }else if(extension.equalsIgnoreCase("xls")){
            wbookObj=new HSSFWorkbook(fisObj);
        }

        return wbookObj;
    }


    public static String fn_FetchData(String workBookPath, String sheetName, int rownum, int cellnum) throws IOException{

        Workbook wbookObj=fn_GetWorkBook(workBookPath);
        Sheet sheetObj=wbookObj.getSheet(sheetName);
        Row.MissingCellPolicy MCP=Row.CREATE_NULL_AS_BLANK;
        int strnumber=Cell.CELL_TYPE_STRING;
        int intnumber=Cell.CELL_TYPE_NUMERIC;
        int blanknumber=Cell.CELL_TYPE_BLANK;

        Row rowObj=sheetObj.getRow(rownum);
        Cell cellObj=rowObj.getCell(cellnum, MCP);
        int cellTypeNum=cellObj.getCellType();
        String cellval=null;
        if(cellTypeNum==intnumber){
            Double cellval1=cellObj.getNumericCellValue();
            Integer cellval2= cellval1.intValue();
            cellval =cellval2.toString();
        }else if(cellTypeNum==strnumber){
            cellval=cellObj.getStringCellValue();

        }else if(cellTypeNum==blanknumber){
            cellval="";
        }

        return cellval;
    }


    public static String fn_FetchData(String workBookPath, String sheetName, int rownum, String ColumnName) throws IOException{

        Workbook wbookObj=fn_GetWorkBook(workBookPath);
        Sheet sheetObj=wbookObj.getSheet(sheetName);
        Row.MissingCellPolicy MCP=Row.CREATE_NULL_AS_BLANK;
        int strnumber=Cell.CELL_TYPE_STRING;
        int intnumber=Cell.CELL_TYPE_NUMERIC;
        int blanknumber=Cell.CELL_TYPE_BLANK;

        Row rowObj=sheetObj.getRow(rownum);
        int columnNumber=fn_GetColumnNumberByColumnName(sheetObj, ColumnName);
        Cell cellObj=rowObj.getCell(columnNumber, MCP);
        int cellTypeNum=cellObj.getCellType();
        String cellval=null;
        if(cellTypeNum==intnumber){
            Double cellval1=cellObj.getNumericCellValue();
            Integer cellval2= cellval1.intValue();
            cellval =cellval2.toString();
        }else if(cellTypeNum==strnumber){
            cellval=cellObj.getStringCellValue();

        }else if(cellTypeNum==blanknumber){
            cellval="";
        }

        return cellval;
    }

    public static int fn_GetColumnNumberByColumnName(Sheet sheetObj, String expColumnName){

        Row fstRowObj=sheetObj.getRow(0);
        int columncount=fstRowObj.getLastCellNum();
        int columnNum=0;
        Row.MissingCellPolicy MCP=Row.CREATE_NULL_AS_BLANK;
        for(int i=0; i<=columncount-1;i++){
            Cell cellObj=fstRowObj.getCell(i, MCP);
            String xl_columnname=cellObj.getStringCellValue();
            if(xl_columnname.trim().equalsIgnoreCase(expColumnName)){
                columnNum=i;
                break;
            }
        }

        return columnNum;
    }




    public static HashMap<String, String> fn_FetchRow(String workBookPath, String sheetName, int rownum) throws IOException{

        HashMap<String, String> HMObj=new HashMap<String, String>();
        Workbook wbookObj=fn_GetWorkBook(workBookPath);
        Sheet sheetObj=wbookObj.getSheet(sheetName);
        Row.MissingCellPolicy MCP=Row.CREATE_NULL_AS_BLANK;
        int strnumber=Cell.CELL_TYPE_STRING;
        int intnumber=Cell.CELL_TYPE_NUMERIC;
        int blanknumber=Cell.CELL_TYPE_BLANK;

        Row rowObj=sheetObj.getRow(rownum);
        Row fstrowObj=sheetObj.getRow(0);

        for(int i=0; i<=rowObj.getLastCellNum()-1;i++){
            Cell cellObj=rowObj.getCell(i, MCP);
            int cellTypeNum=cellObj.getCellType();
            String columnname=fstrowObj.getCell(i, MCP).getStringCellValue();
            String cellval=null;
            if(cellTypeNum==intnumber){
                Double cellval1=cellObj.getNumericCellValue();
                Integer cellval2= cellval1.intValue();
                cellval =cellval2.toString();
            }else if(cellTypeNum==strnumber){
                cellval=cellObj.getStringCellValue();

            }else if(cellTypeNum==blanknumber){
                cellval="";
            }
            HMObj.put(columnname, cellval);
        }


        return HMObj;
    }

    public static HashMap<String, String>[] fn_FetchTotalData(String workBookPath, String sheetName) throws IOException{
        int rowcnt=fn_GetRowCount(workBookPath, sheetName);
        HashMap<String, String>[] HM_Arr=new HashMap[rowcnt];


        Workbook wbookObj=fn_GetWorkBook(workBookPath);
        Sheet sheetObj=wbookObj.getSheet(sheetName);
        Row.MissingCellPolicy MCP=Row.CREATE_NULL_AS_BLANK;
        int strnumber=Cell.CELL_TYPE_STRING;
        int intnumber=Cell.CELL_TYPE_NUMERIC;
        int blanknumber=Cell.CELL_TYPE_BLANK;


        for(int j=1;j<=rowcnt;j++){

            HashMap<String, String> HMObj=new HashMap<String, String>();
            Row rowObj=sheetObj.getRow(j);
            Row fstrowObj=sheetObj.getRow(0);

            for(int i=0; i<=rowObj.getLastCellNum()-1;i++){
                Cell cellObj=rowObj.getCell(i, MCP);
                int cellTypeNum=cellObj.getCellType();
                String columnname=fstrowObj.getCell(i, MCP).getStringCellValue();
                String cellval=null;
                if(cellTypeNum==intnumber){
                    Double cellval1=cellObj.getNumericCellValue();
                    Integer cellval2= cellval1.intValue();
                    cellval =cellval2.toString();
                }else if(cellTypeNum==strnumber){
                    cellval=cellObj.getStringCellValue();

                }else if(cellTypeNum==blanknumber){
                    cellval="";
                }
                HMObj.put(columnname, cellval);
            }
            HM_Arr[j-1]	=HMObj;

        }

        return HM_Arr;
    }

}
