package com.savita.machi_koro.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;

public class MK extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(MK.class.getResource("/com/savita/machi_koro/auth.fxml"));
        Scene scene = new Scene(loader.load(), 550, 150);
        stage.setTitle("MK");
        stage.setScene(scene);
        stage.getIcons().add(new Image("file:" + Paths.get("images", "icon.png")));
        stage.setResizable(false);

        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
