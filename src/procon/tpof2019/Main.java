package procon.tpof2019;

import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        AdministradorCarrera administradorCarrera = new AdministradorCarrera(parque);
        AdministradorTobogan administradorTobogan = new AdministradorTobogan(parque);
        AdministradorPiletas administradorPiletas = new AdministradorPiletas(parque);
        Thread hiloReloj = new Thread(new Reloj(parque, Parque.HORA_ABRE, 0), "Reloj del Parque");
        Thread hiloAdministrador = new Thread(new Administrador(parque), "Administrador");
        Thread hiloTren = new Thread(tren, "Tren");
        Thread hiloCamioneta = new Thread(camioneta, "Camioneta");
        Thread hiloAdmCarrera = new Thread(administradorCarrera, "Adm. Carrera de Gomones");
        Thread hiloAdmTobogan = new Thread(administradorTobogan, "Adm. Toboganes");
        Thread hiloAdmPiletas = new Thread(administradorPiletas, "Adm. Piletas");
        Thread[] visitantes = new Thread[cantidadVisitantes];

        for (int i = 0; i < visitantes.length; i++)
            visitantes[i] = new Thread(new Visitante(parque), "Visitante-" + (i + 1));

        // Iniciar hilos
        hiloReloj.start();
        hiloAdministrador.start();
        hiloTren.start();
        hiloCamioneta.start();
        hiloAdmCarrera.start();
        hiloAdmTobogan.start();
        hiloAdmPiletas.start();

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
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
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
                    Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        });
    }

}
