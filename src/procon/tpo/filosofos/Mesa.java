package procon.tpo.filosofos;

import java.util.concurrent.Semaphore;

public class Mesa {

    /** Mutex */
    private Semaphore mutex;

    /** Semaforo de turnos para comer */
    private Semaphore[] turnos;

    /** Indica cuales filósofos están comiendo */
    private boolean[] comiendo;

    /** Indica cuales filósofos quieren comer */
    private boolean[] quierenComer;

    public Mesa() {
        mutex = new Semaphore(1);
        turnos = new Semaphore[5];
        comiendo = new boolean[5];
        quierenComer = new boolean[5];

        for (int i = 0; i < 5; i++) {
            //turnos[i] = new Semaphore(1);
            turnos[i] = new Semaphore(0);
            comiendo[i] = false;
            quierenComer[i] = false;
        }
    }

    /** Toma los 2 tenedores adyacentes del filósofo para comenzar a comer */
    public void tomarTenedores(int filosofo) throws InterruptedException {
        mutex.acquire();
        quierenComer[filosofo] = true;
        intentarComer(filosofo);
        //turnos[filosofo].acquire();
        mutex.release();
        turnos[filosofo].acquire();
        System.out.println(Thread.currentThread().getName() + " toma los tenedores");
    }

    /** Deja los 2 tenedores en la mesa para que otros puedan comer */
    public void dejarTenedores(int filosofo) throws InterruptedException {
        mutex.acquire();
        comiendo[filosofo] = false;
        //quierenComer[filosofo] = false;
        System.out.println(Thread.currentThread().getName() + " deja los tenedores");
        intentarComer(filosofoIzquierdo(filosofo));
        intentarComer(filosofoDerecho(filosofo));
        mutex.release();
    }

    /** Intenta adquirir el turno de un filósofo para comer */
    private void intentarComer(int filosofo) {
        if (quierenComer[filosofo] && !comiendo[filosofoIzquierdo(filosofo)] && !comiendo[filosofoDerecho(filosofo)]) {
            quierenComer[filosofo] = false;
            comiendo[filosofo] = true;
            turnos[filosofo].release();
        }
    }

    /** Calcula la posición en la mesa del filósofo izquierdo de uno dado */
    private int filosofoIzquierdo(int filosofo) {
        return (filosofo + (comiendo.length - 1)) % comiendo.length;
    }

    /** Calcula la posición en la mesa del filósofo derecho de uno dado */
    private int filosofoDerecho(int filosofo) {
        return (filosofo + 1) % comiendo.length;
    }
}
