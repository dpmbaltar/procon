package procon.tp04.e04;

import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        //Buffer<Integer> buffer = new BufferMonitor<>();
        Buffer<Integer> buffer = new BufferSemaphore<>();
        Thread productor, consumidor;
        productor = new Thread(new Productor<Integer>(buffer) {
            private int elemento = 0;
            public Integer producir() throws InterruptedException {
                Thread.sleep(ThreadLocalRandom.current().nextInt(100, 500));
                elemento++;
                System.out.println("Productor>>> " + elemento);
                return elemento;
            }
        }, "Productor");
        consumidor = new Thread(new Consumidor<Integer>(buffer) {
            public void consumir(Integer elemento) throws InterruptedException {
                System.out.println("Consumidor>>> " + elemento);
                Thread.sleep(ThreadLocalRandom.current().nextInt(100, 500));
            }
        }, "Consumidor");
        productor.start();
        consumidor.start();
    }

}
