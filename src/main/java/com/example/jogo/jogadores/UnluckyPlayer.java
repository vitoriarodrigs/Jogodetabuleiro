package com.example.jogo.jogadores;

import java.util.Random;

public class UnluckyPlayer extends Player {
    public UnluckyPlayer(String name, javafx.scene.paint.Color color) {
        super(name, color);
        changeType("Unlucky");
    }

    @Override
    public int rollDice() {
        Random random = new Random();
        int dado1 = random.nextInt(6) + 1; // Dado 1 (1 a 6)
        int dado2 = random.nextInt(6) + 1; // Dado 2 (1 a 6)

        // Garantir que a soma seja <= 6
        int soma = dado1 + dado2;
        if (soma > 6) {
            soma = 6; // Ajustar para 6 ou menos
        }
        return soma;
    }

    @Override
    public boolean isUnlucky() {
        return true; // Jogador azarado
    }

    @Override
    public void setPosition(int position) {

        super.position = position ;
    }
}



