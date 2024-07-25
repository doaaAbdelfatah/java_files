import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class BannerDemo extends JFrame implements Runnable {
    private JLabel label ;
    private JLabel label2 ;

    private  JLabel labelClock;
    public BannerDemo() {

        ImageIcon imageIcon1 = new ImageIcon("images/banner1.jpg");
        label = new JLabel(imageIcon1);

        label2 = new JLabel("Welcome");
        label.setPreferredSize(new Dimension(800 , 250));
        label2.setPreferredSize(new Dimension(800 , 250));

        label2.setFont(new Font("Tahoma" , Font.BOLD , 100));
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setForeground(Color.GRAY);
        getContentPane().add(label , BorderLayout.SOUTH);
        getContentPane().add(label2 );
//        setBounds(400,150 , 600 , 500);

        labelClock = new JLabel();
        labelClock.setFont(new Font("Tahoma" ,Font.BOLD , 40));
        labelClock.setPreferredSize(new Dimension(800 , 150));
        getContentPane().add(labelClock , BorderLayout.NORTH );
        labelClock.setHorizontalAlignment(SwingConstants.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Thread t = new Thread(this);
        t.start();

        Thread t2 = new Thread(new ClockThread());
        t2.start();
        getContentPane().setBackground(Color.GREEN);

//        JTextArea textArea = new JTextArea();
//        textArea.setPreferredSize(new Dimension(600, 500));
//        getContentPane().add(textArea);
        pack();
    }

    public static void main(String[] args) {
        new BannerDemo().setVisible(true);
    }

    @Override
    public void run() {
        int sleepTime [] ={2000,500,1000,250};
        String texts []= {"Welcome" , "Hello" , "Greating" , "Bye"};
        ImageIcon [] images  = {
                new ImageIcon("images/banner1.jpg")
                ,new ImageIcon("images/image1.png")
                ,new ImageIcon("images/image2.png")
                ,new ImageIcon("images/image3.png")
        };
//        boolean flag = true;
//        ImageIcon imageIcon1 = new ImageIcon("banner1.jpg");
//        ImageIcon imageIcon2 = new ImageIcon("image1.png");
        Random random = new Random();
        while (true){
//             if (flag) {
//                 label.setIcon(imageIcon1);
//                 flag=false;
//             }else{
//                 label.setIcon(imageIcon2);
//                 flag=true;
//             }

            int r =random.nextInt(4);
            label.setIcon(images[r]);
            label2.setText(texts[r]);
            try {
                Thread.sleep(sleepTime[r]);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    class ClockThread implements Runnable{
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
    }
}
