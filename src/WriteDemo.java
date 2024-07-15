import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteDemo {
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\demo\\xxx.txt");

        FileWriter fileWriter = new FileWriter(file );
        PrintWriter printWriter = new PrintWriter(fileWriter);

        printWriter.println("xx");
        printWriter.println("erewr Body");
        printWriter.println("ewrwerwe Bye");

        printWriter.close();
        fileWriter.close();

    }
}
