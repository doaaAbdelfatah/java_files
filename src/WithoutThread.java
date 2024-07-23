public class WithoutThread {
    public static void main(String[] args) {
        run("first");
        run("second");
    }

    public static void run(String name) {
        for (int i = 0; i < 400; i++) {
            System.out.println(name  + " " + i);
        }
    }
}
