package procon.clase04.barberia;

import java.util.concurrent.ThreadLocalRandom;

public class Barbero extends Persona {
    
    private Barberia barberia;
    
    public Barbero(String nombre, Barberia barberia) {
        super(nombre);
        this.barberia = barberia;
    }

    @Override
    public boolean esBarbero() {
        return true;
    }

    @Override
    public void run() {
        while (true) {
            try {
                cortar();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void cortar() throws InterruptedException {
        barberia.iniciarCorte(this);
        int demora = ThreadLocalRandom.current().nextInt(1, 5) * 100;
        Thread.sleep(demora);
        barberia.finalizarCorte();
    }
}
