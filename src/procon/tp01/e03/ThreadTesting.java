package procon.tp01.e03;

public class ThreadTesting {
    public static void main(String[] args) {
        Thread miHilo = new MiEjecucion();
        miHilo.start();
        // - Agregado para que se comporte de manera determinada
        try {
            miHilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // -
        System.out.println("En el main");
    }
}
