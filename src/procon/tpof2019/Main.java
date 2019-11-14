package procon.tpof2019;

public class Main {

    public static void main(String[] args) {
        Parque parque = new Parque();
        Thread administrador = new Thread(new Administrador(parque), "Administrador");
        Thread camioneta = new Thread(new Camioneta(parque), "Camioneta");
        Thread tren = new Thread(new Tren(parque), "Tren");
        Thread administradorTobogan = new Thread(new AdministradorTobogan(parque), "Administrador (Toboganes)");
        Thread[] visitantes = new Thread[25];

        for (int i = 0; i < visitantes.length; i++)
            visitantes[i] = new Thread(new Visitante(parque), "Visitante-" + (i + 1));

        // Iniciar hilos
        administrador.start();
        camioneta.start();
        tren.start();
        administradorTobogan.start();

        for (int i = 0; i < visitantes.length; i++)
            visitantes[i].start();

        try {
            for (int i = 0; i < visitantes.length; i++)
                visitantes[i].join();
            administrador.join();
            camioneta.join();
            System.out.println("Finalizado");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
