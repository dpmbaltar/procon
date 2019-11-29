package procon.tpof2019;

import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Programa principal.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
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
        Thread[] visitantes = new Thread[cantidadVisitantes];
        Thread[] colaboradores = new Thread[] {
                new Thread(new Reloj(parque, Parque.HORA_ABRE, 0), "Reloj del Parque"),
                new Thread(new Administrador(parque), "Administrador"),
                new Thread(tren, "Tren"),
                new Thread(camioneta, "Camioneta"),
                new Thread(new AdministradorCarrera(parque), "Adm. Carrera de Gomones"),
                new Thread(administradorTobogan, "Adm. Toboganes"),
                new Thread(new AdministradorPiletas(parque), "Adm. Piletas")
        };

        // Crear visitantes
        for (int i = 0; i < visitantes.length; i++)
            visitantes[i] = new Thread(new Visitante(parque), "Visitante-" + (i + 1));

        // Iniciar hilos
        for (int i = 0; i < colaboradores.length; i++)
            colaboradores[i].start();
        for (int i = 0; i < visitantes.length; i++)
            visitantes[i].start();

        // Finalizar
        try {
            for (int i = 0; i < visitantes.length; i++)
                visitantes[i].join();

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
