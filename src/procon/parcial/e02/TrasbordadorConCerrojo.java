package procon.parcial.e02;

import java.util.ArrayDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TrasbordadorConCerrojo implements Trasbordador {

    private final Lock mutex = new ReentrantLock();
    private final Condition puedeCargar = mutex.newCondition();
    private final Condition puedeDescargar = mutex.newCondition();
    private final Condition puedeIr = mutex.newCondition();
    private final Condition puedeVolver = mutex.newCondition();

    /**
     * Indica si el trasbordador está en funcionamiento.
     */
    private boolean funcionando;

    /**
     * Determina si el trasbordador fue o no al otro lado.
     */
    private boolean cruzo;

    /**
     * Contenedor de autos.
     */
    private ArrayDeque<String> autos;

    /**
     * Constructor.
     */
    public TrasbordadorConCerrojo() {
        autos = new ArrayDeque<>(CAPACIDAD);
        funcionando = false;
        cruzo = false;
    }

    @Override
    public void ir() throws InterruptedException {
        mutex.lock();
        try {
            // Esperar si aún no volvió del otro lado o no se cargaron los autos
            while (cruzo || autos.size() < 10)
                puedeIr.await();

            System.out.println("Ir al otro lado...");
            cruzo = true;
            puedeDescargar.signal();
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public void volver() throws InterruptedException {
        mutex.lock();
        try {
            // Esperar si aún no llego del lado de carga o si faltan autos para descargar
            while (!cruzo || autos.size() > 0)
                puedeVolver.await();

            // Volver
            System.out.println("Volver del otro lado...");
            cruzo = false;
            puedeCargar.signal();
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public void cargar(String auto) throws InterruptedException {
        mutex.lock();
        try {
            // Esperar si el trasbordador aún no regresa del otro lado o si está lleno
            while (cruzo || autos.size() == 10)
                puedeCargar.await();

            // Cargar auto
            System.out.print(String.format("Carga %s", auto));
            autos.add(auto);
            visualizarEstado();

            // Notificar que está lleno
            if (autos.size() == 10)
                puedeIr.signal();
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public void descargar() throws InterruptedException {
        mutex.lock();
        try {
            // Esperar si el trasbordador aún no fue al otro lado o si ya fue descargado
            while (!cruzo || autos.size() == 0)
                puedeDescargar.await();

            // Descargar auto
            String auto = autos.remove();
            System.out.print(String.format("Descarga %s", auto));
            visualizarEstado();

            // Notificar que ya fue descargado por completo
            if (autos.size() == 0)
                puedeVolver.signal();
        } finally {
            mutex.unlock();
        }
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
