package procon.parcial2019.e02;

public class Main {

    public static void main(String[] args) {
        Observatorio observatorio = new Observatorio();
        Thread[] hilos = new Thread[15];

        for (int i = 0; i < 5; i++)
            hilos[i] = new Thread(new Visitantes(observatorio));
        for (int i = 5; i < 10; i++)
            hilos[i] = new Thread(new Personal(observatorio));
        for (int i = 10; i < 15; i++)
            hilos[i] = new Thread(new Profesionales(observatorio));

        for (int i = 0; i < hilos.length; i++)
            hilos[i].start();
    }

}
