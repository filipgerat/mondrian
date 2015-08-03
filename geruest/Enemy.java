import java.awt.*;

/**
 * Created by Filip on 7/31/2015.
 */
public class Enemy extends Ball implements Moveable {
    Direction d;

    //speed
    private final int s = 1;

    public enum Direction {
        UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT, UP, RIGHT, DOWN, LEFT //andreas: up, down, left, right
    }

    public Enemy(Board b, int x, int y, int dm, Color c) {
        super(b, x, y, dm, c);
        switch((int)Math.round(Math.random()*7)){
            case 0: d = Direction.UP_RIGHT; break;
            case 1: d = Direction.DOWN_RIGHT; break;
            case 2: d = Direction.UP_LEFT; break;
            case 3: d = Direction.DOWN_LEFT; break;
            case 4: d = Direction.UP; break; //andreas
            case 5: d = Direction.RIGHT; break; //andreas
            case 6: d = Direction.DOWN; break; //andreas
            case 7: d = Direction.LEFT; break; //andreas
        }

    }



    @Override
    public void move() {

        /* Move */
        switch (d){
            case DOWN_LEFT: x-=s; y+=s; break;
            case DOWN_RIGHT: x+=s; y+=s; break;
            case UP_LEFT: x-=s; y-=s; break;
            case UP_RIGHT: x+=s; y-=s; break;
            case UP: y-=s; break;
            case RIGHT: x+=s; break;
            case DOWN: y+=s; break;
            case LEFT: x-=s; break;
        }

        // Left
        if ( x-dm/2 < 0 || y-dm/2+1 < 0 || this.b.theBoard[x - dm/2][y - dm/2 + 1] == Board.FieldState.COLORED) {
            if (d == Direction.DOWN_LEFT) d = Direction.DOWN_RIGHT;
            else if (d == Direction.UP_LEFT) d = Direction.UP_RIGHT;
            else if (d == Direction.UP) d = Direction.RIGHT; //andreas
        }
        if ( this.b.theBoard[x - dm/2][y - dm/2 + 1] == Board.FieldState.LINE) {
            b.gameOver();
        }
        // Right
        if ( x+dm/2 > b.WIDTH || y-dm/2+1 < 0 || this.b.theBoard[x + dm/2][y - dm/2 + 1] == Board.FieldState.COLORED) {
            if (d == Direction.DOWN_RIGHT) d = Direction.DOWN_LEFT;
            else if (d == Direction.UP_RIGHT) d = Direction.UP_LEFT;
            else if (d == Direction.RIGHT) d = Direction.LEFT; //andreas
        }
        if ( this.b.theBoard[x + dm/2][y - dm/2 + 1] == Board.FieldState.LINE) {
            b.gameOver();
        }
        // Up
        if ( x-dm/2+1 < 0 || y-dm/2 < 0 || this.b.theBoard[x - dm/2 + 1][y - dm/2] == Board.FieldState.COLORED) {
            if (d == Direction.UP_LEFT) d = Direction.DOWN_LEFT;
            else if (d == Direction.UP_RIGHT) d = Direction.DOWN_RIGHT;
            else if (d == Direction.UP) d = Direction.DOWN; //andreas
        }
        if ( this.b.theBoard[x - dm/2 + 1][y - dm/2] == Board.FieldState.LINE) {
            b.gameOver();
        }
        // Down
        if ( x-dm/2+1 < 0 || y+dm/2 > b.HEIGHT || this.b.theBoard[x - dm/2 + 1][y + dm/2] == Board.FieldState.COLORED) {
            if (d == Direction.DOWN_LEFT) d = Direction.UP_LEFT;
            else if (d == Direction.DOWN_RIGHT) d = Direction.UP_RIGHT;
            else if (d == Direction.DOWN) d = Direction.UP; //andreas
        }
        if ( this.b.theBoard[x - dm/2 + 1][y + dm/2] == Board.FieldState.LINE) {
            b.gameOver();
        }

    }
}
