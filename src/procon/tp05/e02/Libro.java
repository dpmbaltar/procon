package procon.tp05.e02;

public interface Libro {
    public void comenzarEscritura() throws InterruptedException;
    public void comenzarLectura() throws InterruptedException;
    public void terminarEscritura() throws InterruptedException;
    public void terminarLectura() throws InterruptedException;
}