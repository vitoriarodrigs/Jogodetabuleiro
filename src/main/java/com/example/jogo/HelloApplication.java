package com.example.jogo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Carrega o arquivo FXML da tela inicial
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        StackPane root = fxmlLoader.load();


        Image tela = new Image(getClass().getResource("/tela.png").toExternalForm());
        ImageView imageView = new ImageView(tela);
        imageView.setPreserveRatio(false);
        imageView.setSmooth(true);


        imageView.fitWidthProperty().bind(root.widthProperty());
        imageView.fitHeightProperty().bind(root.heightProperty());
        root.getChildren().add(imageView);


        Scene cena = new Scene(root, 800, 600);
        stage.setTitle("Tela Inicial");
        stage.setScene(cena);  // Atualiza a cena
        stage.show();

        //   imagem  clicada, navega para a segunda página
        imageView.setOnMouseClicked(event -> {
            try {

                FXMLLoader secondPageLoader = new FXMLLoader(getClass().getResource("second_page.fxml"));
                StackPane secondPageRoot = secondPageLoader.load(); // Carrega a segunda tela


                Scene secondPageScene = new Scene(secondPageRoot, 800, 600);
                stage.setScene(secondPageScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
