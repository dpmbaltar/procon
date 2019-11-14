package procon.tpof2019;

public class Main {

    /**
     * Cantidad de visitantes a crear.
     */
    private static final int CANTIDAD_VISITANTES = 150;

    public static void main(String[] args) {
        Parque parque = new Parque();
        Tren tren = new Tren(parque);
        Camioneta camioneta = new Camioneta(parque);
        AdministradorTobogan administradorTobogan = new AdministradorTobogan(parque);
        Thread hiloAdministrador = new Thread(new Administrador(parque), "Administrador");
        Thread hiloTren = new Thread(tren, "Tren");
        Thread hiloCamioneta = new Thread(camioneta, "Camioneta");
        Thread hiloAdmTobogan = new Thread(administradorTobogan, "Administrador (Toboganes)");
        Thread[] visitantes = new Thread[CANTIDAD_VISITANTES];

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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
