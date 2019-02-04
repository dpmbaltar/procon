package procon.tp03.e01;

/**
 * 1. Thread.yield() indica al planificador de tareas que el hilo actual está
 * dispuesto a ceder el uso del procesador. Por eso es que al ejecutar esta
 * sentencia, se observa que a veces se intercala el uso del procesador de cada
 * hilo en ejecución.
 * 
 * 2. El efecto de "synchronized (syncObject)" implica que al momento entrar en
 * la sección crítica, se adquiere el "lock" del objeto syncObject, una
 * instancia de Object dentro de la clase DualSync.
 * 
 * 3. El efecto de "synchronized void f()" implica que al momento de entrar en
 * la sección crítica, se adquiere el "lock" del objeto instancia de DualSync, y
 * no de la instancia de Object como se indica en el punto 2.
 * 
 * 4. La diferencia entre yield() y sleep() radica en que el primero puede
 * servir para depuración y pruebas, ya que intenta mejorar la progresión entre
 * hilos bajo condiciones de carrera. En cambio, sleep() hace que el hilo actual
 * deje su ejecución durante el tiempo especificado, sin dejar de libre el
 * "lock" que pueda tener, por lo que su uso debe ser cuidadoso.
 * 
 * 5. Lo presentado funciona de la siguiente forma: desde un hilo  se llama a
 * f() y desde otro a g(). Ambos muestran en pantalla 5 veces su nombre,
 * intentando ceder el uso del procesador en vada iteración.
 */
public class SyncObject {
    public static void main(String[] args) {
        final DualSynch ds = new DualSynch();
        // solo por cuestiones prácticas se trabaja de esta forma
        Thread hilo = new Thread() {
            public void run() {
                ds.f();
            }
        };
        hilo.start();
        ds.g();
    }
}
