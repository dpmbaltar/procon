package procon.parcial.e01;

import java.util.Random;

public class LadoA implements Runnable {

    private static int tren = 0;
    private final TramoCompartido tc;

    public LadoA(TramoCompartido tc) {
        this.tc = tc;
    }

    private synchronized static final String generarTren() {
        return "Tren A-" + (++tren);
    }

    @Override
    public void run() {
        Random aleatorio = new Random();
        String tren = null;
        try {
            while (true) {
                tren = generarTren();
                tc.entrarDesdeLadoA(tren);
                Thread.sleep(aleatorio.nextInt(10) * 100 + 1000); // De 1 a 2 seg.
                tc.salirDesdeLadoA(tren);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
