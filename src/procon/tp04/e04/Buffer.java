package procon.tp04.e04;

public interface Buffer<E> {
    public void agregar(E elemento) throws InterruptedException;
    public E sacar() throws InterruptedException;
}
