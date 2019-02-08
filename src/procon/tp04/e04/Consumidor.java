package procon.tp04.e04;

abstract public class Consumidor<E> implements Runnable {

    private Buffer<E> buffer;

    public Consumidor(Buffer<E> buffer) {
        this.buffer = buffer;
    }

    abstract public void consumir(E elemento) throws InterruptedException;

    @Override
    public void run() {
        try {
            while (true) {
                consumir(buffer.sacar());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
