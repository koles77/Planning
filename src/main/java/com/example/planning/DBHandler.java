package com.example.planning;

import org.apache.poi.EmptyFileException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DBHandler {
//    File file = new File("src\\main\\resources\\DB\\documents.xls");
    File file = new File("documents.xls");

//    String path = "src\\main\\resources\\DB\\documents.xls";
    String path = "documents.xls";

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
            for (int i = 2; i < wb.getNumberOfSheets(); i++) {
                listOfPagesName.add(wb.getSheetName(i));
//                System.out.println(wb.getSheetName(i));
            }
            listOfNames = listOfPagesName;
            wb.close();
            forMainDB.close();
        }
    }

    public void addDocToMainDB(String docsName, String docsRegNum) throws IOException {
        try {
            ArrayList<String> listOfMainCell = new ArrayList<>();
            FileInputStream fis = new FileInputStream(file);
            HSSFWorkbook tempWB = new HSSFWorkbook(fis, true);
            fis.close();
            HSSFSheet sheet = tempWB.createSheet(docsName);
            HSSFRow row = sheet.createRow(2);

            sheet.createRow(0).createCell(0).setCellValue(docsName);
            sheet.createRow(1).createCell(0).setCellValue(docsRegNum);

            for (int i = 0; i < 24; i++) {
                row.createCell(i).setCellValue(tempWB.getSheet("EmptyPage").getRow(2).getCell(i).toString());
//                System.out.println(tempWB.getSheet("EmptyPage").getRow(2).getCell(i).toString());
            }

//            FileOutputStream fos = new FileOutputStream(path);
            FileOutputStream fos = new FileOutputStream(new File(path));
            tempWB.write(fos);
            fos.close();

        } catch (EmptyFileException e) {
            System.out.println("File is empty");
        }

    }

    public HashMap<String, ArrayList<String>> getGuideInfo() throws IOException {
        ArrayList<String> kindOfActList = new ArrayList<>();
        ArrayList<String> forTakeAChoiceList = new ArrayList<>();
        ArrayList<String> forTakeAnExecutorList = new ArrayList<>();
        ArrayList<String> forTakeAnCoExecutorList = new ArrayList<>();

        HashMap<String, ArrayList<String>> arrayOfArray = new HashMap<>();
        FileInputStream getGuideFIS = new FileInputStream(file);
        HSSFWorkbook getGuideWB = new HSSFWorkbook(getGuideFIS);

        int i = 2;
        try {
            while (getGuideWB.getSheetAt(0).getRow(i).getCell(1).getCellType() != CellType._NONE) {
                kindOfActList.add(getGuideWB.getSheetAt(0).getRow(i).getCell(1).getStringCellValue());
                i++;
            }
        }
        catch (NullPointerException e) {
            arrayOfArray.put("kindOfActList", kindOfActList);
//            System.out.println("Исключение ++");
        }
        int j = 3;
        try {
            while (!getGuideWB.getSheetAt(0).getRow(1).getCell(j).getStringCellValue().equals("Исполнитель")) {
                ArrayList<String> kindOfWorkPersons = new ArrayList<>();
                int k = 2;
                String nameOfKind = getGuideWB.getSheetAt(0).getRow(1).getCell(j).getStringCellValue();
                forTakeAChoiceList.add(nameOfKind);
                try{
                    while (getGuideWB.getSheetAt(0).getRow(k).getCell(j).getCellType() != CellType._NONE) {
                        kindOfWorkPersons.add(getGuideWB.getSheetAt(0).getRow(k).getCell(j).getStringCellValue());
                        k++;
                    }
                }
                catch (NullPointerException s) {
                    System.out.println("Исключение номер " + k);
                }
                arrayOfArray.put(nameOfKind, kindOfWorkPersons);
                j += 2;
            }
            arrayOfArray.put("forTakeAChoiceList", forTakeAChoiceList);

        }
        catch (NullPointerException s) {
            System.out.println("ForTakeAchoiceList exception");
        }
        int z = 2;
        try {
            while (getGuideWB.getSheetAt(0).getRow(z).getCell(11).getCellType() == CellType.STRING) {
                forTakeAnExecutorList.add(getGuideWB.getSheetAt(0).getRow(z).getCell(11).getStringCellValue());
                z++;
            }
        }
        catch (NullPointerException e) {
            arrayOfArray.put("forTakeAnExecutorList", forTakeAnExecutorList);
//            for (String g : forTakeAnExecutorList) System.out.println(g);
//            System.out.println("Исключение в исполнителях");
        }
        int f = 2;
        try {
            while (getGuideWB.getSheetAt(0).getRow(f).getCell(13).getCellType() == CellType.STRING) {
                forTakeAnCoExecutorList.add(getGuideWB.getSheetAt(0).getRow(f).getCell(13).getStringCellValue());
                f++;
            }
        }
        catch (NullPointerException e) {
            arrayOfArray.put("forTakeAnCoExecutorList", forTakeAnCoExecutorList);
//            for (String g : forTakeAnCoExecutorList) System.out.println(g);
//            System.out.println("Исключение в соисполнителях");
        }


        getGuideWB.close();
        getGuideFIS.close();
    return  arrayOfArray;
    }

    public void addItemsInDoc (String sheetName, String numberOfItem, String nameOfDocument, String registrationNumer,
                               String textOfItem, String kindOfAction, String mainPerson, String executor, String coexecutor) {
        //пишем метод для добавления пунктов в док
        int indexOfCurrentRow = getNumberOfcurrentRow(sheetName);
        try {
            FileInputStream fis = new FileInputStream(file);
            HSSFWorkbook tempWB = new HSSFWorkbook(fis, true);
            HSSFRow currentRow = tempWB.getSheet(sheetName).createRow(indexOfCurrentRow);

            currentRow.createCell(0).setCellValue(numberOfItem);
            currentRow.createCell(1).setCellValue(nameOfDocument.toString());
            currentRow.createCell(2).setCellValue(registrationNumer.toString());
            currentRow.createCell(3).setCellValue(textOfItem.toString());
            currentRow.createCell(4).setCellValue(kindOfAction.toString());
            currentRow.createCell(5).setCellValue(mainPerson.toString());
            currentRow.createCell(6).setCellValue(executor.toString());
            currentRow.createCell(7).setCellValue(coexecutor.toString());


            FileOutputStream fos = new FileOutputStream(path);
            tempWB.write(fos);
            fos.close();
        } catch (Exception e) {
            System.out.println(e);
        }



//            FileOutputStream fos = new FileOutputStream(path);
//            tempWB.write(fos);
//            fos.close();
//            Неполный список полей для заполнения листа
//            String numerOfitem, String textOfItem, String kindOfAct, String executor,
//                    String coexecutor, Boolean mainDiv, Boolean firstDiv, Boolean secDiv, Boolean thirdDiv

    }

    public int getNumberOfcurrentRow(String sheetName) {
        int indexOfRow = 0;
        try {
            FileInputStream fis = new FileInputStream(file);
            HSSFWorkbook tempWB = new HSSFWorkbook(fis, true);
            HSSFSheet sheet = tempWB.getSheet(sheetName);
            fis.close();
            while (sheet.getRow(indexOfRow).getCell(0).getCellType() != CellType._NONE) {
                indexOfRow++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    return indexOfRow;
    }

}
