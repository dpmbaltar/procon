package procon.tp05.e04;

/**
 * TP05 - Ejercicio 04
 * Puente
 */
public class Main {

    private static final int CANTIDAD_AUTOS = 10;

    public static void main(String[] args) {
        GestionaTrafico gt = new GestionaTraficoMonitor();
        Thread traficoNorte = new Thread(new Trafico('N', gt));
        Thread traficoSur = new Thread(new Trafico('S', gt));
        traficoNorte.start();
        traficoSur.start();
    }

}
