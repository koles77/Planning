package com.example.planning;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class DBHandler {
   File file = new File("src\\main\\resources\\DB\\documents.xls");
   FileInputStream inpStreamFile = new FileInputStream(file);
   Workbook wb = new HSSFWorkbook(inpStreamFile);
   public ArrayList<String> listOfPagesName = new ArrayList<>();


    public DBHandler() throws IOException {
       if (file.exists()) {
          System.out.println("File is ready! Quantity of sheets is:" + wb.getNumberOfSheets());
          for (int i = 0; i < wb.getNumberOfSheets(); i++) {
             listOfPagesName.add(wb.getSheetName(i));
             System.out.println(wb.getSheetName(i));
          }
       }
    }
}
