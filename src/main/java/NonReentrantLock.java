import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class NonReentrantLock implements Lock, Serializable {
    public void lock() {

    }

    public void lockInterruptibly() throws InterruptedException {

    }

    public boolean tryLock() {
        return false;
    }

    public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
        return false;
    }

    public void unlock() {

    }

    public Condition newCondition() {
        return null;
    }
}
