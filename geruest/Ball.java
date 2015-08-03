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

    /**
     * Andreas: Checks if two balls are collided.
     * @param ball1
     * @param ball2
     * @return True, if collided, false otherwise
     */
    public static boolean isCollided(Moveable ball1, Moveable ball2) {
        int size1=ball1.getSize()/2;
        int size2=ball2.getSize()/2;
        double xDif = ball1.getX() - ball2.getX();
        double yDif = ball1.getY() - ball2.getY();
        double distanceSquared = xDif * xDif + yDif * yDif;
        return distanceSquared < (size1 + size2) * (size1 + size2);
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

    public int getSize() {
        return dm;
    }

}
/*/fg*/