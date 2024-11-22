package piece;
import game.Board;
import game.Tile;

import java.util.ArrayList;

public abstract class Piece {

    protected boolean isWhite;

    protected boolean isCaptured;

    protected int x;

    protected int y;

    protected String name;

    protected String type;

    protected boolean firstMove;

    protected int value;

    protected int movesTaken;

    public Piece(boolean isWhite, String name){
        this.isWhite = isWhite;
        this.isCaptured = false;
        this.name = name;
        this.firstMove = true;
        this.movesTaken = 0;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public String getName(){
        return this.name;
    }

    public void addMoveTaken(){
        this.movesTaken++;
    }

    public void removeMoveTaken(){
        this.movesTaken--;
    }

    public int getMovesTaken(){
        return this.movesTaken;
    }

    public int getValue(){
        return this.value;
    }

    public int getPositionValue(){
        int positionValue = 0;
        if(x > 1 && x < 6){
            positionValue += 1;
        }
        if(y > 1 && y < 6){
            positionValue += 1;
        }
        return positionValue;
    }

    public boolean checkIsWhite(){
        return this.isWhite;
    }

    public String getType(){
        return this.type;
    }

    public abstract ArrayList<Integer[]> pieceMovement(Board board);

    public abstract boolean checkValidMove(int x, int y, Board board);

    public void setCaptured(){
        this.isCaptured = true;
    }

    public void setNotCaptured(){
        this.isCaptured = false;
    }

    public boolean isCaptured(){
        return this.isCaptured;
    }

    public boolean isFirstMove(){
        return firstMove;
    }

    public void firstMoveTaken(){
        this.firstMove = false;
    }

    public void firstMoveNotTaken(){
        this.firstMove = true;
    }

    public boolean canAttackKing(Piece king, Board board){
        if(king != null && king.checkIsWhite() != this.checkIsWhite()){
            int kingX = king.getX();
            int kingY = king.getY();
            return this.checkValidMove(kingX, kingY, board);
        }
        else{
            return false;
        }
    }

    public ArrayList<String> getPathToKing(Piece king, Board board){
        ArrayList<String> path = new ArrayList<>();
        int kingX = king.getX();
        int kingY = king.getY();
        int diffX = Math.abs(kingX - this.x);
        int diffY = Math.abs(kingY - this.y);
        int slope;
        Tile[][] chessBoard = board.getBoard();
        if(diffX == 0){
            slope = -1000;
            for(int i = 0; i < diffY; i++){
                String coordinate;
                if(kingY > this.y){
                    coordinate = this.x + " " + (this.y + i);
                }
                else{
                    coordinate = this.x + " " + (this.y - i);
                }
                path.add(coordinate);
            }

        } else if (diffY == 0) {
            slope = 0;
            for(int i = 0; i < diffX; i++){
                String coordinate;
                if(kingX > this.x){
                    coordinate = (this.x+i) + " " + this.y;
                }
                else{
                    coordinate = (this.x-i) + " " + (this.y - i);
                }
                path.add(coordinate);
            }

        }
        else{
            slope = (kingY - this.y) / (kingX - this.x);
            for(int i = 0; i < diffX; i++){
                String coordinate = "";
                if(kingX > this.x && kingY > this.y){
                    coordinate = (this.x+i) + " " + (this.y+i);
                }
                else if(kingX > this.x && kingY < this.y){
                    coordinate = (this.x+i) + " " + (this.y - i);
                }
                else if (kingX < this.x && kingY > this.y) {
                    coordinate = (this.x-i) + " " + (this.y+i);
                }
                else if(kingX < this.x && kingY < this.y){
                    coordinate = (this.x-i) + " " + (this.y-i);
                }
                path.add(coordinate);
            }

        }
        return path;

    }
}
