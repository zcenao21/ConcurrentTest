import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class WaitAndNotify2 {
    static Random random = new Random();
    public static void main(String[] args) throws InterruptedException {
        Runnable target;
        final TaskQueueTest2 test = new TaskQueueTest2();
        for(int i=0;i<3;i++){
            final int finalI = i;
            Thread producer = new Thread(){
                public void run(){
                    try{
                        while(true){
                            test.putTask(finalI+"");
                            sleep(900+random.nextInt(900));
                        }
                    }catch (Exception e){}
                }
            };
            producer.setDaemon(true);
            producer.start();
        }

        for(int j=0;j<3;j++){
            final int finalJ = j;
            Thread consumer = new Thread(){
                public void run(){
                    try{
                        while(true){
                            test.exeTask(finalJ);
                            sleep(900+random.nextInt(900));
                        }
                    }catch (Exception e){}
                }
            };
            consumer.setDaemon(true);
            consumer.start();
        }

        Thread.sleep(5000);
        while(test.getTasksCnt()!=0){
            Thread.sleep(100);
        }

        System.out.println(System.currentTimeMillis()+": main thread done");
    }
}

class TaskQueueTest2{
    private int cnt = 0;
    private ArrayList<String> taskList = new ArrayList<String>();
    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public int getTasksCnt(){
        return taskList.size();
    }
    public void putTask(String taskName){
        lock.lock();
        try{
            System.out.println("User " + taskName + " put task " + cnt);
            taskList.add(cnt++ + "");
            condition.signal();
        }finally {
            lock.unlock();
        }
    }

    public synchronized void exeTask(int taskNumber) throws InterruptedException {
        lock.lock();
        try{
            while(taskList.isEmpty()){
                condition.await();
            }
            System.out.println("Consumer " + taskNumber + " consume task " + taskList.get(0));
            taskList.remove(0);
        }finally {
            lock.unlock();
        }

    }
}
