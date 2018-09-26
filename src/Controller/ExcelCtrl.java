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

            Workbook wb = WorkbookFactory.create(new File(url));
//            OPCPackage pkg = OPCPackage.open(new File(url));
//            XSSFWorkbook wb = new XSSFWorkbook(pkg);
//            FileInputStream file = new FileInputStream(new File(url));
            //Get the workbook instance for XLS file
//            HSSFWorkbook workbook = new HSSFWorkbook(file);

            //Get first sheet from the workbook
//            HSSFSheet sheet = workbook.getSheetAt(0);
            Sheet sheet = wb.getSheetAt(0);
            //Iterate through each rows from first sheet
            Iterator<Row> rowIterator = sheet.iterator();
            Row row = rowIterator.next();

            while(rowIterator.hasNext()) {
                    row = rowIterator.next();

                    Cell col1 = row.getCell(1);
                    double numReg = 0;

                    if (!isCellEmpty(col1)) {
                        numReg = col1.getNumericCellValue();
                    }
                    Cell col2 = row.getCell(2);
                    String titulo = "";
                    if (!isCellEmpty(col2)) {
                        titulo = col2.getStringCellValue();
                    }
                    Cell col3 = row.getCell(3);
                    String formato = "";
                    if (!isCellEmpty(col3)) {
                        formato = col3.getStringCellValue();
                    }
                    Cell col4 = row.getCell(4);
                    String original = "";
                    boolean orig;
                    if (!isCellEmpty(col4)) {
                        original = col4.getStringCellValue();
                        orig = original.equalsIgnoreCase("si");
                    }
                    Cell col5 = row.getCell(5);
                    String tipoSoft = "";
                    if (!isCellEmpty(col5)) {
                        tipoSoft = col5.getStringCellValue();
                    }
                    Cell col6 = row.getCell(6);
                    String man ="";
                    boolean manual = false;
                    if (!isCellEmpty(col6)) {
                        man = col6.getStringCellValue();
                        manual = man.equalsIgnoreCase("si");
                    }
                    Cell col7 = row.getCell(7);
                    String ca ="";
                    boolean caja = false;
                    if (!isCellEmpty(col7)) {
                        ca = col7.getStringCellValue();
                        caja = ca.equalsIgnoreCase("si");
                    }
                    Cell col8 = row.getCell(8);
                    String sistemaOperativo = "";
                    String[] so = null;
                    if (!isCellEmpty(col8)) {
                        sistemaOperativo = col8.getStringCellValue();
                        so = sistemaOperativo.split(",");
                    }
                    Cell col9 = row.getCell(9);
                    double partes = 0;
                    if (!isCellEmpty(col9)) {
                        partes = col9.getNumericCellValue();
                    }
                    Cell col10 = row.getCell(10);
                    String ubicacion = "";
                    if (!isCellEmpty(col10)) {
                        ubicacion = col10.getStringCellValue();
                    }
                    Cell col11 = row.getCell(11);
                    double numcaja = 0;
                    if (!isCellEmpty(col11)) {
                        numcaja = col11.getNumericCellValue();
                    }

                    System.out.println(numReg + " " + titulo + " " + formato + " " + original + " " + tipoSoft + " " + manual + " " + caja + " " + sistemaOperativo + " " + partes + " " + ubicacion + " " + numcaja);
//                    Iterator<Cell> cellIterator = row.cellIterator();
//                    while(cellIterator.hasNext()) {
//
//                            Cell cell = cellIterator.next();
//
////                            switch(cell.getCellType()) {
////                                case BOOLEAN:
////                                    System.out.print(cell.getBooleanCellValue() + "\t\t");
////                                    break;
////                                case NUMERIC:
////                                    System.out.print(cell.getNumericCellValue() + "\t\t");
////                                    break;
////                                case STRING:
////                                    System.out.print(cell.getStringCellValue() + "\t\t");
////                                    break;
////                            }
//                            System.out.print(cell.getColumnIndex());
//                    }
//                    System.out.println("");
            }
            wb.close();
//            FileOutputStream out =
//                    new FileOutputStream(new File("C:\\test.xls"));
//            workbook.write(out);
//            out.close();

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

    public static boolean isCellEmpty(final Cell cell) {
    if (cell == null || cell.getCellType() == CellType.BLANK) {
        return true;
    }

    if (cell.getCellType() == CellType.STRING && cell.getStringCellValue().isEmpty()) {
        return true;
    }

    return false;
}
}
