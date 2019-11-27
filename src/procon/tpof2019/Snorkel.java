package procon.tpof2019;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * "Disfruta de Snorkel ilimitado"
 * Implementado con Lock.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Snorkel {

    /**
     * Cantidad de equipos de snorkel.
     */
    public static final int CANTIDAD_EQUIPOS_SNORKEL = 20;

    /**
     * La cantidad de equipos completos.
     */
    private int equiposDisponibles;

    /**
     * Mutex.
     */
    private final Lock mutex = new ReentrantLock();

    /**
     * Condición de disponibilidad de equipos.
     */
    private final Condition hayEquipoDisponible = mutex.newCondition();

    /**
     * Cola de espera para adquirir equipos.
     */
    private final LinkedList<String> cola = new LinkedList<>();

    /**
     * Vista del parque.
     */
    private final VistaParque vista = VistaParque.getInstancia();

    /**
     * Constructor vacío.
     */
    public Snorkel() {
        this(CANTIDAD_EQUIPOS_SNORKEL);
    }

    /**
     * Constructor con la cantidad de equipos.
     *
     * @param equipos la cantidad de equipos completos
     */
    public Snorkel(int equiposDisponibles) {
        this.equiposDisponibles = equiposDisponibles;
    }

    /**
     * Visitante adquiere un equipo.
     *
     * @return verdadero si adquirió un equipo, falso en caso contrario
     * @throws InterruptedException
     */
    public boolean adquirirEquipo() throws InterruptedException {
        boolean tieneEquipo = false;
        boolean espero = false;
        int tiempoEspera = 15;
        String visitante = Thread.currentThread().getName();

        mutex.lock();
        try {
            cola.add(visitante);

            // Esperar hasta 15 minutos
            while ((equiposDisponibles == 0 || !cola.peek().equals(visitante)) && !espero && tiempoEspera > 0) {
                espero = !hayEquipoDisponible.await(Tiempo.enMinutos(tiempoEspera), TimeUnit.MILLISECONDS);
                tiempoEspera -= 5;
            }

            // Se vuelve a comprobar en caso de que haber esperado más de 15 minutos
            if (equiposDisponibles > 0 && cola.peek().equals(visitante)) {
                equiposDisponibles--;
                cola.poll();
                tieneEquipo = true;

                vista.printSnorkel(String.format("%s adquiere equipo", Thread.currentThread().getName()));
                vista.sacarEquipoSnorkel();
            }
        } finally {
            mutex.unlock();
        }

        return tieneEquipo;
    }

    /**
     * Visitante inicia la actividad.
     *
     * @throws InterruptedException
     */
    public void iniciar() throws InterruptedException {
        vista.printSnorkel(String.format("%s inicia snorkel", Thread.currentThread().getName()));
        vista.agregarVisitanteSnorkel();

        Thread.sleep(Tiempo.entreMinutos(30, 60));
    }

    /**
     * Visitante finaliza la actividad.
     */
    public void finalizar() {
        vista.printSnorkel(String.format("%s finaliza snorkel", Thread.currentThread().getName()));
        vista.sacarVisitanteSnorkel();
    }

    /**
     * Visitante devuelve un equipo.
     */
    public void devolverEquipo() {
        mutex.lock();
        try {
            vista.printSnorkel(String.format("%s devuelve equipo", Thread.currentThread().getName()));
            vista.agregarEquipoSnorkel();

            equiposDisponibles++;
            hayEquipoDisponible.signalAll();
        } finally {
            mutex.unlock();
        }
    }

}
