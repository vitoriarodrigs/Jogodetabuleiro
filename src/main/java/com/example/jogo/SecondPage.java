package com.example.jogo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SecondPage {

    public Scene getScene(Stage stage) {
        try {
            // Carregar o arquivo FXML da segunda página (second-page.fxml)
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("second_page.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);  // Tamanho da cena
            return scene;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Caso haja erro no carregamento
        }
    }
}



