package game;

import piece.Piece;

public class Tile {

    private Piece piece;

    public Tile(){
        piece = null;
    }

    public void setPiece(Piece piece){
        this.piece = piece;
    }

    public Piece getPiece(){
        return this.piece;
    }
}
