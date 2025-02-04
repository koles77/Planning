package com.example.planning.controllers;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.example.planning.DBHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class GetReportController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button getReportMenuButton;

    @FXML
    private ComboBox<String> monthComboBox;

    @FXML
    void initialize() throws IOException {
        ObservableList<String> langs = FXCollections.observableArrayList(
                "01 - Январь", "02 - Февраль", "03 - март",
                "04 - Апрель", "05 - Май", "06 - Июнь",
                "07 - Июль", "08 - Август", "09 - Сентябрь",
                "10 - Октябрь", "11 - Ноябрь", "12 - Декабрь");
        monthComboBox.setItems(langs);
        monthComboBox.setValue("01 - Январь");

        DBHandler dbh = new DBHandler();

        getReportMenuButton.setOnAction(actionEvent -> {
            String month = monthComboBox.getValue().substring(0,2);
            try {
                dbh.takeActivityByMonthFromDB(Integer.parseInt(month));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            XWPFDocument document = new XWPFDocument();

            try {

                for (Map.Entry<String, HashMap<String,ArrayList<String>>> entry : dbh.takeActivityByMonthFromDB(Integer.parseInt(month)).entrySet()) {
                   String section = entry.getKey();
                   StringBuilder mainBlock = new StringBuilder("");

                    for (Map.Entry<String, ArrayList<String>> entry1 : dbh.takeActivityByMonthFromDB(Integer.parseInt(month)).get(section).entrySet()) {
                        mainBlock.append(entry1.getKey() + System.lineSeparator());
                        for (String point : entry1.getValue()) {
                            mainBlock.append(point + System.lineSeparator());
                        }
                    }

                    XWPFParagraph paragraph = document.createParagraph();
                    XWPFRun run = paragraph.createRun();
                    run.setText(mainBlock.toString());
                }

                FileOutputStream outputStream = new FileOutputStream(month + ".docx");
                document.write(outputStream);
                outputStream.close();

            } catch (Exception e) {
                System.out.println("WTF!");
            }

        });
    }

}
