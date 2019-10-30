package procon.parcial2019.e02;

import java.util.Random;

public class Profesionales implements Runnable {

    private static int profesionales = 0;
    private final Observatorio observatorio;

    public Profesionales(Observatorio observatorio) {
        this.observatorio = observatorio;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            try {
                String nombre = generarNombre();
                observatorio.entraProfesional(nombre);
                Thread.sleep(random.nextInt(10) * 100 + 1000);
                observatorio.saleProfesional(nombre);
                Thread.sleep(random.nextInt(10) * 1000 + 3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static final String generarNombre() {
        profesionales++;
        return "Profesional-" + profesionales;
    }

}
