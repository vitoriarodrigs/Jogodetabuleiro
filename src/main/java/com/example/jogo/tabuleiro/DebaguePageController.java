package com.example.jogo.tabuleiro;


import com.example.jogo.jogadores.Player;
import com.example.jogo.jogadores.UnluckyPlayer;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DebaguePageController {
    @FXML
    private TextField casasselecionadas;
    @FXML
    private Label jogadoratual;
    @FXML
    private StackPane boot;

    @FXML
    private Pane Casas;

    @FXML
    private Circle player1Circle, player2Circle, player3Circle, player4Circle, player5Circle, player6Circle;

    @FXML
    private Button rollDiceButton;

    @FXML
    private Label messageLabel;

    private Map<Integer, Circle> casaCirculos = new HashMap<>();
    private Map<Player, Circle> pecasJogadores = new HashMap<>();
    private List<Player> jogadores;
    private int currentPlayerIndex = 0;

    @FXML
    public void initialize() {
       // jogadoratual.setText(jogadores.get(currentPlayerIndex).getName());
        for (int i = 0; i < Casas.getChildren().size(); i++) {
            if (Casas.getChildren().get(i) instanceof Circle) {
                Circle casa = (Circle) Casas.getChildren().get(i);
                casa.setVisible(false); // Casas ficam invisíveis o tempo todo
                String id = casa.getId();
                if (id != null) {
                    try {
                        int numeroCasa = Integer.parseInt(id);
                        casaCirculos.put(numeroCasa, casa);
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        }
    }


    public void inicializarJogadores(List<Player> jogadores) {
        this.jogadores = jogadores;
        Circle[] circulos = {player1Circle, player2Circle, player3Circle, player4Circle, player5Circle, player6Circle};

        for (int i = 0; i < circulos.length; i++) {
            if (i < jogadores.size()) {
                Player jogador = jogadores.get(i);
                Circle peca = circulos[i];
                pecasJogadores.put(jogador, peca);
                peca.setVisible(true);  // Torna a peça visível
                peca.setFill(jogador.getColor());
                atualizarPosicaoJogador(jogador, 0);
            } else {
                circulos[i].setVisible(false);  // Torna a peça invisível se o jogador não estiver na partida
            }
        }
    }

    @FXML
    public void rolarDados() throws IOException{
        // Passa para o próximo jogador que não foi marcado para pular a vez
        while (jogadores.get(currentPlayerIndex).isSkippingNextTurn()) {
            exibirMensagem(jogadores.get(currentPlayerIndex).getName() + " está pulando a rodada.");
            jogadores.get(currentPlayerIndex).setSkippingNextTurn(false);  // Resetando a flag de pulo de turno
            currentPlayerIndex = (currentPlayerIndex + 1) % jogadores.size();
        }

        if (casasselecionadas.getText().isEmpty()){
            return;
        }

        Player jogadorAtual = jogadores.get(currentPlayerIndex);
        int resultadoDados = Integer.parseInt(casasselecionadas.getText());
        int novaPosicao = jogadorAtual.getPosition() + resultadoDados;

        if (novaPosicao > 40) {
            novaPosicao = 40;
        }

        jogadorAtual.setPosition(resultadoDados);
        atualizarPosicaoJogador(jogadorAtual, resultadoDados);

        // Verifica as casas especiais após a rolagem
        verificarCasasEspeciais(jogadorAtual, resultadoDados,novaPosicao);

        if (novaPosicao == 40) {
            exibirMensagem(jogadorAtual.getName() + " venceu o jogo!");
            rollDiceButton.setDisable(true);
            // Cria uma pausa para mostrar o movimento antes de trocar de cena


            PauseTransition pausa = new PauseTransition(Duration.seconds(1));  // 1 segundo de pausa
            pausa.setOnFinished(event -> transferirVitoria(jogadorAtual));  // Chama o método de transferência após a pausa
            pausa.play();  // Executa a pausa

        }
        jogadores.get(currentPlayerIndex).setTurns(jogadores.get(currentPlayerIndex).getTurns()+1);
        jogadoratual.setText( "Vez do \n"+jogadorAtual.getName() ) ;
        currentPlayerIndex = (currentPlayerIndex + 1) % jogadores.size();
    }

    @FXML
    public void transferirVitoria(Player jogadorAtual){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/victoria.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) rollDiceButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
           VitoriaController vitoria = loader.getController();
            vitoria.preencherLista(jogadores);
            vitoria.preencherVencedor( jogadorAtual.getName(), jogadorAtual.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void atualizarPosicaoJogador(Player jogador, int novaPosicao) {
        Circle peca = pecasJogadores.get(jogador);
        Circle casa = casaCirculos.get(novaPosicao);

        if (peca != null && casa != null) {
            peca.setLayoutX(casa.getLayoutX());
            peca.setLayoutY(casa.getLayoutY());
        }
    }

    public void exibirMensagem(String mensagem) {
        messageLabel.setText(mensagem);
    }

    public void verificarCasasEspeciais(Player jogador, int resultadoDados, int novaPosicao ) {
        int posicao = jogador.getPosition();

        switch (posicao) {
            case 5:
            case 15:
            case 30:
                if (!(jogador instanceof UnluckyPlayer)) {
                    jogador.setPosition(jogador.getPosition() + 3); // Avança 3 casas, exceto se for um jogador azarado
                    atualizarPosicaoJogador(jogador, jogador.getPosition());
                    exibirMensagem(jogador.getName() + " avançou 3 casas \n na Casa da Sorte!");
                }
                break;

            case 10:
            case 25:
            case 38:
                jogador.setSkippingNextTurn(true);  // pula a próxima rodada
                exibirMensagem(jogador.getName() + " caiu em uma casa especial e \n perderá a próxima rodada.");
                currentPlayerIndex = (currentPlayerIndex + 1) % jogadores.size();  // Pula a vez do jogador

                break;

            case 13:
                exibirMensagem(jogador.getName() + " caiu na Casa Surpresa!");
                mudarTipoJogador(jogador);
                break;

            case 17:
            case 27:
                exibirMensagem(jogador.getName() + " escolheu outro jogador para \n voltar ao início.");
                enviarOutroJogadorAoInicio(jogador);
                break;

            case 20:
            case 35:
                exibirMensagem(jogador.getName() + " trocou de lugar com o\n jogador mais atrás.");
                trocarLugarComJogadorMaisAtras(jogador);
                break;
            default:exibirMensagem( " rolou " + resultadoDados + " e foi para a casa " + novaPosicao);
        }

    }

    private void mudarTipoJogador(Player jogador) {
        Random random = new Random();
        int tipoAleatorio = random.nextInt(3);
        switch (tipoAleatorio) {
            case 0:
                jogador.changeType("Lucky");
                exibirMensagem(jogador.getName() + " agora é um Jogador Normal!");
                break;
            case 1:
                jogador.changeType("Unlucky");
                exibirMensagem(jogador.getName() + " agora é um Jogador Com Sorte!");
                break;
            case 2:
                jogador.changeType("Normal");
                exibirMensagem(jogador.getName() + " agora é um Jogador Azarado!");
                break;
        }
    }

    private void enviarOutroJogadorAoInicio(Player jogadorAtual) {
        Player escolhido = obterJogadorMaisAtras(jogadorAtual);
        if (escolhido != null) {
            escolhido.setPosition(0);
            atualizarPosicaoJogador(escolhido, 0);
            exibirMensagem(escolhido.getName() + " foi enviado ao início por " + jogadorAtual.getName() + ".");
        }
    }

    private void trocarLugarComJogadorMaisAtras(Player jogadorAtual) {
        Player jogadorMaisAtras = obterJogadorMaisAtras(jogadorAtual);
        if (jogadorMaisAtras != null) {
            int tempPosicao = jogadorAtual.getPosition();
            jogadorAtual.setPosition(jogadorMaisAtras.getPosition());
            jogadorMaisAtras.setPosition(tempPosicao);

            atualizarPosicaoJogador(jogadorAtual, jogadorAtual.getPosition());
            atualizarPosicaoJogador(jogadorMaisAtras, jogadorMaisAtras.getPosition());
        }
    }

    private Player obterJogadorMaisAtras(Player jogadorAtual) {
        return jogadores.stream()
                .filter(j -> j != jogadorAtual)
                .min((j1, j2) -> Integer.compare(j1.getPosition(), j2.getPosition()))
                .orElse(null);
    }

}
