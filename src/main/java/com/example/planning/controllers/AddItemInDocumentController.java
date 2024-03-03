package com.example.planning.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
import java.util.*;


import com.example.planning.ActionWithWindow;
import com.example.planning.DBHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class AddItemInDocumentController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addItemToDocBtn;

    @FXML
    private Button cancelBtnCreateDocWindow;

    @FXML
    private ListView<String> coexecutorsListView;

    @FXML
    private Label currentDocNameLabel;

    @FXML
    private Label dueDateLabel;

    @FXML
    private DatePicker dueDatePicker;

    @FXML
    protected ListView<String> executorsListView;

    @FXML
    private Label itemAssistantLabel;

    @FXML
    private Label itemExecutorLabel;

    @FXML
    private TextArea itemTextCurrentDocField;

    @FXML
    private Label itemTextCurrentDocLabel;

    @FXML
    private Label kindOfActItemLabel;

    @FXML
    private CheckBox dailyChekBox;

    @FXML
    private CheckBox monthlyCheckBox;

    @FXML
    private TextField nameOfDocumentTextField;

    @FXML
    private Label numberCurrentDocLabel;

    @FXML
    private TextField numberDocumentField;

    @FXML
    private Label numberItemCurrentDocLabel;

    @FXML
    private TextField numberOfDocumentTextField;

    @FXML
    private Button okBtnItemInDocWindow;

    @FXML
    private CheckBox quarterlyCheckBox;

    @FXML
    private ListView<String> typeOfActivityListView;

    @FXML
    private CheckBox weeklyChekBox;

    @FXML
    private Label selectedCoExecutorsLabel;

    @FXML
    private Label selectedExecutorsLabel;

    @FXML
    private Button showTheDocButton;

    @FXML
    private Button CleanDateBtnItemInDoc;

    @FXML
    private Label DateOneLabel;

    @FXML
    private Label DateTwoLabel;
    static String nameOfDoc = "null";
    static String execLine = "";
    static String coexecLine = "";

    Set<String> listOfExec = new LinkedHashSet<>();
    Set<String> listOfCoexec = new LinkedHashSet<>();
    ArrayList<String> dateArg = new ArrayList<>();

    @FXML
    void initialize() throws IOException {
        nameOfDocumentTextField.setText(nameOfDoc);
        DBHandler dbh = new DBHandler();
        HashMap<String, ArrayList<String>> mainMap = dbh.getGuideInfo();
        itemTextCurrentDocField.setWrapText(true);
        numberOfDocumentTextField.setText(dbh.getNumberOfTheDoc(nameOfDoc));
        // Проставляем виды направлений деятельности
        ObservableList<String> kindOfActArray = FXCollections.observableArrayList(mainMap.get("kindOfActList"));
        typeOfActivityListView.setItems(kindOfActArray);

        //Проставлеяем executors
        ObservableList<String> executorsArray = FXCollections.observableArrayList(mainMap.get("forTakeAnExecutorList"));
        executorsListView.setItems(executorsArray);

        //Проставляем coexecutors
        ObservableList<String> coexecutorsArray = FXCollections.observableArrayList(mainMap.get("forTakeAnCoExecutorList"));
        coexecutorsListView.setItems(coexecutorsArray);

        addItemToDocBtn.setOnAction(actionEvent -> {

            for (String s : listOfExec) {
                execLine += s + "; ";
                System.out.println(execLine);
            }
            for (String s : listOfCoexec) {
                coexecLine += s + "; ";
                System.out.println(coexecLine);
            }

            LocalDate date = dueDatePicker.getValue();
            dateArg = dbh.toGetDateArrList(date, dailyChekBox, weeklyChekBox, monthlyCheckBox, quarterlyCheckBox);

//            if (dateArg.size() != 2) {
//                dateArg = dbh.toGetDateArrList(date, dailyChekBox, weeklyChekBox, monthlyCheckBox, quarterlyCheckBox);
//                System.out.println("dateArg != 2");
//            } else {
//                ArrayList<String> targetArr = new ArrayList<>();
//                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//                for (String s : dateArg) targetArr.add(LocalDate.parse(s).format(dtf));
//            }

            dbh.addItemsInDoc(nameOfDoc, numberDocumentField.getText(), nameOfDocumentTextField.getText(),
                    numberOfDocumentTextField.getText(), itemTextCurrentDocField.getText(),
                    typeOfActivityListView.getSelectionModel().getSelectedItems().get(0),
                    execLine, coexecLine, dateArg);

            listOfExec = new LinkedHashSet<>();
            listOfCoexec = new LinkedHashSet<>();
            DateOneLabel.setText("Date 1");
            DateTwoLabel.setText("Date 2");
        });

        showTheDocButton.setOnAction(actionEvent -> {
            Runtime rt = Runtime.getRuntime();
            File file = new File("C:\\Program Files (x86)\\Microsoft Office\\Office12\\EXCEL.exe");
            File file1 = new File("C:\\Program Files\\Microsoft Office\\Office14\\EXCEL.exe");
            if (file.exists()) {
                try {
                    Process p = rt.exec("C:\\Program Files (x86)\\Microsoft Office\\Office12\\EXCEL.exe documents.xls");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (file1.exists()) {
                try {
                    Process p = rt.exec("C:\\Program Files\\Microsoft Office\\Office14\\EXCEL.exe documents.xls");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
//      Добавление двух дат
        dueDatePicker.setOnAction(actionEvent -> {
            dateArg.add(dueDatePicker.getValue().toString());
            if (DateOneLabel.getText().equals("Date 1")) DateOneLabel.setText(dueDatePicker.getValue().toString());
            else DateTwoLabel.setText(dueDatePicker.getValue().toString());
        });
//      Кнопка очистки массива дат
        CleanDateBtnItemInDoc.setOnAction(actionEvent -> {
            dateArg.clear();
            System.out.println(dateArg.size());
            DateOneLabel.setText("Date 1");
            DateTwoLabel.setText("Date 2");
        });

        okBtnItemInDocWindow.setOnAction(actionOkEvent -> {
            Stage addDoc = (Stage) okBtnItemInDocWindow.getScene().getWindow();
            addDoc.close();

            ActionWithWindow rwin = new ActionWithWindow();
            rwin.toRefresh("/com/example/planning/documentsWindow.fxml", "Documents");
        });
        dbh.toSetItemInListView(executorsListView, listOfExec, selectedExecutorsLabel);
        dbh.toSetItemInListView(coexecutorsListView, listOfCoexec, selectedCoExecutorsLabel);
    }
}
