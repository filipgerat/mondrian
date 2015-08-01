import java.awt.*;

/**
 * Created by Filip on 7/31/2015.
 */
public class Player extends Ball implements Moveable {
    /* Speed of the Ball */
    private final int speed = 1;

    /* Direction of the movement */
    public enum Direction {
        UP, DOWN, LEFT, RIGHT, STOP
    }
    Direction d;

    public Player(Board b, int x, int y, int dm, Color c){
        super(b, x, y, dm, c);
        d = Direction.STOP;
    }

    @Override
    public void move(){
        switch (d){
            case UP:
                if (y > 0) {
                    y -= speed;
                } else {
                    d = Direction.STOP;
                }
                break;
            case DOWN:
                if (y < b.getHeight()) {
                    y += speed;
                } else {
                    d = Direction.STOP;
                }
                break;
            case LEFT:
                if (x > 0) {
                    x -= speed;
                } else {
                    d = Direction.STOP;
                }
                break;
            case RIGHT:
                if (x < b.getWidth()) {
                    x += speed;
                } else {
                    d = Direction.STOP;
                }
                break;
            default: break;
        }
    }


}
