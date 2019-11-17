package procon.tpof2019;

import java.awt.EventQueue;

public class Main {

    /**
     * Inicia la ejecución del parque según la cantidad de visitantes dada.
     *
     * @param cantidadVisitantes la cantidad de visitantes
     */
    public static void iniciar(int cantidadVisitantes) {
        Parque parque = new Parque();
        Tren tren = new Tren(parque);
        Camioneta camioneta = new Camioneta(parque);
        AdministradorTobogan administradorTobogan = new AdministradorTobogan(parque);
        Thread hiloAdministrador = new Thread(new Administrador(parque), "Administrador");
        Thread hiloTren = new Thread(tren, "Tren");
        Thread hiloCamioneta = new Thread(camioneta, "Camioneta");
        Thread hiloAdmTobogan = new Thread(administradorTobogan, "Administrador (Toboganes)");
        Thread[] visitantes = new Thread[cantidadVisitantes];

        for (int i = 0; i < visitantes.length; i++)
            visitantes[i] = new Thread(new Visitante(parque), "Visitante-" + (i + 1));

        // Iniciar hilos
        hiloAdministrador.start();
        hiloTren.start();
        hiloCamioneta.start();
        hiloAdmTobogan.start();

        for (int i = 0; i < visitantes.length; i++)
            visitantes[i].start();

        try {
            for (int i = 0; i < visitantes.length; i++)
                visitantes[i].join();

            hiloAdministrador.join();
            administradorTobogan.finalizar();
            tren.parar();
            camioneta.parar();
            System.out.println("<<Finalizado>>");
            VistaParque.getInstancia().finalizar();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    VistaParque frame = VistaParque.getInstancia();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
