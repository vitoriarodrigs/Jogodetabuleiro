package com.example.jogo.jogadores;

import java.util.Random;

public class LuckyPlayer extends Player {
    public LuckyPlayer(String name, javafx.scene.paint.Color color) {
        super(name, color);
        changeType("Lucky");
    }

    @Override
    public int rollDice() {
        Random random = new Random();
        int dado1 = random.nextInt(6) + 1; // Dado 1 (1 a 6)
        int dado2 = random.nextInt(6) + 1; // Dado 2 (1 a 6)

        // Garantir que a soma seja >= 7
        int soma = dado1 + dado2;
        if (soma < 7) {
            soma = 7; // Ajustar para 7 ou mais
        }
        return soma;
    }

    @Override
    public boolean isUnlucky() {
        return false;
    }

    @Override
    public void setPosition(int position) {

        super.position = position ;
    }
}



