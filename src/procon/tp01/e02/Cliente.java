package procon.tp01.e02;

public class Cliente extends Thread {
    public void run() {
        System.out.println("Soy " + Thread.currentThread().getName());
        Recurso.uso();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
    }
}
