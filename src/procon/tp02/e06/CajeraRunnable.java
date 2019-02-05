package procon.tp02.e06;

public class CajeraRunnable implements Runnable {
    private String nombre;
    private Cliente cliente;
    private long initialTime;

    public CajeraRunnable(String nombre, Cliente cliente, long initialTime) {
        this.nombre = nombre;
        this.cliente = cliente;
        this.initialTime = initialTime;
    }

    @Override
    public void run() {
        double total = 0.0;
        System.out.println("La cajera " + this.nombre
                + " COMIENZA A PROCESAR LA COMPRA DEL CLIENTE "
                + this.cliente.getNombre() + " EN EL TIEMPO: "
                + (System.currentTimeMillis() - this.initialTime) / 1000
                + "seg");
        for (int i = 0; i < this.cliente.getCarroCompra().length; i++) {
            this.esperarXsegundos(cliente.getCarroCompra()[i].getTiempo());
            total+= cliente.getCarroCompra()[i].getPrecio();
            System.out.println("Procesado el producto " + (i + 1)
                    + " del cliente " + this.cliente.getNombre() + "->Tiempo: "
                    + (System.currentTimeMillis() - this.initialTime) / 1000
                    + "seg");
        }
        System.out
                .println("La cajera" + this.nombre + "HA TERMINADO DE PROCESAR"
                        + this.cliente.getNombre() + " EN EL TIEMPO: "
                        + (System.currentTimeMillis() - this.initialTime) / 1000
                        + "seg - TOTAL: " + total);
    }

    private void esperarXsegundos(int seg) {
        try {
            Thread.sleep(seg * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
