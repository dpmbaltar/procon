package procon.tp02.e06;

import java.util.concurrent.ThreadLocalRandom;

public class Producto {

    private static final String[] VARIOS = { "Pan", "Huevos", "Fideos", "Carne",
            "Pollo", "Jugo", "Jabón", "Shampoo", "Pasta dental", "Galletas",
            "Gaseosa", "Papas", "Cebollas", "Zanahorias", "Ajo", "Tomates",
            "Perejil", "Sal", "Aceite", "Leche" };

    private String nombre;
    private double precio;
    private int tiempo;

    public Producto(String nombre, double precio, int tiempo) {
        this.nombre = nombre;
        this.precio = precio;
        this.tiempo = tiempo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public static Producto aleatorio() {
        String n = VARIOS[ThreadLocalRandom.current().nextInt(0,
                VARIOS.length - 1)];
        double p = ThreadLocalRandom.current().nextDouble(5.0, 250.00);
        int t = ThreadLocalRandom.current().nextInt(1, 3);
        return new Producto(n, p, t);
    }
}
