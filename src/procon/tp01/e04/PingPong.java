package procon.tp01.e04;

public class PingPong implements Runnable {

    /**
     * Lo que va a escribir.
     */
    private String cadena;

    /**
     * Tiempo entre escritura.
     */
    private int delay;

    public PingPong(String cartel, int cantMs) {
        cadena = cartel;
        delay = cantMs;
    };

    public void run() {
        for (int i = 1; i < delay * 10; i++) {
            System.out.println(cadena + " ");
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
