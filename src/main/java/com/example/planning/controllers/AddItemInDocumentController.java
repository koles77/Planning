package com.example.planning.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.*;


import com.example.planning.DBHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import static org.apache.xmlbeans.XmlBeans.getTitle;

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
    private TextField itemTextCurrentDocField;

    @FXML
    private Label itemTextCurrentDocLabel;

    @FXML
    private Label kindOfActItemLabel;

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
    private ListView<String> responsibleListView;

    @FXML
    private Label s;

    @FXML
    private ListView<String> typeOfActivityListView;

    @FXML
    private CheckBox weeklyChekBox;

    @FXML
    private Button showTheDocButton;
    static String nameOfDoc = "null";
    static String execLine = "";
    static String coexecLine = "";
    static String responsibleLine = "";

    Set<String> listOfResponsible = new LinkedHashSet<>();
    Set<String> listOfExec = new LinkedHashSet<>();
    Set<String> listOfCoexec = new LinkedHashSet<>();

    @FXML
    void initialize() throws IOException {
        nameOfDocumentTextField.setText(nameOfDoc);
        DBHandler dbh = new DBHandler();
        HashMap<String, ArrayList<String>> mainMap = dbh.getGuideInfo();

        // Проставляем виды направлений деятельности
        ObservableList<String> kindOfActArray = FXCollections.observableArrayList(mainMap.get("kindOfActList"));
        typeOfActivityListView.setItems(kindOfActArray);

        // Проставляем на  responsibleListView фамилии отетственных
        ObservableList<String> responsiblePersonsArray = FXCollections.observableArrayList(mainMap.get("forTakeAChoiceList"));
        responsibleListView.setItems(responsiblePersonsArray);

        //Проставлеяем executors
        ObservableList<String> executorsArray = FXCollections.observableArrayList(mainMap.get("forTakeAnExecutorList"));
        executorsListView.setItems(executorsArray);

        //Проставляем coexecutors
        ObservableList<String> coexecutorsArray = FXCollections.observableArrayList(mainMap.get("forTakeAnCoExecutorList"));
        coexecutorsListView.setItems(coexecutorsArray);


        addItemToDocBtn.setOnAction(actionEvent -> {
            for (String s : listOfResponsible) {
                responsibleLine += s + "; ";
                System.out.println(responsibleLine);
            }
            for (String s : listOfExec) {
                execLine += s + "; ";
                System.out.println(execLine);
            }
            for (String s : listOfCoexec) {
                coexecLine += s + "; ";
                System.out.println(coexecLine);
            }

            dbh.addItemsInDoc(nameOfDoc, numberDocumentField.getText(), nameOfDocumentTextField.getText(),
                    numberOfDocumentTextField.getText(), itemTextCurrentDocField.getText(),
                    typeOfActivityListView.getSelectionModel().getSelectedItems().get(0),
                    responsibleLine, execLine, coexecLine);
        });

        showTheDocButton.setOnAction(actionEvent -> {
            Runtime rt = Runtime.getRuntime();
            try {
                Process p = rt.exec("C:\\Program Files (x86)\\Microsoft Office\\Office12\\EXCEL.exe documents.xls");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        okBtnItemInDocWindow.setOnAction(actionOkEvent -> {

            Stage addDoc = (Stage) okBtnItemInDocWindow.getScene().getWindow();
            addDoc.close();

//            ActionWithWindow rwin = new ActionWithWindow();
//            rwin.toRefresh("/com/example/planning/documentsWindow.fxml", "Documents");
        });

// Переделать по-человечски!!!! Без повторения кода
        executorsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        executorsListView.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                ObservableList<String> selectedItems = executorsListView.getSelectionModel().getSelectedItems();
                for (String s : selectedItems) {
                    listOfExec.add(s);
                    System.out.println(listOfExec);
                }
            }
        });

        coexecutorsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        coexecutorsListView.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                ObservableList<String> selectedItems = coexecutorsListView.getSelectionModel().getSelectedItems();
                for (String s : selectedItems) {
                    listOfCoexec.add(s);
                    System.out.println(listOfCoexec);
                }
            }
        });

        responsibleListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        responsibleListView.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                ObservableList<String> selectedItems = responsibleListView.getSelectionModel().getSelectedItems();
                for (String s : selectedItems) {
                    listOfResponsible.add(s);
                    System.out.println(listOfResponsible);
                }
            }
        });

    }
}
