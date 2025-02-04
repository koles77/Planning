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
import org.apache.poi.ss.usermodel.Row;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import javax.xml.transform.Source;
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
        ArrayList<String> subsectionKindOfActList = new ArrayList<>();
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
        int k = 2;
        try {
            while (getGuideWB.getSheetAt(0).getRow(k).getCell(2).getCellType() != CellType._NONE) {
                subsectionKindOfActList.add(getGuideWB.getSheetAt(0).getRow(k).getCell(2).getStringCellValue());
                k++;
            }
        } catch (NullPointerException e) {
            arrayOfArray.put("subsectionKindOfActList", subsectionKindOfActList);
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

    public void addItemsInDoc(String sheetName, String nameOfDocument, String registrationNumer,
                              String textOfItem, String kindOfAction, String subsectionKindOfAction, String executor, String coexecutor,
                              ArrayList<String> date) {
        //пишем метод для добавления пунктов в док
        int indexOfCurrentRow = getNumberOfcurrentRow(sheetName);
        try {
            FileInputStream fis = new FileInputStream(file);
            HSSFWorkbook tempWB = new HSSFWorkbook(fis, true);
            HSSFRow currentRow = tempWB.getSheet(sheetName).createRow(indexOfCurrentRow);

            currentRow.createCell(1).setCellValue(nameOfDocument.toString());
            currentRow.createCell(2).setCellValue(registrationNumer.toString());
            currentRow.createCell(3).setCellValue(textOfItem.toString());
            currentRow.createCell(4).setCellValue(kindOfAction.toString());

            if (subsectionKindOfAction == null) {
                if (kindOfAction.substring(0,4).contains("III.")) {
                    subsectionKindOfAction = "3. ";
                } else subsectionKindOfAction = "1. ";
            }

            currentRow.createCell(5).setCellValue(subsectionKindOfAction.toString());
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

    //получение записей из БД для контроля мероприятий
    public ArrayList<String> takeActivityFromDB() throws IOException {
        ArrayList<String> listOfPoints = new ArrayList<>();
        FileInputStream fis = new FileInputStream(file);
        HSSFWorkbook tempWB = new HSSFWorkbook(fis);
        int quantity = tempWB.getNumberOfSheets();
        LocalDate date = LocalDate.now();
        LocalDate controlDate = date.plusDays(10);

        for (int i = 2; i <= quantity - 1; i++) {
            HSSFSheet sheet = tempWB.getSheetAt(i);
            int k = 3;
            while (k <= sheet.getLastRowNum()) {
                int j = 8;
                while (sheet.getRow(k).getCell(j) != null) {
                    String dateInTab = sheet.getRow(k).getCell(j).getStringCellValue();

                    if (dateInTab.equals("everyDay")) {
                        String point = sheet.getRow(k).getCell(0).getStringCellValue() + "\n" + sheet.getRow(k).getCell(3).getStringCellValue() +
                                "\n" + sheet.getRow(k).getCell(4).getStringCellValue() + "\n " + dateInTab;
                        listOfPoints.add(point);
                    } else {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                        LocalDate localDate = LocalDate.parse(dateInTab, dtf);
                        if (localDate.isAfter(date) && localDate.isBefore(controlDate)) {
                            String point = sheet.getRow(k).getCell(3).getStringCellValue() +
                                    "\n" + sheet.getRow(k).getCell(4).getStringCellValue() + "\n " + dateInTab;
                            listOfPoints.add(point);
                            System.out.println("пункт");
                        }
                    }
                    j++;
                }
                k++;
            }
        }
        return listOfPoints;
    }

    // получение всех мероприятий в месяце
    public HashMap<String, HashMap<String, ArrayList<String>>> takeActivityByMonthFromDB(int month) throws IOException {
        HashMap<String, HashMap<String, ArrayList<String>>> generalMap = new HashMap<>();

        FileInputStream fis = new FileInputStream(file);
        HSSFWorkbook tempWB = new HSSFWorkbook(fis);
        int quantity = tempWB.getNumberOfSheets();

        int l = 2;

        String itemOfKOA;
        String itemOfSubsection;
        // l - строка в справочнике
        while (tempWB.getSheetAt(0).getRow(l).getCell(1) != null) {
            itemOfKOA = tempWB.getSheetAt(0).getRow(l).getCell(1).toString();
            generalMap.put(itemOfKOA, new HashMap<>());


            int m = 2;
            while (tempWB.getSheetAt(0).getRow(m).getCell(2) != null) {
                itemOfSubsection = tempWB.getSheetAt(0).getRow(m).getCell(2).toString();
//                itemOfKOA = tempWB.getSheetAt(0).getRow(m).getCell(1).toString();

                int indexOfSubsection = 0; // индекс подраздела

              // разобраться нах!!!!!
                if (itemOfKOA.contains("IV.")) indexOfSubsection = 4;
                else if (itemOfKOA.contains("III.")) {
                    indexOfSubsection = 3;
                    generalMap.get(itemOfKOA).put("3. ", new ArrayList<>());
                }
                else if (itemOfKOA.contains("II.")) indexOfSubsection = 2;
                else if (itemOfKOA.contains("I.")) {
                    indexOfSubsection = 1;
                    generalMap.get(itemOfKOA).put("1. ", new ArrayList<>());
                }

                try {
                    while (!itemOfSubsection.equals(null)) {
                       if (itemOfSubsection.substring(0,1).equals(String.valueOf(indexOfSubsection))) {
                            generalMap.get(itemOfKOA).put(itemOfSubsection, new ArrayList<>());
                        }
                        itemOfSubsection = tempWB.getSheetAt(0).getRow(m).getCell(2).toString();
                        m++;
                    }
                } catch (NullPointerException s) {
                    System.out.println(s);
                }
            }
            l++;
        }

        for (int i = 2; i <= quantity - 1; i++) {
            HSSFSheet sheet = tempWB.getSheetAt(i);
            // k - строки
            int k = 3;
            while (k <= sheet.getLastRowNum()) {
                // j - столбцы
                int j = 8;
                while (sheet.getRow(k).getCell(j) != null) {
                    String dateInTab = sheet.getRow(k).getCell(j).getStringCellValue();
                    Row row = sheet.getRow(k);
                    if (dateInTab.equals("everyDay")) {
                        generalMap.get(row.getCell(4).toString()).get(row.getCell(5).toString()).add(row.getCell(3).toString()
                                + "; Исполнитель: " + row.getCell(6).toString() + " Соисполнитель: " + row.getCell(7).toString()
                                + " Срок исполнения: ежедневно.");
                    }
                    j++;
                }
                k++;



//                {

//                    else {
//                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//                        LocalDate localDate = LocalDate.parse(dateInTab, dtf);
//
//                        if (localDate.getMonthValue() == month) {
//                            String point = sheet.getRow(k).getCell(0).getStringCellValue() + "\n" + sheet.getRow(k).getCell(3).getStringCellValue() +
//                                    "\n" + sheet.getRow(k).getCell(4).getStringCellValue() + "\n" + dateInTab;
//                            listOfPoints.add(point);
//                        }
//                    }
//                    j++;

//                }

            }
        }
        return generalMap;
    }

    private record contains() {
    }
}
