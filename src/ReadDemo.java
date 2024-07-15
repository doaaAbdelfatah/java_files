import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadDemo {
    public static void main(String[] args) {
        File file = new File("C:\\demo\\data.txt");
        if (file.exists()){
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
//                String l ;   //   ""
//                while ((l =bufferedReader.readLine() )!= null)
//                    System.out.println( l);
//                int i = 1;
                while (true){
//                    System.out.print(i++ + " : ");
                    String l = bufferedReader.readLine();
                    if (l == null) break;
                    System.out.println(l);
                }
                bufferedReader.close();
                fileReader.close();

            }catch (IOException ioException){
                System.out.println(ioException.getMessage());
            }

        }
    }
}
