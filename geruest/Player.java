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

    /**
     * Constructor for Player (Filled Circle).
     * @param b The board where the player will be painted.
     * @param x The starting x-coordinate. (must be within bounds of board)
     * @param y The starting y-coordinate. (must be within bounds of board)
     * @param dm The diameter in pixels.
     * @param c The color of the player.
     */
    public Player(Board b, int x, int y, int dm, Color c){
        super(b, x, y, dm, c);
        d = Direction.STOP;
        state = PlayerState.STABLE;
    }

    /**
     * Moves the player according to its saved direction, which is set by the MondrianKeyListener if a key has been pressed.
     * After the x and y data changed properly, this method will check if the new coordinate is a LINE, COLORED or UNCOLORED.
     * If it is a LINE, the player will remove this LINE, as this means, the player either made a loop, or is going
     * back directly the same way. If it is COLORED, the player is in a field, and will draw the new field, if he was not
     * in a field before (STABLE/DRAWING). If it is UNCOLORED the player will draw a LINE.     *
     */
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
            if( d == Direction.STOP || countUncolored() == 3 ) { //will erase, as the player goes back the same way
                if( d==Direction.STOP ) state = PlayerState.DRAWING;
                else if( d!=Direction.STOP ) state = PlayerState.ERASING;
                b.theBoard[x][y] = Board.FieldState.UNCOLORED;
            } else {                                            //will erase a loop.
                int i = x;                                      //will erase the first two pixels according to the direction
                int j = y;                                      //the player had before the loop.
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
                while (i != x || j != y) { //will erase the rest of the pixels, until the beginning of the loop is found.
                    b.theBoard[i][j] = Board.FieldState.UNCOLORED;
                    if (b.theBoard[i][j + 1] == Board.FieldState.LINE) j++;
                    else if (b.theBoard[i - 1][j] == Board.FieldState.LINE) i--;
                    else if (b.theBoard[i + 1][j] == Board.FieldState.LINE) i++;
                    else if (b.theBoard[i][j - 1] == Board.FieldState.LINE) j--;
                    else break;
                }
            }
        }else if ( b.theBoard[x][y] == Board.FieldState.UNCOLORED ) { //Will draw a Line
            b.theBoard[x][y] = Board.FieldState.LINE;
            state = PlayerState.DRAWING;
        }
    }

    /**
     * Counts the uncolored pixels near the player (only above, underneath, left and right from the player)
     * This value gives information, if the player goes the same way back he came.
     * @return Amount of uncolored pixels near the player.
     */
    private int countUncolored() {
        int i=0;
        if( b.theBoard[x - 1][y] == Board.FieldState.UNCOLORED ) i++;
        if( b.theBoard[x + 1][y] == Board.FieldState.UNCOLORED ) i++;
        if( b.theBoard[x][y - 1] == Board.FieldState.UNCOLORED ) i++;
        if( b.theBoard[x][y + 1] == Board.FieldState.UNCOLORED ) i++;
        return i;
    }


}
