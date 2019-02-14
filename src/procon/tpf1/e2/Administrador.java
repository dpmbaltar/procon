package procon.tpf1.e2;

public class Administrador extends Miembro {

    public Administrador(String nombre, Almacen almacen) {
        super(nombre, almacen);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                almacen.reponer(15, 20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Administrador>>> finaliza");
        }
    }

}
