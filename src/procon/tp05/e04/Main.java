package procon.tp05.e04;

/**
 * TP05 - Ejercicio 04
 * Puente
 */
public class Main {

    public static void main(String[] args) {
        //GestionaTrafico gt = new GestionaTraficoMonitor();
        //GestionaTrafico gt = new GestionaTraficoCerrojo();
        GestionaTrafico gt = new GestionaTraficoConSemaforo();
        Thread[] trafico = new Thread[5];

        for (int i = 0; i < 5; i++)
            trafico[i] = new Thread(new Trafico(gt));
        for (int i = 0; i < 5; i++)
            trafico[i].start();
    }

}
