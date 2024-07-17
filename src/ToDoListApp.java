import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ToDoListApp extends JFrame {
    private JTextField taskField;
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;

    public ToDoListApp() {
        this.setTitle("To-Do List");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        JMenuItem menuItemSave = new JMenuItem("Save");
        JMenuItem menuItemLoad = new JMenuItem("Load");
        JMenuItem menuItemExit = new JMenuItem("Exit");
        fileMenu.add(menuItemSave);
        fileMenu.add(menuItemLoad);
        fileMenu.addSeparator();
        fileMenu.add(menuItemExit);

        JMenuItem menuItemRemove = new JMenuItem("Remove");
        JMenuItem menuItemMarkCompleted = new JMenuItem("Mark as Completed");
        editMenu.add(menuItemRemove);
        editMenu.add(menuItemMarkCompleted);

        taskField = new JTextField();
        JButton addButton = new JButton("Add Task");
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(taskField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        this.add(inputPanel, BorderLayout.NORTH);
        this.add(new JScrollPane(taskList), BorderLayout.CENTER);

        addButton.addActionListener(e -> addTask());
        menuItemSave.addActionListener(e -> saveTasks());
        menuItemLoad.addActionListener(e -> loadTasks());
        menuItemExit.addActionListener(e -> System.exit(0));
        menuItemRemove.addActionListener(e -> removeTask());
        menuItemMarkCompleted.addActionListener(e -> markTaskCompleted());
    }

    private void addTask() {
        String task = taskField.getText().trim();
        if (!task.isEmpty()) {
            taskListModel.addElement(task);
            taskField.setText("");
        }
    }

    private void saveTasks() {
        JFileChooser fileChooser = new JFileChooser();
        int choice = fileChooser.showSaveDialog(this);
        if (choice == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                for (int i = 0; i < taskListModel.size(); i++) {
                    writer.println(taskListModel.get(i));
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void loadTasks() {
        JFileChooser fileChooser = new JFileChooser();
        int choice = fileChooser.showOpenDialog(this);
        if (choice == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                taskListModel.clear();
                String task;
                while ((task = reader.readLine()) != null) {
                    taskListModel.addElement(task);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void removeTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            taskListModel.remove(selectedIndex);
        }
    }

    private void markTaskCompleted() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            String task = taskListModel.get(selectedIndex);
            taskListModel.set(selectedIndex, "<html><strike>" + task + "</strike></html>");
        }
    }

    public static void main(String[] args) {
        new ToDoListApp().setVisible(true);
    }
}
