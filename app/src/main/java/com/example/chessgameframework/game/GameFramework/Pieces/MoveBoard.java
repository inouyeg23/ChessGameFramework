package com.example.chessgameframework.game.GameFramework.Pieces;

import com.example.chessgameframework.ChessGameState;
import com.example.chessgameframework.game.GameFramework.Piece;

public class MoveBoard {

    //8 by 8 array containing true or false if the piece can move there
    //initiallized to all false and is updated when we call getMoves
    private boolean[][] board;


    public MoveBoard(){
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
            if(piece.isBlack()){
                //moves are +1+1, +0+1, +0+2, -1+1
                if(row == 1 && gameState.getPiece(row+1,col) == null && gameState.getPiece(row+2,col) == null)
                    board[row+2][col] = true;

                if(row < 7 && gameState.getPiece(row+1,col) == null)
                    board[row+1][col] = true;

                if(row < 7 && col < 7 && gameState.getPiece(row+1,col+1) != null && !gameState.getPiece(row+1,col+1).isBlack())
                    board[row+1][col+1] = true;

                if(row < 7 && col > 0 && gameState.getPiece(row+1,col-1) != null && !gameState.getPiece(row+1,col-1).isBlack())
                    board[row+1][col-1] = true;

            }
            else{
                //moves are -1-1, +0-1,+0-2, +1,-1

                if(row == 6 && gameState.getPiece(row-1,col) == null && gameState.getPiece(row-2,col) == null)
                    board[row-2][col] = true;

                if(row > 0 && gameState.getPiece(row-1,col) == null)
                    board[row-1][col] = true;

                if(row > 0 && col < 7 && gameState.getPiece(row-1,col-1) != null && !gameState.getPiece(row-1,col+1).isBlack())
                    board[row-1][col+1] = true;

                if(row > 0 && col > 0 && gameState.getPiece(row-1,col-1) != null && !gameState.getPiece(row-1,col-1).isBlack())
                    board[row-1][col-1] = true;

            }
        }
        else if(piece instanceof King){
            //up
            if(row > 0 && (gameState.getPiece(row - 1,col) == null || gameState.getPiece(row - 1,col).isBlack() != piece.isBlack()))
                board[row-1][col] = true;
            //down
            if(row < 7 && (gameState.getPiece(row + 1,col) == null || gameState.getPiece(row + 1,col).isBlack() != piece.isBlack()))
                board[row+1][col] = true;
            //right
            if(col < 7 && (gameState.getPiece(row ,col + 1) == null || gameState.getPiece(row,col + 1).isBlack() != piece.isBlack()))
                board[row][col + 1] = true;
            //left
            if(col > 0 && (gameState.getPiece(row ,col - 1) == null || gameState.getPiece(row,col - 1).isBlack() != piece.isBlack()))
                board[row][col - 1] = true;
            //up right
            if(row > 0 && col < 7 && (gameState.getPiece(row - 1,col + 1) == null || gameState.getPiece(row - 1,col + 1).isBlack() != piece.isBlack()))
                board[row-1][col + 1] = true;
            //up left
            if(row > 0 && col > 0 && (gameState.getPiece(row - 1,col - 1) == null || gameState.getPiece(row - 1,col - 1).isBlack() != piece.isBlack()))
                board[row - 1][col - 1] = true;
            //down right
            if(row < 7 && col< 7 && (gameState.getPiece(row + 1,col + 1) == null || gameState.getPiece(row + 1,col + 1).isBlack() != piece.isBlack()))
                board[row + 1][col + 1] = true;
            //down left
            if(row < 7 && col > 0 && (gameState.getPiece(row + 1,col - 1) == null || gameState.getPiece(row + 1,col - 1).isBlack() != piece.isBlack()))
                board[row + 1][col - 1] = true;

        }
        else if(piece instanceof Queen){
            //down right
            for(int i = 1; row+i < 8 && col + i <8; i++){
                if(gameState.getPiece(row + i,col + i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(row + i,col + i).isBlack() == piece.isBlack())
                        break;
                    board[row + i][col + i] = true;
                    break;
                }
                else
                    board[row + i][col + i] = true;
            }
            //down left
            for(int i = 1; row+i < 8 && col - i > -1; i++){
                if(gameState.getPiece(row + i,col - i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(row + i,col - i).isBlack() == piece.isBlack())
                        break;
                    board[row + i][col - i] = true;
                    break;
                }
                else
                    board[row + i][col - i] = true;
            }
            //up left
            for(int i = 1; row-i > -1 && col - i > -1; i++){
                if(gameState.getPiece(row - i,col - i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(row - i,col - i).isBlack() == piece.isBlack())
                        break;
                    board[row - i][col - i] = true;
                    break;
                }
                else
                    board[row - i][col - i] = true;
            }
            //up right
            for(int i = 1; row-i > -1 && col + i < 8; i++){
                if(gameState.getPiece(row - i,col + i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(row - i,col + i).isBlack() == piece.isBlack())
                        break;
                    board[row - i][col + i] = true;
                    break;
                }
                else
                    board[row - i][col + i] = true;
            }

            for(int i = row + 1; i <= 7; i++){
                if(gameState.getPiece(i,col) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(i,col).isBlack() == piece.isBlack())
                        break;
                    board[i][col] = true;
                    break;
                }
                else
                    board[i][col] = true;
            }

