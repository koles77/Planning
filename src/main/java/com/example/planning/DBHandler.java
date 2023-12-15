package com.example.planning;

import org.apache.poi.EmptyFileException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.*;
import java.util.ArrayList;

public class DBHandler {
    File file = new File("src\\main\\resources\\DB\\documents.xls");
    String path = "src\\main\\resources\\DB\\documents.xls";
    public ArrayList<String> listOfNames = new ArrayList<>();
    public DBHandler() throws IOException {

        if (!file.exists()) {
            System.out.println("file is not available");
          }

        else {
            FileInputStream forMainDB = new FileInputStream(file);
            HSSFWorkbook wb = new HSSFWorkbook(forMainDB, true);
            System.out.println("File is ready! Quantity of sheets is:" + wb.getNumberOfSheets());
            ArrayList<String> listOfPagesName = new ArrayList<>();
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                listOfPagesName.add(wb.getSheetName(i));
                System.out.println(wb.getSheetName(i));
            }
            listOfNames = listOfPagesName;
            wb.close();
            forMainDB.close();
        }
    }

    public void addDocToMainDB(String docsName, String docsRegNum) throws IOException {
       try {
             FileInputStream fis = new FileInputStream(file);
             HSSFWorkbook tempWB = new HSSFWorkbook(fis, true);
             fis.close();

             tempWB.createSheet(docsName);
             tempWB.getSheet(docsName).createRow(0).createCell(0).setCellValue(docsName);
             tempWB.getSheet(docsName).createRow(1).createCell(0).setCellValue(docsRegNum);
             FileOutputStream fos = new FileOutputStream(path);
             tempWB.write(fos);
             fos.close();
       }
       catch (EmptyFileException e) {
          System.out.println("File is empty");
       }

    }



}
