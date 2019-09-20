package procon.tpo.filosofos;

public class Main {

    public static void main(String[] args) {
        Thread[] hilos = new Thread[5];
        Tenedor[] tenedores = new Tenedor[5];

        // Crear tenedores
        for (int i = 0; i < 5; i++) {
            tenedores[i] = new Tenedor(i + 1);
        }

        // Crear filósofos con los tenedores correspondientes
        for (int i = 0; i < 5; i++) {
            Filosofo filosofo = new Filosofo(tenedores[i], tenedores[(i + 1) % 5]);
            hilos[i] = new Thread(filosofo, "Filósofo " + (i + 1));
            hilos[i].start();
        }
    }

}
