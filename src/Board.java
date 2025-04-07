import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    private int dots;
    public boolean ingame = true;
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private Image dot;
    private Image apple;
    private Image head;
    private Timer timer;
    private final int All_dots = 900;
    private final int dot_Size = 10;
    private final int RANDOM_POSITION = 29;
    private int apple_x;
    private int apple_y;
    private final int x[] = new int[All_dots];
    private final int y[] = new int[All_dots];

    Board() {
        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(350,350));

        loadImages();
        initGame();
        addKeyListener(new TAdapter());

    }

    public void loadImages() {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/apple.png"));
        apple = i1.getImage();
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("icons/dot.png"));
        dot = i2.getImage();
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("icons/head.png"));
        head = i3.getImage();
    }

    public void initGame() {
        dots = 3;


        for (int i = 0; i < dots; i++) {
            y[i] = 50;
            x[i] = 50 - (i * dot_Size);
        }
        locateapple();
        timer = new Timer(140, this);
        timer.start();

    }

    public void locateapple() {
        int r = (int) (Math.random() * RANDOM_POSITION);
        apple_x = r * dot_Size;
        r = (int) (Math.random() * RANDOM_POSITION);
        apple_y = r * dot_Size;

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (ingame) {
            g.drawImage(apple, apple_x, apple_y, this);
            for (int i = 0; i < dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[i], y[i], this);
                } else {
                    g.drawImage(dot, x[i], y[i], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();
        }else gameover(g);
    }
    public void gameover(Graphics g){
        String msg = "Game Over !";
        Font font =new Font("SAN SERIF",Font.BOLD,14);
        g.setColor(Color.WHITE);
        g.setFont(font);
        FontMetrics metrics = getFontMetrics(font);
        g.drawString(msg, ((300 - metrics.stringWidth(msg)) / 2), 300/2);

    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (leftDirection) {
            x[0] = x[0] - dot_Size;
        }
        if (rightDirection) {
            x[0] = x[0] + dot_Size;
        }
        if (upDirection) {
            y[0] = y[0] - dot_Size;
        }
        if (downDirection) {
            y[0] = y[0] + dot_Size;
        }
    }

    public void CheckApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            locateapple();
        }
    }

    public void checkcollision() {
        for (int i = dots; i > 0; i--) {
            if ((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
                ingame = false;
            }
        }
        if (y[0] >= getHeight()) ingame = false;
        if (x[0] >= getWidth()) ingame = false;
        if (y[0] < 0) ingame = false;
        if (x[0] < 0) ingame = false;

        if (!ingame) timer.stop();
    }

    public void actionPerformed(ActionEvent ae) {
        if (ingame) {
            move();
            checkcollision();
            CheckApple();
        }

        repaint();

    }

    public class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key == KeyEvent.VK_RIGHT && (!leftDirection)) {
                upDirection = false;
                rightDirection = true;
                downDirection = false;
                leftDirection = false;
            }
            if (key == KeyEvent.VK_UP && (!downDirection)) {
                leftDirection = false;
                upDirection = true;
                rightDirection = false;
            }
            if (key == KeyEvent.VK_DOWN && (!upDirection)) {
                leftDirection = false;
                rightDirection = false;
                downDirection = true;
            }

        }
    }

    public static void main(String[] args) {

    }
}
