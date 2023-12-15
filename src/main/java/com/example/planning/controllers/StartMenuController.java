package com.example.planning.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.planning.ActionWithWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StartMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button documentsButton;

    @FXML
    private Button controlButton;

    @FXML
    private Button getReportButton;

    @FXML
    void initialize() {
        documentsButton.setOnAction(actionEvent -> {
            Stage mainWindow = (Stage) documentsButton.getScene().getWindow();
            mainWindow.close();

        // Запускается окно всех имеющихся документов
            ActionWithWindow action = new ActionWithWindow();
            action.toShow("/com/example/planning/documentsWindow.fxml", "Documents", 500, 500);

        });

    }

}
