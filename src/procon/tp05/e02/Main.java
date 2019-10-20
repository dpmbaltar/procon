package procon.tp05.e02;

public class Main {

    public static void main(String[] args) {
        Libro libro = new LibroSemaforoBinario();
        //Libro libro = new LibroMonitor();
        //Libro libro = new LibroCerrojo();
        Thread[] lectores = new Thread[6];
        Thread[] escritores = new Thread[3];

        // Crear hilos
        for (int i = 0; i < escritores.length; i++)
            escritores[i] = new Thread(new Escritor(libro), "Escritor-" + i);
        for (int i = 0; i < lectores.length; i++)
            lectores[i] = new Thread(new Lector(libro), "Lector-" + i);

        // Iniciar hilos

        for (int i = 0; i < lectores.length; i++)
            lectores[i].start();
        for (int i = 0; i < escritores.length; i++)
            escritores[i].start();
    }

}
