package com.example.planning.controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.planning.DBHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DocumentsWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button createBtnDocWindow;

    @FXML
    private Button deleteBtnDocWindow;

    @FXML
    private Button updateBtnDocWindow;

    @FXML
    void initialize()  {
        try {
            DBHandler dbHandler = new DBHandler();
            //Добавляем из хэндлера массив с наименованиями листов для создания кнопок
            ArrayList<String> listOfBtn = dbHandler.listOfPagesName;

        } catch (IOException e) {
            System.out.println("File is not ready");
        }

        createBtnDocWindow.setOnMouseClicked(mouseEvent -> {

           Stage createDocStage = new Stage();
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation((getClass().getResource("/com/example/planning/createDocumentWindow.fxml")));
           try {
               Scene docsScene = new Scene(loader.load(), 500, 500);
               createDocStage.setTitle("Create document");
               createDocStage.setScene(docsScene);
               createDocStage.show();
           } catch (IOException e) {
               e.printStackTrace();
           }
       });

    }

}
