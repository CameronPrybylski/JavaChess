package gui;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClickableJPanel extends JPanel {

    private int boardX;
    private int boardY;

    public ClickableJPanel(int x, int y) {
        this.boardX = x;
        this.boardY = y;
    }

    public ClickableJPanel() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("JPanel clicked!");
            }
        });
    }

    public Integer[] pieceXY(){
        Integer[] location = new Integer[2];
        location[0] = boardX;
        location[1] = boardY;
        return location;
    }

    public int getBoardX(){
        return this.boardX;
    }

    public int getBoardY(){
        return this.boardY;
    }



}