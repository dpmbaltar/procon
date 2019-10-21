package procon.parcial.e01;

public class Main {

    private static final int CANTIDAD = 5;

    public static void main(String[] args) {
        TramoCompartido tc = new TramoCompartidoConSemaforo();
        //TramoCompartido tc = new TramoCompartidoConMonitor();
        //TramoCompartido tc = new TramoCompartidoConCerrojo();
        Thread[] entradas = new Thread[CANTIDAD];

        for (int i = 0; i < entradas.length; i++) {
            if ((i % 2) == 0)
                entradas[i] = new Thread(new LadoA(tc));
            else
                entradas[i] = new Thread(new LadoB(tc));
        }

        for (int i = 0; i < entradas.length; i++)
            entradas[i].start();
    }

}
