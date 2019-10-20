package procon.parcial.e01;

import java.util.LinkedList;
import java.util.Queue;

public class TramoCompartidoConMonitor implements TramoCompartido {

    private boolean tramoOcupado = false;
    private char ultimoLado = 0;

    private Queue<Object> trenesDesdeA = new LinkedList<>();
    private Queue<Object> trenesDesdeB = new LinkedList<>();

    @Override
    public synchronized void entrarDesdeLadoA(String tren) throws InterruptedException {
        // Tren entra en la cola de espera de A
        trenesDesdeA.add(tren);

        // Primer tren vino desde A
        if (ultimoLado == 0)
            ultimoLado = 'A';

        // Esperar si:
        // hay un tren ocupando el tramo;
        // ya paso uno desde A y hay esperando en B;
        // no es el turno del tren actual
        while (tramoOcupado || (!tramoOcupado && (ultimoLado == 'A' && !trenesDesdeB.isEmpty()))
                || !trenesDesdeA.peek().equals(tren))
            wait();

        // Usar tramo compartido
        trenesDesdeA.remove();
        tramoOcupado = true;
        System.out.println(String.format("Entra %s", tren));
    }

    @Override
    public synchronized void salirDesdeLadoA(String tren) {
        System.out.println(String.format("Sale %s", tren));
        ultimoLado = 'A';
        tramoOcupado = false;
        notifyAll();
    }

    @Override
    public synchronized void entrarDesdeLadoB(String tren) throws InterruptedException {
        // Tren entra en la cola de espera de B
        trenesDesdeB.add(tren);

        // Primer tren vino desde B
        if (ultimoLado == 0)
            ultimoLado = 'B';

        // Esperar si:
        // hay un tren ocupando el tramo;
        // ya paso uno desde B y hay esperando en A;
        // no es el turno del tren actual
        while (tramoOcupado || (!tramoOcupado && (ultimoLado == 'B' && !trenesDesdeA.isEmpty()))
                || !trenesDesdeB.peek().equals(tren))
            wait();

        // Usar tramo compartido
        trenesDesdeB.remove();
        tramoOcupado = true;
        System.out.println(String.format("Entra %s", tren));
    }

    @Override
    public synchronized void salirDesdeLadoB(String tren) {
        System.out.println(String.format("Sale %s", tren));
        ultimoLado = 'B';
        tramoOcupado = false;
        notifyAll();
    }

}
