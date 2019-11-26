package procon.tpof2019;

/**
 * "Disfruta de Snorkel ilimitado"
 * Implementado con Monitor.
 */
public class Snorkel {

    /**
     * La cantidad de equipos completos.
     */
    private int equiposDisponibles;

    /**
     * Vista del parque.
     */
    private final VistaParque vista = VistaParque.getInstancia();

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
    public synchronized boolean adquirirEquipo() throws InterruptedException {
        boolean tieneEquipo = false;

        // Esperar una sola vez entre 10 y 15 minutos
        if (equiposDisponibles == 0) {
            wait(Tiempo.entreMinutos(10, 15));
        }

        if (equiposDisponibles > 0) {
            equiposDisponibles--;
            tieneEquipo = true;

            vista.printSnorkel(String.format("%s adquiere equipo", Thread.currentThread().getName()));
            vista.sacarEquipoSnorkel();
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
    public synchronized void devolverEquipo() {
        equiposDisponibles++;
        vista.printSnorkel(String.format("%s devuelve equipo", Thread.currentThread().getName()));
        vista.agregarEquipoSnorkel();
    }

}
