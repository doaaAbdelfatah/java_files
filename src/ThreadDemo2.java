public class ThreadDemo2 implements  Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
    }
    public ThreadDemo2() {
        Thread t1 = new Thread(this );
        Thread t2 = new Thread(new Xxx());

        t1.start();
        t2.start();

    }

    public static void main(String[] args) {
        ThreadDemo2 t =  new ThreadDemo2();
    }
}


class Xxx implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 150; i++) {
            System.out.println("Xxxx " + i);
        }
    }
}


