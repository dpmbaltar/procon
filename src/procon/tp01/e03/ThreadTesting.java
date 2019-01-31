package procon.tp01.e03;

/**
 * (a) Sin el código agregado entre (b), no se puede asegurar cual hilo
 * terminará primero, si el del main() o miHilo.
 */
public class ThreadTesting {
    public static void main(String[] args) {
        Thread miHilo = new MiEjecucion();
        miHilo.start();
        // - (b)
        // - Agregado para que se comporte de manera determinada
        try {
            miHilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // - (b)
        System.out.println("En el main");
    }
}