            for(int i = row - 1; i >= 0; i--){
                if(gameState.getPiece(i,col) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(i,col).isBlack() == piece.isBlack())
                        break;
                    board[i][col] = true;
                    break;
                }
                else
                    board[i][col] = true;
            }

            for(int i = col + 1; i <= 7; i++){
                if(gameState.getPiece(row,i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(row,i).isBlack() == piece.isBlack()){
                        break;
                    }
                    board[row][i] = true;
                    break;
                }
                else
                    board[row][i] = true;
            }

            for(int i = col - 1; i >= 0; i--){
                if(gameState.getPiece(row,i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(row,i).isBlack() == piece.isBlack())
                        break;
                    board[row][i] = true;
                    break;
                }
                else
                    board[row][i] = true;
            }

        }
        else if(piece instanceof Rook){

            for(int i = row + 1; i <= 7; i++){
                if(gameState.getPiece(i,col) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(i,col).isBlack() == piece.isBlack())
                        break;
                    board[i][col] = true;
                    break;
                }
                else
                    board[i][col] = true;
            }

            for(int i = row - 1; i >= 0; i--){
                if(gameState.getPiece(i,col) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(i,col).isBlack() == piece.isBlack())
                        break;
                    board[i][col] = true;
                    break;
                }
                else
                    board[i][col] = true;
            }

            for(int i = col + 1; i <= 7; i++){
                if(gameState.getPiece(row,i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(row,i).isBlack() == piece.isBlack()){
                        break;
                    }
                    board[row][i] = true;
                    break;
                }
                else
                    board[row][i] = true;
            }

            for(int i = col - 1; i >= 0; i--){
                if(gameState.getPiece(row,i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(row,i).isBlack() == piece.isBlack())
                        break;
                    board[row][i] = true;
                    break;
                }
                else
                    board[row][i] = true;
            }
        }
        else if(piece instanceof Bishop){
            //down right
            for(int i = 1; row+i < 8 && col + i <8; i++){
                if(gameState.getPiece(row + i,col + i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(row + i,col + i).isBlack() == piece.isBlack())
                        break;
                    board[row + i][col + i] = true;
                    break;
                }
                else
                    board[row + i][col + i] = true;
            }
            //down left
            for(int i = 1; row+i < 8 && col - i > -1; i++){
                if(gameState.getPiece(row + i,col - i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(row + i,col - i).isBlack() == piece.isBlack())
                        break;
                    board[row + i][col - i] = true;
                    break;
                }
                else
                    board[row + i][col - i] = true;
            }
            //up left
            for(int i = 1; row-i > -1 && col - i > -1; i++){
                if(gameState.getPiece(row - i,col - i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(row - i,col - i).isBlack() == piece.isBlack())
                        break;
                    board[row - i][col - i] = true;
                    break;
                }
                else
                    board[row - i][col - i] = true;
            }
            //up right
            for(int i = 1; row-i > -1 && col + i < 8; i++){
                if(gameState.getPiece(row - i,col + i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(row - i,col + i).isBlack() == piece.isBlack())
                        break;
                    board[row - i][col + i] = true;
                    break;
                }
                else
                    board[row - i][col + i] = true;
            }

        }
        else if(piece instanceof Knight){
            //left up
            if(row > 0 && col > 1 && (gameState.getPiece(row - 1,col - 2) == null || gameState.getPiece(row - 1,col - 2).isBlack() != piece.isBlack()))
                board[row-1][col - 2] = true;
            //left down
            if(row < 7 && col > 1 && (gameState.getPiece(row + 1,col - 2) == null || gameState.getPiece(row + 1,col - 2).isBlack() != piece.isBlack()))
                board[row+1][col - 2] = true;
            //right up
            if(row < 0 && col < 6 && (gameState.getPiece(row - 1,col + 2) == null || gameState.getPiece(row - 1,col + 2).isBlack() != piece.isBlack()))
                board[row-1][col + 2] = true;
            //right down
            if(row > 7 && col < 6 && (gameState.getPiece(row + 1,col + 2) == null || gameState.getPiece(row + 1,col + 2).isBlack() != piece.isBlack()))
                board[row+1][col + 2] = true;
            //up right
            if(row > 1 && col < 7 && (gameState.getPiece(row - 2,col + 1) == null || gameState.getPiece(row - 2,col + 1).isBlack() != piece.isBlack()))
                board[row-2][col + 1] = true;
            //up left
            if(row > 1 && col > 0 && (gameState.getPiece(row - 2,col - 1) == null || gameState.getPiece(row - 2,col - 1).isBlack() != piece.isBlack()))
                board[row - 2][col - 1] = true;
            //down right
            if(row < 6 && col < 7 && (gameState.getPiece(row + 2,col + 1) == null || gameState.getPiece(row + 2,col + 1).isBlack() != piece.isBlack()))
                board[row + 2][col + 1] = true;
            //down left
            if(row < 6 && col > 0 && (gameState.getPiece(row + 2,col - 1) == null || gameState.getPiece(row + 2,col - 1).isBlack() != piece.isBlack()))
                board[row + 2][col - 1] = true;
        }

        return;
    }

    public boolean getCanMove(int row,int col){
        return board[row][col];
    }
}
