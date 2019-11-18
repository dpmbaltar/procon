package procon.tpof2019;

import java.util.LinkedList;

/**
 * Shop donde pasan el rato los visitantes y compran souvenir opcionalmente.
 * Implementado con Monitor.
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

    /**
     * Vista del parque.
     */
    private final VistaParque vista = VistaParque.getInstancia();

    /**
     * Visitante entra al shop.
     */
    public synchronized void entrar() {
        vista.printShop(String.format("%s entra al shop", Thread.currentThread().getName()));
        vista.agregarClienteShop();
    }

    /**
     * Visitante compra un souvenir (opcional).
     *
     * @return la caja donde pagar
     */
    public synchronized int comprar() {
        int caja = -1;
        String visitante = Thread.currentThread().getName();

        // Ir hacia la caja con menos gente
        if (caja1.size() <= caja2.size()) {
            caja1.add(visitante);
            caja = 1;
        } else {
            caja2.add(visitante);
            caja = 2;
        }

        vista.printShop(String.format("%s decide comprar souvenir", visitante));
        vista.agregarClienteCaja(caja);

        return caja;
    }

    /**
     * Visitante paga (si compró un souvenir).
     *
     * @param cajaElegida la caja donde va a pagar (-1 si no compró)
     * @throws InterruptedException
     */
    public synchronized void pagar(int cajaElegida) throws InterruptedException {
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

            vista.printShop(String.format("%s paga en caja %d", visitante, cajaElegida));
            vista.sacarClienteCaja(cajaElegida);
            caja.remove();
            notifyAll();
        }
    }

    /**
     * Visitante sale del shop.
     */
    public synchronized void salir() {
        vista.printShop(String.format("%s sale del shop", Thread.currentThread().getName()));
        vista.sacarClienteShop();
    }

}
