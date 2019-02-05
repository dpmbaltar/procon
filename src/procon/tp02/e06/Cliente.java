package procon.tp02.e06;

import java.util.concurrent.ThreadLocalRandom;

public class Cliente {
    private static int id = 0;
    private String nombre;
    private Producto[] carroCompra;
    
    public Cliente() {
        this(null, null);
    }
    
    public Cliente(String nombre, Producto[] carroCompra) {
        this.nombre = nombre;
        this.carroCompra = carroCompra;
    }
    
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Producto[] getCarroCompra() {
        return carroCompra;
    }
    public void setCarroCompra(Producto[] carroCompra) {
        this.carroCompra = carroCompra;
    }

    public static Cliente aleatorio() {
        int cantidad = ThreadLocalRandom.current().nextInt(1,15);
        Producto[] carroCompra = new Producto[cantidad];
        for (int i = 0; i < carroCompra.length; i++) {
            carroCompra[i] = Producto.aleatorio();
        }
        return new Cliente("Cliente " + ++id, carroCompra);
    }
}
