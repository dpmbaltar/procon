package procon.tp05.e05;

/**
 * TP05 - Ejercicio 05
 * Buffer Oscilante
 */
public class Main {

    private static final int CANTIDAD_GENERADORES = 5;
    private static final int CANTIDAD_EXTRACTORES = 5;

    public static void main(String[] args) {
        BufferOscilante buffer = new BufferOscilanteSemaforo();
        Thread[] generadores = new Thread[CANTIDAD_GENERADORES];
        Thread[] extractores = new Thread[CANTIDAD_EXTRACTORES];

        // Crear hilos
        for (int i = 0; i < generadores.length; i++)
            generadores[i] = new Thread(new Generador(buffer), "Generador-" + i);
        for (int i = 0; i < extractores.length; i++)
            extractores[i] = new Thread(new Extractor(buffer), "Extractor-" + i);

        // Iniciar hilos
        for (int i = 0; i < generadores.length; i++)
            generadores[i].start();
        for (int i = 0; i < extractores.length; i++)
            extractores[i].start();
    }

}
