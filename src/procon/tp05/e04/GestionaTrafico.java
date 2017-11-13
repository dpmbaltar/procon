package procon.tp05.e04;

public interface GestionaTrafico {
    public void entrarCocheDelNorte(int id) throws InterruptedException;
    public void entrarCocheDelSur(int id) throws InterruptedException;
    public void salirCocheDelNorte() throws InterruptedException;
    public void salirCocheDelSur() throws InterruptedException;
}
