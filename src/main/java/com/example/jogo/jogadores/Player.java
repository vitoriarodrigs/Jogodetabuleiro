package com.example.jogo.jogadores;

import javafx.scene.paint.Color;

public abstract class Player {
    private String name;
    private Color color;
    protected int position;
    private int turns;
    private String type;
    private boolean isSkippingNextTurn;  // Novo atributo para pular

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
        this.position = 0;
        this.turns = 0;
        this.type = "Normal"; // Tipo padrão
        this.isSkippingNextTurn = false; // Inicializa como não pula
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public int getPosition() {
        return position;
    }

    public int getTurns() {
        return turns;
    }

    public String getType() {
        return type;
    }
    public void setTurns(int turns) {
        this.turns = turns;
    }

    public void move(int spaces) {
        position += spaces;
        turns++;
    }

    public void changeType(String newType) {
        type = newType;
    }
    public boolean isSkippingNextTurn() {
        return isSkippingNextTurn;
    }

    public void setSkippingNextTurn(boolean skippingNextTurn) {
        this.isSkippingNextTurn = skippingNextTurn;
    }

    public abstract int rollDice();
    public abstract boolean isUnlucky();
    // Método abstrato setPosition
    public abstract void setPosition(int position);
}
