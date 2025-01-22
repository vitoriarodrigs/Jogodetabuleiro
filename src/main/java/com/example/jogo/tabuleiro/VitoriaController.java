package com.example.jogo.tabuleiro;

import com.example.jogo.jogadores.Player;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;


public class VitoriaController {
private Stage stage;
private Scene scene;
private Parent root;

    @FXML
    private Button jogarnovamente;

    @FXML
    private ListView<String> lista;

    @FXML
    private Label vencedornome;

    @FXML
    private Label vencedortipo;
    @FXML
    private ImageView imageView; // O mesmo ID definido no FXML

    @FXML
    public void jogarNovamente(ActionEvent event)  throws IOException {
        root = FXMLLoader.load(getClass().getResource("/hello-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public  void preencherVencedor(String nome, String type){
        vencedornome.setText(nome);
        vencedortipo.setText(type);
    }
    public void preencherLista(List<Player> jogadores){
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Player p: jogadores){
            items.add(p.getName()+"  |   "+p.getType()+ "  |   Jogadas: "+p.getTurns()+"  |   Casa: "+ p.getPosition());
        }
        lista.setItems(items);
    }

}


