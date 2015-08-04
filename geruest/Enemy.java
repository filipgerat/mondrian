import java.awt.*;

/**
 * Created by Filip on 7/31/2015.
 */
public class Enemy extends Ball implements Moveable {
    Direction d;

    //speed
    private final int s = 1;

    /** Direction of the enemy */
    public enum Direction {
        UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT, UP, RIGHT, DOWN, LEFT //andreas: up, down, left, right
    }

    /**
     * Constructor for enemy. (Filled Circle)
     * @param b The Board, on which the enemy will be painted.
     * @param x The starting x-coordinate of the enemy.
     * @param y The starting y-coordinate of the enemy.
     * @param dm The diameter of the enemy in pixels.
     * @param c The color of the enemy.
     */
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

    /**
     * Moves the enemy according to its saved direction and coordinate data.
     * This method also calculates random movement for the Enemy,
     * once completely random, once it tries to change the direction towards the Player.
     * The chances for these behaviours can be set either in Mondrian.java:RANDOM, or directly
     * in this method (see bottom values).
     */
    @Override
    public void move() {
        boolean noChange = true; //Do not change randomly if direction will be changed due to walls.

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

        /* Check Collisions: Collision with Player is in Board.java */
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

        /**
         * Only change direction randomly if not changed just right now due to a wall or a field.
         */
        if( noChange ) {
            int i = (int)(Math.random()*10000); //Get a random number between 0 and 10.000
            if( i%1131<100 ) {                  //Change values here to raise or lower the possibility of changing the
                int px = b.getPlayerX();        //direction TOWARDS the Player
                int py = b.getPlayerY();
                if( px > x ) i = i%3;           //sets i for on of 3 possible directions (see bottom) which are mostly
                else if( py < y ) i = i%3+2;    //towards the player
                else if( px < x ) i = i%3+4;
                else if( py > y ) i = i%3+6;
            }
            switch(i%Mondrian.RANDOM){          //Either changes the direction Towards the player (if i changed above
                case 0: d = Direction.DOWN_RIGHT; break; //i will stay, as it is always smaller than RANDOM... i%RANDOM will then be i
                case 1: d = Direction.RIGHT; break; //if i is bigger than 8 however, the direction will not be changed, or it will change
                case 2: d = Direction.UP_RIGHT; break; //randomly if i%RANDOM is between 0 and 8.
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
