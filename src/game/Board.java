package game;

import piece.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Board {

    private Tile[][] board;

    private HashMap<String, Piece> whitePieces;

    private HashMap<String, Piece> blackPieces;

    private Player white;

    private Player black;

    //private boolean blackCheck;

    //private boolean whiteCheck;

    public Board(){
        board  = new Tile[8][8];
        whitePieces = new HashMap<>();
        blackPieces = new HashMap<>();
        white = new Player(true, false);
        black = new Player(false, false);
        initializePieces();
        initializeBoard();
    }

    public Board(boolean whiteIsAI){
        board  = new Tile[8][8];
        whitePieces = new HashMap<>();
        blackPieces = new HashMap<>();
        if(whiteIsAI){
            white = new Player(true, true);
            black = new Player(false, false);
        }
        else{
            white = new Player(true, false);
            black = new Player(false, true);
        }
        initializePieces();
        initializeBoard();
    }

    public Board(String both){
        board  = new Tile[8][8];
        whitePieces = new HashMap<>();
        blackPieces = new HashMap<>();
        white = new Player(true, true);
        black = new Player(false, true);
        initializePieces();
        initializeBoard();
    }

    private void initializePieces(){
        for(int i = 0; i < 15; i++){
            if(i < 8){
                String wp = "WhitePawn" + (i+1);
                String bp = "BlackPawn" + (i+1);
                Piece whitePawn = new Pawn(true, wp);
                Piece blackPawn = new Pawn(false, bp);
                whitePieces.put(wp, whitePawn);
                blackPieces.put(bp, blackPawn);
            }
            else if(i < 10){
                String wp = "WhiteRook" + (i-8+1);
                String bp = "BlackRook" + (i-8+1);
                Piece whiteRook = new Rook(true, wp);
                Piece blackRook = new Rook(false, bp);
                whitePieces.put(wp, whiteRook);
                blackPieces.put(bp, blackRook);
            }
            else if(i < 12){
                String wp = "WhiteKnight" + (i-10+1);
                String bp = "BlackKnight" + (i-10+1);
                Piece whiteKnight = new Knight(true, wp);
                Piece blackKnight = new Knight(false, bp);
                whitePieces.put(wp, whiteKnight);
                blackPieces.put(bp, blackKnight);
            }
            else if(i < 14){
                String wp = "WhiteBishop" + (i-12+1);
                String bp = "BlackBishop" + (i-12+1);
                Piece whiteBishop = new Bishop(true, wp);
                Piece blackBishop = new Bishop(false, bp);
                whitePieces.put(wp, whiteBishop);
                blackPieces.put(bp, blackBishop);
            }
            else{
                String wq = "WhiteQueen";
                String bq = "BlackQueen";
                String wk = "WhiteKing";
                String bk = "BlackKing";
                Piece whiteQueen = new Queen(true, wq);
                Piece whiteKing = new King(true, wk);
                Piece blackQueen = new Queen(false, bq);
                Piece blackKing = new King(false, bk);
                whitePieces.put(wq, whiteQueen);
                blackPieces.put(bq, blackQueen);
                whitePieces.put(wk, whiteKing);
                blackPieces.put(bk, blackKing);
            }
        }
        white.setPieces(whitePieces);
        black.setPieces(blackPieces);
    }

    private void setPieces(String color, String pieceType, String pieceNum, int i, int j){
        Piece piece;
        board[i][j] = new Tile();
        if(!pieceType.equals("Not")){
            if (color.equals("White")) {
                piece = whitePieces.get(color + pieceType + pieceNum);
            } else {
                piece = blackPieces.get(color + pieceType + pieceNum);
            }
            piece.setX(j);
            piece.setY(i);
            board[i][j].setPiece(piece);
        }
    }

    private void initializeBoard(){
        String pieceNum = "1";
        String color;
        String pieceType;
        for(int i = 0; i < 8; i++){
            if(i < 2){
                color = "White";
            }
            else{
                color = "Black";
            }
            for(int j = 0; j < 8; j++){
                if(j <= 2){
                    pieceNum = "1";
                }
                if(j > 2){
                    pieceNum = "2";
                }
                if(i == 0 || i == 7) {
                    if ((j == 0 || j == 7)) {
                        pieceType = "Rook";
                    } else if (j == 1 || j == 6) {
                        pieceType = "Knight";
                    } else if (j == 2 || j == 5) {
                        pieceType = "Bishop";
                    } else if (j == 3) {
                        pieceType = "Queen";
                        pieceNum = "";
                    } else {
                        pieceType = "King";
                        pieceNum = "";
                    }
                }
                else if(i == 1 || i == 6){
                    pieceType = "Pawn";
                    pieceNum = Integer.toString(j+1);
                }
                else{
                    pieceType = "Not";
                }

                setPieces(color, pieceType, pieceNum, i, j);

            }
        }
    }

    public Tile[][] getBoard(){
        return this.board;
    }

    public HashMap<String, Piece> getWhitePieces(){
        return this.whitePieces;
    }

    public HashMap<String, Piece> getBlackPieces(){
        return this.blackPieces;
    }

    public Player getWhite(){
        return this.white;
    }

    public Player getBlack(){
        return this.black;
    }

    public void drawBoard(){
        for(int i = 7; i >= 0; i--){
            for(int j = 0; j < 8; j++){
                if(board[i][j].getPiece() != null){
                    System.out.print(board[i][j].getPiece().getName() + " ");
                }
                else{
                    System.out.print("|----------|");
                }
            }
            System.out.print("\n");
        }
    }

    public Piece getPiece(int x, int y){
        if(x > 7 || x < 0 || y > 7 || y < 0){
            return null;
        }
        return board[y][x].getPiece();
    }

    public ArrayList<Piece> getPiecesCanAttackKing(Player player, Piece king){
        ArrayList<Piece> piecesCanAttack = new ArrayList<>();
        for(String p : player.getPieces().keySet()){
            Piece piece = player.getPieces().get(p);
            if(!piece.isCaptured() && piece.canAttackKing(king, this)){
                piecesCanAttack.add(piece);
            }
        }
        return piecesCanAttack;
    }

    public boolean canProtectKing(Player player, Piece king, ArrayList<ArrayList<String>> paths){
        ArrayList<Piece> piecesCanProK = new ArrayList<>();
        for(String piece : player.getPieces().keySet()){
            Piece p = player.getPieces().get(piece);
            if(!p.isCaptured()){
                ArrayList<String> path = paths.getFirst();
                for(String coordinate : path){
                    int x = Character.getNumericValue(coordinate.charAt(0));
                    int y = Character.getNumericValue(coordinate.charAt(2));
                    if(p.checkValidMove(x, y, this)){
                        //System.out.println(p.getName() + " Can protect king");
                        return true;
                    }
                }
            }

        }
        return false;
    }

    //Check if check mate
    public boolean checkIfCheckMate(Player player){
        if(!player.getCheck()){
            return false;
        }
        Piece king;
        if(player.checkIsWhite()){
            king = whitePieces.get("WhiteKing");
        }
        else{
            king = blackPieces.get("BlackKing");
        }
        int kingX = king.getX();
        int kingY = king.getY();
        //Check if you can move king out of check
        for(int i = -1; i < 2; i++){
            for(int j = -1; j < 2; j++){
                if(king.checkValidMove(kingX+i, kingY+j, this)){
                    return false;
                }
            }
        }

        //Check if another piece can block king
        ArrayList<Piece> piecesCanAttack;
        if(king.checkIsWhite()){
            piecesCanAttack = getPiecesCanAttackKing(black, king);
        }
        else{
            piecesCanAttack = getPiecesCanAttackKing(white, king);
        }
        if(piecesCanAttack.size() > 1){
            return true;
        }
        ArrayList<ArrayList<String>> paths = new ArrayList<>();
        for(Piece p : piecesCanAttack){
            ArrayList<String> path = p.getPathToKing(king, this);
            paths.add(path);
        }

        boolean canGetAway = canProtectKing(player, king, paths);

        return !canGetAway;
    }

    public void castleRook(int prevX, int x, int y){
        boolean right = x > prevX;
        Piece rook;
        int rookX;
        int move;
        if(right){
            rookX = 7;
            move = 1;
        }
        else{
            rookX = 0;
            move = -1;
        }
        rook = board[y][rookX].getPiece();
        rook.setX(x-move);
        rook.setY(y);
        board[y][rookX].setPiece(null);
        board[y][x-move].setPiece(rook);
        rook.addMoveTaken();
    }

    public boolean movePiece(Player player, Piece piece, int x, int y){
        if(x > 7 || x < 0 || y > 7 || y < 0 || piece == null){
            return false;
        }
        if(piece.checkIsWhite() == player.checkIsWhite() && piece.checkValidMove(x, y, this)){
            boolean check;
            Piece capturedPiece = board[y][x].getPiece();
            if(board[y][x].getPiece() != null){
                board[y][x].getPiece().setCaptured();
            }
            if(piece.isFirstMove()){
                piece.firstMoveTaken();
            }
            board[y][x].setPiece(piece);
            int prevX = piece.getX();
            int prevY = piece.getY();
            board[prevY][prevX].setPiece(null);
            piece.setY(y);
            piece.setX(x);

            HashMap<String, Piece> allyPieces;
            HashMap<String, Piece> enemyPieces;
            Player enemy;
            Piece enemyKing;
            Piece allyKing;
            if(piece.checkIsWhite()){
                allyPieces = whitePieces;
                enemyPieces = blackPieces;
                enemy = black;
                enemyKing = blackPieces.get("BlackKing");
                allyKing = whitePieces.get("WhiteKing");
            }
            else{
                allyPieces = blackPieces;
                enemyPieces = whitePieces;
                enemy = white;
                enemyKing = whitePieces.get("WhiteKing");
                allyKing = blackPieces.get("BlackKing");
            }
            for(String piece1 : enemyPieces.keySet()){
                if(!enemyPieces.get(piece1).isCaptured() && enemyPieces.get(piece1).canAttackKing(allyKing, this)){
                    if(capturedPiece != null)
                        capturedPiece.setNotCaptured();
                    board[y][x].setPiece(capturedPiece);
                    piece.setX(prevX);
                    piece.setY(prevY);
                    board[prevY][prevX].setPiece(piece);
                    return false;
                }
            }
            if(piece.getValue() == 1 && piece.getY() == 0){
                int numOfQueens = 0;
                int numOfKnights = 0;
                for(String p : allyPieces.keySet()){
                    if(allyPieces.get(p).getType().equals("Queen")){
                        numOfQueens++;
                    }
                    if(allyPieces.get(p).getType().equals("Knight")){
                        numOfKnights++;
                    }
                }
                for(String p : allyPieces.keySet()){
                    Piece promotedPiece = allyPieces.get(p);
                    if(promotedPiece.getX() == piece.getX() && promotedPiece.getY() == piece.getY()){
                        int newX = piece.getX();
                        int newY = piece.getY();
                        String queen;
                        String knight;
                        Piece newPiece;
                        Piece newKnight;
                        if(piece.checkIsWhite()){
                            queen = "WhiteQueen";
                            knight = "WhiteKnight";
                        }
                        else{
                            queen = "BlackQueen";
                            knight = "BlackKnight";
                        }
                        newPiece = new Queen(piece.checkIsWhite(), queen);
                        newKnight = new Knight(piece.checkIsWhite(), knight);
                        newKnight.setX(newX);
                        newKnight.setY(newY);
                        String name;
                        if(newKnight.checkValidMove(enemyKing.getX(), enemyKing.getY(), this)){
                            newPiece = newKnight;
                            numOfKnights++;
                            name = knight + numOfKnights;
                        }
                        else{
                            numOfQueens++;
                            name = queen + numOfQueens;
                        }
                        newPiece.setY(newY);
                        newPiece.setX(newX);
                        board[newX][newY].setPiece(newPiece);
                        allyPieces.remove(p);
                        player.getPieces().remove(p);

                        allyPieces.put(name, newPiece);
                        player.getPieces().put(name, newPiece);
                        piece = newPiece;
                        break;
                    }
                }
            }
            player.setCheck(false);
            check = piece.canAttackKing(enemyKing, this);
            enemy.setCheck(check);
            piece.addMoveTaken();
            if(piece.getType().equals("King") && Math.abs(prevX - x) > 1){
                castleRook(prevX, x, y);
            }
            return true;
        }
        else{
            return false;
        }
    }

    public void undoMove(Piece piece1, Piece piece2, int prevX1, int prevY1, int prevX2, int prevY2, boolean firstMove){
        if(piece2 != null){
            piece2.setNotCaptured();
            piece2.setX(prevX2);
            piece2.setY(prevY2);
        }
        if(firstMove){
            piece1.firstMoveNotTaken();
        }
        int diffX = Math.abs(piece1.getX() - prevX1);
        boolean right = piece1.getX() > prevX1;
        if(piece1.getType().equals("King") && diffX == 2){
            undoCastling(piece1, prevX1, prevY1);
        }
        piece1.removeMoveTaken();
        piece1.setX(prevX1);
        piece1.setY(prevY1);
        board[prevY1][prevX1].setPiece(piece1);
        board[prevY2][prevX2].setPiece(piece2);
        if((piece1.checkIsWhite() && piece1.getY() == 1) || (!piece1.checkIsWhite() && piece1.getY() == 6)) {
            piece1.firstMoveNotTaken();
        }
    }

    public void undoCastling(Piece king, int kingPrevX, int kingPrevY){
        boolean right = king.getX() > kingPrevX;
        int rookPrevX;
        int rookNewX;
        Piece rook;
        if(right){
            rookPrevX = king.getX() - 1;
            rookNewX = 7;
        }
        else{
            rookPrevX = king.getX() + 1;
            rookNewX = 0;
        }
        rook = this.getPiece(rookPrevX, kingPrevY);
        rook.setX(rookNewX);
        rook.removeMoveTaken();
        this.getBoard()[kingPrevY][rookPrevX].setPiece(null);
        this.getBoard()[kingPrevY][rookNewX].setPiece(rook);
    }

}
