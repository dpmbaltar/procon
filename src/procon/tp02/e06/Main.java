package procon.tp02.e06;

public class Main {
    public static void main(String[] args) {
        Cliente cliente1 = Cliente.aleatorio();
        Cliente cliente2 = Cliente.aleatorio();
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
