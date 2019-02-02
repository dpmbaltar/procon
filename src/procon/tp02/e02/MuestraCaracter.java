package procon.tp02.e02;

public abstract class MuestraCaracter implements Runnable {
    protected Pantalla pantalla;
    public MuestraCaracter(Pantalla p) {
        this.pantalla = p;
    }

    @Override
    abstract public void run();
}
