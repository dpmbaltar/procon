package procon.tp01.e06;

public class Main {
    public static void main(String[] args) {
        Cliente cliente1 = new Cliente("Cliente 1",
                new int[] { 2, 2, 1, 5, 2, 3 });
        Cliente cliente2 = new Cliente("Cliente 2",
                new int[] { 1, 3, 5, 1, 1 });
        // Cajera cajera1 = new Cajera("Cajera 1");
        // Tiempo inicial de referencia
        long initialTime = System.currentTimeMillis();
        // cajera1.procesarCompra(cliente1, initialTime);
        // cajera1.procesarCompra(cliente2, initialTime);
        // CajeraThread cajera1 = new CajeraThread("Cajera 1", cliente1,
        // initialTime);
        // CajeraThread cajera2 = new CajeraThread("Cajera 2", cliente2,
        // initialTime);
        CajeraRunnable cr1 = new CajeraRunnable("Cajera 1", cliente1,
                initialTime);
        CajeraRunnable cr2 = new CajeraRunnable("Cajera 2", cliente2,
                initialTime);
        Thread t1 = new Thread(cr1);
        Thread t2 = new Thread(cr2);
        t1.start();
        t2.start();
    }
}
