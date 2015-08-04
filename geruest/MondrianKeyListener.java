import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Filip on 7/31/2015.
 */
public class MondrianKeyListener extends KeyAdapter {

    /* The Ball which should be controlled */
    Player b;

    /* Sets the direction of the player ball. Moving is controlled by the board.*/
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_DOWN:
                if (b.d == Player.Direction.UP) {
                    b.d = Player.Direction.STOP;
                } else {
                    b.d = Player.Direction.DOWN;
                }
                break;
            case KeyEvent.VK_UP:
                if (b.d == Player.Direction.DOWN) {
                    b.d = Player.Direction.STOP;
                } else {
                    b.d = Player.Direction.UP;
                }
                break;
            case KeyEvent.VK_LEFT:
                if (b.d == Player.Direction.RIGHT) {
                    b.d = Player.Direction.STOP;
                } else {
                    b.d = Player.Direction.LEFT;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (b.d == Player.Direction.LEFT) {
                    b.d = Player.Direction.STOP;
                } else {
                    b.d = Player.Direction.RIGHT;
                }
                break;
            default: break;
        }
    }

    /**
     * Constructor for MondrianKeyListener.
     * @param b The Player whose direction will be changed.
     */
    public MondrianKeyListener(Player b){
        this.b = b;
    }

}
