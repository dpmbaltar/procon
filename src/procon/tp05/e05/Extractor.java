package procon.tp05.e05;

import java.util.concurrent.ThreadLocalRandom;

public class Extractor implements Runnable {

    private final BufferOscilante buffer;

    public Extractor(BufferOscilante buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Integer dato = (Integer) buffer.extraer();
                Thread.sleep(ThreadLocalRandom.current().nextInt(8, 10) * 100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
