package com.example.planning.controllers;

import java.io.*;
import java.net.URL;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;

import com.example.planning.DBHandler;
import com.example.planning.ActionWithWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.poi.EmptyFileException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbookFactory;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CreateDocumentController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button cancelBtnCreateDocWindow;

    @FXML
    private Label docNameLabel;

    @FXML
    private TextField nameDocumentField;

    @FXML
    private Label numberDocLabel;

    @FXML
    private TextField numberDocumentField;

    @FXML
    private Button okBtnCreateDocWindow;


    @FXML
    void initialize() throws IOException {
        DBHandler handler = new DBHandler();
        okBtnCreateDocWindow.setOnAction(actionEvent -> {
            try {
                handler.addDocToMainDB(nameDocumentField.getText(), numberDocumentField.getText());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Stage addDoc = (Stage) okBtnCreateDocWindow.getScene().getWindow();
            addDoc.close();

            ActionWithWindow rwin = new ActionWithWindow();
            rwin.toRefresh("/com/example/planning/documentsWindow.fxml", "Documents");
        });


    }

}
