package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import piece.*;

public class Player {

    protected boolean isWhite;

    protected HashMap<String, Piece> pieces;

    protected boolean check;

    protected boolean isAI;

    public Player(boolean isWhite, boolean isAI){
        this.isWhite = isWhite;
        this.check = false;
        this.isAI = isAI;
    }

    public boolean checkIsWhite(){
        return this.isWhite;
    }

    public boolean checkIsAI(){
        return this.isAI;
    }

    public void setPieces(HashMap<String, Piece> pieces){
        this.pieces = pieces;
    }

    public HashMap<String, Piece> getPieces(){
        return this.pieces;
    }

    public void setCheck(boolean check){
        this.check = check;
    }

    public boolean getCheck(){
        return this.check;
    }

    public int evaluateBoard(Board board, Player opposingPlayer){
        int score = 0;
        HashMap<String, Piece> maxPieces;
        HashMap<String, Piece> minPieces;
        if(this.checkIsAI()){
            maxPieces = this.getPieces();
            minPieces = opposingPlayer.getPieces();
            if(getCheck()){
                score -= 5;
            }
        }
        else{
            maxPieces = opposingPlayer.getPieces();
            minPieces = this.getPieces();
            if(getCheck()){
                score += 5;
            }
        }
        Piece opponentKing;
        if(isWhite){
            opponentKing = board.getBlackPieces().get("BlackKing");
        }
        else{
            opponentKing = board.getWhitePieces().get("WhiteKing");
        }
        for(String piece : maxPieces.keySet()){
            Piece p = maxPieces.get(piece);
            if(!p.isCaptured()){
                score += p.getValue();
                score += p.getPositionValue();
                if(p.canAttackKing(opponentKing, board)){
                    score += 5;
                }
            }
        }
        for(String piece : minPieces.keySet()) {
            Piece p = minPieces.get(piece);
            if (!p.isCaptured()) {
                score -= p.getValue();
                score -= p.getPositionValue();
                if(p.canAttackKing(opponentKing, board)){
                    score -= 5;
                }
            }
        }

        return score;
    }

    public HashMap<Piece, ArrayList<Integer[]>> generateLegalMoves(Board board){
        HashMap<Piece, ArrayList<Integer[]>> allPossibleMoves = new HashMap<>();
        for(String piece : pieces.keySet()) {
            Piece p = pieces.get(piece);
            if (!p.isCaptured()) {
                ArrayList<Integer[]> moves = p.pieceMovement(board);
                allPossibleMoves.put(p, moves);
            }
        }
        return allPossibleMoves;
    }

    public int minimax(int depth, boolean isMaximizingPlayer, Board board, Player player, ArrayList<Integer[]> scoresMoves, int actualDepth){
        if(depth == 0 || board.checkIfCheckMate(this) || board.checkIfCheckMate(player)){
            return evaluateBoard(board, player);
        }
        if(isMaximizingPlayer){
            HashMap<Piece, ArrayList<Integer[]>> allPossibleMoves = generateLegalMoves(board);
            int maxEval = Integer.MIN_VALUE;
            for(Piece piece : allPossibleMoves.keySet()){
                ArrayList<Integer[]> moves = allPossibleMoves.get(piece);
                for(Integer[] coordinate : moves){
                    Integer[] moveAndScore = new Integer[5];
                    int prevX = piece.getX();
                    int prevY = piece.getY();
                    Piece p = board.getPiece(coordinate[0], coordinate[1]);
                    boolean firstMove = piece.isFirstMove();
                    boolean validMove = board.movePiece(this, piece, coordinate[0], coordinate[1]);
                    if(!validMove){
                        continue;
                    }
                    int eval = player.minimax(depth - 1, false, board, this, scoresMoves, actualDepth);
                    board.undoMove(piece, p, prevX, prevY, coordinate[0], coordinate[1], firstMove);
                    moveAndScore[0] = piece.getX();
                    moveAndScore[1] = piece.getY();
                    moveAndScore[2] = coordinate[0];
                    moveAndScore[3] = coordinate[1];
                    moveAndScore[4] = eval;
                    if(eval > maxEval && depth == actualDepth){
                        scoresMoves.add(moveAndScore);
                    }
                    maxEval = Math.max(maxEval, eval);
                }
            }
            return maxEval;
        }
        else{
            HashMap<Piece, ArrayList<Integer[]>> allPossibleMoves = generateLegalMoves(board);
            int minEval = Integer.MAX_VALUE;
            for(Piece piece : allPossibleMoves.keySet()){
                ArrayList<Integer[]> moves = allPossibleMoves.get(piece);
                for(Integer[] coordinate : moves){
                    Integer[] moveAndScore = new Integer[5];
                    int prevX = piece.getX();
                    int prevY = piece.getY();
                    Piece p = board.getPiece(coordinate[0], coordinate[1]);
                    boolean firstMove = piece.isFirstMove();
                    boolean validMove = board.movePiece(this, piece, coordinate[0], coordinate[1]);
                    if(!validMove){
                        continue;
                    }
                    int eval = player.minimax(depth-1, true, board, this, scoresMoves, actualDepth);
                    board.undoMove(piece, p, prevX, prevY, coordinate[0], coordinate[1], firstMove);
                    moveAndScore[0] = piece.getX();
                    moveAndScore[1] = piece.getY();
                    moveAndScore[2] = coordinate[0];
                    moveAndScore[3] = coordinate[1];
                    moveAndScore[4] = eval;

                    minEval = Math.min(minEval, eval);
                }
            }
            return minEval;
        }
    }


}
