import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Arrays;

public class TODOApp extends JFrame {

    private JTextField textField ;
    private JButton button ;
    private DefaultListModel<String> dlm;
    private JList<String> list;

    private File file;

    public TODOApp(){
        setBounds(400,150,500,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        JMenuItem menuItemNew = new JMenuItem("New");
        JMenuItem menuItemSave = new JMenuItem("Save");
        JMenuItem menuItemSaveAs = new JMenuItem("Save As");
        JMenuItem menuItemLoad = new JMenuItem("Load");
        JMenuItem menuItemExit = new JMenuItem("Exit");
        fileMenu.add(menuItemNew);
        fileMenu.addSeparator();
        fileMenu.add(menuItemSave);
        fileMenu.add(menuItemSaveAs);
        fileMenu.addSeparator();
        fileMenu.add(menuItemLoad);
        fileMenu.addSeparator();
        fileMenu.add(menuItemExit);

        menuItemExit.addActionListener(e->{System.exit(0);});
        JMenuItem menuItemRemove = new JMenuItem("Remove");
        JMenuItem menuItemMarkCompleted = new JMenuItem("Mark as Completed");
        editMenu.add(menuItemRemove);
        editMenu.add(menuItemMarkCompleted);
        ////////////////////////////////////////
        JPanel panelNorth = new JPanel(new BorderLayout());
//        panelNorth.setBackground(Color.BLUE);
        panelNorth.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        getContentPane().add(panelNorth , BorderLayout.NORTH);

        textField  = new JTextField();
        panelNorth.add(textField);

        button = new JButton("Add");
        panelNorth.add(button , BorderLayout.EAST);

        dlm = new DefaultListModel<>();
//        dlm.add(0,"Ahmed");
//        dlm.add(1,"Dina");
//        String [] names ={"Sara" , "Ahmed"};
        list = new JList<>(dlm);
        JScrollPane scrollPane = new JScrollPane(list);

        getContentPane().add(scrollPane);

        button.addActionListener(e->{
            dlm.add(dlm.size(),textField.getText());
            textField.setText("");
        });


        menuItemNew.addActionListener(e->{
            dlm.clear();
            this.setTitle("");
            file = null;
        });
        menuItemRemove.addActionListener(e->{
           int indexes[] =  list.getSelectedIndices(); // [2,4]
            for (int i = indexes.length-1 ; i >=0 ; i--) {
                dlm.remove(indexes[i]);
            }
        });

        menuItemSave.addActionListener(e->{
            if (file == null ){
               save();
            }else{
                try {
                    FileWriter writer = new FileWriter(file);
                    PrintWriter out = new PrintWriter(writer);
                    for (int i = 0; i <dlm.size() ; i++) {
                        out.println(  dlm.get(i));
                    }
                    out.close();
                    writer.close();
                }catch (IOException ioException){
                    System.out.println(ioException.getMessage());
                }
            }
        });
        menuItemSaveAs.addActionListener(e->{
            save();
        });

        menuItemLoad.addActionListener(e->{
            JFileChooser fileChooser = new JFileChooser();
            int choice =  fileChooser.showOpenDialog(null) ;
            if (choice == 0) {
                file = fileChooser.getSelectedFile();
                this.setTitle(file.getAbsolutePath());
                try {
                    FileReader fileReader= new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    dlm.clear();
                    String line ;
                    while ((line = bufferedReader.readLine() ) != null){
                            dlm.add(dlm.size() , line);
                    }
                    bufferedReader.close();
                    fileReader.close();
                } catch (IOException ex) {
                    System.out.println(ex);
                }

            }
        });

        menuItemMarkCompleted.addActionListener(e->{
//            int i = list.getSelectedIndex();
//            dlm.set(i  , "<html><s><i><span color='gray'>" + dlm.get(i)+ "</span></i><s></html>");

           int indexes [] =  list.getSelectedIndices();
            for (int i = 0; i < indexes.length ; i++) {
                dlm.set(indexes[i]  , "<html><s><i><span color='gray'>" + dlm.get(indexes[i])+ "</span></i><s></html>");
            }
        });

    }
    public static void main(String[] args) {
        new TODOApp().setVisible(true);
    }

    void save(){
        JFileChooser fileChooser = new JFileChooser();
        int choice =  fileChooser.showSaveDialog(null) ;
        if (choice == 0) {
            try {
                file = fileChooser.getSelectedFile();
                this.setTitle(file.getAbsolutePath());
                FileWriter writer = new FileWriter(file);
                PrintWriter out = new PrintWriter(writer);
                for (int i = 0; i <dlm.size() ; i++) {
                    out.println(  dlm.get(i));
                }
                out.close();
                writer.close();
            }catch (IOException ioException){
                System.out.println(ioException.getMessage());
            }
        }
    }
}
