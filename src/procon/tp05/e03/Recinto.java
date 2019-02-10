package procon.tp05.e03;

public interface Recinto {
    public void entrar() throws InterruptedException;
    public void salir() throws InterruptedException;
    public int pedirBandeja(boolean quiereRefresco) throws InterruptedException;
    public Bandeja tomarBandeja(int nroMostrador) throws InterruptedException;
    public void tomarAbridor() throws InterruptedException;
    public void dejarAbridor() throws InterruptedException;
    public int pedirPostre() throws InterruptedException;
    public void tomarPostre(int nroMostrador) throws InterruptedException;
}
