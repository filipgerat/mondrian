import java.awt.*;

/**
 * Created by Filip on 7/31/2015.
 *fg:*/
public class Ball implements Paintable {
    Board b;
    int x;
    int y;
    int dm;
    Color c;

    /**
     * Makes a new Ball
     * @param b The board, on which the ball will be painted.
     * @param x The x-coordinate on the board of the ball.
     * @param y The y-coordinate on the board of the ball.
     * @param dm The diameter of the ball.
     * @param c The color of the ball.
     */
    public Ball(Board b, int x, int y, int dm, Color c){
        this.b = b;
        this.x = x;
        this.y = y;
        this.dm = dm;
        this.c = c;
    }


    @Override
    public void paintComponent(Graphics2D g2) {
        g2.setPaint(c);
        g2.fillOval(x - dm / 2, y - dm / 2, dm, dm);
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

}
/*/fg*/