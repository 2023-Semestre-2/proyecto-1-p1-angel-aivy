package com.proyecto1.gestordeprocesos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("process-manager.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Process Manager!");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
