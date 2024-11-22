import game.Board;
import game.Player;
import piece.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        Player white = board.getWhite();
        Player black = board.getBlack();
        //board.drawBoard();
        Scanner scanner = new Scanner(System.in);
        int x = 0;
        int y = 0;
        Piece p;
        boolean whiteTurn = true;
        while(x >= 0 && y >= 0){
            board.drawBoard();
            if(board.checkIfCheckMate(white) || board.checkIfCheckMate(black)){
                if(whiteTurn){
                    System.out.println("Check mate! Black wins!");
                    break;
                }
                else{
                    System.out.println("Check mate! White wins!");
                    break;
                }
            }
            int blackX = -1;
            int blackY = -1;
            Integer[] move = new Integer[5];
            if(whiteTurn) {
                x = scanner.nextInt();
                y = scanner.nextInt();
                p = board.getPiece(x, y);
            }
            else if(board.getBlack().checkIsAI()){
                //Integer[] coordinate = new Integer[2];
                //coordinate[0] = 0;
                //coordinate[1] = 0;
                //Piece piece = black.getPieces().get("BlackKing");
                ArrayList<Integer[]> moves = new ArrayList<>();
                int bestMove = black.minimax(5, true, board, white, moves, 5);
                //int max = Integer.MIN_VALUE;
                System.out.println(bestMove);
                for(Integer[] theMove : moves){
                    System.out.println(Arrays.toString(theMove));
                    //Piece bp = board.getPiece(theMove[0], theMove[1]);
                    if(theMove[4] == bestMove){
                        move = theMove;
                        //break;
                        //max = theMove[4];
                    }
                }
                if(board.checkIfCheckMate(black)){
                    System.out.println("Check mate! White wins!");
                    break;
                }
                blackX = move[0];
                blackY = move[1];
                p = board.getPiece(blackX, blackY);
            }
            else{
                x = scanner.nextInt();
                y = scanner.nextInt();
                p = board.getPiece(x, y);
            }
            if(p == null){
                System.out.println("Out of bounds!");
                continue;
            }
            if(whiteTurn != p.checkIsWhite()){
                System.out.println("Wrong color piece");
                continue;
            }
            if(whiteTurn) {
                x = scanner.nextInt();
                y = scanner.nextInt();
            } else if (!board.getBlack().checkIsAI()) {
                x = scanner.nextInt();
                y = scanner.nextInt();
            }
            boolean validMove;
            if(whiteTurn) {
                validMove = board.movePiece(white, p, x, y);
                //System.out.println(p.getMovesTaken());
            } else if (!board.getBlack().checkIsAI()) {
                validMove = board.movePiece(black, p, x, y);
            } else{
                validMove = board.movePiece(black, p, move[2], move[3]);
            }
            if(!validMove){
                if(whiteTurn && board.checkIfCheckMate(white)){
                    System.out.println("Check mate! Black wins!");
                    break;
                }
                else if(!whiteTurn && board.checkIfCheckMate(black)){
                    System.out.println("Check mate! White wins!");
                    break;
                }
                System.out.println("Invalid Move");
                continue;
            }
            whiteTurn = !whiteTurn;
        }
        board.drawBoard();
    }
}