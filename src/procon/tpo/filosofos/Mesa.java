package procon.tpo.filosofos;

import java.util.concurrent.Semaphore;

public class Mesa {

    /** Los tenedores en la mesa */
    private Semaphore[] tenedores;

    /**
     * Constructor.
     */
    public Mesa() {
        tenedores = new Semaphore[5];

        for (int i = 0; i < tenedores.length; i++) {
            tenedores[i] = new Semaphore(1);
        }
    }

    /** Intenta tomar los tenedores */
    public boolean tomarTenedores(int filosofo) {
        boolean exito = false;

        // Intenta tomar los dos tenedores
        if (tenedores[tenedorIzquierdo(filosofo)].tryAcquire()) {
            if (tenedores[tenedorDerecho(filosofo)].tryAcquire()) {
                exito = true;
                System.out.println(Thread.currentThread().getName() + " toma los tenedores");
            } else {
                tenedores[tenedorIzquierdo(filosofo)].release();
            }
        }

        return exito;
    }

    /** Deja los tenedores */
    public void dejarTenedores(int filosofo) {
        // Libera ambos tenedores
        tenedores[tenedorIzquierdo(filosofo)].release();
        tenedores[tenedorDerecho(filosofo)].release();
    }

    /** Calcula la posición del tenedor izquierdo */
    private int tenedorIzquierdo(int filosofo) {
        return (filosofo + (tenedores.length - 1)) % tenedores.length;
    }

    /** Calcula la posición del tenedor derecho */
    private int tenedorDerecho(int filosofo) {
        return filosofo % tenedores.length;
    }

//    /** Mutex */
//    private Semaphore mutex;
//
//    /** Semaforo de turnos para comer */
//    private Semaphore[] turnos;
//
//    /** Indica cuales filósofos están comiendo */
//    private boolean[] comiendo;
//
//    /** Indica cuales filósofos quieren comer */
//    private boolean[] quierenComer;
//
//    public Mesa() {
//        mutex = new Semaphore(1);
//        turnos = new Semaphore[5];
//        comiendo = new boolean[5];
//        quierenComer = new boolean[5];
//
//        for (int i = 0; i < 5; i++) {
//            //turnos[i] = new Semaphore(1);
//            turnos[i] = new Semaphore(0);
//            comiendo[i] = false;
//            quierenComer[i] = false;
//        }
//    }
//
//    /** Toma los 2 tenedores adyacentes del filósofo para comenzar a comer */
//    public void tomarTenedores(int filosofo) throws InterruptedException {
//        mutex.acquire();
//        quierenComer[filosofo] = true;
//        intentarComer(filosofo);
//        //turnos[filosofo].acquire();
//        mutex.release();
//        turnos[filosofo].acquire();
//        System.out.println(Thread.currentThread().getName() + " toma los tenedores");
//    }
//
//    /** Deja los 2 tenedores en la mesa para que otros puedan comer */
//    public void dejarTenedores(int filosofo) throws InterruptedException {
//        mutex.acquire();
//        comiendo[filosofo] = false;
//        //quierenComer[filosofo] = false;
//        System.out.println(Thread.currentThread().getName() + " deja los tenedores");
//        intentarComer(filosofoIzquierdo(filosofo));
//        intentarComer(filosofoDerecho(filosofo));
//        mutex.release();
//    }
//
//    /** Intenta adquirir el turno de un filósofo para comer */
//    private void intentarComer(int filosofo) {
//        if (quierenComer[filosofo] && !comiendo[filosofoIzquierdo(filosofo)] && !comiendo[filosofoDerecho(filosofo)]) {
//            quierenComer[filosofo] = false;
//            comiendo[filosofo] = true;
//            turnos[filosofo].release();
//        }
//    }
//
//    /** Calcula la posición en la mesa del filósofo izquierdo de uno dado */
//    private int filosofoIzquierdo(int filosofo) {
//        return (filosofo + (comiendo.length - 1)) % comiendo.length;
//    }
//
//    /** Calcula la posición en la mesa del filósofo derecho de uno dado */
//    private int filosofoDerecho(int filosofo) {
//        return (filosofo + 1) % comiendo.length;
//    }
}
