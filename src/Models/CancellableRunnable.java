package Models;

public interface CancellableRunnable extends Runnable {
    void cancel();
}
