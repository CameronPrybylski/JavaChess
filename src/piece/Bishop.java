package piece;
import game.Board;
import game.Tile;

import java.util.ArrayList;


public class Bishop extends Piece{

    public Bishop(boolean white, String name){
        super(white, name);
        this.type = "Bishop";
        this.value = 3;
    }

    @Override
    public ArrayList<Integer[]> pieceMovement(Board board){
        ArrayList<Integer[]> moves = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            for (int j = 1; j < 8; j++) {
                int newX = this.x;
                int newY = this.y;
                if(i == 0){
                    newX += j;
                    newY += j;
                } else if (i == 1) {
                    newX -= j;
                    newY += j;
                } else if (i == 2) {
                    newX += j;
                    newY -= j;
                } else{
                    newX -= j;
                    newY -= j;
                }
                Integer[] coordinate = new Integer[2];
                coordinate[0] = newX;
                coordinate[1] = newY;
                if(checkValidMove(coordinate[0], coordinate[1], board)) {
                    moves.add(coordinate);
                }
            }
        }
        return moves;
    }

    @Override
    public boolean checkValidMove(int x, int y, Board board) {
        if(x > 7 || x < 0 || y > 7 || y < 0){
            return false;
        }
        int diffX = Math.abs(x - this.x);
        int diffY = Math.abs(y - this.y);
        if (diffY != diffX) {
            return false;
        }
        Tile destTile = board.getBoard()[y][x];
        if (x > this.x) {
            for (int i = 1; i < diffX; i++) {
                if (y > this.y && board.getBoard()[this.y + i][this.x + i].getPiece() != null) {
                    return false;
                }
                else if(y < this.y && board.getBoard()[this.y - i][this.x + i].getPiece() != null){
                    return false;
                }
            }
        }
        else {
            for (int i = 1; i < diffX; i++) {
                if (y > this.y && board.getBoard()[this.y + i][this.x - i].getPiece() != null) {
                    return false;
                }
                else if(y < this.y && board.getBoard()[this.y - i][this.x - i].getPiece() != null){
                    return false;
                }
            }
        }
        if(destTile.getPiece() != null){
            return destTile.getPiece().checkIsWhite() != this.isWhite;
        }
        else{
            return true;
        }
    }
}
