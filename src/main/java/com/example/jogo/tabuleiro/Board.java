package com.example.jogo.tabuleiro;

import com.example.jogo.jogadores.Player;

import java.util.List;

public class Board {

    private final List<Player> jogadores;
    private int turno;

    public Board(List<Player> jogadores) {
        this.jogadores = jogadores;
        this.turno = 0;
    }

    public Player getJogadorAtual() {
        return jogadores.get(turno % jogadores.size());
    }

    public int rolarDados(Player jogador) {
        int resultado = jogador.rollDice();
        int novaPosicao = Math.min(jogador.getPosition() + resultado, 40); // Limita a posição máxima na casa 40
        jogador.setPosition(novaPosicao);
        return novaPosicao;
    }

    public boolean verificarVitoria(Player jogador) {
        return jogador.getPosition() == 40; // Verifica se o jogador chegou na casa final
    }

    public void proximoTurno() {
        turno++;
    }

    public List<Player> getJogadores() {
        return jogadores;
    }
}
