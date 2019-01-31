package procon.clase04.barberia;

public class Cliente extends Persona {
    
    private Barberia barberia;
    
    public Cliente(String nombre, Barberia barberia) {
        super(nombre);
        this.barberia = barberia;
    }

    @Override
    public boolean esCliente() {
        return true;
    }

    @Override
    public void run() {
        try {
            barberia.entrar(this);
            barberia.salir(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
