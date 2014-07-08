import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 7/4/14
 * Time: 1:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExcelServiceImpl {
    public List<String> mainReadFromExcelIterator(String fileName) throws FileNotFoundException, IOException {

        int sheetNo = 0;

        List<String> readExcelData = new LinkedList<String>();
        int cnt = 0;
        try {

            InputStream input = new BufferedInputStream(new FileInputStream(fileName));
            POIFSFileSystem fs = new POIFSFileSystem(input);
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(sheetNo);

            Iterator rows = sheet.rowIterator();

            while (rows.hasNext()) {
                int cellCount = 0;
                int flagStop = 0;
                HSSFRow row = (HSSFRow) rows.next();
                System.out.println("\n");
                Iterator cells = row.cellIterator();

                while (cells.hasNext()) {
                    if (cellCount <= 10) {
                        int cellValueInt;
                        String CellValue;
                        HSSFCell cell = (HSSFCell) cells.next();

                        if (HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                            //System.out.print( cell.getNumericCellValue()+"     " );
                            cellValueInt = (int) cell.getNumericCellValue();
                            CellValue = Integer.toString(cellValueInt);
                        } else {
                            CellValue = cell.getStringCellValue();
                        }
                        readExcelData.add(CellValue);
                        cnt++;

                        cellCount++;
                    }

                    if (cellCount == 11)
                        break;
                }

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return readExcelData;
    }


    public static boolean updateCellContent(String fileName, String dataToAppend, int cellRow, int cellColumn) {
        boolean success = false;
        int sheetNo = 0;
        try {
            InputStream input = new BufferedInputStream(new FileInputStream(fileName));
            POIFSFileSystem fs = new POIFSFileSystem(input);
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(sheetNo);

            HSSFCell cell = sheet.getRow(cellRow).getCell(cellColumn);
            cell.setCellValue(dataToAppend + cell.getStringCellValue());

            input.close();

            FileOutputStream outFile = new FileOutputStream(new File(fileName));
            wb.write(outFile);
            outFile.close();

            success = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

}