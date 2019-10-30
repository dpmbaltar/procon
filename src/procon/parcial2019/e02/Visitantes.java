package procon.parcial2019.e02;

import java.util.Random;

public class Visitantes implements Runnable {

    private static int visitante = 0;
    private final Observatorio observatorio;

    public Visitantes(Observatorio observatorio) {
        this.observatorio = observatorio;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            try {
                String nombre = generarNombre();
                boolean sillaDeRuedas = random.nextBoolean();
                observatorio.entraVisitante(nombre, sillaDeRuedas);
                Thread.sleep(random.nextInt(10) * 100 + 1000);
                observatorio.saleVisitante(nombre, sillaDeRuedas);
                Thread.sleep(random.nextInt(10) * 1000 + 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static final String generarNombre() {
        visitante++;
        return "Visitante-" + visitante;
    }

}
