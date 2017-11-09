/**
 * Parcial - Ejercicio 2
 */
package procon.parcial.e02;

/**
 * Un trasbordador lleva autos de un lado del río al otro tal que:
 * - El trasbordador carga hasta 10 autos.
 * - LLeva los autos al otro lado solo cuando esta lleno.
 * - Vuelve del otro lado luego de haber descargado todos los autos.
 * - Considderar métodos ir() y volver().
 *
 *     Lado A          Trasbordador         Lado B
 *            ||~~~~~~~~~~~~~~~~~~~~~~~||
 * -----------\/~~~~~+----------+\~~~~~\/-----------
 *   => => =>   ~~~~~|==========  )~~~~    => => =>
 * -----------/\~~~~~+----------+/~~~~~/\-----------
 *            ||~~~~~~~~~~~~~~~~~~~~~~~||
 */
public class Trasbordo {

    /**
     * La instancia del trasbordador.
     */
    private Trasbordador trasbordador;

    /**
     * Constructor
     */
    public Trasbordo() {
        trasbordador = new Trasbordador();
    }

    /**
     * Inicia el trasbordo, llevando al trasbordador de un lado del río al otro,
     * según las condiciones dadas.
     * @throws InterruptedException
     */
    public void iniciar() throws InterruptedException {
        trasbordador.iniciar();

        // El lado A representa el lugar donde llegan los autos para cruzar
        // El lado B representa el lugar donde los autos se descargan y siguen
        Thread ladoA = new Thread(new LadoA(trasbordador), "Lado-A");
        Thread ladoB = new Thread(new LadoB(trasbordador), "Lado-B");

        ladoA.start();
        ladoB.start();

        while (true) {
            trasbordador.ir();
            trasbordador.volver();
        }
    }

    public static void main(String[] args) {
        try {
            Trasbordo trasbordo = new Trasbordo();
            trasbordo.iniciar();
        } catch (InterruptedException e) {}
    }
}
