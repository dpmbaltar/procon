package procon.parcial.e01;

/**
 * Interface de TramoCompartido, para las distintas implementaciones.
 */
public interface TramoCompartido {
    public void entrarDesdeLadoA(String tren) throws InterruptedException;
    public void salirDesdeLadoA(String tren);
    public void entrarDesdeLadoB(String tren) throws InterruptedException;
    public void salirDesdeLadoB(String tren);
}
