/**
 * Parcial 30/10/2017
 * Implementación del 1er ejercicio del parcial tratando a cada Tren como un
 * nuevo Thread, que pasará solo 1 vez por la vía compartida según corresponda.
 * Los trenes pasan de forma aleatoria por el tramo compartido cada un tiempo
 * aleatorio desde el tramo A o B, según sea generado.
 *
 *        B
 * ━━┳━━━━┻━━
 *   A
 */
package procon.parcial.e01;

import java.util.Random;

/**
 * Simula una estación por donde pasan los trenes por un tramo compartido.
 */
public class Estacion {

    /**
     * Cantidad de trenes que pasarán por el tramo compartido.
     */
    private static final int CANTIDAD_DE_TRENES = 10;

    public static void main(String[] args) {
        int demora;
        Random random = new Random();
        String nombre = "";
        TramoCompartido tramoCompartido = new TramoCompartidoConCerrojo();
        //TramoCompartido tramoCompartido = new TramoCompartidoConSemaforo();
        Tren tren;

        // Crear trenes e iniciarlos cada cierto tiempo
        for (int i = 1; i <= CANTIDAD_DE_TRENES; i++) {
            nombre = "Tren-"+i;
            tren = new Tren(tramoCompartido);
            tren.setNombre(nombre);

            // Asignar tramo aleatoreamente
            if (random.nextInt(10) < 5) {
                tren.setTramo('A');
            } else {
                tren.setTramo('B');
            }

            // Iniciar ejecución
            (new Thread(tren)).start();

            // Esperar por un tiempo antes del nuevo tren
            try {
                demora = random.nextInt(10) * 100;
                Thread.sleep(demora);
            } catch (InterruptedException e) {}
        }
    }
}
