import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private final int WIDTH = 200;
    private final int HEIGHT = 200;

    /*fg: Timer */
    private Timer timer;

    /* Progress to win */
    private double percent;

    /* Main colors */
    private final Color PLAYER_COLOR = Color.yellow;
    private final Color BG_COLOR = Color.white;

    /* the two dimensional array to store the board values
     * STATES OF FIELD: */
     public enum FieldState {
        COLORED, LINE, UNCOLORED, DONT_COLOR
    }
    /* The main player Board */
    public FieldState[][] theBoard;

    //TODO: implement a Color[WIDTH][HEIGHT] table for the different colors of territories

    /* The moveable components of the game */
    private LinkedList<Moveable> moveables = new LinkedList<Moveable>();

    /* The drawable components */
    private LinkedList<Paintable> paintables = new LinkedList<Paintable>();

    /*/fg*/

    public Board() {
        super();

        /*fg: initialize theBoard -- bounds*/
        theBoard = new FieldState[WIDTH][];
        for(int i = 0; i < WIDTH; i++){
            theBoard[i] = new FieldState[HEIGHT];
            for(int j = 0; j < HEIGHT; j++){
                theBoard[i][j] = FieldState.UNCOLORED;
            }
            theBoard[i][0] = FieldState.COLORED;
            theBoard[i][HEIGHT - 1] = FieldState.COLORED;
        }
        for (int i = 0; i < WIDTH; i++){
           theBoard[0][i] = FieldState.COLORED;
           theBoard[WIDTH - 1][i] = FieldState.COLORED;
        }

        /* Initialize player: */
        Player p = new Player(this, 0, 0, 8, Color.blue);
        moveables.offer(p);
        paintables.offer(p);

        /* Initialize KeyListener */
        setFocusable(true);
        addKeyListener(new MondrianKeyListener(p));

        /* Initialize Enemy */
        Enemy e = new Enemy(this, (int)Math.round(Math.random() * WIDTH - 2) + 1, (int)Math.round(Math.random() * HEIGHT - 2) + 1, 3, Color.red);
        moveables.offer(e);
        paintables.offer(e);

        /* Start the timer */
        timer = new Timer(18, this);
        timer.start();


        /*/fg*/


        setBackground(Color.WHITE);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDoubleBuffered(true);
    }

    /*fg: Timer Task */
    @Override
    public void actionPerformed(ActionEvent e) {

        //TODO: check if percent > 80, then gameOver()

        /* Move the objects */
        for (Moveable m: moveables) {
            m.move();
        }
        repaint();
    }
    /*/fg*/

    /*fg: Drawing */
    public void paintComponent( Graphics g ) {

        /* Paint the board */
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i<WIDTH; i++){
            for (int j = 0; j<HEIGHT; j++){
                switch (theBoard[i][j]){
                    case LINE:
                    case COLORED:
                        // TODO: Find color in the color table and setPaint.
                        g2.setPaint(PLAYER_COLOR);
                        break;
                    default:  g2.setPaint(BG_COLOR); break;
                }
                g2.drawLine(i,j,i,j);
            }
        }
        /* Paint the objects */
        for (Paintable p: paintables){
            p.paintComponent(g2);
        }
        /* Paint progress */
        g2.drawString(String.format("%.1f / 80%%", percent * 100), WIDTH - 55, 10);
    }

    /* Helpers for bounds detection in movement functions */
    public int getHeight(){
        return HEIGHT;
    }
    public int getWidth(){
        return WIDTH;
    }

    /* Game over function */
    public void gameOver(){
        timer.stop();

        //TODO: write a proper gameOver function, check if percent > 80, if yes, do a winning screen, if no make a losing screen
    }

    /* Fill the board after closing a teritory */
    public void fillProper() {
        /* Get the enemies, and iteratively mark the territories, in which they are */
        for (Moveable e : moveables) {
            if (e.getClass() == Enemy.class) {
                LinkedList<Point> q = new LinkedList<Point>();
                q.offer(new Point(e.getX(), e.getY()));
                Point p;
                while ((p = q.poll()) != null) {
                    if (theBoard[p.x][p.y] == FieldState.UNCOLORED) {
                        theBoard[p.x][p.y] = FieldState.DONT_COLOR;
                        if (theBoard[p.x + 1][p.y] == FieldState.UNCOLORED) q.offer(new Point(p.x + 1, p.y));
                        if (theBoard[p.x - 1][p.y] == FieldState.UNCOLORED) q.offer(new Point(p.x - 1, p.y));
                        if (theBoard[p.x][p.y + 1] == FieldState.UNCOLORED) q.offer(new Point(p.x, p.y + 1));
                        if (theBoard[p.x][p.y - 1] == FieldState.UNCOLORED) q.offer(new Point(p.x, p.y - 1));
                    }
                }

            }
        }
        /* Recalculate progress */
        double colored = 0;
        /* Color all the rest of points, and unmark the territories */
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                //TODO: set a color, random or create a sequence
                switch (theBoard[i][j]) {
                    case UNCOLORED:
                        theBoard[i][j] = FieldState.COLORED;
                        //TODO: fill the corresponding pixel in the color table
                        break;
                    case LINE:
                        theBoard[i][j] = FieldState.COLORED;
                        //TODO: fill the corresponding pixel in the color table
                        break;
                    case DONT_COLOR:
                        theBoard[i][j] = FieldState.UNCOLORED;
                        break;
                    default:
                        break;
                }
                if (theBoard[i][j] == FieldState.COLORED) colored++;
            }
        }
        percent = colored / (WIDTH * HEIGHT);
    }


    /*/fg*/


}
