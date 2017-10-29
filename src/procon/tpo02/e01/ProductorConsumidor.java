/**
 * Trabajo Práctico Obligatorio 02
 * Ejercicio 01
 */
package procon.tpo02.e01;

import java.util.ArrayDeque;
import java.util.Random;

/**
 * Programa Productor-Consumidor.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class ProductorConsumidor {

    /**
     * Limite del buffer de datos.
     */
    public static final int LIMITE = 50;

    /**
     * Buffer de datos.
     */
    private ArrayDeque<Integer> datos;

    /**
     * Semáforo (binario) para exclusión mutua del acceso al buffer de datos.
     */
    private MonitorSemaf mutex;

    /**
     * Semáforo de control de espacios vacíos del buffer.
     */
    private MonitorSemaf vacio;

    /**
     * Semáforo de control de espacios ocupados del buffer.
     */
    private MonitorSemaf lleno;

    public static void main(String[] args) {
        ProductorConsumidor pc = new ProductorConsumidor();
        pc.iniciar();
    }

    /**
     * Constructor.
     */
    public ProductorConsumidor() {
        datos = new ArrayDeque<Integer>(LIMITE);
        mutex = new MonitorSemaf(1);
        vacio = new MonitorSemaf(LIMITE);
        lleno = new MonitorSemaf(0);
    }

    /**
     * Inicia los hilos productor-consumidor.
     */
    public void iniciar() {
        Thread productor = new Thread(new Productor(), "Productor");
        Thread consumidor = new Thread(new Consumidor(), "Consumidor");

        try {
            productor.start();
            consumidor.start();
            productor.join();
            consumidor.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupción");
        }

        System.out.println("Terminado");
    }

    /**
     * Muestra en pantalla el estado del buffer en forma de barra de carga.
     * Ejemplo: [###--]: 3/5 (tres espacios ocupados, 2 vacíos)
     */
    private void mostrarEstado() {
        StringBuilder estado = new StringBuilder("[");
        for (int i = 0; i < LIMITE; i++) {
            if (i < datos.size())
                estado.append("#");
            else
                estado.append("-");
        }
        estado.append("]: " + datos.size() + "/" + LIMITE);
        System.out.println(estado);
    }

    /**
     * Clase del hilo Productor. Produce números aleatorios para ingresarlos al
     * buffer.
     */
    private class Productor implements Runnable {

        @Override
        public void run() {
            Random generadorDatos = new Random();
            while (true) {
                ponerDato(generadorDatos.nextInt(100));
            }
        }

        /**
         * Pone un dato en el buffer.
         */
        private void ponerDato(int dato) {
            try {
                vacio.acquireM();
                mutex.acquireM();
                datos.add(dato);
                mostrarEstado();
                mutex.releaseM();
                lleno.releaseM();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Clase del hilo Consumidor. Saca los números del buffer, generados por el
     * productor.
     */
    private class Consumidor implements Runnable {

        @Override
        public void run() {
            while (true) {
                sacarDato();
            }
        }

        /**
         * Saca un dato del buffer.
         */
        private void sacarDato() {
            int dato;
            try {
                lleno.acquireM();
                mutex.acquireM();
                dato = datos.remove();
                mostrarEstado();
                mutex.releaseM();
                vacio.releaseM();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
