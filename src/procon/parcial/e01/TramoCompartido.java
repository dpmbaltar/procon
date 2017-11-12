package procon.parcial.e01;

/**
 * Interface de TramoCompartido, para las distintas implementaciones.
 */
public interface TramoCompartido {

    /**
     * Simula entrar un tren al tramo compartido.
     * @param tren el tren que va a entrar
     * @throws InterruptedException
     */
    public void entrar(Tren tren) throws InterruptedException;

    /**
     * Simula salir el tren que entró al tramo compartido.
     */
    public void salir();
}
