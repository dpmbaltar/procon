package procon.tp02.e04;

public class TareaA implements Runnable {

    private Valor valor;

    public TareaA(Valor valor) {
        this.valor = valor;
    }

    @Override
    public void run() {
        try {
            int total = valor.getValor();
            total++;
            valor.setValor(total);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
