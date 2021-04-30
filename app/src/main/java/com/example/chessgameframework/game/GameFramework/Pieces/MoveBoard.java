package com.example.chessgameframework.game.GameFramework.Pieces;

import com.example.chessgameframework.ChessGameState;
import com.example.chessgameframework.game.GameFramework.Piece;

public class MoveBoard {


    private boolean[][] board;
    private boolean[][] checkBoard;
    private int numMoves;
    private int kingMoves;



    public MoveBoard(){
        numMoves = 0;
        kingMoves = 0;


        board = new boolean[8][8];
        checkBoard = new boolean[8][8];
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                board[i][j] = false;
                checkBoard[i][j] = false;
            }
        }
    }

    public void findMoves(ChessGameState gameState, int row, int col){
        findPossibleMoves(gameState,row,col, true);

        //removeCheckMoves(gameState,gameState.getPiece(row,col).isBlack());
    }

    public void findPossibleMoves(ChessGameState gameState, int row, int col, boolean checkForChecks){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                this.board[i][j] = false;
            }
        }
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
                //en passant black
                if (row == 1) {
                    if (col < 7 && gameState.getPiece(row + 2, col + 1) != null && gameState.getPiece(row + 1, col) == null && gameState.getPiece(row + 2, col + 1).isBlack() != gameState.getPiece(row, col).isBlack()) {

                        addMoveToBoardIfGood(gameState, row, col, 2, 1, checkForChecks);
                    }

                    if (col > 0 && gameState.getPiece(row + 2, col - 1) != null && gameState.getPiece(row + 1, col) == null && gameState.getPiece(row + 2, col - 1).isBlack() != gameState.getPiece(row, col).isBlack()) {

                        addMoveToBoardIfGood(gameState, row, col, 2, -1, checkForChecks);
                    }
                }


                //moves are +1+1, +0+1, +0+2, -1+1
                if (row == 1 && gameState.getPiece(row + 2, col) == null && gameState.getPiece(row + 2, col) == null) {

                    addMoveToBoardIfGood(gameState,row,col,2,0, checkForChecks);

                }
                if(gameState.getPiece(row + 1, col) == null) {
                    addMoveToBoardIfGood(gameState, row, col, 1, 0, checkForChecks);
                }

                if(row < 7 && col < 7 && gameState.getPiece(row+1,col+1) != null && gameState.getPiece(row+1,col+1).isBlack() != gameState.getPiece(row,col).isBlack()) {

                    addMoveToBoardIfGood(gameState,row,col,1,1, checkForChecks);
                }

                if(row < 7 && col > 0 && gameState.getPiece(row+1,col-1) != null && gameState.getPiece(row+1,col-1).isBlack() != gameState.getPiece(row,col).isBlack()) {

                    addMoveToBoardIfGood(gameState,row,col,1,-1, checkForChecks);
                }
            }
            else{

                //en passant white
                if(row == 6) {
                    if (col < 7 && gameState.getPiece(row - 2, col + 1) != null && gameState.getPiece(row - 1, col) == null && gameState.getPiece(row - 2, col + 1).isBlack() != gameState.getPiece(row, col).isBlack()) {

                        addMoveToBoardIfGood(gameState, row, col, -2, 1, checkForChecks);

                    }

                    if (col > 0 && gameState.getPiece(row - 2, col - 1) != null && gameState.getPiece(row - 1, col) == null && gameState.getPiece(row - 2, col - 1).isBlack() != gameState.getPiece(row, col).isBlack()) {

                        addMoveToBoardIfGood(gameState, row, col, -2, -1, checkForChecks);

                    }
                }

                //moves are -1-1, +0-1,+0-2, +1,-1

                if(row == 6 && gameState.getPiece(row-1,col) == null) {

                    addMoveToBoardIfGood(gameState,row,col,-2,0, checkForChecks);

                }
                if(gameState.getPiece(row - 1, col) == null) {
                    addMoveToBoardIfGood(gameState, row, col, -1, 0, checkForChecks);
                }
                if(row > 0 && col < 7 && gameState.getPiece(row-1,col+1) != null && gameState.getPiece(row-1,col+1).isBlack() != gameState.getPiece(row,col).isBlack()) {

                    addMoveToBoardIfGood(gameState,row,col,-1,1, checkForChecks);

                }

                if(row > 0 && col > 0 && gameState.getPiece(row-1,col-1) != null && gameState.getPiece(row-1,col-1).isBlack() != gameState.getPiece(row,col).isBlack()) {

                    addMoveToBoardIfGood(gameState,row,col,-1,-1, checkForChecks);

                }

            }
        }
        else if(piece instanceof King){
            //up
            addMoveToBoardIfGood(gameState,row,col,-1,0, checkForChecks);
            //down
            addMoveToBoardIfGood(gameState,row,col,1,0, checkForChecks);
            //right
            addMoveToBoardIfGood(gameState,row,col,0,+1, checkForChecks);
            //left
            addMoveToBoardIfGood(gameState,row,col,0,-1, checkForChecks);
            //up right
            addMoveToBoardIfGood(gameState,row,col,-1,+1, checkForChecks);
            //up left
            addMoveToBoardIfGood(gameState,row,col,-1,-1, checkForChecks);
            //down right
            addMoveToBoardIfGood(gameState,row,col,1,1, checkForChecks);
            //down left
            addMoveToBoardIfGood(gameState,row,col,1,-1, checkForChecks);

            //Castling to the right
            King Kpiece = (King) piece;
            if(!Kpiece.getHasMoved() && gameState.getPiece(row,col + 1) == null && gameState.getPiece(row, col + 2) == null
                    && gameState.getPiece(row,col + 3) instanceof Rook) {
                //all conditions are met for castling to the right
                addMoveToBoardIfGood(gameState, row, col, 0, 2, checkForChecks);

                if (piece.isBlack()) {
                    gameState.castlingRightBlack = true;

                }
                else {
                    gameState.castlingRightWhite = true;
                }
            }

            //castling to the left
            if(col == 4 && !Kpiece.getHasMoved() && gameState.getPiece(row,col - 1) == null && gameState.getPiece(row,col - 2) == null
                    && gameState.getPiece(row,col - 3) == null
                    && gameState.getPiece(row,col - 4) instanceof Rook){
                //all conditions are met for castling to the left
                addMoveToBoardIfGood(gameState,row,col,0,-2,checkForChecks);

                if (piece.isBlack()) {
                    gameState.castlingLeftBlack = true;
                }
                else {
                    gameState.castlingLeftWhite = true;
                }
            }

        }
        else if(piece instanceof Queen){
            for(int i = 1; row + i < 8; i++){
                if(!addMoveToBoardIfGood(gameState,row,col,i, 0, checkForChecks))
                    break;
                else if(gameState.getPiece(row + i,col) != null)
                    break;
            }

            for(int i = 1; row - i >= 0; i++){
                if(!addMoveToBoardIfGood(gameState,row,col,-i, 0, checkForChecks))
                    break;
                else if(gameState.getPiece(row - i,col) != null)
                    break;
            }

            for(int i = 1; col + i < 8; i++){
                if(!addMoveToBoardIfGood(gameState,row,col,0, i, checkForChecks))
                    break;
                else if(gameState.getPiece(row,col + i) != null)
                    break;
            }

            for(int i = 1; col - i >= 0; i++){
                if(!addMoveToBoardIfGood(gameState,row,col,0,-i, checkForChecks))
                    break;
                else if(gameState.getPiece(row,col - i) != null)
                    break;
            }

            for(int i = 1; row + i < 8 && col + i < 8; i++){
                if(!addMoveToBoardIfGood(gameState,row,col,i,i, checkForChecks))
                    break;
                else if(gameState.getPiece(row + i,col + i) != null)
                    break;
            }
            //down left
            for(int i = 1; row + i < 8 && col - i > -1; i++){
                if(!addMoveToBoardIfGood(gameState,row,col,i,-i, checkForChecks))
                    break;
                else if(gameState.getPiece(row + i,col - i) != null)
                    break;
            }
            //up left
            for(int i = 1; row - i > -1 && col + i > -1; i++){
                if(!addMoveToBoardIfGood(gameState,row,col, -i, -i, checkForChecks))
                    break;
                else if(gameState.getPiece(row - i,col - i) != null)
                    break;
            }
            //up right
            for(int i = 1; row - i > -1 && col + i < 8; i++){
                if(!addMoveToBoardIfGood(gameState,row,col,-i,i, checkForChecks))
                    break;
                else if(gameState.getPiece(row - i,col + i) != null)
                    break;
            }

        }
        else if(piece instanceof Rook){

            for(int i = 1; row + i < 8; i++){
                if(!addMoveToBoardIfGood(gameState,row,col,i, 0, checkForChecks))
                    break;
                else if(gameState.getPiece(row + i,col) != null)
                    break;
            }

            for(int i = 1; row - i >= 0; i++){
                if(!addMoveToBoardIfGood(gameState,row,col,-i, 0, checkForChecks))
                    break;
                else if(gameState.getPiece(row - i,col) != null)
                    break;
            }

            for(int i = 1; col + i < 8; i++){
                if(!addMoveToBoardIfGood(gameState,row,col,0, i, checkForChecks))
                    break;
                else if(gameState.getPiece(row,col + i) != null)
                    break;
            }

            for(int i = 1; col - i >= 0; i++){
                if(!addMoveToBoardIfGood(gameState,row,col,0,-i, checkForChecks))
                    break;
                else if(gameState.getPiece(row,col - i) != null)
                    break;
            }
        }
        else if(piece instanceof Bishop){
            //down right
            for(int i = 1; row + i < 8 && col + i < 8; i++){
                if(!addMoveToBoardIfGood(gameState,row,col,i,i, checkForChecks))
                    break;
                else if(gameState.getPiece(row + i,col + i) != null)
                    break;
            }
            //down left
            for(int i = 1; row + i < 8 && col - i > -1; i++){
                if(!addMoveToBoardIfGood(gameState,row,col,i,-i, checkForChecks))
                    break;
                else if(gameState.getPiece(row + i,col - i) != null)
                    break;
            }
            //up left
            for(int i = 1; row - i > -1 && col + i > -1; i++){
                if(!addMoveToBoardIfGood(gameState,row,col, -i, -i, checkForChecks))
                    break;
                else if(gameState.getPiece(row - i,col - i) != null)
                    break;
            }
            //up right
            for(int i = 1; row - i > -1 && col + i < 8; i++){
                if(!addMoveToBoardIfGood(gameState,row,col,-i,i, checkForChecks))
                    break;
                else if(gameState.getPiece(row - i,col + i) != null)
                    break;
            }

        }
        else if(piece instanceof Knight){
            //The eight possible moves a knight can make
            addMoveToBoardIfGood(gameState,row,col,-1,-2, checkForChecks);
            addMoveToBoardIfGood(gameState,row,col,1,-2, checkForChecks);
            addMoveToBoardIfGood(gameState,row,col,-1,2, checkForChecks);
            addMoveToBoardIfGood(gameState,row,col,1,2, checkForChecks);
            addMoveToBoardIfGood(gameState,row,col,-2,1, checkForChecks);
            addMoveToBoardIfGood(gameState,row,col,-2,-1, checkForChecks);
            addMoveToBoardIfGood(gameState,row,col,2,1, checkForChecks);
            addMoveToBoardIfGood(gameState,row,col,2,-1, checkForChecks);


        }
    }

    public boolean getCanMove(int row,int col){
        return board[row][col];
    }

    public int getNumMoves(){
        return this.numMoves;
    }

    private boolean addMoveToBoardIfGood(ChessGameState gameState, int pieceRow, int pieceCol, int rowDiff, int colDiff, boolean checkForChecks){
        Piece piece = gameState.getPiece(pieceRow,pieceCol);
        boolean isBlack = piece.isBlack();
        if(pieceRow + rowDiff <= 7 && pieceRow + rowDiff >= 0){
            if(pieceCol + colDiff <= 7 && pieceCol + colDiff >= 0){
                //move is in the board

                if(gameState.getPiece(pieceRow + rowDiff, pieceCol + colDiff) == null ||
                        gameState.getPiece(pieceRow + rowDiff, pieceCol + colDiff).isBlack() != piece.isBlack()){
                    if(checkForChecks) {
                        //now lets make the move and see if we are in check when the move is made
                        ChessGameState gs = new ChessGameState(gameState);
                        //gs.movePiece(pieceCol ,pieceRow,pieceCol + colDiff,pieceRow+rowDiff,piece);

                            Piece rook;
                        if(gameState.castlingRightWhite){
                            gameState.castlingRightWhite = false;
                            //gs.setPiece(7,7, null);
                            rook = gameState.getPiece(7,7);
                            gs.movePiece(7, 7, 7, 5, rook);
                            //gs.movePiece(7, 4, 7, 6, piece);
                            //gameState.kingIsMoving(piece);
                        }
                        else if(gameState.castlingLeftWhite){
                            gameState.castlingLeftWhite = false;
                            //gs.setPiece(7,0, null);
                            rook = gameState.getPiece(7,0);
                            gs.movePiece(7, 0, 7, 2, rook);
                            //gs.movePiece(7, 4, 7, 2, piece);
                            //gameState.kingIsMoving(piece);
                        }
                        else if(gameState.castlingRightBlack){
                            gameState.castlingRightBlack = false;
                            //gs.setPiece(0,7, null);
                            rook = gameState.getPiece(0,7);
                            gs.movePiece(0, 7, 0, 5, rook);
                            //gs.movePiece(0, 4, 0, 6, piece);
                            //gameState.kingIsMoving(piece);
                        }
                        else if(gameState.castlingLeftBlack){
                            gameState.castlingLeftBlack = false;
                            //gs.setPiece(0,0, null);
                            rook = gameState.getPiece(0,0);
                            gs.movePiece(0, 0, 0, 2, rook);
                            //gs.movePiece(0, 4, 0, 2, piece);
                            //gameState.kingIsMoving(piece);
                        }

                        gs.movePiece(pieceRow, pieceCol, pieceRow + rowDiff, pieceCol + colDiff, piece);

                        gameState.kingSearch();

                        //move has been made, lets check if we are in check.
                        int[] kingLoc = getKingLoc(gs, isBlack);
                        if (piece instanceof King) {
                            if (!getIfKingInCheck(gs, kingLoc[0], kingLoc[1])) {
                                System.out.println(isBlack);
                                board[pieceRow + rowDiff][pieceCol + colDiff] = true;
                                numMoves++;
                                return true;
                            }
                        } else {

                            System.out.println(isBlack);
                            if (!getIfKingInCheck(gs, kingLoc[0], kingLoc[1])) {
//                                System.out.println("Move would not put the king in check");
                                board[pieceRow + rowDiff][pieceCol + colDiff] = true;
                                numMoves++;
                                return true;
                            }
                        }
                    }
                    else{
                        board[pieceRow + rowDiff][pieceCol + colDiff] = true;
                        numMoves++;
                        return true;
                    }
                }


            }
        }

        return false;
    }

    private int[] getKingLoc(ChessGameState gameState, boolean isBlacksTurn){
        //find king
        int kRow = -1;
        int kCol = -1;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Piece p = gameState.getPiece(i,j);

                if(p instanceof King && p.isBlack() == isBlacksTurn){
                    kRow = i;
                    kCol = j;
                    break;
                }
            }
        }

        int[] kingLoc = new int[2];
        kingLoc[0] = kRow;
        kingLoc[1] = kCol;

        return kingLoc;

    }
