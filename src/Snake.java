import javax.swing.*;
import java.awt.*;

public class Snake extends JFrame {
    Snake() {
        super("Snake Game");
        add(new Board());
//        pack function refresh the page so that you can immediately look the changes
        pack();
        setResizable(true);


        setLocationRelativeTo(null);

//        Setvisible once used cannot be used again
        setVisible(true);
    }

    public static void main(String[] args) {
        new Snake();
    }
}