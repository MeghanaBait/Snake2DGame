import javax.swing.*;

public class SnakeGame extends JFrame {
    Board board;
    SnakeGame(){
        board = new Board();
        add(board);
        //no Scaling of window
        pack();
        setResizable(false);
        setVisible(true);
    }
    public static void main(String[] args) {
       //initialize Snake Game
       SnakeGame snakeGame = new SnakeGame();
    }
}