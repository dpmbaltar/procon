package procon.tp02.e05;

import java.util.concurrent.ThreadLocalRandom;

public class Auto extends Vehiculo implements Runnable {

    private Surtidor surtidor;

    public Auto(int modelo, String marca, String patente, int kmTanque,
            int kmFaltantesParaElService, Surtidor surtidor) {
        super(modelo, marca, patente, kmTanque, kmFaltantesParaElService);
        this.surtidor = surtidor;
    }

    private void andar() {
        try {
            int t = ThreadLocalRandom.current().nextInt(1, 9);
            Thread.sleep(t * 10);
            kmFaltantesParaElService--;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void cargar() {
        surtidor.usar(this);
    }

    @Override
    public void run() {
        while (true) {
            andar();
            if (kmFaltantesParaElService <= 0) {
                cargar();
            }
        }
    }

    @Override
    public String toString() {
        return "Auto [marca=" + marca + ",modelo=" + modelo + ",patente="
                + patente + "]";
    }
}
