package procon.tp03.e05;

import java.util.concurrent.ThreadLocalRandom;

public class SynchronizedCounterTest {
    public static class CounterTest implements Runnable {
        private Counter counter;

        public CounterTest(Counter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            try {
                int r = ThreadLocalRandom.current().nextInt(1, 5);
                Thread.sleep(r * 100);
                counter.increment();
                counter.decrement();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        testSynchronizedCounter(new SynchronizedCounter());
        testSynchronizedCounter(new SynchronizedObjectCounter());
    }

    public static void testSynchronizedCounter(Counter sc) {
        CounterTest c1 = new CounterTest(sc);
        CounterTest c2 = new CounterTest(sc);
        CounterTest c3 = new CounterTest(sc);
        Thread t1 = new Thread(c1);
        Thread t2 = new Thread(c2);
        Thread t3 = new Thread(c3);
        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(sc.value());
    }
}
