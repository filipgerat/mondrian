import java.awt.*;

/**
 * Created by Filip on 7/31/2015.
 */
public class Enemy extends Ball implements Moveable {
    Direction d;

    public enum Direction {
        UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT
    }

    public Enemy(Board b, int x, int y, int dm, Color c) {
        super(b, x, y, dm, c);
        switch((int)Math.round(Math.random()*3)){
            case 0: d = Direction.UP_RIGHT; break;
            case 1: d = Direction.DOWN_RIGHT; break;
            case 2: d = Direction.UP_LEFT; break;
            case 3: d = Direction.DOWN_LEFT; break;
        }
    }

    @Override
    public void move() {

        /* Move */
        switch (d){
            case DOWN_LEFT: x--; y++; break;
            case DOWN_RIGHT: x++; y++; break;
            case UP_LEFT: x--; y--; break;
            case UP_RIGHT: x++; y--; break;
        }

        /* Check Collisions */
        for (int i = 1; i < dm - 1; i++){
            // Left
            if (this.b.theBoard[x - dm/2][y - dm/2 + i] == Board.FieldState.COLORED) {
                if (d == Direction.DOWN_LEFT) d = Direction.DOWN_RIGHT;
                if (d == Direction.UP_LEFT) d = Direction.UP_RIGHT;
            }
            if (this.b.theBoard[x - dm/2][y - dm/2 + i] == Board.FieldState.LINE) {
                b.gameOver();
            }
            // Right
            if (this.b.theBoard[x + dm/2][y - dm/2 + i] == Board.FieldState.COLORED) {
                if (d == Direction.DOWN_RIGHT) d = Direction.DOWN_LEFT;
                if (d == Direction.UP_RIGHT) d = Direction.UP_LEFT;
            }
            if (this.b.theBoard[x + dm/2][y - dm/2 + i] == Board.FieldState.LINE) {
                b.gameOver();
            }
            // Up
            if (this.b.theBoard[x - dm/2 + i][y - dm/2] == Board.FieldState.COLORED) {
                if (d == Direction.UP_LEFT) d = Direction.DOWN_LEFT;
                if (d == Direction.UP_RIGHT) d = Direction.DOWN_RIGHT;
            }
            if (this.b.theBoard[x - dm/2 + i][y - dm/2] == Board.FieldState.LINE) {
                b.gameOver();
            }
            // Down
            if (this.b.theBoard[x - dm/2 + i][y + dm/2] == Board.FieldState.COLORED) {
                if (d == Direction.DOWN_LEFT) d = Direction.UP_LEFT;
                if (d == Direction.DOWN_RIGHT) d = Direction.UP_RIGHT;
            }
            if (this.b.theBoard[x - dm/2 + i][y + dm/2] == Board.FieldState.LINE) {
                b.gameOver();
            }
        }
    }
}
