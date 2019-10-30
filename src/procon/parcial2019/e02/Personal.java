package procon.parcial2019.e02;

import java.util.Random;

public class Personal implements Runnable {

    private static int personal = 0;
    private final Observatorio observatorio;

    public Personal(Observatorio observatorio) {
        this.observatorio = observatorio;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            try {
                String nombre = generarNombre();
                observatorio.entraPersonal(nombre);
                Thread.sleep(random.nextInt(10) * 100 + 1000);
                observatorio.salePersonal(nombre);
                Thread.sleep(random.nextInt(10) * 1000 + 2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static final String generarNombre() {
        personal++;
        return "Personal-" + personal;
    }

}
