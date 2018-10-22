/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;
import DAO.*;
import Model.*;
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
                //CODIGO
                    Cell col1 = row.getCell(1);
                    double numReg = 0;
                    String codigo = null;
                    if (!isCellEmpty(col1)) {
                        numReg = col1.getNumericCellValue();
                        codigo = String.valueOf(numReg);
                    }
                //NOMBRE
                    Cell col2 = row.getCell(2);
                    String titulo = "";
                    if (!isCellEmpty(col2)) {
                        titulo = col2.getStringCellValue();
                    }
                //FORMATO
                    Cell col3 = row.getCell(3);
                    String formato = "";
                    String formato1 = "INVALIDO";
                    FormatoDB f = new FormatoDB();
                    f.connect();
                    if (!isCellEmpty(col3)) {
                        formato = col3.getStringCellValue();
                        List<String> fo = f.read("Formatos");
                        for(String form : fo)
                            if(form.equalsIgnoreCase(formato)){
                                formato1=form;
                                break;
                            }
                    }
                //ORIGEN
                    Cell col4 = row.getCell(4);
                    String original = "";
                    int origen = 0;
                    if (!isCellEmpty(col4)) {
                        original = col4.getStringCellValue();
                        if(original.equalsIgnoreCase("si"))
                            origen = 1;
                        else if(original.equalsIgnoreCase("no"))
                            origen = 3;
                        else
                            origen = 2;
                    }
                //DRIVER/PROGRAMA/DESCONOCIDO/de Recup/Galeria de Videos/Sistema Operativo
                    Cell col5 = row.getCell(5);
                    String tipoSoft = "";
                    if (!isCellEmpty(col5)) {
                        tipoSoft = col5.getStringCellValue();
                    }
                //MANUAL
                    Cell col6 = row.getCell(6);
                    String man ="";
                    boolean manual = false;
                    if (!isCellEmpty(col6)) {
                        man = col6.getStringCellValue();
                        manual = man.equalsIgnoreCase("si");
                    }
                //CAJA
                    Cell col7 = row.getCell(7);
                    String ca ="";
                    boolean caja = false;
                    if (!isCellEmpty(col7)) {
                        ca = col7.getStringCellValue();
                        caja = ca.equalsIgnoreCase("si");
                    }
                //SISTEMA OPERATIVO
                    Cell col8 = row.getCell(8);
                    String sistemaOperativo = "";
                    String[] so = null;
                    if (!isCellEmpty(col8)) {
                        sistemaOperativo = col8.getStringCellValue();
                        so = sistemaOperativo.split(",");
                    }
                //PARTES
                    Cell col9 = row.getCell(9);
                    double partes = 0;
                    if (!isCellEmpty(col9)) {
                        partes = col9.getNumericCellValue();
                    }
                //UBICACION
                    Cell col10 = row.getCell(10);
                    String ubicacion = "";
                    UbicacionesCtrl u = new UbicacionesCtrl();
                    if(u.getUbis().isEmpty())
                        u.cargarUbicaciones();
                    Ubicaciones ub = null;
                    if (!isCellEmpty(col10)) {
                        ubicacion = col10.getStringCellValue();
                        ub = u.findUbicacion(ubicacion);
                    }
                //OBSERVACIONES
                    Cell col11 = row.getCell(11);
                    String obs = null;
                    if (!isCellEmpty(col11)) {
                        obs = col11.getStringCellValue();
                    }
                    String version=" ";

                    SoftwareCtrl s = new SoftwareCtrl();
                    boolean nuevo = s.altaSoftware(titulo, version);
                    SoftwareDB swDB = new SoftwareDB();
                    swDB.setNombre(titulo);
                    swDB.setVersion(version);
                    swDB.connect();
                    String y = swDB.executeSearch();
                    int codidoSw = 0;
                    if(y.matches("[0-9]*"))
                        codidoSw = Integer.parseInt(swDB.executeSearch());
                    Software soft = new SoftwareCtrl().findSoftware(codidoSw);
                    List<String> sistOp = new ArrayList<>();
                    if(nuevo){
                        for(String x : so){
                           s.agregarSoDeSw(codidoSw, x);
                           sistOp.add(x);
                        }
                        soft.setSistOp(sistOp);
                    }else if(soft!=null)
                        sistOp = soft.getSistOp();
                    List<Software> listSw = new ArrayList<>();
                    if(soft!=null)
                        listSw.add(soft);
                    MediosCtrl mec = new MediosCtrl();
                    if(MediosCtrl.getMedios().isEmpty())
                        mec.cargarMedios();
                    Medios m = mec.findMedio(codigo);
                    if(m==null){
                        f.setFormato(formato1);
                        String fo = f.searchTable();
                        int formid = Integer.parseInt(fo);
                        mec.altaMedio(codigo, titulo, formid, caja, manual, origen, ub, false, " ", " ", (int)partes, listSw);
                        m = new Medios(codigo, titulo, formato1, caja, manual, origen, ub, false, " ", obs, (int)partes);
                        MediosCtrl.getMedios().add(m);
                    }
                    //System.out.println(numReg + " " + titulo + " " + formato + " " + original + " " + tipoSoft + " " + manual + " " + caja + " " + sistemaOperativo + " " + partes + " " + ubicacion + " " + obs);
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
