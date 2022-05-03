import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class HorseContest {
    volatile static Object obj=new Object();
    static Random random=new Random();
    static boolean isEnd=false;
    static int horseRunningCnt=7;
    static StringBuilder finalResultStr=new StringBuilder();
    static long timeStart=0;

    public boolean finishOne(){
        synchronized (obj){
            horseRunningCnt--;
        }
        return true;
    }

    public static void main(String[] args) {
        String[] horses = "ABCDEFG".split("");
        timeStart=System.currentTimeMillis();
        for (String name:horses){
            new Thread(new HorseContest().new Horse(name,random.nextInt(5),0)).start();
        }
        while(true){
            synchronized (obj){
                try{
                    obj.notify();
                    Thread.sleep(5);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            try{
                Thread.sleep(5);
            }catch (Exception e){
                e.printStackTrace();
            }
            if(horseRunningCnt==0){
                break;
            }
        }
        System.out.println("Contest finish now! result:");
        System.out.println(finalResultStr.toString());
    }

    class Horse implements Runnable{
        String name;
        int speed;
        int distance;

        Horse(String name,int speed, int distance){
            this.name=name;
            this.speed=speed;
            this.distance=distance;
        }

        public void run() {
            while(distance<20){
                synchronized (obj){
                    try{
                        System.out.println(this);
                        obj.wait();
                        distance++;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                try{
                    int sleepSeconds=random.nextInt(50);
                    Thread.sleep(sleepSeconds);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            synchronized (obj){
                if(!isEnd){
                    long timeCost=System.currentTimeMillis() - timeStart;
                    finalResultStr.append("Horse "+name+" Win!"+" Time Cost:"+timeCost+"毫秒\n");
                    isEnd=true;
                }else{
                    long timeCost=System.currentTimeMillis() - timeStart;
                    finalResultStr.append("Horse "+name+" Finish Contest!"+" Time Cost:"+timeCost+"毫秒\n");
                }
            }

            finishOne();
        }

        public String toString(){
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<distance;i++){
                sb.append("=");
            }
            sb.append("> ");
            sb.append(name);
            return sb.toString();
        }
    }
}

class Solution {
    public int clumsy(int N) {
        int result = 0;
        if(N>4){
            switch(N%4){
                case 0:result=N+1;break;
                case 1:;
                case 2:result=N+2;break;
                case 3:result=N-2;break;
            }
        }else{
            switch(N){
                case 0:result=0;break;
                case 1:result=1;break;
                case 2:result=2;break;
                case 3:result=6;break;
                case 4:result=7;break;
            }
        }
        return result;
    }
}
