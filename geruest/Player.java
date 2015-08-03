import java.awt.*;

/**
 * Created by Filip on 7/31/2015.
 */
public class Player extends Ball implements Moveable {
    /* State of player */
    private enum PlayerState {
        STABLE, DRAWING, ERASING
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
        if ((b.theBoard[x][y] == Board.FieldState.COLORED) && (state != PlayerState.STABLE)){ /* Fills the board, when reaching colored teritory */
            b.fillProper();
            state = PlayerState.STABLE;
            d = Direction.STOP;
        }else if( b.theBoard[x][y] == Board.FieldState.LINE ) { //removes loops  //andreas
            if( d == Direction.STOP || countUncolored() == 3 ) {
                if( d==Direction.STOP ) state = PlayerState.DRAWING;
                else if( d!=Direction.STOP ) state = PlayerState.ERASING;
                b.theBoard[x][y] = Board.FieldState.UNCOLORED;
            }
            else {
                int i = x;
                int j = y;
                switch (d) {
                    case UP:
                        j++;  //be aware that x=0 on the top
                        break;
                    case DOWN:
                        j--;
                        break;
                    case LEFT:
                        i++;
                        break;
                    case RIGHT:
                        i--;
                        break;
                }
                b.theBoard[i][j] = Board.FieldState.UNCOLORED; //j is up/down
                if (b.theBoard[i][j + 1] == Board.FieldState.LINE && d != Direction.UP) j--;
                else if (b.theBoard[i - 1][j] == Board.FieldState.LINE && d != Direction.LEFT) i--;
                else if (b.theBoard[i + 1][j] == Board.FieldState.LINE && d != Direction.RIGHT) i++;
                else if (b.theBoard[i][j - 1] == Board.FieldState.LINE && d != Direction.DOWN) j++;
                while (i != x || j != y) { //until the beginning of the loop is found
                    b.theBoard[i][j] = Board.FieldState.UNCOLORED;
                    if (b.theBoard[i][j + 1] == Board.FieldState.LINE) j++;
                    else if (b.theBoard[i - 1][j] == Board.FieldState.LINE) i--;
                    else if (b.theBoard[i + 1][j] == Board.FieldState.LINE) i++;
                    else if (b.theBoard[i][j - 1] == Board.FieldState.LINE) j--;
                    else break;
                }
            }
        }else if ( b.theBoard[x][y] == Board.FieldState.UNCOLORED ) {
            b.theBoard[x][y] = Board.FieldState.LINE;
            state = PlayerState.DRAWING;
        }
    }

    private int countUncolored() {
        int i=0;
        if( b.theBoard[x - 1][y] == Board.FieldState.UNCOLORED ) i++;
        if( b.theBoard[x + 1][y] == Board.FieldState.UNCOLORED ) i++;
        if( b.theBoard[x][y - 1] == Board.FieldState.UNCOLORED ) i++;
        if( b.theBoard[x][y + 1] == Board.FieldState.UNCOLORED ) i++;
        return i;
    }


}
