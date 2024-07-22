import javax.swing.*;
import java.io.*;

public class Notepade extends JFrame {
    private  JTextArea textArea;
    private File file;

    public Notepade(){
        this.setBounds(400, 200, 600, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        JMenuItem menuItemNew = new JMenuItem("New");
        JMenuItem menuItemOpen = new JMenuItem("Open");
        JMenuItem menuItemSave = new JMenuItem("Save");
        JMenuItem menuItemSaveAs = new JMenuItem("Save as");
        JMenuItem menuItemExit = new JMenuItem("Exit");

        menu.add(menuItemNew);
        menu.add(menuItemOpen);
        menu.addSeparator();
        menu.add(menuItemSave);
        menu.add(menuItemSaveAs);
        menu.addSeparator();
        menu.add(menuItemExit);

        textArea=new JTextArea();
        JScrollPane scrollPane=new JScrollPane(textArea);
        this.getContentPane().add(scrollPane);

        menuItemNew.addActionListener(e->{
            textArea.setText("");
            this.setTitle("");
            file = null;
        });
        menuItemExit.addActionListener(e->{System.exit(0);});

        menuItemSave.addActionListener(e->{
            if (file != null){
                try {
                    FileWriter writer = new FileWriter(file);
                    PrintWriter out = new PrintWriter(writer);
                    out.println(textArea.getText());
                    out.close();
                    writer.close();
                }catch (IOException ioException){
                    System.out.println(ioException.getMessage());
                }
            }else {
                save();
            }
        });
        menuItemSaveAs.addActionListener(e->{
            save();
        });

        menuItemOpen.addActionListener(e->{
            JFileChooser fileChooser = new JFileChooser();
            int choice =  fileChooser.showOpenDialog(null) ;
            if (choice == 0) {
                file = fileChooser.getSelectedFile();
                this.setTitle(file.getAbsolutePath());
                try {
                    FileReader fileReader= new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String line ; int c = 0;
                    while ((line = bufferedReader.readLine() ) != null){
                        if (c++ == 0 ) textArea.setText( line);
                        else textArea.setText( textArea.getText() + "\n"+  line);
                    }
                    bufferedReader.close();
                    fileReader.close();
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        });
    }
    void save(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save As...");
        int choice =  fileChooser.showSaveDialog(null) ;
        if (choice == JFileChooser.APPROVE_OPTION) {
            try {
                file = fileChooser.getSelectedFile();
                this.setTitle(file.getAbsolutePath());
                FileWriter writer = new FileWriter(file);
                PrintWriter out = new PrintWriter(writer);
                out.println(textArea.getText());
                out.close();
                writer.close();
            }catch (IOException ioException){
                System.out.println(ioException.getMessage());
            }
        }
    }
    public static void main(String[] args) {
            new Notepade().setVisible(true);
    }
}
