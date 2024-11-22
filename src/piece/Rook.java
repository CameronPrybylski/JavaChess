package piece;
import game.Board;
import game.Tile;

import java.util.ArrayList;

public class Rook extends Piece{

    public Rook(boolean white, String name){
        super(white, name);
        this.type = "Rook";
        this.value = 3;
    }

    @Override
    public ArrayList<Integer[]> pieceMovement(Board board){
        ArrayList<Integer[]> moves = new ArrayList<>();
        for(int i = -7; i <= 7; i++){
            for(int j = 0; j < 2; j++){
                Integer[] coordinate = new Integer[2];
                if(j == 0) {
                    coordinate[0] = this.x + i;
                    coordinate[1] = this.y;
                }
                else{
                    coordinate[0] = this.x;
                    coordinate[1] = this.y + i;
                }
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
        int diffY = Math.abs(y - this.y);
        int diffX = Math.abs(x - this.x);
        Tile destTile = board.getBoard()[y][x];
        if((diffX > 0 && diffY > 0) || (diffX == 0 && diffY == 0)){
            return false;
        }
        if(diffX == 0){
            for (int i = 1; i < diffY; i++){
                if(y > this.y) {
                    if (board.getBoard()[this.y + i][this.x].getPiece() != null) {
                        return false;
                    }
                }
                else{
                    if(board.getBoard()[this.y - i][this.x].getPiece() != null){
                        return false;
                    }
                }
            }
        }
        else{
            for (int i = 1; i < diffX; i++){
                if(x > this.x) {
                    if (board.getBoard()[this.y][this.x + i].getPiece() != null) {
                        return false;
                    }
                }
                else{
                    if(board.getBoard()[this.y][this.x - i].getPiece() != null){
                        return false;
                    }
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
