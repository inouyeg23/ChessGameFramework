package com.example.chessgameframework.game.GameFramework;

public abstract class Piece {

    //if isBlack is true, than it is black, otherwise it is white
    private boolean isBlack;
    private boolean isTaken;

    //piece class with it's color as a parameter
    public Piece(boolean isBlack) {
        setBlack(this.isBlack);
        setTaken(this.isTaken);
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
    /**
     * FIXME
     * canMove method should be true if the piece can move, and false if it cannot.
     * need a class/method to determine whether the selected square is occupied or not.
     *          (maybe a class called Square??) so that it can take a piece as well
     *
     * 
     **/
    public abstract boolean canMove(int row, int col, int selectRow, int selectCol, Piece piece);
}
