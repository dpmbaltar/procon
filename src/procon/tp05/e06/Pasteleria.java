package procon.tp05.e06;

public class Pasteleria {

    public static void main(String[] args) {
        Mostrador mostrador = new Mostrador();
        Thread[] hilos = {
                new Thread(new Horno(mostrador, 1000), "Horno-A"),
                new Thread(new Horno(mostrador, 2000), "Horno-B"),
                new Thread(new Horno(mostrador, 3000), "Horno-C"),
                new Thread(new Brazo(mostrador), "Brazo"),
                new Thread(new Empaquetador(mostrador), "Empaquetador-1")
        };

        for (int i = 0; i < hilos.length; i++)
            hilos[i].start();
    }
}
