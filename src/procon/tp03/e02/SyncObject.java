package procon.tp03.e02;

/**
 * 1. Hay dos hilos en ejecución: el del main() y el de la variable hilo, creado
 * con la clase Thread.
 * 
 * 2. El recurso compartido es la variable dato de la clase DualSync.
 * 
 * 3. La salida no siempre es la esperada, y va cambiando dentro de un rango de
 * valores distintos.
 * 
 * 4. La salida dada es posible, ya que como la variable dato no esta
 * correctamente bajo exclusión mutua, puede ocurrir lo siguiente:
 * 
 * - se ejecuta g(), opera y guarda dato = 25 e imprime;
 * - se ejecuta f(), obtiene y opera dato = 25 * 4 = 100, guarda y para ejec.;
 * - vuelve a ejecutarse g() varias veces incrementando a 100 de a 20;
 * - cuando g() sumo 160 a dato, g para su ejec. y continua f(), imprime 160;
 * - luego opera varias veces f(), y al final una vez mas g().
 */
public class SyncObject {
    public static void main(String[] args) {
        final DualSync ds = new DualSync();
        Thread hilo = new Thread() {
            @Override
            public void run() {
                ds.f();
            }
        };
        hilo.start();
        ds.g();
    }
}
