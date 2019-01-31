package procon.clase04.barberia;

public class IniciaBarberia {

    public static void main(String[] args) {
        Barberia barberia = new Barberia();
        Barbero barbero = new Barbero("Barbero Dormilón", barberia);
        Cliente cliente;
        (new Thread(barbero, barbero.getNombre())).start();
        for (int i = 1; i <= 100; i++) {
            cliente = new Cliente("Cliente #" + i, barberia);
            (new Thread(cliente, cliente.getNombre())).start();
        }
    }
}
