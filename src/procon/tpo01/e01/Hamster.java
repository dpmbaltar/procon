/*
 * Trabajo Práctico Obligatorio 01
 * Ejercicio 01
 */
package procon.tpo01.e01;

/**
 * Hamster; come, se ejercita y descansa en una jaula.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class Hamster implements Runnable {

    /**
     * La jaula donde vive el hamster.
     */
    private Jaula jaula;

    /**
     * El nombre del hamster.
     */
    private String nombre;

    /**
     * Constructor.
     */
    public Hamster() {
        this("Sin nombre");
    }

    /**
     * Constructor con nombre.
     *
     * @param nombre el nombre del hamster.
     */
    public Hamster(String nombre) {
        this(nombre, null);
    }

    /**
     * Constructor con nombre y jaula.
     *
     * @param nombre el nombre del hamster.
     * @param jaula la jaula donde convivirá el hamster.
     */
    public Hamster(String nombre, Jaula jaula) {
        this.nombre = nombre;
        this.jaula = jaula;
    }

    /**
     * Simula comer, ejercitarse y descansar, siempre que esté en una jaula.
     */
    @Override
    public void run() {
        try {
            comer();
            ejercitarse();
            descansar();
        } catch (NullPointerException e) {
            System.out.println(
                    "¡El hamster " + nombre + " no está en una jaula!");
        }
    }

    /**
     * Come del plato de la jaula.
     */
    protected void comer() throws NullPointerException {
        jaula.getPlato().usar();
    }

    /**
     * Se ejercita en la rueda de la jaula.
     */
    protected void ejercitarse() throws NullPointerException {
        jaula.getRueda().usar();
    }

    /**
     * Descansa en la hamaca de la jaula.
     */
    protected void descansar() throws NullPointerException {
        jaula.getHamaca().usar();
    }

    /**
     * Retorna la jaula donde convive el hamster.
     *
     * @return la jaula.
     */
    public Jaula getJaula() {
        return jaula;
    }

    /**
     * Retorna el nombre del hamster.
     *
     * @return el nombre.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece la jaula donde convive el hamster.
     *
     * @param jaula la jaula.
     */
    public void setJaula(Jaula jaula) {
        this.jaula = jaula;
    }

    /**
     * Establece el nombre de la jaula.
     *
     * @param nombre el nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
