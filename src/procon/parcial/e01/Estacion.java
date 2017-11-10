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

public class Estacion {

    public static void main(String[] args) {
        Random r = new Random();
        Tren tren;
        TramoCompartido tramoCompartido = new TramoCompartido();
        for (int i = 1; i <= 10; i++) {
            tren = new Tren(tramoCompartido);
            tren. setOrden(i);
            tren.setNombre("Tren #"+i);
            if (r.nextInt(10) < 5) {
                tren.setTramo('A');
            } else {
                tren.setTramo('B');
            }

            (new Thread(tren)).start();

            try {
                Thread.sleep(r.nextInt(10) * 100);
            } catch (InterruptedException e) {}
        }
    }
}
