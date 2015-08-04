import java.awt.*;
import javax.swing.*;

//TODO: (optional) Exception handling

public class Mondrian extends JFrame {

    public static final int RANDOM = 100; //A greater value results in less random changes of the enemy. A smaller
                                            //value results in more frequent random changes of the enemy.

    public Mondrian() {
        add(new Board());

        setResizable(false);
        pack();

        setTitle("Mondrian");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
   }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Mondrian window = new Mondrian();
                window.setLocationRelativeTo(null);
                window.setVisible(true);
            }
        });
    }

}
