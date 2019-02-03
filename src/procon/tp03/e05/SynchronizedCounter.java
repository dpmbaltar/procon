package procon.tp03.e05;

public class SynchronizedCounter implements Counter {

    private int c = 0;

    public synchronized void increment() {
        c++;
    }

    /**
     * Se agregó el modificador synchronized al método, ya que al no estar
     * sincronizado, se producían inconsistencias en la variable contadora.
     */
    public synchronized void decrement() {
        c--;
    }

    public synchronized int value() {
        return c;
    }
}
