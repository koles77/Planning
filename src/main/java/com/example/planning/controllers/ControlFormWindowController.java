package com.example.planning.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.planning.ActionWithWindow;
import com.example.planning.DBHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ControlFormWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane docField;

    @FXML
    private ScrollPane labelsScrollPane;

    @FXML
    private VBox labelsVBox;

    @FXML
    void initialize() throws IOException {

        DBHandler dbh = new DBHandler();
        for (String point : dbh.takeActivityFromDB()) {
            Label pointLabel = new Label(point);
            pointLabel.setPrefWidth(600);
            pointLabel.setMinHeight(130);
            pointLabel.setWrapText(true);
            labelsVBox.getChildren().add(pointLabel);

        }

    }

}
