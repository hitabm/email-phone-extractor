package ir.barasm.presenter.concurrent;

import java.util.concurrent.Semaphore;

public class Mutex {

    private Semaphore semaphore;
    private boolean open;
    private final Object sync = new Object();

    public Mutex() {
        this(false);
    }

    public Mutex(boolean lock) {
        semaphore = new Semaphore(lock ? 0 : 1);
        open = lock ? false : true;
    }


    public void lock() {

        try {
            while (!open) {
                semaphore.acquire();
            }
            synchronized (sync) {
                open = false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void unlock() {
        semaphore.release();
        synchronized (sync) {
            open = true;
        }
    }
}
