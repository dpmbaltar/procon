package procon.tpof2019;

public class Main {

    public static void main(String[] args) {
        Parque parque = new Parque();
        Tren tren = new Tren(parque);
        Camioneta camioneta = new Camioneta(parque);
        Thread administrador = new Thread(new Administrador(parque), "Administrador");
        Thread hiloTren = new Thread(tren, "Tren");
        Thread hiloCamioneta = new Thread(camioneta, "Camioneta");
        Thread administradorTobogan = new Thread(new AdministradorTobogan(parque), "Administrador (Toboganes)");
        Thread[] visitantes = new Thread[125];

        for (int i = 0; i < visitantes.length; i++)
            visitantes[i] = new Thread(new Visitante(parque), "Visitante-" + (i + 1));

        // Iniciar hilos
        administrador.start();
        hiloCamioneta.start();
        hiloTren.start();
        administradorTobogan.start();

        for (int i = 0; i < visitantes.length; i++)
            visitantes[i].start();

        try {
            for (int i = 0; i < visitantes.length; i++)
                visitantes[i].join();
            administrador.join();
            tren.parar();
            camioneta.parar();
            System.out.println("Finalizado");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
