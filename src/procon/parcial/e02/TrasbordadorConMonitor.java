package procon.parcial.e02;

import java.util.ArrayDeque;

public class TrasbordadorConMonitor implements Trasbordador {

    /**
     * Indica si el trasbordador está en funcionamiento.
     */
    private boolean funcionando;

    /**
     * Determina si el trasbordador fue o no al otro lado.
     */
    private boolean fue;

    /**
     * Contenedor de autos.
     */
    private ArrayDeque<String> autos;

    /**
     * Constructor.
     */
    public TrasbordadorConMonitor() {
        autos = new ArrayDeque<>(CAPACIDAD);
        funcionando = false;
        fue = false;
    }

    @Override
    public synchronized void ir() throws InterruptedException {
        // Esperar si aún no volvió del otro lado o no se cargaron los autos
        while (fue || autos.size() < 10)
            wait();

        System.out.println("Ir al otro lado...");
        fue = true;
        notifyAll();
    }

    @Override
    public synchronized void volver() throws InterruptedException {
        // Esperar si aún no llego del lado de carga o si faltan autos para
        // descargar del trasbordador
        while (!fue || autos.size() > 0)
            wait();

        System.out.println("Volver del otro lado...");
        fue = false;
        notifyAll();
    }

    @Override
    public synchronized void cargar(String auto) throws InterruptedException {
        // Esperar si el trasbordador aún no regresa del otro lado
        while (fue)
            wait();

        autos.add(auto);
        visualizarEstado();

        if (autos.size() >= 10)
            notifyAll();
    }

    @Override
    public synchronized void descargar() throws InterruptedException {
        // Esperar si el trasbordador aún no fue al otro lado
        while (!fue)
            wait();

        autos.remove();
        visualizarEstado();

        if (autos.size() <= 0)
            notifyAll();
    }

    @Override
    public boolean estaFuncionando() {
        return funcionando;
    }

    @Override
    public void iniciar() {
        funcionando = true;
    }

    @Override
    public void terminar() {
        funcionando = false;
    }

    private void visualizarEstado() {
        StringBuilder estado = new StringBuilder("[");
        for (int i = 0; i < CAPACIDAD; i++) {
            if (i < autos.size())
                estado.append("=");
            else
                estado.append(" ");
        }
        estado.append("]")
                .append(" ")
                .append(autos.size())
                .append("/")
                .append(CAPACIDAD);
        System.out.println(estado);
    }
}
