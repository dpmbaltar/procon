package procon.tpf1.e2;

/**
 * El administrador del club, que se encarga de reponer los ingredientes.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class Administrador extends Miembro {

    /**
     * Constructor con el nombre del administrador y el almacén.
     * 
     * @param nombre  el nombre
     * @param almacen el almacén
     */
    public Administrador(String nombre, Almacen almacen) {
        super(nombre, almacen);
    }

    /**
     * Repone los ingredientes del almacén cuando sea necesario.
     */
    @Override
    public void run() {
        try {
            boolean debeReponer = true;
            int reposiciones = 0;
            while (debeReponer) {
                debeReponer = almacen.reponer(this, 15, 20);
                reposiciones++;
            }

            System.out.println(getNombre() + ">>> termina con " + reposiciones
                    + " reposiciones");
        } catch (InterruptedException e) {
            System.out.println(getNombre() + ">>> reposición interrumpida");
        }
    }

}
