import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class BlockingQueueTest{
    static int cnt = 0;
    static Random random = new Random();
    public static void main(String[] args) throws Exception{
        final CopyOnWriteArrayList list = new CopyOnWriteArrayList<Integer>();
        for(int i=0;i<2;i++){
            new Thread(new Runnable() {
                public void run() {
                    while(true){
                        System.out.println(Thread.currentThread() + "producing number to queue " + cnt);
                        list.add(cnt++);
                        try {
                            Thread.sleep(10000 + random.nextInt(10000));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

        for(int i=0;i<5;i++){
            new Thread(new Runnable() {
                public void run() {
                    while(true){
                        while (list.size()>0) {
                            System.out.println(Thread.currentThread() + "consume " + list.get(0));
                            list.remove(0);
                        }
                        try {
                            Thread.sleep(100 + random.nextInt(10));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

    }
}
