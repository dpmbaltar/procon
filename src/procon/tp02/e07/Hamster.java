/*
 * Trabajo Práctico Obligatorio 01
 * Ejercicio 01
 */
package procon.tp02.e07;

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
     * Indica si el hamster comió.
     */
    private boolean comio = false;
    
    /**
     * Indica si el hamster se ejercitó.
     */
    private boolean ejercito = false;
    
    /**
     * Indica si el hamster descansó.
     */
    private boolean descanso = false;

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
     * @param jaula  la jaula donde convivirá el hamster.
     */
    public Hamster(String nombre, Jaula jaula) {
        this.nombre = nombre;
        this.jaula = jaula;
    }

    /**
     * Simula comer, ejercitarse y descansar, siempre que esté en una jaula.
     * Para que el hamster pueda realizar las 3 cosas en cualquier orden según
     * esté habilitado el recurso a utilizar, se modifican los métodos usar() de
     * los recursos para que devuelvan true/false según pudo utilizarlo o no, y
     * se envuelven las acciones dentro de un loop hasta que el hamster haya
     * realizado las 3 acciones.
     */
    @Override
    public void run() {
        try {
            while (!comio || !ejercito || !descanso) {
                comer();
                ejercitarse();
                descansar();
            }
        } catch (NullPointerException e) {
            System.out.println(
                    "¡El hamster " + nombre + " no está en una jaula!");
        }
    }

    /**
     * Come del plato de la jaula.
     */
    protected void comer() throws NullPointerException {
        if (!comio) {
            comio = jaula.getPlato().usar();
        }
    }

    /**
     * Se ejercita en la rueda de la jaula.
     */
    protected void ejercitarse() throws NullPointerException {
        if (!ejercito) {
            ejercito = jaula.getRueda().usar();
        }
    }

    /**
     * Descansa en la hamaca de la jaula.
     */
    protected void descansar() throws NullPointerException {
        if (!descanso) {
            descanso = jaula.getHamaca().usar();
        }
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
