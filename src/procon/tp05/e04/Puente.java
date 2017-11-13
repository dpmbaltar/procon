package procon.tp05.e04;

import java.util.Random;

public class Puente {

    public static final int CANTIDAD_DE_AUTOS = 10;

    public static void main(String[] args) {
        int demora;
        Random random = new Random();
        GestionaTrafico gestionaTrafico = new GestionaTraficoConSemaforo();
        Coche coche;

        // Crear coches
        for (int n = 1; n <= CANTIDAD_DE_AUTOS; n++) {
            coche = new Coche(gestionaTrafico);
            coche.setId(n);

            // Asignar "desde" aleatoreamente
            if (random.nextInt(10) < 5) {
                coche.setDesde('N');
            } else {
                coche.setDesde('S');
            }

            // Iniciar ejecución
            (new Thread(coche)).start();

            // Esperar por un tiempo antes del nuevo tren
            try {
                demora = random.nextInt(10) * 100;
                Thread.sleep(demora);
            } catch (InterruptedException e) {}
        }
    }
}
