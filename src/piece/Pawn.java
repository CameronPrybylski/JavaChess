package piece;

import game.Board;

import java.util.ArrayList;

public class Pawn extends Piece{

    public Pawn(boolean white, String name){
        super(white, name);
        this.type = "Pawn";
        this.firstMove = true;
        this.value = 1;
    }

    @Override
    public ArrayList<Integer[]> pieceMovement(Board board){
        ArrayList<Integer[]> moves = new ArrayList<>();
        //if(this.isWhite) {
        for (int i = -1; i <= 1; i++) {
            for (int j = 1; j <= 2; j++) {
                if(i != 0 && j > 1){
                    break;
                }
                Integer[] coordinate = new Integer[2];
                if(this.isWhite){
                    coordinate[0] = this.x + i;
                    coordinate[1] = this.y + j;
                }
                else{
                    coordinate[0] = this.x + i;
                    coordinate[1] = this.y - j;
                }
                if(checkValidMove(coordinate[0], coordinate[1], board)){
                    moves.add(coordinate);
                }
            }
        }
        return moves;
    }

    public void enPassant(Piece enemyPiece, int enemyX, int enemyY, Board board){
        enemyPiece.setCaptured();
        board.getBoard()[enemyY][enemyX].setPiece(null);
    }

    @Override
    public boolean checkValidMove(int x, int y, Board board) {
        if(x > 7 || x < 0 || y > 7 || y < 0){
            return false;
        }
        boolean validMove = false;
        int diffX = Math.abs(x - this.x);
        int diffY;
        if(isWhite) {
            diffY = y - this.y;
        }
        else{
            diffY = this.y - y;
        }
        if(diffX > 1 || diffY > 2 || diffY < 1) {
            return false;
        }
        if((!firstMove && diffY > 1) || (diffY > 1 && diffX > 0)){
            return false;
        }
        if(diffX == 0){
            for(int i = 1; i <= diffY; i++){
                if(this.checkIsWhite() && board.getBoard()[this.y + i][this.x].getPiece() != null){
                    return false;
                } else if (!this.checkIsWhite() && board.getBoard()[this.y - i][this.x].getPiece() != null) {
                    return false;
                }
            }
            return true;
        } else if(diffY == 1 && diffX == 1 && board.getBoard()[y][x].getPiece() != null){
            Piece enemyPiece = board.getBoard()[y][x].getPiece();
            if (enemyPiece.checkIsWhite() && !this.checkIsWhite()){
                return true;
            }
            else return !enemyPiece.checkIsWhite() && this.checkIsWhite();
        }
        else if(diffY == 1 && diffX == 1 && board.getBoard()[y][x].getPiece() == null) {
            //Piece enemyPawn;
            int moveY;
            int enemyStartY;
            if(this.checkIsWhite()){
                moveY = -1;
                enemyStartY = 6;
            }
            else{
                moveY = 1;
                enemyStartY = 1;
            }
            if(board.getBoard()[y+moveY][x].getPiece() != null){
                Piece enemyPiece = board.getBoard()[y+moveY][x].getPiece();
                if(enemyPiece.getType().equals("Pawn") && enemyPiece.checkIsWhite() != this.checkIsWhite() && enemyPiece.movesTaken == 1){
                    int newY = enemyPiece.getY();
                    int newX = enemyPiece.getX();
                    if(Math.abs(enemyStartY - newY) == 2){
                        enPassant(enemyPiece, newX, newY, board);
                        return true;
                    }
                }
            }
            return false;
        }
        else{
            return true;
        }
    }

}
