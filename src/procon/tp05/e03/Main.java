package procon.tp05.e03;

public class Main {

    public static void main(String[] args) {
        Recinto recinto = new RecintoMonitor();
        Thread[] soldados = new Thread[12];

        // Crear hilos
        for (int i = 0; i < soldados.length; i++)
            soldados[i] = new Thread(new Soldado(recinto), "Soldado-" + i);

        // Iniciar
        for (int i = 0; i < soldados.length; i++)
            soldados[i].start();
    }

}
