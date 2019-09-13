package procon.tp02.e04;

/**
 * Valores posibles sin exclusión mutua: 4, 6, 7, 8.
 * Valores posibles con exclusión mutua: 7, 8.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            Valor valor = new Valor();
            Thread t1 = new Thread(new TareaA(valor));
            Thread t2 = new Thread(new TareaB(valor));

            try {
                t1.start();
                t2.start();
                t1.join();
                t2.join();

                System.out.println(valor.getValor());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
