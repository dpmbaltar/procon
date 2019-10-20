package procon.tp05.e04;

import java.util.LinkedList;
import java.util.Queue;

public class GestionaTraficoMonitor implements GestionaTrafico {

    /* Máximo de autos que pueden cruzar desde un lado hasta dejar pasar desde el lado opuesto */
    private static final int LIMITE_AUTOS = 2;
    /* Dirección con prioridad de paso, según el límite de autos por vez establecido */
    private char direccionActual;
    private int cantidadPasaronNorte = 0;
    private int cantidadPasaronSur = 0;

    private int quierenPasarNorte = 0;
    private int quierenPasarSur = 0;

    private final Queue<Object> autosNorte = new LinkedList();
    private final Queue<Object> autosSur = new LinkedList();

    /* Para esta condición se asume que siempre van a seguir pasando autos */
    private boolean tienePrioridad(char desde) {
        boolean puedePasar = true;
        int diferenciaPasaron = cantidadPasaronNorte - cantidadPasaronSur;

        if (desde == 'N') { // Pasaron el límite desde norte
            if (diferenciaPasaron >= LIMITE_AUTOS && quierenPasarSur > 0) {
                puedePasar = false;System.out.println("Blocked: " + cantidadPasaronNorte + " - " + cantidadPasaronSur);
            } else if (!autosSur.isEmpty()) {
                puedePasar = false;
            }
        } else if (desde == 'S') { // Pasaron el límite desde sur
            if (diferenciaPasaron <= -LIMITE_AUTOS && quierenPasarNorte > 0) {
                puedePasar = false;System.out.println("Blocked: " + cantidadPasaronNorte + " - " + cantidadPasaronSur);
            } else if (!autosNorte.isEmpty()) {
                puedePasar = false;
            }
        }

        return puedePasar;
    }

    @Override
    public synchronized void entrarCocheDelNorte(int auto) throws InterruptedException {
        quierenPasarNorte++;

        // Esperar si están pasando autos desde el sur
        while (/*!autosSur.isEmpty() || */!tienePrioridad('N'))
            wait();

        quierenPasarNorte--;
        cantidadPasaronNorte++;
        autosNorte.add(auto);
        System.out.println("Auto N-" + auto + " entra desde NORTE");

        // Respetar orden de ingreso
        while (!autosNorte.peek().equals(auto))
            wait();
    }

    @Override
    public synchronized void entrarCocheDelSur(int auto) throws InterruptedException {
        quierenPasarSur++;

        // Esperar si están pasando autos desde el norte
        while (/*!autosNorte.isEmpty() || */!tienePrioridad('S'))
            wait();

        quierenPasarSur--;
        cantidadPasaronSur++;
        autosSur.add(auto);
        System.out.println("Auto S-" + auto + " entra desde SUR");

        // Respetar orden de ingreso
        while (!autosSur.peek().equals(auto))
            wait();
    }

    @Override
    public synchronized void salirCocheDelNorte(int auto) {
        System.out.println("Auto N-" + auto + " sale desde NORTE");
        autosNorte.poll();
        notifyAll();
    }

    @Override
    public synchronized void salirCocheDelSur(int auto) {
        System.out.println("Auto S-" + auto + " sale desde SUR");
        autosSur.poll();
        notifyAll();
    }

}
