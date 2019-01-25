package procon.tp00.repaso.e01;

public abstract class OperacionBinaria {
    protected double valor1;
    protected double valor2;
    protected double resultado;
    
    public void cargar1(double valor1) {
        this.valor1 = valor1;
    }
    
    public void cargar2(double valor2) {
        this.valor2 = valor2;
    }
    
    public abstract void operar();

    public double getValor1() {
        return valor1;
    }

    public double getValor2() {
        return valor2;
    }

    public double getResultado() {
        return resultado;
    }
}
