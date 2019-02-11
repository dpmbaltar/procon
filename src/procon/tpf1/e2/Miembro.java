package procon.tpf1.e2;

import java.util.concurrent.ThreadLocalRandom;

public class Miembro implements Runnable {

    protected final String nombre;
    protected final Almacen almacen;

    public Miembro(String nombre, Almacen almacen) {
        this.nombre = nombre;
        this.almacen = almacen;
    }

    @Override
    public void run() {
        try {
            int litrosFabricados = 0;

            while (litrosFabricados < 10) {
                // almacen.entrar(this);
                // almacen.probarVinoSiHay();
                if (almacen.iniciarMezcla()) {
                    Thread.sleep(150);
                    litrosFabricados+= almacen.finalizarMezcla();
                    almacen.iniciarFermentacion();
                    Thread.sleep(4000);
                    almacen.finalizarFermentacion();
                    almacen.esperarPruebaVino();
                }
                
//                else {
//                    almacen.probarVino();
//                }
//                almacen.salir(this);
//                almacen.esperarPruebaVino();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
