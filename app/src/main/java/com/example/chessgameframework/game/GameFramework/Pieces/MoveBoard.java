package com.example.chessgameframework.game.GameFramework.Pieces;

import com.example.chessgameframework.ChessGameState;
import com.example.chessgameframework.game.GameFramework.Piece;

public class MoveBoard {

    //8 by 8 array containing true or false if the piece can move there
    //initiallized to all false and is updated when we call getMoves
    private boolean[][] board;
    private int numMoves;


    public MoveBoard(){
        numMoves = 0;
        board = new boolean[8][8];
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                board[i][j] = false;
            }
        }
    }

    public void findMoves(ChessGameState gameState, int row, int col){

        //check the color
        //figure out the normal moves that it would be able to make with no conditionals
        //check if the player is in check then the move would need to get rid of check
        //check if moving the piece would cause the player to be in check


        //check what piece it is and figure out the possible moves accordingly
        Piece piece = gameState.getPiece(row,col);
        if(piece == null || gameState == null){
            return;
        }
        else if(piece instanceof Pawn){
            //pawns can move in the three spaces infront of them
            if(piece.isBlack()) {
                //moves are +1+1, +0+1, +0+2, -1+1
                if (row == 1 && gameState.getPiece(row + 1, col) == null && gameState.getPiece(row + 2, col) == null) {
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row+2,col,gameState.getPlayerTurn())) {
                        board[row + 2][col] = true;
                        numMoves++;
                    }
                }

                if (row < 7 && gameState.getPiece(row + 1, col) == null){
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row+1,col,gameState.getPlayerTurn())) {
                        board[row + 1][col] = true;
                        numMoves++;
                    }
                }

                if(row < 7 && col < 7 && gameState.getPiece(row+1,col+1) != null && !gameState.getPiece(row+1,col+1).isBlack()) {
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row+1,col + 1,gameState.getPlayerTurn())) {
                        board[row + 1][col + 1] = true;
                        numMoves++;
                    }
                }

                if(row < 7 && col > 0 && gameState.getPiece(row+1,col-1) != null && !gameState.getPiece(row+1,col-1).isBlack()) {
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row+1,col - 1,gameState.getPlayerTurn())) {
                        board[row + 1][col - 1] = true;
                        numMoves++;
                    }
                }
            }
            else{
                //moves are -1-1, +0-1,+0-2, +1,-1

                if(row == 6 && gameState.getPiece(row-1,col) == null && gameState.getPiece(row-2,col) == null) {
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row-2,col,gameState.getPlayerTurn())) {
                        board[row - 2][col] = true;
                        numMoves++;
                    }
                }

                if(row > 0 && gameState.getPiece(row-1,col) == null) {
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row-1,col,gameState.getPlayerTurn())) {
                        board[row - 1][col] = true;
                        numMoves++;
                    }
                }

                if(row > 0 && col < 7 && gameState.getPiece(row-1,col+1) != null && !gameState.getPiece(row-1,col+1).isBlack()) {
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row-1,col+1,gameState.getPlayerTurn())) {
                        board[row - 1][col + 1] = true;
                        numMoves++;
                    }
                }

                if(row > 0 && col > 0 && gameState.getPiece(row-1,col-1) != null && !gameState.getPiece(row-1,col-1).isBlack()) {
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row-1,col-1,gameState.getPlayerTurn())) {
                        board[row - 1][col - 1] = true;
                        numMoves++;
                    }
                }

            }
        }
        else if(piece instanceof King){
            //up
            if(row > 0 && (gameState.getPiece(row - 1,col) == null || gameState.getPiece(row - 1,col).isBlack() != piece.isBlack())) {
                if(checkIfMoveWouldPutInCheck(gameState,row,col, row-1,col,gameState.getPlayerTurn())) {
                    board[row - 1][col] = true;
                    numMoves++;
                }
            }
            //down
            if(row < 7 && (gameState.getPiece(row + 1,col) == null || gameState.getPiece(row + 1,col).isBlack() != piece.isBlack())) {
                if(checkIfMoveWouldPutInCheck(gameState,row,col, row+1,col,gameState.getPlayerTurn())) {
                    board[row + 1][col] = true;
                    numMoves++;
                }
            }
            //right
            if(col < 7 && (gameState.getPiece(row ,col + 1) == null || gameState.getPiece(row,col + 1).isBlack() != piece.isBlack())) {
                if(checkIfMoveWouldPutInCheck(gameState,row,col, row,col+1,gameState.getPlayerTurn())) {
                    board[row][col + 1] = true;
                    numMoves++;
                }
            }
            //left
            if(col > 0 && (gameState.getPiece(row ,col - 1) == null || gameState.getPiece(row,col - 1).isBlack() != piece.isBlack())) {
                if(checkIfMoveWouldPutInCheck(gameState,row,col, row,col-1,gameState.getPlayerTurn())) {
                    board[row][col - 1] = true;
                    numMoves++;
                }
            }
            //up right
            if(row > 0 && col < 7 && (gameState.getPiece(row - 1,col + 1) == null || gameState.getPiece(row - 1,col + 1).isBlack() != piece.isBlack())) {
                if(checkIfMoveWouldPutInCheck(gameState,row,col, row-1,col + 1,gameState.getPlayerTurn())) {
                    board[row - 1][col + 1] = true;
                    numMoves++;
                }
            }
            //up left
            if(row > 0 && col > 0 && (gameState.getPiece(row - 1,col - 1) == null || gameState.getPiece(row - 1,col - 1).isBlack() != piece.isBlack())) {
                if(checkIfMoveWouldPutInCheck(gameState,row,col, row-1,col-1,gameState.getPlayerTurn())) {
                    board[row - 1][col - 1] = true;
                    numMoves++;
                }
            }
            //down right
            if(row < 7 && col< 7 && (gameState.getPiece(row + 1,col + 1) == null || gameState.getPiece(row + 1,col + 1).isBlack() != piece.isBlack())) {
                if(checkIfMoveWouldPutInCheck(gameState,row,col, row+1,col+1,gameState.getPlayerTurn())) {
                    board[row + 1][col + 1] = true;
                    numMoves++;
                }
            }
            //down left
            if(row < 7 && col > 0 && (gameState.getPiece(row + 1,col - 1) == null || gameState.getPiece(row + 1,col - 1).isBlack() != piece.isBlack())) {
                if(checkIfMoveWouldPutInCheck(gameState,row,col, row+1,col-1,gameState.getPlayerTurn())) {
                    board[row + 1][col - 1] = true;
                    numMoves++;
                }
            }

        }
        else if(piece instanceof Queen){
            //down right
            for(int i = 1; row+i < 8 && col + i <8; i++){
                if(gameState.getPiece(row + i,col + i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(row + i,col + i).isBlack() == piece.isBlack())
                        break;
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row+i,col+i,gameState.getPlayerTurn())) {
                        board[row + i][col + i] = true;
                        numMoves++;
                    }
                    break;
                }
                else {
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row+i,col+i,gameState.getPlayerTurn())) {
                        board[row + i][col + i] = true;
                        numMoves++;
                    }
                }
            }
            //down left
            for(int i = 1; row+i < 8 && col - i > -1; i++){
                if(gameState.getPiece(row + i,col - i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(row + i,col - i).isBlack() == piece.isBlack())
                        break;
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row+i,col-i,gameState.getPlayerTurn())) {
                        board[row + i][col - i] = true;
                        numMoves++;
                    }
                    break;
                }
                else {
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row+i,col-i,gameState.getPlayerTurn())) {
                        board[row + i][col - i] = true;
                        numMoves++;
                    }
                }
            }
            //up left
            for(int i = 1; row-i > -1 && col - i > -1; i++){
                if(gameState.getPiece(row - i,col - i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(row - i,col - i).isBlack() == piece.isBlack())
                        break;
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row-i,col-i,gameState.getPlayerTurn())) {
                        board[row - i][col - i] = true;
                        numMoves++;
                    }
                    break;
                }
                else {
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row-i,col-i,gameState.getPlayerTurn())) {
                        board[row - i][col - i] = true;
                        numMoves++;
                    }
                }
            }
            //up right
            for(int i = 1; row-i > -1 && col + i < 8; i++){
                if(gameState.getPiece(row - i,col + i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(row - i,col + i).isBlack() == piece.isBlack())
                        break;
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row-i,col+i,gameState.getPlayerTurn())) {
                        board[row - i][col + i] = true;
                        numMoves++;
                    }
                    break;
                }
                else {
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row-i,col+i,gameState.getPlayerTurn())) {
                        board[row - i][col + i] = true;
                        numMoves++;
                    }
                }

            }

            for(int i = row + 1; i <= 7; i++){
                if(gameState.getPiece(i,col) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(i,col).isBlack() == piece.isBlack())
                        break;
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, i,col,gameState.getPlayerTurn())) {
                        board[i][col] = true;
                        numMoves++;
                    }
                    break;
                }
                else {
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, i,col,gameState.getPlayerTurn())) {
                        board[i][col] = true;
                        numMoves++;
                    }
                }
            }

            for(int i = row - 1; i >= 0; i--){
                if(gameState.getPiece(i,col) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(i,col).isBlack() == piece.isBlack())
                        break;
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, i,col,gameState.getPlayerTurn())) {
                        board[i][col] = true;
                        numMoves++;
                    }
                    break;
                }
                else {
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, i,col,gameState.getPlayerTurn())) {
                        board[i][col] = true;
                        numMoves++;
                    }
                }
            }

            for(int i = col + 1; i <= 7; i++){
                if(gameState.getPiece(row,i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(row,i).isBlack() == piece.isBlack()){
                        break;
                    }
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row,i,gameState.getPlayerTurn())) {
                        board[row][i] = true;
                        numMoves++;
                    }
                    break;
                }
                else {
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row,i,gameState.getPlayerTurn())) {
                        board[row][i] = true;
                        numMoves++;
                    }
                }
            }

            for(int i = col - 1; i >= 0; i--){
                if(gameState.getPiece(row,i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(row,i).isBlack() == piece.isBlack())
                        break;
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row,i,gameState.getPlayerTurn())) {
                        board[row][i] = true;
                        numMoves++;
                    }
                    break;
                }
                else {
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row,i,gameState.getPlayerTurn())) {
                        board[row][i] = true;
                        numMoves++;
                    }
                }
            }

        }
        else if(piece instanceof Rook){

            for(int i = row + 1; i <= 7; i++){
                if(gameState.getPiece(i,col) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(i,col).isBlack() == piece.isBlack())
                        break;
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, i,col,gameState.getPlayerTurn())) {
                        board[i][col] = true;
                        numMoves++;
                    }
                    break;
                }
                else {
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, i,col,gameState.getPlayerTurn())) {
                        board[i][col] = true;
                        numMoves++;
                    }
                }
            }

            for(int i = row - 1; i >= 0; i--){
                if(gameState.getPiece(i,col) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(i,col).isBlack() == piece.isBlack())
                        break;
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, i,col,gameState.getPlayerTurn())) {
                        board[i][col] = true;
                        numMoves++;
                    }
                    break;
                }
                else {
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, i,col,gameState.getPlayerTurn())) {
                        board[i][col] = true;
                        numMoves++;
                    }
                }
            }

            for(int i = col + 1; i <= 7; i++){
                if(gameState.getPiece(row,i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(row,i).isBlack() == piece.isBlack()){
                        break;
                    }
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row,i,gameState.getPlayerTurn())) {
                        board[row][i] = true;
                        numMoves++;
                    }
                    break;
                }
                else {
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row,i,gameState.getPlayerTurn())) {
                        board[row][i] = true;
                        numMoves++;
                    }
                }
            }

            for(int i = col - 1; i >= 0; i--){
                if(gameState.getPiece(row,i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(row,i).isBlack() == piece.isBlack())
                        break;
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row,i,gameState.getPlayerTurn())) {
                        board[row][i] = true;
                        numMoves++;
                    }
                    break;
                }
                else {
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row,i,gameState.getPlayerTurn())) {
                        board[row][i] = true;
                        numMoves++;
                    }
                }
            }
        }
        else if(piece instanceof Bishop){
            //down right
            for(int i = 1; row+i < 8 && col + i <8; i++){
                if(gameState.getPiece(row + i,col + i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(row + i,col + i).isBlack() == piece.isBlack())
                        break;
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row+i,col+i,gameState.getPlayerTurn())) {
                        board[row + i][col + i] = true;
                        numMoves++;
                    }
                    break;
                }
                else {
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row+i,col+i,gameState.getPlayerTurn())) {
                        board[row + i][col + i] = true;
                        numMoves++;
                    }
                }
            }
            //down left
            for(int i = 1; row+i < 8 && col - i > -1; i++){
                if(gameState.getPiece(row + i,col - i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(row + i,col - i).isBlack() == piece.isBlack())
                        break;
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row+i,col-i,gameState.getPlayerTurn())) {
                        board[row + i][col - i] = true;
                        numMoves++;
                    }
                    break;
                }
                else {
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row+i,col-i,gameState.getPlayerTurn())) {
                        board[row + i][col - i] = true;
                        numMoves++;
                    }
                }
            }
            //up left
            for(int i = 1; row-i > -1 && col - i > -1; i++){
                if(gameState.getPiece(row - i,col - i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(row - i,col - i).isBlack() == piece.isBlack())
                        break;
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row-i,col-i,gameState.getPlayerTurn())) {
                        board[row - i][col - i] = true;
                        numMoves++;
                    }
                    break;
                }
                else {
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row-i,col-i,gameState.getPlayerTurn())) {
                        board[row - i][col - i] = true;
                        numMoves++;
                    }
                }
            }
            //up right
            for(int i = 1; row-i > -1 && col + i < 8; i++){
                if(gameState.getPiece(row - i,col + i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(row - i,col + i).isBlack() == piece.isBlack())
                        break;
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row-i,col+i,gameState.getPlayerTurn())) {
                        board[row - i][col + i] = true;
                        numMoves++;
                    }
                    break;
                }
                else {
                    if(checkIfMoveWouldPutInCheck(gameState,row,col, row-i,col+i,gameState.getPlayerTurn())) {
                        board[row - i][col + i] = true;
                        numMoves++;
                    }
                }

            }

        }
        else if(piece instanceof Knight){
            //left up
            if(row > 0 && col > 1 && (gameState.getPiece(row - 1,col - 2) == null || gameState.getPiece(row - 1,col - 2).isBlack() != piece.isBlack())) {
                if(checkIfMoveWouldPutInCheck(gameState,row,col, row-1,col-2,gameState.getPlayerTurn())) {
                    board[row - 1][col - 2] = true;
                    numMoves++;
                }
            }
            //left down
            if(row < 7 && col > 1 && (gameState.getPiece(row + 1,col - 2) == null || gameState.getPiece(row + 1,col - 2).isBlack() != piece.isBlack())) {
                if(checkIfMoveWouldPutInCheck(gameState,row,col, row+1,col-2,gameState.getPlayerTurn())) {
                    board[row + 1][col - 2] = true;
                    numMoves++;
                }
            }
            //right up
            if(row < 0 && col < 6 && (gameState.getPiece(row - 1,col + 2) == null || gameState.getPiece(row - 1,col + 2).isBlack() != piece.isBlack())){
                if(checkIfMoveWouldPutInCheck(gameState,row,col, row-1,col+2,gameState.getPlayerTurn())) {
                    board[row - 1][col + 2] = true;
                    numMoves++;
                }
            }
            //right down
            if(row > 7 && col < 6 && (gameState.getPiece(row + 1,col + 2) == null || gameState.getPiece(row + 1,col + 2).isBlack() != piece.isBlack())){
                if(checkIfMoveWouldPutInCheck(gameState,row,col, row+1,col+2,gameState.getPlayerTurn())) {
                    board[row + 1][col + 2] = true;
                    numMoves++;
                }
            }
            //up right
            if(row > 1 && col < 7 && (gameState.getPiece(row - 2,col + 1) == null || gameState.getPiece(row - 2,col + 1).isBlack() != piece.isBlack())){
                if(checkIfMoveWouldPutInCheck(gameState,row,col, row-2,col+1,gameState.getPlayerTurn())) {
                    board[row - 2][col + 1] = true;
                    numMoves++;
                }
            }
            //up left
            if(row > 1 && col > 0 && (gameState.getPiece(row - 2,col - 1) == null || gameState.getPiece(row - 2,col - 1).isBlack() != piece.isBlack())){
                if(checkIfMoveWouldPutInCheck(gameState,row,col, row-2,col-1,gameState.getPlayerTurn())) {
                    board[row - 2][col - 1] = true;
                    numMoves++;
                }
            }
            //down right
            if(row < 6 && col < 7 && (gameState.getPiece(row + 2,col + 1) == null || gameState.getPiece(row + 2,col + 1).isBlack() != piece.isBlack())){
                if(checkIfMoveWouldPutInCheck(gameState,row,col, row+2,col+1,gameState.getPlayerTurn())) {
                    board[row + 2][col + 1] = true;
                    numMoves++;
                }
            }
            //down left
            if(row < 6 && col > 0 && (gameState.getPiece(row + 2,col - 1) == null || gameState.getPiece(row + 2,col - 1).isBlack() != piece.isBlack())){
                if(checkIfMoveWouldPutInCheck(gameState,row,col, row+2,col-1,gameState.getPlayerTurn())) {
                    board[row + 2][col - 1] = true;
                    numMoves++;
                }
            }
        }


    }

    public boolean getCanMove(int row,int col){
        return board[row][col];
    }

    public int getNumMoves(){
        return this.numMoves;
    }


    private boolean checkIfMoveWouldPutInCheck(ChessGameState gameState, int row, int col, int destRow, int destCol, int playerNum){
        ChessGameState ngs = new ChessGameState(gameState);
        ngs.movePiece(row,col,destRow,destCol,ngs.getPiece(row, col));
        ngs.inCheck();
        if(playerNum == 0){
            return ngs.isCheckedWhite();
        }
        else
            return ngs.isCheckedBlack();

    }
}
