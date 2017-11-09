package procon.parcial.e02;

import java.util.Random;

public class LadoB implements Runnable {

    private Trasbordador trasbordador;

    public LadoB(Trasbordador t) {
        trasbordador = t;
    }

    @Override
    public void run() {
        while (trasbordador.estaFuncionando()) {
            int demora = (new Random()).nextInt(10) * 100;
            try {
                trasbordador.descargar();
                Thread.sleep(demora);
            } catch (InterruptedException e) {}
        }
    }

}
