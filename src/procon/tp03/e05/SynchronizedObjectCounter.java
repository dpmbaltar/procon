package procon.tp03.e05;

public class SynchronizedObjectCounter implements Counter {

    private Integer c = new Integer(0);

    public void increment() {
        // Se cambio la siguiente sentencia, ya que al usar un objeto distinto
        // que el que usa decrement(), se producen bloqueos con disintos locks
        // y producen inconsistencia en la variable contadora
        // synchronized (c) {
        synchronized (this) {
            c++;
        }
    }

    public void decrement() {
        synchronized (this) {
            c--;
        }
    }

    public int value() {
        synchronized (this) {
            return c;
        }
    }
}
