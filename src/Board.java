import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
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
    //Images
    Image body, head, apple;
    Timer timer;
    int DELAY = 200;
    boolean leftDirection = true;
    boolean rightDirection = false;
    boolean upDirection = false;
    boolean downDirection = false;
    boolean inGame = true;
    Board(){
        TAdapter tAdapter = new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);
        initGame();
        loadImages();
    }

    //initialize game
    public void initGame(){
        DOTS = 3;
        //Initialize snake Position
        x[0] = 250;
        y[0] = 250;
        for (int i = 1; i < DOTS; i++) {
            x[i] = x[0]+DOT_SIZE*i;
            y[i] = y[0];
        }

        //Initializing apple position
        locateApple();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    //add images to image object
    public void loadImages(){
        ImageIcon bodyIcon = new ImageIcon("src/Resources/dot.png");
        body = bodyIcon.getImage();

        ImageIcon headIcon = new ImageIcon("src/Resources/head.png");
        head = headIcon.getImage();

        ImageIcon appleIcon = new ImageIcon("src/Resources/apple.png");
        apple = appleIcon.getImage();
    }

    //draw images at snake and apple position
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }

    //draw image
    public void doDrawing(Graphics g){
        if(inGame){
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < DOTS; i++) {
                if(i == 0){
                    g.drawImage(head, x[0], y[0], this);
                }
                else{
                    g.drawImage(body, x[i], y[i], this);
                }
            }
        }

        else{
            gameOver(g);
            timer.stop();
        }
    }

    //Randomize apple position
    public void locateApple(){
        appleX = ((int)(Math.random()*39))*DOT_SIZE;
        appleY = ((int)(Math.random()*39))*DOT_SIZE;
    }

    // check collision with border and body
    public  void checkCollisions(){
        //Collision with body
        for (int i = 1; i < DOTS; i++) {
            if(i > 4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
        }
        //Collision with borders
        if(x[0] < 0){
            inGame = false;
        }
        if(x[0] >= boardWidth){
            inGame = false;
        }
        if(y[0] < 0){
            inGame = false;
        }
        if(y[0] >= boardHeight){
            inGame = false;
        }
    }

    public void gameOver(Graphics g){
        String msg = "Game Over";
        int score = (DOTS - 3)*100;
        String scoreMsg = "Score: "+ Integer.toString(score);
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fontMetrics = getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (boardWidth-fontMetrics.stringWidth(msg))/2,(boardHeight/4));
        g.drawString(scoreMsg, (boardWidth-fontMetrics.stringWidth(scoreMsg))/2,3*(boardHeight/4));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(inGame){
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    //make snake move
    public void move(){
        for (int i = DOTS - 1; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if(rightDirection){
            x[0] += DOT_SIZE;
        }
        if(upDirection){
            y[0] -= DOT_SIZE;
        }
        if(leftDirection){
            x[0] -= DOT_SIZE;
        }
        if(downDirection){
            y[0] += DOT_SIZE;
        }
    }

    //make snake eat food
    public void checkApple(){
        if(appleX == x[0] && appleY == y[0]){
            DOTS++;
            locateApple();
        }
    }

    //implement controls
    private  class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key = keyEvent.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !rightDirection){
                leftDirection = true;
                upDirection = false;
                downDirection =false;
            }

            if(key == KeyEvent.VK_RIGHT && !leftDirection){
                rightDirection = true;
                upDirection = false;
                downDirection =false;
            }

            if(key == KeyEvent.VK_UP && !downDirection){
                upDirection = true;
                leftDirection = false;
                rightDirection =false;
            }
            if(key == KeyEvent.VK_DOWN && !upDirection){
                downDirection = true;
                leftDirection = false;
                rightDirection =false;
            }
        }
    }
}
