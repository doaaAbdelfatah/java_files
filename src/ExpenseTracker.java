import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashSet;

public class ExpenseTracker extends JFrame {
    private JTextField expenseField, amountField, categoryField;
    private JTable expenseTable;
    private JList<String> categoryList;
    private DefaultTableModel tableModel;
    private DefaultListModel<String> listModel;
    private JButton addButton, deleteButton, viewCategoriesButton;
    private JMenuItem  newMenuItem, saveMenuItem, saveAsMenuItem ,openMenuItem ,exitMenuItem;

    private File file;
    public ExpenseTracker() {
        setTitle("Expense Tracker");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Menu setup
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        newMenuItem = new JMenuItem("New");
        saveMenuItem = new JMenuItem("Save");
        saveAsMenuItem = new JMenuItem("Save As");
        openMenuItem = new JMenuItem("Open");
        exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Text fields for input
        expenseField = new JTextField(10);
        amountField = new JTextField(10);
        categoryField = new JTextField(10);

        // Table for expenses
        String[] columnNames = {"Expense", "Amount", "Category"};
        tableModel = new DefaultTableModel(columnNames, 0);
        expenseTable = new JTable(tableModel);

        // List for categories
        listModel = new DefaultListModel<>();
        categoryList = new JList<>(listModel);
        categoryList.setPreferredSize(new Dimension(250,300));
        // Buttons
        addButton = new JButton("Add Expense");
        deleteButton = new JButton("Delete Expense");
        viewCategoriesButton = new JButton("View Categories");

        // Input panel for text fields and add button
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Expense:"));
        inputPanel.add(expenseField);
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountField);
        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(categoryField);
        inputPanel.add(addButton);
        add(inputPanel, BorderLayout.NORTH);

        // Scroll pane for the table
        JScrollPane tableScrollPane = new JScrollPane(expenseTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Scroll pane for the list
        JScrollPane listScrollPane = new JScrollPane(categoryList);
        add(listScrollPane, BorderLayout.EAST);

        // Button panel for other buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewCategoriesButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e->{addExpense();});

        saveMenuItem.addActionListener(e->{
            if (file == null){
                saveExpense(); // show dialog // =  save as
            }else{
                writeToFile(); // save
            }
        });


        saveAsMenuItem.addActionListener(e->{saveExpense();});
        newMenuItem.addActionListener(e->{clear();});
        openMenuItem.addActionListener(e->{loadFile();});
        deleteButton.addActionListener(e->{deleteManyExpense();});
        exitMenuItem.addActionListener(e->{System.exit(0);});
        viewCategoriesButton.addActionListener(e->{showCategories();});
    }

    private  void addExpense (){
        String [] expense = {expenseField.getText() , amountField.getText() , categoryField.getText()};
        tableModel.addRow(expense);
    }

    private  void deleteExpense (){
        int rowNum =  expenseTable.getSelectedRow();
        if (rowNum >=0){
            tableModel.removeRow(rowNum);
        }else{
            JOptionPane.showMessageDialog(null , "please select row to delete");
        }
    }

    private  void deleteManyExpense (){
        int rowNums [] =  expenseTable.getSelectedRows(); // [2 , 4]
        if (rowNums.length >0){
            for (int i = rowNums.length -1 ; i>=0 ; i--) {
                tableModel.removeRow(rowNums[i]);
            }
        }else{
            JOptionPane.showMessageDialog(null , "please select row to delete");
        }
    }

    private  void loadFile (){
        JFileChooser fileChooser = new JFileChooser();
        int choice = fileChooser.showOpenDialog(null);
        if (choice==JFileChooser.APPROVE_OPTION){
            file = fileChooser.getSelectedFile() ;
            this.setTitle(file.getAbsolutePath());
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader reader = new BufferedReader(fileReader);
                String line =null;
                while ((line =reader.readLine()) != null ){
                   String [] data = line.split(",")  ;  // Apple,70,Fruits
                    tableModel.addRow(data);
                }
                reader.close();
                fileReader.close();

            }catch (Exception exp){
                System.out.println(exp.getMessage());
            }
        }
    }

    private  void clear (){
        expenseField.setText("");
        amountField.setText("");
        categoryField.setText("");
        listModel.clear();
        this.setTitle("");
        for (int i = tableModel.getRowCount()-1; i >=0 ; i--) {
            tableModel.removeRow(i);
        }
    }

    private  void saveExpense (){
        JFileChooser fileChooser = new JFileChooser();
        int choice = fileChooser.showSaveDialog(null);
        if (choice==JFileChooser.APPROVE_OPTION){
            file = fileChooser.getSelectedFile() ;
            this.setTitle(file.getAbsolutePath());
            writeToFile();
        }
    }

    private void writeToFile(){
        try {
            FileWriter fileWriter = new FileWriter(file);
            PrintWriter out = new PrintWriter(fileWriter);

            for (int i = 0; i <tableModel.getRowCount() ; i++) {
                String exp = (String) tableModel.getValueAt(i , 0);
                String amount = (String) tableModel.getValueAt(i , 1);
                String cat = (String) tableModel.getValueAt(i , 2);
                out.println(exp + "," + amount + "," + cat);
            }

            out.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private  void showCategories(){
        // columns : 0 , 1 , 2 Category
        HashSet <String> categoriesSet = new HashSet<>();
        HashSet <String> categoriesSetWithAmount = new HashSet<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
           categoriesSet.add((String) tableModel.getValueAt(i , 2));
        }
        for (String c : categoriesSet){
            int sumCatAmount =0;
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String cat = (String) tableModel.getValueAt(i , 2);
                if (cat.equals(c)){
                    int amount =  Integer.parseInt ((String)tableModel.getValueAt(i , 1));
                    sumCatAmount+= amount;
                }
            }
            categoriesSetWithAmount.add(c + " - " + sumCatAmount);
        }
        listModel.clear();
        listModel.addAll(categoriesSetWithAmount);
    }
    public static void main(String[] args) {
        ExpenseTracker app = new ExpenseTracker();
        app.setVisible(true);

    }
}
