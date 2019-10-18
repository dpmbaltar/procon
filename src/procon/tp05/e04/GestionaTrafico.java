package procon.tp05.e04;

public interface GestionaTrafico {
    public void entrarCocheDelNorte(int auto) throws InterruptedException;
    public void entrarCocheDelSur(int auto) throws InterruptedException;
    public void salirCocheDelNorte(int auto) throws InterruptedException;
    public void salirCocheDelSur(int auto) throws InterruptedException;
}
