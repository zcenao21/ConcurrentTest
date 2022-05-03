import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class ProducerAndConsumerLockTest {
    volatile static LinkedList<Hamburger> hamburgerQueue;
    volatile long idCnt=0;

    public static void main(String[] args) {
        ProducerAndConsumerLockTest test=new ProducerAndConsumerLockTest();
        test.hamburgerQueue=new LinkedList<Hamburger>();
        Producer producer1=new ProducerAndConsumerLockTest().new Producer();
        new Thread(producer1).start();
        Consumer consumer=new ProducerAndConsumerLockTest().new Consumer();
        new Thread(consumer).start();
    }

    class Hamburger{
        long id;

        Hamburger(long id){
            this.id=id;
        }

        public String toString(){
            return ""+id;
        }
    }

    class Producer implements Runnable{
        public void run() {
            while(true){
                synchronized (hamburgerQueue){
                    while(hamburgerQueue.size()>10) {
                        try {
                            hamburgerQueue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        Thread.sleep(1000);
                        hamburgerQueue.add(new Hamburger(idCnt++));
                        System.out.println("produce a hamburger, hamburger queue size: " + hamburgerQueue.size());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    hamburgerQueue.notifyAll();
                }
            }
        }
    }

    class Consumer implements Runnable{
        public void run() {
            while(true){
                synchronized (hamburgerQueue){
                    while(hamburgerQueue.size()<5){
                        try{
                            hamburgerQueue.wait();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(new Random().nextInt(2000));

                        Hamburger hamburger=hamburgerQueue.removeLast();
                        System.out.println("eat a hamburger "+ hamburger+", hamburger queue size: "+hamburgerQueue.size());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    hamburgerQueue.notifyAll();
                }
            }
        }
    }

}
