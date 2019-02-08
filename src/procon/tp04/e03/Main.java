package procon.tp04.e03;

import procon.tp04.e03.Impresora.Tipo;

public class Main {
    public static void main(String[] args) {
        Thread t1, t2, t3, t4, t5, t6;
        ServicioImpresion si = new ServicioImpresionLock();
        t1 = new Thread(new Usuario(si));
        t2 = new Thread(new Usuario(si));
        t3 = new Thread(new Usuario(si));
        t4 = new Thread(new Impresora(si, Tipo.A, 1));
        t5 = new Thread(new Impresora(si, Tipo.A, 2));
        t6 = new Thread(new Impresora(si, Tipo.B, 3));
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
    }
}
