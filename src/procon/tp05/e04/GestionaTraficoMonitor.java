package procon.tp05.e04;

import java.util.LinkedList;
import java.util.Queue;

public class GestionaTraficoMonitor implements GestionaTrafico {

    /* Máximo de autos que pueden cruzar desde un lado hasta dejar pasar desde el lado opuesto */
    private static final int LIMITE_AUTOS_POR_VEZ = 10;
    /* Dirección con prioridad de paso, según el límite de autos por vez establecido */
    private char prioridad;
    private int cantidadAutosNorte = 0;
    private int cantidadAutosSur = 0;

    private final Queue<Object> autosNorte = new LinkedList();
    private final Queue<Object> autosSur = new LinkedList();

    @Override
    public synchronized void entrarCocheDelNorte(int auto) throws InterruptedException {
        // Esperar si están pasando autos desde el sur
        while (!autosSur.isEmpty())
            wait();

        autosNorte.add(auto);
        System.out.println("Auto N-" + auto + " entra desde NORTE");

        // Respetar orden de ingreso
        while (!autosNorte.peek().equals(auto))
            wait();
    }

    @Override
    public synchronized void entrarCocheDelSur(int auto) throws InterruptedException {
        // Esperar si están pasando autos desde el norte
        while (!autosNorte.isEmpty())
            wait();

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
