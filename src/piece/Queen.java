package piece;
import game.Board;
import game.Tile;

import java.util.ArrayList;

public class Queen extends Piece{

    public Queen(boolean white, String name){
        super(white, name);
        this.type = "Queen";
        this.value = 6;
    }

    @Override
    public ArrayList<Integer[]> pieceMovement(Board board) {
        ArrayList<Integer[]> moves = new ArrayList<>();
        for(int i = -7; i <= 7; i++){
            for(int j = -7; j <= 7; j++){
                Integer[] coordinate = new Integer[2];
                coordinate[0] = this.x + i;
                coordinate[1] = this.y + j;
                if(checkValidMove(coordinate[0], coordinate[1], board)){
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
        Tile[][] chessBoard = board.getBoard();
        if(diffY == 0 && diffX == 0){
            return false;
        }
        if(diffX == 0){
            for(int i = 1; i < diffY; i++){
                if(y > this.y && chessBoard[this.y+i][this.x].getPiece() != null){
                    return false;
                } else if (y < this.y && chessBoard[this.y-i][this.x].getPiece() != null) {
                    return false;
                }
            }
        } else if (diffX > 0 && diffY == 0) {
            for(int i = 1; i < diffX; i++){
                if(x > this.x && chessBoard[this.y][this.x+i].getPiece() != null){
                    return false;
                } else if (x < this.x && chessBoard[this.y][this.x-i].getPiece() != null) {
                    return false;
                }
            }
        }
        else{
            if(diffX != diffY){
                return false;
            }
            for(int i = 1; i < diffX; i++){
                if(x > this.x && y > this.y && chessBoard[this.y+i][this.x+i].getPiece() != null){
                    return false;
                } else if (x < this.x && y < this.y && chessBoard[this.y-i][this.x-i].getPiece() != null) {
                    return false;
                } else if (x > this.x && y < this.y && chessBoard[this.y-i][this.x+i].getPiece() != null) {
                    return false;
                } else if (x < this.x && y > this.y && chessBoard[this.y+i][this.x-i].getPiece() != null) {
                    return false;
                }
            }
        }
        if(chessBoard[y][x].getPiece() != null){
            Piece p = chessBoard[y][x].getPiece();
            return this.checkIsWhite() != p.checkIsWhite();
        }
        else{
            return true;
        }
    }
}
