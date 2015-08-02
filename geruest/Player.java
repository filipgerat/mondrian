import java.awt.*;

/**
 * Created by Filip on 7/31/2015.
 */
public class Player extends Ball implements Moveable {
    /* State of player */
    private enum PlayerState {
        STABLE, DRAWING
    }
    PlayerState state;
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
        state = PlayerState.STABLE;
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
                if (y < b.getHeight() - 1) {
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
                if (x < b.getWidth() - 1) {
                    x += speed;
                } else {
                    d = Direction.STOP;
                }
                break;
            default: break;
        }
        /* Goes into drawing state, when enteres uncolored teritory */
        if (b.theBoard[x][y] == Board.FieldState.UNCOLORED) {
            b.theBoard[x][y] = Board.FieldState.LINE;
            state = PlayerState.DRAWING;
        }
        /* Fills the board, when reaching colored teritory */
        if ((b.theBoard[x][y] == Board.FieldState.COLORED) && (state == PlayerState.DRAWING)){
            b.fillProper();
            state = PlayerState.STABLE;
        }

    }


}
