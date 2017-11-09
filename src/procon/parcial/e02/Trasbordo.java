package procon.parcial.e02;

public class Trasbordo {

    private Trasbordador trasbordador;

    public Trasbordo() {
        trasbordador = new Trasbordador();
    }

    public void iniciar() throws InterruptedException {
        Thread ladoA = new Thread(new LadoA(trasbordador), "Lado-A");
        Thread ladoB = new Thread(new LadoB(trasbordador), "Lado-B");

        ladoA.start();
        ladoB.start();

        while (true) {
            trasbordador.ir();
            trasbordador.volver();
        }
    }

    public static void main(String[] args) {
        try {
            Trasbordo trasbordo = new Trasbordo();
            trasbordo.iniciar();
        } catch (InterruptedException e) {}
    }
}
