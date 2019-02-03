package procon.tp02.e04;

abstract public class Operador implements Runnable {
    
    private Valor valor;
    
    public Operador(Valor valor) {
        this.valor = valor;
    }

    @Override
    abstract public void run();
}
