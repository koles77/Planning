package com.example.planning;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import org.apache.poi.EmptyFileException;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class DBHandler {
    //    File file = new File("src\\main\\resources\\DB\\documents.xls");
    File file = new File("documents.xls");

    //    String path = "src\\main\\resources\\DB\\documents.xls";
    String path = "documents.xls";

    public ArrayList<String> listOfNames = new ArrayList<>();

    public DBHandler() throws IOException {

        if (!file.exists()) {
            System.out.println("file is not available");
        } else {
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
        } catch (NullPointerException e) {
            arrayOfArray.put("kindOfActList", kindOfActList);
        }
        int z = 2;
        try {
            while (getGuideWB.getSheetAt(0).getRow(z).getCell(11).getCellType() == CellType.STRING) {
                forTakeAnExecutorList.add(getGuideWB.getSheetAt(0).getRow(z).getCell(11).getStringCellValue());
                z++;
            }
        } catch (NullPointerException e) {
            arrayOfArray.put("forTakeAnExecutorList", forTakeAnExecutorList);
        }

        int f = 2;
        try {
            while (getGuideWB.getSheetAt(0).getRow(f).getCell(13).getCellType() == CellType.STRING) {
                forTakeAnCoExecutorList.add(getGuideWB.getSheetAt(0).getRow(f).getCell(13).getStringCellValue());
                f++;
            }
        } catch (NullPointerException e) {
            arrayOfArray.put("forTakeAnCoExecutorList", forTakeAnCoExecutorList);
        }

        getGuideWB.close();
        getGuideFIS.close();
        return arrayOfArray;
    }

    public void addItemsInDoc(String sheetName, String numberOfItem, String nameOfDocument, String registrationNumer,
                              String textOfItem, String kindOfAction, String executor, String coexecutor,
                              ArrayList<String> date) {
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

            currentRow.createCell(6).setCellValue(executor.toString());
            currentRow.createCell(7).setCellValue(coexecutor.toString());
            int i = 8;
            for (String d : date) {
                currentRow.createCell(i).setCellValue(d);
                i++;
            }

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

    //Получить номер документа и поместить его в форму добавления пунктов в документ
    public String getNumberOfTheDoc(String sheet) {
        String numberOfThedoc = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            HSSFWorkbook tempWB = new HSSFWorkbook(fis, true);
            HSSFSheet sheetOfCurrentDoc = tempWB.getSheet(sheet);
            numberOfThedoc = sheetOfCurrentDoc.getRow(1).getCell(0).toString();
            fis.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return numberOfThedoc;
    }

    //Работа с ListView (добавление элементов, отметки на кнопках, множественный выбор)
    public void toSetItemInListView(ListView<String> listView, Set<String> setList, Label label) {

        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listView.setOnMouseClicked((EventHandler<Event>) event -> {

            System.out.println(listView.getSelectionModel().getSelectedItem() + " " + setList.toString());
            ObservableList<String> selectedItems = listView.getSelectionModel().getSelectedItems();

            if (!setList.isEmpty()) {
                Iterator<String> iterator = setList.iterator();
                while (iterator.hasNext()) {
                    String name = iterator.next();
                    if (name.equals(listView.getSelectionModel().getSelectedItem())) {
                        iterator.remove();

                        StringBuilder line = new StringBuilder();
                        for (String n : setList) line.append(n + "; ");
                        label.setText(line.toString());
                    } else {
                        for (String s : selectedItems) {
                            setList.add(s);

                            StringBuilder line = new StringBuilder();
                            for (String n : setList) line.append(n + "; ");
                            label.setText(line.toString());
                        }
                    }
                }
            } else {
                for (String s : selectedItems) {
                    setList.add(s);

                    StringBuilder line = new StringBuilder();
                    for (String n : setList) line.append(n + "; ");
                    label.setText(line.toString());
                }
            }
        });
    }

    //Работа с датами
    public ArrayList<String> toGetDateArrList(LocalDate date, CheckBox daily, CheckBox weekly, CheckBox monthly, CheckBox quarently) {
        ArrayList<String> targetArr = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formatedDate = date.format(dtf);

        if (daily.isSelected()) {
            targetArr.add("everyDay");
            return targetArr;
        }
        // Недельные мероприятия
        else if (weekly.isSelected()) {
            int currentYear = date.getYear();
            targetArr.add(formatedDate);
            while (date.getDayOfYear() < 365 && date.getYear() == currentYear) {
                date = date.plusDays(7);
                formatedDate = date.format(dtf);
                targetArr.add(formatedDate.toString());
            }
            return targetArr;
        }
        //Ежемесячные мероприятия
        else if (monthly.isSelected()) {
            int currentYear = date.getYear();
            targetArr.add(formatedDate);
            while (date.getDayOfYear() < 365 && date.getYear() == currentYear) {
                date = date.plusMonths(1);
                formatedDate = date.format(dtf);
                targetArr.add(formatedDate.toString());
            }
            return targetArr;
        }
        //Ежеквартальные мероприятия
        else if (quarently.isSelected()) {
            int currentYear = date.getYear();
            targetArr.add(formatedDate);
            while (date.getDayOfYear() < 365 && date.getYear() == currentYear) {
                date = date.plusMonths(3);
                formatedDate = date.format(dtf);
                targetArr.add(formatedDate.toString());
            }
            return targetArr;
        }
        targetArr.add(formatedDate);
        return targetArr;
    }

    //получение записей из БД
    public ArrayList<String> takeActivityFromDB() throws IOException {
        ArrayList<String> listOfPoints = new ArrayList<>();
        FileInputStream fis = new FileInputStream(file);
        HSSFWorkbook tempWB = new HSSFWorkbook(fis);
        int quantity = tempWB.getNumberOfSheets();
        LocalDate date = LocalDate.now();
        System.out.println(date);
        LocalDate controlDate = date.plusDays(10);
        System.out.println(tempWB.getSheetAt(2).getLastRowNum());

        for (int i = 2; i <= quantity - 1; i++) {
            HSSFSheet sheet = tempWB.getSheetAt(i);
            int k = 3;
            while (k <= sheet.getLastRowNum()) {
                int j = 8;
                while (sheet.getRow(k).getCell(j) != null) {
                    String dateInTab = sheet.getRow(k).getCell(j).getStringCellValue();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    LocalDate localDate = LocalDate.parse(dateInTab, dtf);
                    if (localDate.isAfter(date) && localDate.isBefore(controlDate)) {
                        String point = sheet.getRow(k).getCell(0).getStringCellValue() + "\n" + sheet.getRow(k).getCell(3).getStringCellValue() +
                                "\n" + sheet.getRow(k).getCell(4).getStringCellValue() + "\n " + dateInTab;
                        listOfPoints.add(point);
                        System.out.println("пункт");
                    }
                    j++;
                }
                k++;
            }
        }
        return listOfPoints;
    }
}
