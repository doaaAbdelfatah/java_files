public class ThreadDemo {
    public static void main(String[] args) {
        MyThread thread1 = new MyThread();
        thread1.setName("First");

        MyThread thread2 = new MyThread();
        thread2.setName("Second");

        MyThread thread3 = new MyThread();
        thread2.setName("Third");

        thread1.start();
        thread2.start();
        thread3.start();
    }
}

class MyThread extends  Thread{
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println( Thread.currentThread().getName() + " - "+ i);
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
        }
    }
}
