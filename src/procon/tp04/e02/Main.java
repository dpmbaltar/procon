package procon.tp04.e02;

public class Main {
    public static void main(String[] args) {
        GestorSala gs = new GestorSalaMonitor();
        SensorTemperatura st = new SensorTemperatura(gs, 3);
        Personas p = new Personas(gs);
        Thread tst = new Thread(st);
        Thread tp = new Thread(p);
        
        tst.start();
        tp.start();
    }
}
