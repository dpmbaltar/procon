package procon.tp05.e04;

import java.util.Random;

public class Coche implements Runnable {

    private int id;
    private char desde;
    private GestionaTrafico gestionaTrafico;

    public Coche(GestionaTrafico gt) {
        this.id = 0;
        this.desde = ' ';
        this.gestionaTrafico = gt;
    }

    @Override
    public void run() {
        try {
            int demora = (new Random()).nextInt(5) * 100;
            if (desde == 'N') {
                gestionaTrafico.entrarCocheDelNorte(id);
                System.out.println("Coche "+id+" pasando desde el Norte...");
                Thread.sleep(demora);
                gestionaTrafico.salirCocheDelNorte(id);
            } else if (desde == 'S') {
                gestionaTrafico.entrarCocheDelSur(id);
                System.out.println("Coche "+id+" pasando desde el Sur...");
                Thread.sleep(demora);
                gestionaTrafico.salirCocheDelSur(id);
            }
        } catch (InterruptedException e) {}
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getDesde() {
        return desde;
    }

    public void setDesde(char desde) {
        this.desde = desde;
    }

}
