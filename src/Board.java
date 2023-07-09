import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    //board dimensions
    int boardHeight = 400;
    int boardWidth = 400;
    //snake properties
    int MAX_DOTS = 1600;
    int DOT_SIZE = 10;
    int DOTS;
    int x[] = new int[MAX_DOTS];
    int y[] = new int[MAX_DOTS];

    //apple properties
    int appleX, appleY;
    Board(){
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);

    }

    //initialize game
    public void initGame(){
        DOTS = 3;
        //Initialize snake Position
        x[0] = 50;
        y[0] = 50;
        for (int i = 0; i < DOTS; i++) {
            x[i] = x[0]+DOT_SIZE;
            y[i] = y[0];
        }

        //Initializing apple position
        appleX = 150;
        appleY = 150;
    }
}
