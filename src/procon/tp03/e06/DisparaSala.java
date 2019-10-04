package procon.tp03.e06;

/**
 * Corregido con Semáforos, Monitor y Lock - Bien
 * Nota: se puede mejorar la implementación con locks, haciendo similar a los semáforos, utilizando una condición por
 * fumador
 */
public class DisparaSala {

    public static void main(String[] args) {
        //SalaFumadores sala = new SalaFumadoresSemaphore();
        SalaFumadores sala = new SalaFumadoresMonitor();
        //SalaFumadores sala = new SalaFumadoresLock();
        Thread[] fumadores = new Thread[3];

        for (int i = 0; i < fumadores.length; i++)
            fumadores[i] = new Thread(new Fumador(i + 1, sala));

        for (int i = 0; i < fumadores.length; i++)
            fumadores[i].start();

        Thread agente = new Thread(new Agente(sala));
        agente.start();
    }
}