/*
    private void removeCheckMoves(ChessGameState gameState, boolean isBlacksTurn){
        //find king
        int kRow = -1;
        int kCol = -1;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Piece p = gameState.getPiece(i,j);
                if(p != null && p instanceof King && p.isBlack() == isBlacksTurn){
                    kRow = i;
                    kCol = j;
                    break;
                }
            }
        }

        //get the moves of the opponents pieces, if any of them attack the king then the opponent is in check
        boolean inCheck = getIfKingInCheck(gameState, kRow, kCol);

        //we need to get out of check so go through all of the moves and make them and check if the king is still in check
        MoveBoard mb = new MoveBoard();


        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(gameState.getPiece(i,j) != null && gameState.getPiece(i,j).isBlack() == isBlacksTurn) {
                    mb.findPossibleMoves(gameState, i, j, false);
                    for (int ii = 0; ii < 8; ii++) {
                        for (int jj = 0; jj < 8; jj++) {
                            if (mb.getCanMove(ii, jj)) {
                                ChessGameState cp = new ChessGameState(gameState);
                                cp.movePiece(i, j, ii, jj, gameState.getPiece(i, j));
                                if(getIfKingInCheck(cp,kRow,kCol)){
                                    //king is in check after the move was made so it was a bad move
                                    board[i][j] = false;
                                }
                            }
                        }
                    }
                }
            }
        }

        //otherwise we need to check that all of our moves don't put ourself in check

    }
*/
    private boolean getIfKingInCheck(ChessGameState gameState, int kingRow, int kingCol){

        boolean inCheck = false;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                System.out.println("King row: " + kingRow + ", King col: " + kingCol);
                if(gameState.getPiece(i,j) != null && gameState.getPiece(i,j).isBlack() != gameState.getPiece(kingRow,kingCol).isBlack()){
                    MoveBoard mb = new MoveBoard();
                    mb.findPossibleMoves(gameState,i,j, false);

                    if(mb.board[kingRow][kingCol] && i != kingRow && j != kingCol) {
                        System.out.println(gameState.getPiece(i,j) + " is located at: " + i + ", " + j);
                        inCheck = true;

                    }
                }
            }
        }
        return inCheck;
    }


}
