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

//    @FXML
//    private TextField numberDocumentField;

    @FXML
    private Label numberItemCurrentDocLabel;

    @FXML
    private TextField numberOfDocumentTextField;

    @FXML
    private Button okBtnItemInDocWindow;

    @FXML
    private Button showTheDocButton;

    @FXML
    private Button CleanDateBtnItemInDoc;

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
    private Label DateOneLabel;

    @FXML
    private Label DateTwoLabel;

    @FXML
    private Label DateThreeLabel;

    @FXML
    private Label DateFourLabel;

    @FXML
    private Label DateFiveLabel;

    @FXML
    private Label DateSixLabel;

    @FXML
    private Label DateSevenLabel;

    @FXML
    private Label DateEightLabel;

    @FXML
    private Label DateNineLabel;

    @FXML
    private Label DateTenLabel;

    @FXML
    private Label subsectionComboBoxLabel;

    @FXML
    private ComboBox<String> subsectionComboBox;

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
        // Проставляем подразделы основных направлений
        ObservableList<String> subsectionKindOfActArray = FXCollections.observableArrayList(mainMap.get("subsectionKindOfActList"));
        subsectionComboBox.setItems(subsectionKindOfActArray);




        //Проставлеяем executors
        ObservableList<String> executorsArray = FXCollections.observableArrayList(mainMap.get("forTakeAnExecutorList"));
        executorsListView.setItems(executorsArray);

        //Проставляем coexecutors
        ObservableList<String> coexecutorsArray = FXCollections.observableArrayList(mainMap.get("forTakeAnCoExecutorList"));
        coexecutorsListView.setItems(coexecutorsArray);

        addItemToDocBtn.setOnAction(actionEvent -> {

            for (String s : listOfExec) {
                execLine += s + "; ";
            }
            for (String s : listOfCoexec) {
                coexecLine += s + "; ";
            }
            if (DateTwoLabel.getText().equals("Date 2")) {
                LocalDate date = dueDatePicker.getValue();
                dateArg = dbh.toGetDateArrList(date, dailyChekBox, weeklyChekBox, monthlyCheckBox, quarterlyCheckBox);
            }

            dbh.addItemsInDoc(nameOfDoc, nameOfDocumentTextField.getText(),
                    numberOfDocumentTextField.getText(), itemTextCurrentDocField.getText(),
                    typeOfActivityListView.getSelectionModel().getSelectedItems().get(0), subsectionComboBox.getValue(),
                    execLine, coexecLine, dateArg);

            DateOneLabel.setText("Date 1");
            DateTwoLabel.setText("Date 2");

            execLine = "";
            coexecLine = "";

            // сделать полный перезапуск формы
            Stage currentStage = (Stage) addItemToDocBtn.getScene().getWindow();
            currentStage.close();
            ActionWithWindow act = new ActionWithWindow();
            act.toRefresh("/com/example/planning/fillingOutOfDocumentFormWindow.fxml", nameOfDocumentTextField.getText());
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
            if (!DateOneLabel.getText().equals("Date 1")) {
                dailyChekBox.setDisable(true);
                weeklyChekBox.setDisable(true);
                monthlyCheckBox.setDisable(true);
                quarterlyCheckBox.setDisable(true);
            }
            dateArg.add(dueDatePicker.getValue().toString());
            if (DateOneLabel.getText().equals("Date 1")) DateOneLabel.setText(dueDatePicker.getValue().toString());
            else if (DateTwoLabel.getText().equals("Date 2")) DateTwoLabel.setText(dueDatePicker.getValue().toString());
            else if (DateThreeLabel.getText().equals("Date 3")) DateThreeLabel.setText(dueDatePicker.getValue().toString());
            else if (DateFourLabel.getText().equals("Date 4")) DateFourLabel.setText(dueDatePicker.getValue().toString());
            else if (DateFiveLabel.getText().equals("Date 5")) DateFiveLabel.setText(dueDatePicker.getValue().toString());
            else if (DateSixLabel.getText().equals("Date 6")) DateSixLabel.setText(dueDatePicker.getValue().toString());
            else if (DateSevenLabel.getText().equals("Date 7")) DateSevenLabel.setText(dueDatePicker.getValue().toString());
            else if (DateEightLabel.getText().equals("Date 8")) DateEightLabel.setText(dueDatePicker.getValue().toString());
            else if (DateNineLabel.getText().equals("Date 9")) DateNineLabel.setText(dueDatePicker.getValue().toString());
            else DateTenLabel.setText(dueDatePicker.getValue().toString());
        });
//      Кнопка очистки массива дат
        CleanDateBtnItemInDoc.setOnAction(actionEvent -> {
            dateArg.clear();
            DateOneLabel.setText("Date 1");
            DateTwoLabel.setText("Date 2");
            DateThreeLabel.setText("Date 3");
            DateFourLabel.setText("Date 4");
            DateFiveLabel.setText("Date 5");
            DateSixLabel.setText("Date 6");
            DateSevenLabel.setText("Date 7");
            DateEightLabel.setText("Date 8");
            DateNineLabel.setText("Date 9");
            DateTenLabel.setText("Date 10");
        });

        okBtnItemInDocWindow.setOnAction(actionOkEvent -> {
            Stage addDoc = (Stage) okBtnItemInDocWindow.getScene().getWindow();
            addDoc.close();

            ActionWithWindow rwin = new ActionWithWindow();
            rwin.toShow("/com/example/planning/documentsWindow.fxml", "Documents", 500, 500);
        });

        dbh.toSetItemInListView(executorsListView, listOfExec, selectedExecutorsLabel);
        dbh.toSetItemInListView(coexecutorsListView, listOfCoexec, selectedCoExecutorsLabel);
    }
}
