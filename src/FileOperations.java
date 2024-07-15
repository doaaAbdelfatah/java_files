import  java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class FileOperations {
    public static void main(String[] args) {
        File file = new File("C:\\demo\\newfile.txt");

        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (file.exists()) System.out.println("file created successfully");

        File file2 = new File("C:\\demo\\newdirectory");
        if (!file2.exists()) file2.mkdirs();
        if (file2.exists()) System.out.println("Folder created successfully");
        System.out.println("----------------");
        for (String name : file2.list()) System.out.println(name);
        System.out.println("----------------");
        System.out.println(Arrays.toString( file2.list()));
        System.out.println("----------------");
        for (File f : file2.listFiles()){
            if (f.isFile()){
                if (f.length() < 1024 ){
                    System.out.println(f.getName() + " : " + f.length() + " Byte");
                }else{
                    double s = f.length() /1024 ;
                    System.out.println(f.getName() + " : " + s + " KB");
                }

            }
            else{
                System.out.println(f.getName() + " is Directory");
            }
        }

        System.out.println("-------------");
        System.out.println(file.delete());

        for (File f : file2.listFiles()){
            f.delete();
            System.out.println(f.getName() + " deleted");
        }
        System.out.println(file2.delete());

    }
}
