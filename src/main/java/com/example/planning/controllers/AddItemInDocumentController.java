package com.example.planning.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.planning.ActionWithWindow;
import com.example.planning.DBHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddItemInDocumentController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label ItemExecutorLabel;

    @FXML
    private ComboBox<?> ItemExecutorsCombobox;

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
    private ComboBox<?> itemAssistantsCombobox;

    @FXML
    private TextField itemTextCurrentDocField;

    @FXML
    private Label itemTextCurrentDocLabel;

    @FXML
    private Label kindOfActItemLabel;

    @FXML
    private ComboBox<String> kindOfActItemsCombobox;

    @FXML
    private ComboBox<?> mainPersonsCombobox;

    @FXML
    private Label numberCurrentDocLabel;

    @FXML
    private TextField numberDocumentField;

    @FXML
    private Label numberItemCurrentDocLabel;

    @FXML
    private Button okBtnCreateDocWindow;

    @FXML
    private Label s;

    @FXML
    private CheckBox savePatternCheckBox;
    @FXML
    void initialize() throws IOException {
        DBHandler dbh = new DBHandler();

        ArrayList<String> kindOfActArray = dbh.getGuideInfo();
        for (String a : kindOfActArray) {
            kindOfActItemsCombobox.getItems().add(a);
        }


    }

}
