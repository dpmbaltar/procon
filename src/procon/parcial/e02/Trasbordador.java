package procon.parcial.e02;

import java.util.ArrayDeque;
import java.util.concurrent.Semaphore;

public class Trasbordador {

    public static final int CAPACIDAD = 10;
    private boolean funcionando = true;
    private ArrayDeque<String> autos;
    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore ir = new Semaphore(0);
    private final Semaphore volver = new Semaphore(0);
    private final Semaphore descarga = new Semaphore(0);
    private final Semaphore carga = new Semaphore(CAPACIDAD);

    public Trasbordador() {
        autos = new ArrayDeque<>(CAPACIDAD);
    }

    public void ir() throws InterruptedException {
        ir.acquire();
        System.out.println("Ir al otro lado...");
        descarga.release(CAPACIDAD);
    }

    public void volver() throws InterruptedException {
        volver.acquire();
        System.out.println("Volver del otro lado...");
        carga.release(CAPACIDAD);
    }

    /**
     * Carga el trasbordador con un auto.
     * @param auto
     * @throws InterruptedException
     */
    public void cargar(String auto) throws InterruptedException {
        carga.acquire();
        mutex.acquire();
        autos.add(auto);
        visualizarEstado();

        if (autos.size() >= CAPACIDAD)
            ir.release();
        mutex.release();
    }

    /**
     * Descarga un auto del trasbordador.
     * @param auto
     * @throws InterruptedException
     */
    public void descargar() throws InterruptedException {
        descarga.acquire();
        mutex.acquire();
        autos.remove();
        visualizarEstado();

        if (autos.size() <= 0)
            volver.release();
        mutex.release();
    }

    public boolean estaFuncionando() {
        return funcionando;
    }

    public void setFuncionando(boolean funcionando) {
        this.funcionando = funcionando;
    }

    private void visualizarEstado() {
        StringBuilder estado = new StringBuilder("[");
        for (int i = 0; i < CAPACIDAD; i++) {
            if (i < autos.size())
                estado.append("#");
            else
                estado.append("_");
        }
        estado.append("]")
                .append(" ")
                .append(autos.size())
                .append("/")
                .append(CAPACIDAD);
        System.out.println(estado);
    }
}
