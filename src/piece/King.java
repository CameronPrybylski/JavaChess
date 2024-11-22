package piece;
import game.Board;
import game.Tile;

import java.util.ArrayList;
import java.util.HashMap;

public class King extends Piece{

    public King(boolean white, String name){
        super(white, name);
        this.type = "King";
        this.value = 10;
    }

    public ArrayList<Integer[]> pieceMovement(Board board) {
        ArrayList<Integer[]> moves = new ArrayList<>();
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                Integer[] coordinate = new Integer[2];
                coordinate[0] = this.x + i;
                coordinate[1] = this.y + j;
                if(checkValidMove(coordinate[0], coordinate[1], board)){
                    moves.add(coordinate);
                }
            }
        }
        for(int i = -2; i <= 2; i+=4){
            Integer[] coordinate = new Integer[2];
            coordinate[0] = this.x + i;
            coordinate[1] = this.y;
            if(checkValidMove(coordinate[0], coordinate[1], board)){
                moves.add(coordinate);
            }
        }
        return moves;
    }

    @Override
    public int getPositionValue(){

        int positionValue = 0;
        if(x < 4){
            int xScore = 7 - x - 4;
            positionValue += x;
        }
        if(y < 4){
            int yScore = 7 - y - 4;
            positionValue += y;
        }
        if(x >= 4){
            int xScore = x - 4;
            positionValue += xScore;
        }
        if(y >= 4){
            int yScore = y - 4;
            positionValue += yScore;
        }
        return positionValue;

    }

    public boolean moveToCheck(int x, int y, Board board){
        HashMap<String, Piece> enemyPieces;
        Piece p = board.getPiece(x, y);
        if(p != null){
            board.getBoard()[y][x].setPiece(null);
        }
        if(this.checkIsWhite()){
            enemyPieces = board.getBlackPieces();
        }
        else{
            enemyPieces = board.getWhitePieces();
        }
        for(String piece : enemyPieces.keySet()){
            if(!enemyPieces.get(piece).isCaptured() && enemyPieces.get(piece).checkValidMove(x, y, board)){
                board.getBoard()[y][x].setPiece(p);
                return true;
            }
        }
        board.getBoard()[y][x].setPiece(p);
        return false;
    }

    public boolean canCastle(int x, int y, Board board){
        if(isWhite && this.x != 4 && this.y != 0){
            return false;
        } else if (!isWhite && this.x != 4 && this.y != 7) {
            return false;
        }
        if(isWhite && board.getWhite().getCheck() || (!isWhite && board.getBlack().getCheck())){
            return false;
        }

        //Check if King and Rook have not moved
        Piece rook;
        boolean right = x > this.x;
        int rookX;
        if(right){
            rookX = 7;
        } else{
            rookX = 0;
        }
        if(board.getBoard()[y][rookX].getPiece() == null || !board.getBoard()[y][rookX].getPiece().getType().equals("Rook")){
            return false;
        }
        rook = board.getBoard()[y][rookX].getPiece();
        if(rook.isWhite != this.isWhite || rook.movesTaken > 0 || this.movesTaken > 0){
            return false;
        }
        HashMap<String, Piece> enemyPieces;
        if(isWhite){
            enemyPieces = board.getBlackPieces();
        }
        else{
            enemyPieces = board.getWhitePieces();
        }

        for(int i = 1; i <= 2; i++){
            int move;
            if(right){
                move = i;
            }
            else{
                move = i * -1;
            }
            if(board.getBoard()[y][this.x+move].getPiece() != null){
                return false;
            }
            for(String p : enemyPieces.keySet()){
                Piece piece = enemyPieces.get(p);
                if(piece.checkValidMove(this.x+move, y, board)){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean checkValidMove(int x, int y, Board board) {
        if(x > 7 || x < 0 || y > 7 || y < 0){
            return false;
        }
        int diffX = Math.abs(x - this.x);
        int diffY = Math.abs(y - this.y);
        Tile[][] chessBoard = board.getBoard();
        if(diffX == 2 && diffY == 0){
            return canCastle(x, y, board);
        }
        if((diffY > 1 || diffX > 1) || (diffX == 0 && diffY == 0)){
            return false;
        }
        //Add check if King is moving into check
        if(this.moveToCheck(x, y, board)){
            return false;
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
