package com.example.planning.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.example.planning.ActionWithWindow;
import com.example.planning.DBHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private Label currentDocNameLabel;

    @FXML
    private Label dueDateLabel;

    @FXML
    private DatePicker dueDatePicker;

    @FXML
    private Label itemAssistantLabel;

    @FXML
    private ComboBox<String> itemAssistantsCombobox;

    @FXML
    private Label itemExecutorLabel;

    @FXML
    private ComboBox<String> itemExecutorsCombobox;

    @FXML
    private TextField itemTextCurrentDocField;

    @FXML
    private Label itemTextCurrentDocLabel;

    @FXML
    private Label kindOfActItemLabel;

    @FXML
    private ComboBox<String> kindOfActItemsCombobox;

    @FXML
    private ComboBox<String> mainPersonsCombobox;

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
    private CheckBox mainDivCheckBox;

    @FXML
    private CheckBox firstDivCheckBox;

    @FXML
    private CheckBox secondDivCheckBox;

    @FXML
    private CheckBox thirdDivCheckBox;

    @FXML
    private Label s;

    @FXML
    private CheckBox savePatternCheckBox;
    static String nameOfDoc = "null";
    @FXML
    void initialize() throws IOException {
        nameOfDocumentTextField.setText(nameOfDoc);
        DBHandler dbh = new DBHandler();

        HashMap<String, ArrayList<String>> mainMap = dbh.getGuideInfo();
        ArrayList<String> kindOfActArray = mainMap.get("kindOfActList");
//        kindOfActItemsCombobox.setPromptText(kindOfActArray.get(1));
        for (String a : kindOfActArray) kindOfActItemsCombobox.getItems().add(a);
        // Проставляем на первоначальном комбобоксе фамилии отетственных
        for (String c : mainMap.get("ТО")) mainPersonsCombobox.getItems().add(c);
//        mainPersonsCombobox.setPromptText(mainMap.get("ТО").get(0));

        //Проставлеяем исполнителей
        for(String z : mainMap.get("forTakeAnExecutorList")) itemExecutorsCombobox.getItems().add(z);
//        itemExecutorsCombobox.setPromptText(mainMap.get("forTakeAnExecutorList").get(0));

        //Проставляем соисполнителей
        for(String z : mainMap.get("forTakeAnCoExecutorList")) itemAssistantsCombobox.getItems().add(z);
//        itemAssistantsCombobox.setPromptText(mainMap.get("forTakeAnCoExecutorList").get(0));

        kindOfActItemsCombobox.setOnAction(actionEvent -> {
            try {
                HashMap<String, ArrayList<String>> sMap = dbh.getGuideInfo();
                String nameOfKind = kindOfActItemsCombobox.getValue();
                ArrayList<String> mainPersonsList = sMap.get(nameOfKind);
                mainPersonsCombobox.getItems().clear();
                for (String b : mainPersonsList) {
                    mainPersonsCombobox.getItems().add(b);
                }
//                mainPersonsCombobox.setPromptText(mainPersonsList.get(0));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        });
        addItemToDocBtn.setOnAction(actionEvent -> {
                dbh.addItemsInDoc(nameOfDoc, numberDocumentField.getText(), nameOfDocumentTextField.getText(),
                        numberOfDocumentTextField.getText(), itemTextCurrentDocField.getText(), kindOfActItemsCombobox.getValue(),
                        mainPersonsCombobox.getValue(), itemExecutorsCombobox.getValue(), itemAssistantsCombobox.getValue());


//            dbh.addItemsInDoc(nameOfDoc, numberDocumentField.getText(), itemTextCurrentDocField.getText(),
//                        kindOfActItemsCombobox.getValue(), itemExecutorsCombobox.getValue(), itemAssistantsCombobox.getValue(),
//                        mainDivCheckBox.isSelected(), firstDivCheckBox.isSelected(), secondDivCheckBox.isSelected(), thirdDivCheckBox.isSelected());

        });

        okBtnItemInDocWindow.setOnAction(actionEvent -> {
            Stage addDoc = (Stage) okBtnItemInDocWindow.getScene().getWindow();
            addDoc.close();

//            ActionWithWindow rwin = new ActionWithWindow();
//            rwin.toRefresh("/com/example/planning/documentsWindow.fxml", "Documents");
        });

    }


}
