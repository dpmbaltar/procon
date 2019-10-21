package procon.parcial.e01;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TramoCompartidoConCerrojo implements TramoCompartido {

    private final Lock mutex = new ReentrantLock();
    private final Condition puedeEntrarA = mutex.newCondition();
    private final Condition puedeEntrarB = mutex.newCondition();

    private boolean tramoOcupado = false;
    private char ultimoLado = 0;

    private Queue<Object> trenesDesdeA = new LinkedList<>();
    private Queue<Object> trenesDesdeB = new LinkedList<>();

    @Override
    public void entrarDesdeLadoA(String tren) throws InterruptedException {
        mutex.lock();
        try {
            // Tren entra en la cola de espera de A
            trenesDesdeA.add(tren);

            // Primer tren vino desde A
            if (ultimoLado == 0)
                ultimoLado = 'A';

            // Esperar si:
            // hay un tren ocupando el tramo;
            // ya paso uno desde A y hay esperando en B;
            // no es el turno del tren actual
            while (tramoOcupado
                    || (!tramoOcupado && (ultimoLado == 'A' && !trenesDesdeB.isEmpty()))
                    || !trenesDesdeA.peek().equals(tren))
                puedeEntrarA.await();

            // Usar tramo compartido
            trenesDesdeA.remove();
            tramoOcupado = true;
            System.out.println(String.format("Entra %s", tren));
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public void salirDesdeLadoA(String tren) {
        mutex.lock();
        try {
            System.out.println(String.format("Sale %s", tren));
            ultimoLado = 'A';
            tramoOcupado = false;

            if (!trenesDesdeB.isEmpty())
                puedeEntrarB.signalAll();
            else
                puedeEntrarA.signalAll();
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public void entrarDesdeLadoB(String tren) throws InterruptedException {
        mutex.lock();
        try {
            // Tren entra en la cola de espera de B
            trenesDesdeB.add(tren);

            // Primer tren vino desde B
            if (ultimoLado == 0)
                ultimoLado = 'B';

            // Esperar si:
            // hay un tren ocupando el tramo;
            // ya paso uno desde B y hay esperando en A;
            // no es el turno del tren actual
            while (tramoOcupado
                    || (!tramoOcupado && (ultimoLado == 'B' && !trenesDesdeA.isEmpty()))
                    || !trenesDesdeB.peek().equals(tren))
                puedeEntrarB.await();

            // Usar tramo compartido
            trenesDesdeB.remove();
            tramoOcupado = true;
            System.out.println(String.format("Entra %s", tren));
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public void salirDesdeLadoB(String tren) {
        mutex.lock();
        try {
            System.out.println(String.format("Sale %s", tren));
            ultimoLado = 'B';
            tramoOcupado = false;

            if (!trenesDesdeA.isEmpty())
                puedeEntrarA.signalAll();
            else
                puedeEntrarB.signalAll();
        } finally {
            mutex.unlock();
        }
    }

}
