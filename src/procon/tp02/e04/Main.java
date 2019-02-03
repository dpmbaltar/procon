package procon.tp02.e04;

/**
 * Valores posibles sin exclusión mutua: 4, 6, 8.
 * Valores posibles con exclusión mutua: 7, 8.
 * 
 * @author dpmbaltar
 *
 */
public class Main {
    public static void main(String[] args) {
        Thread t1, t2;
        Valor valor = new Valor();

        t1 = new Thread(new Operador(valor) {
            @Override
            public void run() {
                int v = 0;
                try {
                    v = valor.getValor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                v++;
                valor.setValor(v);
            }
        });
        t2 = new Thread(new Operador(valor) {
            @Override
            public void run() {
                int v = 0;
                try {
                    v = valor.getValor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                v *= 2;
                valor.setValor(v);
            }
        });

        try {
            t1.start();
            t2.start();
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Total: " + valor.getValor());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
