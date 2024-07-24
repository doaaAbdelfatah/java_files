import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Clock extends JPanel implements Runnable {
    private  JLabel labelClock;
    private  JPanel panelClock;
    public Clock(){
        panelClock = this;

        labelClock = new JLabel();
        labelClock.setFont(new Font("Tahoma" ,Font.BOLD , 40));
        add(labelClock);

        Thread t =new Thread(this);
        Thread t2 =new Thread(new ChangeColorThread());
        t.start();
        t2.start();
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setBounds(400,150,400,150);
        frame.getContentPane().add(new Clock());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
        while (true){
            labelClock.setText(sdf.format(new Date()));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    class ChangeColorThread implements  Runnable{
        @Override
        public void run() {
            Random random = new Random();
            while (true){
                int r = random.nextInt(255);
                int g = random.nextInt(255);
                int b = random.nextInt(255);

                panelClock.setBackground(new Color(r,g,b));
//                labelClock.setForeground(new Color(r,g,b));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}


