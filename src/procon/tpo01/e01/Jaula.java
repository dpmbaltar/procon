/*
 * Trabajo Práctico Obligatorio 01
 * Ejercicio 01
 */
package procon.tpo01.e01;

/**
 * Jaula donde conviven los hamsters.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class Jaula {

    /**
     * El plato de la jaula.
     */
    private Plato plato;

    /**
     * La rueda de la jaula.
     */
    private Rueda rueda;

    /**
     * La hamaca de la jaula.
     */
    private Hamaca hamaca;

    /**
     * Constructor.
     */
    public Jaula() {
    }

    /**
     * Simula meter un hamster en la jaula junto a los que ya están, donde
     * compartirán un plato, una rueda y una hamaca.
     * 
     * @param hamster el hamster a meter.
     */
    public void meter(Hamster hamster) {
        hamster.setJaula(this);
        (new Thread(hamster, hamster.getNombre())).start();
    }

    /**
     * Retorna el plato de la jaula.
     * 
     * @return el plato establecido.
     */
    public Plato getPlato() {
        return plato;
    }

    /**
     * Retorna la rueda de la jaula.
     * 
     * @return la rueda establecida.
     */
    public Rueda getRueda() {
        return rueda;
    }

    /**
     * Retorna la hamaca de la jaula.
     * 
     * @return la hamaca establecida.
     */
    public Hamaca getHamaca() {
        return hamaca;
    }

    /**
     * Establece el plato de la jaula.
     * 
     * @param plato el plato a establecer.
     */
    public void setPlato(Plato plato) {
        this.plato = plato;
    }

    /**
     * Establece la rueda de la jaula.
     * 
     * @param rueda la rueda a establecer.
     */
    public void setRueda(Rueda rueda) {
        this.rueda = rueda;
    }

    /**
     * Establece la hamaca de la jaula.
     * 
     * @param hamaca la hamaca a establecer.
     */
    public void setHamaca(Hamaca hamaca) {
        this.hamaca = hamaca;
    }
}
