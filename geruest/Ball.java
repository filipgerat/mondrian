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
     * @param ball1 Ball one to check collision with ball 2.
     * @param ball2 Ball 2.
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

    /**
     * Sets the color of this ball and paints a filled Oval.
     * Should be called within the main paintComponent method.
     * @param g2
     */
    @Override
    public void paintComponent(Graphics2D g2) {
        g2.setPaint(c);
        g2.fillOval(x - dm / 2, y - dm / 2, dm, dm);
    }

    /**
     * Getter for the x-coordinate of the Ball.
     * @return x
     */
    public int getX(){
        return x;
    }

    /**
     * Getter for the y-coordinate of the Ball.
     * @return y
     */
    public int getY(){
        return y;
    }

    /**
     * Getter for the size of the ball (diameter) in pixels.
     * @return diameter
     */
    public int getSize() {
        return dm;
    }

}
/*/fg*/