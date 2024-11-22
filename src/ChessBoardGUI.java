import javax.swing.*;
import java.awt.*;

import game.Board;
import game.Player;
import gui.ClickableJPanel;
import piece.Piece;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ChessBoardGUI {
    private JFrame frame;
    private JPanel chessBoardPanel;

    private JLabel[][] labels;

    private Board board;

    private Player white;

    private Player black;

    private boolean whiteTurn;

    private int pieceX;

    private int pieceY;

    private ArrayList<ClickableJPanel> move;

    public ChessBoardGUI() {
        frame = new JFrame("Chess Game");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        board = new Board(false);
        white = board.getWhite();
        black = board.getBlack();
        whiteTurn = true;

        labels = new JLabel[8][8];
        chessBoardPanel = new JPanel(new GridLayout(8, 8));

        pieceX = -1;
        pieceY = -1;

        move = new ArrayList<ClickableJPanel>();
    }

    public void initializeBoard(){
        chessBoardPanel = new JPanel(new GridLayout(8, 8));
        boolean white = true;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ClickableJPanel square = new ClickableJPanel(col, row);
                if (white) {
                    square.setBackground(Color.WHITE);
                } else {
                    square.setBackground(Color.DARK_GRAY);
                }
                Piece p = board.getBoard()[row][col].getPiece();
                JLabel piece = new JLabel();
                piece.setSize(80, 80);
                if(p != null) {
                    ImageIcon imageIcon = getImageIcon(p);
                    Image image;
                    Image scaledImage;
                    image = imageIcon.getImage();
                    scaledImage = image.getScaledInstance(piece.getWidth(), piece.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
                    piece.setIcon(scaledImageIcon);
                    piece.setHorizontalAlignment(JLabel.CENTER);
                    piece.setVerticalAlignment(JLabel.CENTER);
                }
                else{
                    piece.setIcon(null);
                }
                labels[row][col] = piece;
                if(white){
                    piece.setForeground(Color.BLACK);
                }
                else{
                    piece.setForeground(Color.WHITE);
                }
                square.add(piece);
                square.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        System.out.println("JPanel clicked!");
                        move.add(square);
                        handleUserClick(square);
                    }
                });
                chessBoardPanel.add(square);
                white = !white;
            }
            white = !white;
        }

        frame.add(chessBoardPanel);
        frame.setVisible(true);
        if(this.white.checkIsAI()){
            aiMove(this.white, black);
        }
    }

    public void updateBoard(int prevX, int prevY, int newX, int newY, Player player){
        boolean valid = board.movePiece(player, board.getPiece(prevX,prevY), newX, newY);
        System.out.println(prevX + " " + prevY);
        System.out.println(newX + " " + newY);
        if(valid){
            System.out.println("Valid");
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    Piece p = board.getBoard()[i][j].getPiece();
                    JLabel piece = labels[i][j];
                    piece.setSize(80, 80);
                    if(p != null) {
                        ImageIcon imageIcon = getImageIcon(p);
                        Image image = imageIcon.getImage();
                        Image scaledImage = image.getScaledInstance(piece.getWidth(), piece.getHeight(), Image.SCALE_SMOOTH);
                        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
                        piece.setIcon(scaledImageIcon);
                        piece.setHorizontalAlignment(JLabel.CENTER);
                        piece.setVerticalAlignment(JLabel.CENTER);
                    }
                    else{
                        piece.setIcon(null);
                        piece.setText("");
                    }
                }
            }
            whiteTurn = !whiteTurn;
        }
        String winner = "";
        boolean checkMate = false;
        if(board.checkIfCheckMate(white)) {
            winner = "Black";
            checkMate = true;
        }
        if(board.checkIfCheckMate(black)) {
            winner = "White";
            checkMate = true;
        }
        if(checkMate){
            checkMate(winner);
        }
        move.clear();
        chessBoardPanel.revalidate();
        chessBoardPanel.repaint();
        if(!whiteTurn && black.checkIsAI()){
            aiMove(black, white);
        } else if (whiteTurn && white.checkIsAI()) {
            aiMove(white, black);
        }
    }

    public ImageIcon getImageIcon(Piece p){
        String type = p.getType();
        return switch (type) {
            case "Pawn" -> getPawnImage(p);
            case "Knight" -> getKnightImage(p);
            case "Rook" -> getRookImage(p);
            case "Bishop" -> getBishopImage(p);
            case "Queen" -> getQueenImage(p);
            case "King" -> getKingImage(p);
            default -> null;
        };
    }

    public ImageIcon getPawnImage(Piece p){
        ImageIcon pawn = null;
        if(p.checkIsWhite()){
            pawn = new ImageIcon("/Users/cameronprzybylski/IdeaProjects/JavaChess/pieces-basic-png/white-pawn.png");
        }
        else{
            pawn = new ImageIcon("/Users/cameronprzybylski/IdeaProjects/JavaChess/pieces-basic-png/black-pawn.png");
        }
        return pawn;
    }

    public ImageIcon getKnightImage(Piece p){
        ImageIcon knight = null;
        if(p.checkIsWhite()){
            knight = new ImageIcon("/Users/cameronprzybylski/IdeaProjects/JavaChess/pieces-basic-png/white-knight.png");
        }
        else{
            knight = new ImageIcon("/Users/cameronprzybylski/IdeaProjects/JavaChess/pieces-basic-png/black-knight.png");
        }
        return knight;
    }

    public ImageIcon getRookImage(Piece p){
        ImageIcon rook = null;
        if(p.checkIsWhite()){
            rook = new ImageIcon("/Users/cameronprzybylski/IdeaProjects/JavaChess/pieces-basic-png/white-rook.png");
        }
        else{
            rook = new ImageIcon("/Users/cameronprzybylski/IdeaProjects/JavaChess/pieces-basic-png/black-rook.png");
        }
        return rook;
    }

    public ImageIcon getBishopImage(Piece p){
        ImageIcon bishop = null;
        if(p.checkIsWhite()){
            bishop = new ImageIcon("/Users/cameronprzybylski/IdeaProjects/JavaChess/pieces-basic-png/white-bishop.png");
        }
        else{
            bishop = new ImageIcon("/Users/cameronprzybylski/IdeaProjects/JavaChess/pieces-basic-png/black-bishop.png");
        }
        return bishop;
    }

    public ImageIcon getQueenImage(Piece p){
        ImageIcon queen = null;
        if(p.checkIsWhite()){
            queen = new ImageIcon("/Users/cameronprzybylski/IdeaProjects/JavaChess/pieces-basic-png/white-queen.png");
        }
        else{
            queen = new ImageIcon("/Users/cameronprzybylski/IdeaProjects/JavaChess/pieces-basic-png/black-queen.png");
        }
        return queen;
    }

    public ImageIcon getKingImage(Piece p){
        ImageIcon king = null;
        if(p.checkIsWhite()){
            king = new ImageIcon("/Users/cameronprzybylski/IdeaProjects/JavaChess/pieces-basic-png/white-king.png");
        }
        else{
            king = new ImageIcon("/Users/cameronprzybylski/IdeaProjects/JavaChess/pieces-basic-png/black-king.png");
        }
        return king;
    }



    public void checkMate(String winner){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Piece p = board.getBoard()[i][j].getPiece();
                JLabel label = labels[i][j];
                label.setText(winner + " Wins!");
            }
        }
    }

    public void aiMove(Player ai, Player nonAi){
        ArrayList<Integer[]> aiMoves = new ArrayList<>();
        Integer[] aiMove = new Integer[5];
        int move = ai.minimax(3, true, board, nonAi, aiMoves, 3);
        for(Integer[] moves : aiMoves){
            if(moves[4] == move){
                aiMove = moves;
            }
        }
        updateBoard(aiMove[0], aiMove[1], aiMove[2], aiMove[3], ai);
    }

    public int getX(){
        return this.pieceX;
    }

    public int getY(){
        return this.pieceY;
    }

    public ArrayList<ClickableJPanel> getMove(){
        return this.move;
    }

    public void handleUserClick(ClickableJPanel square){
        if(move.size() == 2){
            int x = this.getMove().get(0).getBoardX();
            int y = this.getMove().get(0).getBoardY();
            int newX = this.getMove().get(1).getBoardX();
            int newY = this.getMove().get(1).getBoardY();
            if(whiteTurn)
                this.updateBoard(x, y, newX, newY, white);
            else
                this.updateBoard(x, y, newX, newY, black);
        }
    }


    public static void main(String[] args) {
        ChessBoardGUI chessBoard = new ChessBoardGUI();
        chessBoard.initializeBoard();
    }
}
