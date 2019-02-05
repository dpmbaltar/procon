package procon.tp02.e06;

public class Cajera {
    private String nombre;
    
    public Cajera(String nombre) {
        this.nombre = nombre;
    }

    // Agregar Constructor, y métodos de acceso
    public void procesarCompra(Cliente cliente, long timeStamp) {
        System.out.println("La cajera " + this.nombre
                + " COMIENZA A PROCESAR LA COMPRA DEL CLIENTE "
                + cliente.getNombre() + " EN EL TIEMPO: "
                + (System.currentTimeMillis() - timeStamp) / 1000 + "seg");
        for (int i = 0; i < cliente.getCarroCompra().length; i++) {
            this.esperarXsegundos(cliente.getCarroCompra()[i].getTiempo());
            System.out.println("Procesado el producto " + (i + 1)
                    + " ->Tiempo: "
                    + (System.currentTimeMillis() - timeStamp) / 1000 + "seg");
        }
        System.out.println("La cajera " + this.nombre
                + " HA TERMINADO DE PROCESAR " + cliente.getNombre()
                + " EN EL TIEMPO: "
                + (System.currentTimeMillis() - timeStamp) / 1000 + "seg");
    }

    private void esperarXsegundos(int seg) {
        try {
            Thread.sleep(seg * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
