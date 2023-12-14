package com.example.planning;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ActionWithWindow {

    public void toRefresh (String path, String title) {
        Stage createDocStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((getClass().getResource(path)));
        try {
            Scene docsScene = new Scene(loader.load(), 500, 500);
            createDocStage.setTitle(title);
            createDocStage.setScene(docsScene);
            createDocStage.show();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

}
