package procon.tp05.e02;

public class Main {

    public static void main(String[] args) {
        RecursoLE recurso = new RecursoLEMonitor();
        Thread t1, t2, t3, t4;
        t1 = new Thread(new Escritor(recurso), "Escritor-1");
        t2 = new Thread(new Lector(recurso), "Lector-1");
        t3 = new Thread(new Lector(recurso), "Lector-2");
        t4 = new Thread(new Lector(recurso), "Lector-3");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

}
