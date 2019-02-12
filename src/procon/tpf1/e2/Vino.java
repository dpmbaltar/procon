package procon.tpf1.e2;

import java.util.HashSet;
import java.util.Set;

public class Vino {

    private final Miembro fabricante;
    private final Set<Miembro> probadores = new HashSet<>();

    public Vino(Miembro fabricante) {
        this.fabricante = fabricante;
    }

    public synchronized Miembro getFabricante() {
        return fabricante;
    }

    public synchronized int getCantidadProbaron() {
        return probadores.size();
    }

    public synchronized boolean probar(Miembro miembro) {
        return probadores.add(miembro);
    }

}
