package procon.tp05.e02;

public class LibroMonitor implements Libro {

    private int leyendo = 0;
    private int escribiendo = 0;
    private boolean escrito = false;

    @Override
    public synchronized void comenzarEscritura() throws InterruptedException {
        while (leyendo > 0 || escribiendo > 0) {
            wait();
        }

        escribiendo++;
        System.out.println(Thread.currentThread().getName() + "> comienza escritura");
    }

    @Override
    public synchronized void comenzarLectura() throws InterruptedException {
        while (escribiendo > 0 || !escrito) {
            wait();
        }

        leyendo++;
        System.out.println(Thread.currentThread().getName() + "> comienza lectura ");
    }

    @Override
    public synchronized void terminarEscritura() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "> termina escritura");
        escribiendo--;

        if (!escrito) {
            escrito = true;
        }

        notifyAll();
    }

    @Override
    public synchronized void terminarLectura() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "> termina lectura");
        leyendo--;
        notifyAll();
    }

}
