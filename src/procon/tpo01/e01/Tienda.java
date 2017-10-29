/*
 * Trabajo Práctico Obligatorio 01
 * Ejercicio 01
 */
package procon.tpo01.e01;

/**
 * Tienda de mascotas.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class Tienda {

    /**
     * Simula meter hamsters en una jaula donde comparten un plato de comida,
     * una rueda para ejercitarse y una hamaca para descansar.
     * 
     * @param args
     */
    public static void main(String[] args) {
        Jaula jaula = new Jaula();
        jaula.setPlato(new Plato());
        jaula.setRueda(new Rueda());
        jaula.setHamaca(new Hamaca());
        jaula.meter(new Hamster("Uno"));
        jaula.meter(new Hamster("Dos"));
        jaula.meter(new Hamster("Tres"));
    }
}
