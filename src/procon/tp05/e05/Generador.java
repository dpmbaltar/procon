package procon.tp05.e05;

import java.util.concurrent.ThreadLocalRandom;

public class Generador implements Runnable {

    private final BufferOscilante buffer;

    public Generador(BufferOscilante buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Integer dato = ThreadLocalRandom.current().nextInt(0, 100);

                if (buffer.insertar(dato)) {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(8, 10) * 100);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
