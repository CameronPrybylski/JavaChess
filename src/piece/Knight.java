package piece;
import game.Board;
import game.Tile;

import java.util.ArrayList;

public class Knight extends Piece{

    public Knight(boolean white, String name){
        super(white, name);
        this.type = "Knight";
        this.value = 2;
    }

    @Override
    public ArrayList<Integer[]> pieceMovement(Board board){
        ArrayList<Integer[]> moves = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            int newX = -10;
            int newY = -10;
            if(i == 0){
                newY = this.y + 2;
            } else if (i == 1) {
                newY = this.y - 2;
            } else if(i == 2){
                newX = this.x + 2;
            } else {
                newX = this.x - 2;
            }
            for(int j = 0; j < 2; j++){
                if(newY != -10){
                    if(j < 1)
                        newX = this.x + 1;
                    else{
                        newX = this.x - 1;
                    }
                }
                else{
                    if(j < 1)
                        newY = this.y + 1;
                    else{
                        newY = this.y - 1;
                    }
                }
                Integer[] coordinate = new Integer[2];
                coordinate[0] = newX;
                coordinate[1] = newY;
                if(checkValidMove(newX, newY, board))
                    moves.add(coordinate);
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
        if(diffX > 2 || diffY > 2 || (Math.abs(diffX - diffY) != 1) || diffY < 1 || diffX < 1){
            return false;
        }
        Tile[][] chessBoard = board.getBoard();
        if(chessBoard[y][x].getPiece() != null){
            Piece p = chessBoard[y][x].getPiece();
            return p.checkIsWhite() != this.checkIsWhite();
        }
        else{
            return true;
        }

    }

    @Override
    public ArrayList<String> getPathToKing(Piece king, Board board){
        ArrayList<String> path = new ArrayList<>();
        String coordinate = this.x + " " + this.y;
        path.add(coordinate);
        return path;
    }
}
