/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;
import java.io.*;
import java.util.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

public class ExcelCtrl {

//http://viralpatel.net/blogs/java-read-write-excel-file-apache-poi/

    public void read(String url){
        try {

            FileInputStream file = new FileInputStream(new File(url));

            //Get the workbook instance for XLS file
            HSSFWorkbook workbook = new HSSFWorkbook(file);

            //Get first sheet from the workbook
            HSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows from first sheet
            Iterator<Row> rowIterator = sheet.iterator();
            while(rowIterator.hasNext()) {
                    Row row = rowIterator.next();

                    //For each row, iterate through each columns
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while(cellIterator.hasNext()) {

                            Cell cell = cellIterator.next();

                            switch(cell.getCellType()) {
                                case BOOLEAN:
                                    System.out.print(cell.getBooleanCellValue() + "\t\t");
                                    break;
                                case NUMERIC:
                                    System.out.print(cell.getNumericCellValue() + "\t\t");
                                    break;
                                case STRING:
                                    System.out.print(cell.getStringCellValue() + "\t\t");
                                    break;
                            }
                    }
                    System.out.println("");
            }
            file.close();
            FileOutputStream out =
                    new FileOutputStream(new File("C:\\test.xls"));
            workbook.write(out);
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(Map<String, Object[]> data){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sample sheet");

//        Map<String, Object[]> data = new HashMap<String, Object[]>();
//        data.put("1", new Object[] {"Emp No.", "Name", "Salary"});
//        data.put("2", new Object[] {1d, "John", 1500000d});
//        data.put("3", new Object[] {2d, "Sam", 800000d});
//        data.put("4", new Object[] {3d, "Dean", 700000d});

        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                    Cell cell = row.createCell(cellnum++);
                if(obj instanceof Date)
                        cell.setCellValue((Date)obj);
                    else if(obj instanceof Boolean)
                            cell.setCellValue((Boolean)obj);
                    else if(obj instanceof String)
                            cell.setCellValue((String)obj);
                    else if(obj instanceof Double)
                            cell.setCellValue((Double)obj);
            }
        }

        try {
                FileOutputStream out =
                                new FileOutputStream(new File("C:\\new.xls"));
                workbook.write(out);
                out.close();
                System.out.println("Excel written successfully..");

        } catch (FileNotFoundException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
}
