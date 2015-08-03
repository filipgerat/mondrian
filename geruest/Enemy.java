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
        boolean noChange = true;

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
        // These place the enemy inside the bounds
        if (x<0+dm/2) x= dm/2;
        if (y<0+dm/2) y=dm/2;
        if (x>b.WIDTH-dm/2-1) x=b.WIDTH-dm/2-1;
        if (y>b.HEIGHT-dm/2-1) y=b.HEIGHT-dm/2-1;

        /* Border pixels of the Jpanel are COLORED, so when they reach/overstep the bounds of the Jpanel, they are
         * placed on the border, and after that the proper collision is invoked.
         */

        /* Check Collisions */
        // TODO: Collision with player
        for (int i = 1; i < dm - 1; i++) {
            // Left -- trigger when the value touches the left side or a colored surface
            if (this.b.theBoard[x - dm / 2][y - dm / 2 + i] == Board.FieldState.COLORED) {
                // Here we push the object away, so we won't face the same collision
                x += s;
                if (d == Direction.DOWN_LEFT) d = Direction.DOWN_RIGHT;
                else if (d == Direction.UP_LEFT) d = Direction.UP_RIGHT;
                else if (d == Direction.LEFT) d = Direction.RIGHT; //andreas
                noChange=false;
            }
            if (this.b.theBoard[x - dm / 2][y - dm / 2 + i] == Board.FieldState.LINE) {
                b.gameOver();
            }
            // Right
            if (this.b.theBoard[x + dm / 2][y - dm / 2 + i] == Board.FieldState.COLORED) {
                x -= s;
                if (d == Direction.DOWN_RIGHT) d = Direction.DOWN_LEFT;
                else if (d == Direction.UP_RIGHT) d = Direction.UP_LEFT;
                else if (d == Direction.RIGHT) d = Direction.LEFT; //andreas
                noChange=false;
            }
            if (this.b.theBoard[x + dm / 2][y - dm / 2 + i] == Board.FieldState.LINE) {
                b.gameOver();
            }
            // Up
            if (this.b.theBoard[x - dm / 2 + i][y - dm / 2] == Board.FieldState.COLORED) {
                y += s;
                if (d == Direction.UP_LEFT) d = Direction.DOWN_LEFT;
                else if (d == Direction.UP_RIGHT) d = Direction.DOWN_RIGHT;
                else if (d == Direction.UP) d = Direction.DOWN; //andreas
                noChange=false;
            }
            if (this.b.theBoard[x - dm / 2 + i][y - dm / 2] == Board.FieldState.LINE) {
                b.gameOver();
            }
            // Down
            if (this.b.theBoard[x - dm / 2 + i][y + dm / 2] == Board.FieldState.COLORED) {
                y -= s;
                if (d == Direction.DOWN_LEFT) d = Direction.UP_LEFT;
                else if (d == Direction.DOWN_RIGHT) d = Direction.UP_RIGHT;
                else if (d == Direction.DOWN) d = Direction.UP; //andreas
                noChange=false;
            }
            if (this.b.theBoard[x - dm / 2 + i][y + dm / 2] == Board.FieldState.LINE) {
                b.gameOver();
            }
        }

        if( noChange ) {
            int i = (int)(Math.random()*10000);
            if( i%1131<100 ) {
                int px = b.getPlayerX();
                int py = b.getPlayerY();
                if( px > x ) i = i%3;
                else if( py < y ) i = i%3+2;
                else if( px < x ) i = i%3+4;
                else if( py > y ) i = i%3+6;
            }
            switch(i%Mondrian.RANDOM){
                case 0: d = Direction.DOWN_RIGHT; break;
                case 1: d = Direction.RIGHT; break; //andreas
                case 2: d = Direction.UP_RIGHT; break;
                case 3: d = Direction.UP; break; //andreas
                case 4: d = Direction.UP_LEFT; break;
                case 5: d = Direction.LEFT; break; //andreas
                case 6: d = Direction.DOWN_LEFT; break;
                case 7: d = Direction.DOWN; break; //andreas
                case 8: d = Direction.DOWN_RIGHT; break;
            }
        }
    }
}
