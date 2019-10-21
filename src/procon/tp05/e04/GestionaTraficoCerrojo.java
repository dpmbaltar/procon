package procon.tp05.e04;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author dpmbaltar
 */
public class GestionaTraficoCerrojo implements GestionaTrafico {

    private final Lock mutex = new ReentrantLock();
    private final Condition pasarDesdeNorte = mutex.newCondition();
    private final Condition pasarDesdeSur = mutex.newCondition();
    private final Condition ordenNorte = mutex.newCondition();
    private final Condition orderSur = mutex.newCondition();
    private final Condition ordenPasando = mutex.newCondition();

    private final static int LIMITE_AUTOS = 2;

    private int cantidadPasaronNorte = 0;
    private int cantidadPasaronSur = 0;
    private int autosPasandoNorte = 0;
    private int autosPasandoSur = 0;

    private final Queue<Object> autosNorte = new LinkedList();
    private final Queue<Object> autosSur = new LinkedList();
    private final Queue<Object> autosPasando = new LinkedList();

    @Override
    public void entrarCocheDelNorte(int auto) throws InterruptedException {
        mutex.lock();
        try {
            // Auto entra en la fila del norte
            autosNorte.add(auto);

            // Esperar si hay autos pasando desde el sur
            // O si pasaron muchos desde el norte y hay esperando desde el sur
            while (autosPasandoSur > 0
                    || (!autosSur.isEmpty() && (cantidadPasaronNorte - cantidadPasaronSur) >= LIMITE_AUTOS))
                pasarDesdeNorte.await();

            // Respetar orden de ingreso
            while (!autosNorte.peek().equals(auto))
                ordenNorte.await();

            // Auto comienza a pasar
            autosPasando.add(auto);
            autosNorte.poll();
            ordenNorte.signalAll();
            autosPasandoNorte++;
            System.out.println("Auto " + auto + " entra desde NORTE");
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public void entrarCocheDelSur(int auto) throws InterruptedException {
        mutex.lock();
        try {
            // Auto entra en la fila del sur
            autosSur.add(auto);

            // Esperar si hay autos pasando desde el norte
            // O si pasaron muchos desde el sur y hay esperando desde el norte
            while (autosPasandoNorte > 0
                    || (!autosNorte.isEmpty() && (cantidadPasaronSur - cantidadPasaronNorte) >= LIMITE_AUTOS))
                pasarDesdeSur.await();

            // Respetar orden de ingreso
            while (!autosSur.peek().equals(auto))
                orderSur.await();

            // Auto comienza a pasar
            autosPasando.add(auto);
            autosSur.poll();
            orderSur.signalAll();
            autosPasandoSur++;
            System.out.println("Auto " + auto + " entra desde SUR");
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public void salirCocheDelNorte(int auto) throws InterruptedException {
        mutex.lock();
        try {
            // Respetar orden de salida
            while (!autosPasando.peek().equals(auto))
                ordenPasando.await();

            System.out.println("Auto " + auto + " sale desde NORTE");
            cantidadPasaronNorte++;
            autosPasando.poll();
            ordenPasando.signalAll();
            autosPasandoNorte--;

            // Notificar autos del sur si ya no hay pasando desde el norte
            if (autosPasandoNorte == 0)
                pasarDesdeSur.signalAll();
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public void salirCocheDelSur(int auto) throws InterruptedException {
         mutex.lock();
        try {
            // Respetar orden de salida
            while (!autosPasando.peek().equals(auto))
                ordenPasando.await();

            System.out.println("Auto " + auto + " sale desde SUR");
            cantidadPasaronSur++;
            autosPasando.poll();
            ordenPasando.signalAll();
            autosPasandoSur--;

            // Notificar autos del norte si ya no hay pasando desde el sur
            if (autosPasandoSur == 0)
                pasarDesdeNorte.signalAll();
        } finally {
            mutex.unlock();
        }
    }

}
