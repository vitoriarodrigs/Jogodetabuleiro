package com.example.jogo.jogadores;

import java.util.Random;

public class NormalPlayer extends Player {

    public NormalPlayer(String name, javafx.scene.paint.Color color) {
        super(name, color);
    }

    @Override
    public int rollDice() {
        Random random = new Random();
        int dado1 = random.nextInt(6) + 1; // Dado 1 (1 a 6)
        int dado2 = random.nextInt(6) + 1; // Dado 2 (1 a 6)

        return dado1 + dado2; // Soma normal (pode ser entre 2 e 12)
    }

    @Override
    public boolean isUnlucky() {
        return false; // Jogador normal não é azarado
    }

    @Override
    public void setPosition(int position) {
        // Lógica para o NormalPlayer: move normalmente
        super.position = position;
    }
}



