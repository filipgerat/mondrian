import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    /** WIDTH and HEIGHT of the Board */
    public final int WIDTH = 200;
    public final int HEIGHT = 200;

    /*fg: Timer */
    private Timer timer;

    /* Progress to win */
    private double percent;
    private boolean over=false;

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

    /** Colors for the fields. //andreas */
    public final static Color[] COLORS = new Color[]{ Color.RED, Color.YELLOW, Color.CYAN, Color.BLACK, Color.LIGHT_GRAY };
    private int colorIterator = 0;
    //andreas
    Color[][] colorTable = new Color[WIDTH][HEIGHT];

    /* The moveable components of the game */
    private LinkedList<Moveable> moveables = new LinkedList<Moveable>();

    /* The drawable components */
    private LinkedList<Paintable> paintables = new LinkedList<Paintable>();

    /*/fg*/

    /**
     * Returns the X-coordinate of the Player
     * @return x, or 0 if there is no player.
     */
    public int getPlayerX() {
        if( moveables.isEmpty() ) return 0;
        return moveables.get(0).getX();
    }

    /**
     * Returns the Y-coordinate of the Player
     * @return y, or 0 if there is no player.
     */
    public int getPlayerY() {
        if( moveables.isEmpty() ) return 0;
        return moveables.get(0).getY();
    }

    /**
     * Constructor for the Board.
     */
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
        Player p = new Player(this, 0, 0, 12, Color.blue);
        moveables.offer(p);
        paintables.offer(p);

        /* Initialize KeyListener */
        setFocusable(true);
        addKeyListener(new MondrianKeyListener(p));

        /* Initialize Enemy */
        Enemy e = new Enemy(this, (int)Math.round(Math.random() * WIDTH - 2) + 1, (int)Math.round(Math.random() * HEIGHT - 2) + 1, 8, Color.red);
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

    /**
     * Timer Task.
     * Will check if fields take more than 80% of the board -> Player wins.
     * Will also check collision between player and enemy. (Player loses)
     * Will repaint the board.
     * @param e The ActionEvent from the KeyListener.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if( percent>=0.8 && !over){
            gameOver(); //andreas
        }

        /* Move the objects */
        for (Moveable m: moveables) {
            m.move();
            if(m.getClass().getCanonicalName().equalsIgnoreCase("Enemy")) {
                if( Ball.isCollided( moveables.get(0), m )) { //andreas: Check collision between enemy and player
                    //if players center is in a field or on edge, player is secure.
                    if( theBoard[moveables.get(0).getX()][moveables.get(0).getY()] != FieldState.COLORED ) gameOver();
                }
            }
        }
        repaint();
    }
    /*/fg*/

    /**
     * Draws the board.
     * Paints all the fields, lines and balls (player and enemy).
     * This method will be called automatically by Swing, whenever it has time for it.
     * Do not call this method manually.
     * You can try to achieve a "manual" call by calling repaint();
     * @param g The graphics component of the board.
     */
    public void paintComponent( Graphics g ) {

        /* Paint the board */
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i<WIDTH; i++){ //line for line
            for (int j = 0; j<HEIGHT; j++){ //column for column in the i-th line
                switch (theBoard[i][j]){
                    case LINE:
                        g2.setPaint(COLORS[(colorIterator+1) % COLORS.length]);//andreas
                        break;
                    case COLORED:
                        g2.setPaint(colorTable[i][j]); //andreas
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
        g2.setPaint(Color.PINK);
        g2.drawString(String.format("%.1f / 80%%", percent * 100), WIDTH - 55, 10);
    }

    /** Game over function
     * Stops the timer and outputs a win or a lost-dialog.
     * Ensures that multiple calls of this method will result in only one output dialog.
     */
    public void gameOver(){
        timer.stop();
        if( !over ) {
            over = true;
            if (percent >= 0.8) JOptionPane.showMessageDialog(this, "Gewonnen!");
            else JOptionPane.showMessageDialog(this, "Haha! Verloren!");
        }
    }

    /** Fill the board after closing a teritory.
     * This paints the fields. Rotates between 5 different colors for each new field.
     * Automatically paints the field, where the enemy is NOT.
     */
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
        colorIterator++; //set color for next field //andreas
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                switch (theBoard[i][j]) {
                    case UNCOLORED:
                        theBoard[i][j] = FieldState.COLORED;
                        colorTable[i][j] = COLORS[colorIterator % COLORS.length]; //andreas
                        break;
                    case LINE:
                        theBoard[i][j] = FieldState.COLORED;
                        colorTable[i][j] = COLORS[colorIterator % COLORS.length]; //andreas
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

}
