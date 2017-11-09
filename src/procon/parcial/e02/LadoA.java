package procon.parcial.e02;

import java.util.Random;

public class LadoA implements Runnable {

    private Trasbordador trasbordador;

    public LadoA(Trasbordador t) {
        trasbordador = t;
    }

    @Override
    public void run() {
        while (trasbordador.estaFuncionando()) {
            int demora = (new Random()).nextInt(10) * 100;
            try {
                trasbordador.cargar("Auto");
                Thread.sleep(demora);
            } catch (InterruptedException e) {}
        }
    }
}
