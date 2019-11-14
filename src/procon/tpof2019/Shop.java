package procon.tpof2019;

import java.util.LinkedList;

/**
 * Shop (Monitores).
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Shop {

    /**
     * Cola de caja 1.
     */
    private final LinkedList<String> caja1 = new LinkedList<>();

    /**
     * Cola de caja 2.
     */
    private final LinkedList<String> caja2 = new LinkedList<>();

    private final VistaParque vista = VistaParque.getInstance();

    /**
     * Visitante entra el shop.
     *
     * @throws InterruptedException
     */
    public synchronized void entrar() {
        String visitante = Thread.currentThread().getName();
        vista.printShop(String.format("%s entra al shop", visitante));
    }

    /**
     * Compra un souvenir (opcional).
     *
     * @return la caja donde pagar
     */
    public synchronized int comprar() {
        int caja = -1;
        String visitante = Thread.currentThread().getName();
        vista.printShop(String.format("%s decide comprar un souvenir", visitante));

        // Ir hacia la caja con menos visitantes
        if (caja1.size() <= caja2.size()) {
            caja1.add(visitante);
            caja = 1;
        } else {
            caja2.add(visitante);
            caja = 2;
        }

        return caja;
    }

    /**
     * Visitante sale del shop.
     *
     * @param caja la caja donde va a pagar (-1 si no compró)
     * @throws InterruptedException
     */
    public synchronized void salir(int cajaElegida) throws InterruptedException {
        String visitante = Thread.currentThread().getName();
        LinkedList<String> caja = null;

        if (cajaElegida == 1)
            caja = caja1;
        else if (cajaElegida == 2)
            caja = caja2;

        if (caja != null) {
            // Esperar su turno para pagar por caja y salir
            while (!caja.peek().equals(visitante))
                wait();

            caja.remove();
            vista.printShop(String.format("%s paga en caja %d y sale del shop", visitante, cajaElegida));
        } else {
            vista.printShop(String.format("%s sale del shop sin comprar", visitante));
        }
    }

}
