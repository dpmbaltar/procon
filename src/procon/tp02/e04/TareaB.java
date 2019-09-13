package procon.tp02.e04;

public class TareaB implements Runnable {

    private Valor valor;

    public TareaB(Valor valor) {
        this.valor = valor;
    }

    @Override
    public void run() {
        try {
            int total = valor.getValor();
            total *= 2;
            valor.setValor(total);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
