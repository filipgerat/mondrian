import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.*;

public class Board extends JPanel implements ActionListener {

    private final int WIDTH = 200;
    private final int HEIGHT = 200;

    /*fg: the two dimensional array to store the board values
     * STATES OF FIELD: */
    private enum FieldState {
        COLORED, LINE, UNCOLORED, DONT_COLOR
    }
    /* The main player Board */
    private FieldState[][] theBoard;

    /* The moveable components of the game */
    private LinkedList<Moveable> moveables = new LinkedList<Moveable>();

    /* The drawable components */
    private LinkedList<Paintable> paintables = new LinkedList<Paintable>();

    /*/fg*/

    public Board() {
        super();

        /*fg: initialize theBoard */
        theBoard = new FieldState[WIDTH][];
        for(FieldState[] column : theBoard){
            column = new FieldState[HEIGHT];
            for(FieldState i : column){
                i = FieldState.UNCOLORED;
            }
        }

        /* Initialize player: */
        Player p = new Player(this, 10, 10, 8, Color.blue);
        moveables.offer(p);
        paintables.offer(p);

        /* Initialize KeyListener */
        setFocusable(true);
        addKeyListener(new MondrianKeyListener(p));

        /* Initialize Enemy */
        Enemy e = new Enemy(this, (int)Math.round(Math.random() * WIDTH), (int)Math.round(Math.random() * HEIGHT), 3, Color.red);
        moveables.offer(e);
        paintables.offer(e);

        /* Start the timer */
        Timer timer = new Timer(18, this);
        timer.start();


        /*/fg*/


        setBackground(Color.WHITE);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDoubleBuffered(true);
    }

    /*fg: Timer Task */
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    /*/fg*/

    /*fg: Drawing */
    public void paintComponent( Graphics g ) {
        /* Move the objects */
        for (Moveable m: moveables) {
            m.move();
        }

        /* Paint the objects */
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (Paintable p: paintables){
            p.paintComponent(g2);
        }
    }

    /* Helpers for bounds detection in movement functions */
    public int getHeight(){
        return HEIGHT;
    }
    public int getWidth(){
        return WIDTH;
    }
    /*/fg*/


}
