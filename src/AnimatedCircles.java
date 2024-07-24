import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class AnimatedCircles extends JPanel implements Runnable {
    private int x = 0; // Circle's x-coordinate
    private int y = 100; // Circle's y-coordinate
    private int radius = 100; // Circle's radius
    private  Color color = Color.BLUE;

    public AnimatedCircles() {
        setPreferredSize(new Dimension(800, 600));
        new Thread(this).start(); // Start the animation thread
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillOval(x, y, radius, radius);
    }
    @Override
    public void run() {
        Random random = new Random();
        while (true) {

            x = random.nextInt(500); // Move the circle to the right
            y = random.nextInt(450); // Move the circle to the right
            radius = random.nextInt(150); // Move the circle to the right

            color  = new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255) );
            repaint(); // Request a repaint to update the circle's position

            try {
                Thread.sleep(1000); // Pause for 50 milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Animated Circles");
        AnimatedCircles animation = new AnimatedCircles();

        frame.add(animation);
        frame.setBounds(400,150,600,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
