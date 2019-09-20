package procon.tpo.filosofos;

/**
 * TPO Filósofos 2019
 */
public class Main {

    public static void main(String[] args) {
        Mesa mesa = new Mesa();
        Thread[] hilos = new Thread[5];

        for (int i = 0; i < 5; i++) {
            Filosofo filosofo = new Filosofo(i, mesa);
            hilos[i] = new Thread(filosofo, "Filósofo-" + i);
            hilos[i].start();
        }
    }

}
