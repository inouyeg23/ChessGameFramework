package com.example.chessgameframework.game.GameFramework;

public abstract class Piece {
    /**
     * @authors: Jonah Ingler
     *
     */
    //if isBlack is true, than it is black, otherwise it is white
    private boolean isBlack;
    private boolean isTaken;
    //piece class with it's color as a parameter
    public Piece(boolean isBlack) {
        this.isBlack = isBlack;
        this.isTaken = false;
    }
    //getters and setters for the two instance variables
    public boolean isBlack() {
        return isBlack;
    }
    public boolean isTaken() {
        return isTaken;
    }
    public void setBlack(boolean black) {
        isBlack = black;
    }
    public void setTaken(boolean taken) {
        isTaken = taken;
    }
}
