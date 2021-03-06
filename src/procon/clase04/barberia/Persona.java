package procon.clase04.barberia;

abstract public class Persona implements Runnable {

    private String nombre;
    
    public Persona() {
        this(null);
    }
    
    public Persona(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public boolean esCliente() {
        return false;
    }
    
    public boolean esBarbero() {
        return false;
    }
    
    @Override
    abstract public void run();
    
    public boolean equals(Persona p) {
        return nombre.equals(p.getNombre());
    }
}
