package procon.tpf1.e2;

import java.util.HashSet;
import java.util.Set;

/**
 * Representa un vino fabricado. Contiene información acerca del fabricante y
 * los miembros del club que lo probaron.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class Vino {

    /**
     * El fabricante.
     */
    private final Miembro fabricante;

    /**
     * Los miembros que probaron el vino.
     */
    private final Set<Miembro> probadores = new HashSet<>();

    /**
     * Constructor con el fabricante.
     * 
     * @param fabricante el fabricante del vino
     */
    public Vino(Miembro fabricante) {
        this.fabricante = fabricante;
    }

    /**
     * Devuelve el fabricante del vino.
     * 
     * @return el fabricante del vino
     */
    public synchronized Miembro getFabricante() {
        return fabricante;
    }

    /**
     * Devuelve la cantidad de miembros que probaron el vino.
     * 
     * @return la cantidad que lo probaron
     */
    public synchronized int getCantidadProbaron() {
        return probadores.size();
    }

    /**
     * Indica si el vino fue probado por un miembro o no.
     * 
     * @param miembro el miembro a comprobar
     * @return verdadero si el miembro lo probó, falso en caso contrario
     */
    public synchronized boolean estaProbadoPor(Miembro miembro) {
        return probadores.contains(miembro);
    }

    /**
     * Marca que el miembro especificado ya probó el vino.
     * 
     * @param miembro el miembro que prueba
     * @return verdadero si el miembro no había probado el vino, falso en caso
     *         contrario, es decir, si ya lo probó antes
     */
    public synchronized boolean probar(Miembro miembro) {
        return probadores.add(miembro);
    }

}
