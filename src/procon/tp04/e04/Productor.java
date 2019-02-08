package procon.tp04.e04;

abstract public class Productor<E> implements Runnable {

    private Buffer<E> buffer;

    public Productor(Buffer<E> buffer) {
        this.buffer = buffer;
    }

    abstract public E producir() throws InterruptedException;

    @Override
    public void run() {
        try {
            while (true) {
                buffer.agregar(producir());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
