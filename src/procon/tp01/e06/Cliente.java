package procon.tp01.e06;

import java.util.concurrent.ThreadLocalRandom;

public class Cliente {
    private static int id = 0;
    private String nombre;
    private int[] carroCompra;
    
    public Cliente() {
        this(null, null);
    }
    
    public Cliente(String nombre, int[] carroCompra) {
        this.nombre = nombre;
        this.carroCompra = carroCompra;
    }
    
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int[] getCarroCompra() {
        return carroCompra;
    }
    public void setCarroCompra(int[] carroCompra) {
        this.carroCompra = carroCompra;
    }

    public static Cliente crear() {
        int cantidad = ThreadLocalRandom.current().nextInt(1,10);
        int[] carroCompra = new int[cantidad];
        for (int i = 0; i < carroCompra.length; i++) {
            carroCompra[i] = ThreadLocalRandom.current().nextInt(1,3);
        }
        return new Cliente("Cliente " + ++id, carroCompra);
    }
}
