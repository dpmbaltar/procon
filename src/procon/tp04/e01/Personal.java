package procon.tp04.e01;

public class Personal extends Thread {
    private String nombre;
    private Saludo saludo;
    private boolean esJefe;

    Personal(Saludo saludo, String nombre) {
        this(saludo, nombre, false);
    }

    Personal(Saludo saludo, String nombre, boolean esJefe) {
        this.esJefe = esJefe;
        this.nombre = nombre;
        this.saludo = saludo;
    }

    public void run() {
        System.out.println("(" + nombre + " llega)");
        saludo.marcar();
        if (esJefe) {
            saludo.saludoJefe();
        } else {
            saludo.esperarJefe(nombre);
        }
    }
}
