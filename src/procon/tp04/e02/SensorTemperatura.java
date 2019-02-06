package procon.tp04.e02;

import java.util.concurrent.ThreadLocalRandom;

public class SensorTemperatura implements Runnable {
    private GestorSala gestorSala;
    private int intervalo;

    public SensorTemperatura(GestorSala gestorSala, int intervalo) {
        this.gestorSala = gestorSala;
        this.intervalo = intervalo;
    }

    @Override
    public void run() {
        try {
            ThreadLocalRandom r = ThreadLocalRandom.current();
            while (true) {
                gestorSala.notificarTemperatura(r.nextInt(20, 40));
                gestorSala.mostrarEstado();
                Thread.sleep(intervalo * 1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
