package com.example.jogo;

import com.example.jogo.jogadores.LuckyPlayer;
import com.example.jogo.jogadores.NormalPlayer;
import com.example.jogo.jogadores.Player;
import com.example.jogo.jogadores.UnluckyPlayer;
import com.example.jogo.tabuleiro.BoardPageController;
import com.example.jogo.tabuleiro.DebaguePageController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SecondPageController {
private Player verificar = null;
private boolean diferente = false;

    @FXML
    private Button jogarButton;

    @FXML
    private Button debugButton;

    @FXML
    private Button backButton;

    @FXML
    private Button adicionarJogadorButton;

    @FXML
    private ComboBox<String> comboBoxJogadores;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private ImageView imageView;

    private final List<Player> jogadores = new ArrayList<>(); // Lista para armazenar jogadores
    private final Set<Color> coresUsadas = new HashSet<>(); // Conjunto para garantir cores únicas

    @FXML
    public void initialize() {
        comboBoxJogadores.getItems().addAll(
                "Jogador Normal",
                "Jogador Azarado",
                "Jogador com Sorte"
        );
        comboBoxJogadores.setPromptText("Selecione o tipo de jogador");

        comboBoxJogadores.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                adicionarJogadorButton.setDisable(false);
            }
        });

        adicionarJogadorButton.setDisable(true);
    }

    @FXML
    private void onAdicionarJogador() {
        if (jogadores.size() < 6) {
            String tipoJogador = comboBoxJogadores.getValue();
            Color corSelecionada = colorPicker.getValue();

            if (tipoJogador != null && corSelecionada != null) {
                if (coresUsadas.contains(corSelecionada)) {
                    System.out.println("Essa cor já foi escolhida por outro jogador. Escolha outra.");
                    return;
                }

                if (verificar!= null ){
                    System.out.println(" passou.");
                    if (verificar.getType() != criarPlayer(tipoJogador, corSelecionada).getType()){
                        jogarButton.setVisible(true);
                        jogarButton.setDisable(false);
                        debugButton.setVisible(true);
                        debugButton.setDisable(false);
                        System.out.println(" Deu certo.");
                    }
                }

                // Cria o jogador com a cor única e o nome correto
                jogadores.add(criarPlayer(tipoJogador, corSelecionada));
                verificar = criarPlayer(tipoJogador, corSelecionada);
                coresUsadas.add(corSelecionada);


                System.out.println("Jogador adicionado: " + tipoJogador);
                System.out.println("Jogadores atuais: " + jogadores);

                if (jogadores.size() >= 6) {
                    adicionarJogadorButton.setDisable(true);
                }
            } else {
                System.out.println("Selecione um tipo de jogador e uma cor antes de adicionar.");
            }
        }
    }

    private Player criarPlayer(String tipoJogador, Color cor) {
        // Cria o jogador com base no tipo e o nome é gerado conforme o tipo do jogador
        return switch (tipoJogador) {
            case "Jogador Normal" -> new NormalPlayer("Jogador Normal", cor);
            case "Jogador Azarado" -> new UnluckyPlayer("Jogador Azarado", cor);
            case "Jogador com Sorte" -> new LuckyPlayer("Jogador com Sorte", cor);
            default -> throw new IllegalArgumentException("Tipo de jogador desconhecido: " + tipoJogador);
        };
    }

    @FXML
    private void handleplayButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("board.fxml"));
            Parent root = loader.load();

            BoardPageController boardController = loader.getController();

            boardController.inicializarJogadores(jogadores); // Passa a lista de jogadores com cores únicas

            Stage stage = (Stage) jogarButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void debagButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("debague.fxml"));
            Parent root = loader.load();

            DebaguePageController boardController = loader.getController();

            boardController.inicializarJogadores(jogadores); // Passa a lista de jogadores com cores únicas

            Stage stage = (Stage) jogarButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleBackButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

