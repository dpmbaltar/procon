package procon.tp04.e02;

public class GestorSalaMonitor implements GestorSala {
    private int temperatura = 20;
    private int cantidadPersonas = 0;
    private int jubiladosEsperando = 0;
    private int maximoPersonas = LIM_PERSONAS;

    public synchronized void entrarSala() {
        try {
            while (cantidadPersonas >= maximoPersonas || jubiladosEsperando > 0)
                wait();
            cantidadPersonas++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void entrarSalaJubilado() {
        try {
            jubiladosEsperando++;
            while (cantidadPersonas >= maximoPersonas)
                wait();
            jubiladosEsperando--;
            cantidadPersonas++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void salirSala() {
        cantidadPersonas--;
        notifyAll();
    }

    public synchronized void notificarTemperatura(int temperatura) {
        this.temperatura = temperatura;
        if (this.temperatura > UMBRAL_TEMP) {
            maximoPersonas = LIM_PERSONAS_UMBRAL_TEMP;
        } else {
            maximoPersonas = LIM_PERSONAS;
        }
        notifyAll();
    }

    public synchronized void mostrarEstado() {
        System.out.print("Personas:    [");
        for (int i = 1; i < LIM_PERSONAS; i++) {
            if (i <= cantidadPersonas) {
                System.out.print('|');
            } else {
                System.out.print('.');
            }
        }
        System.out.println("] " + cantidadPersonas);
        System.out.print("Temperatura: [");
        for (int i = 1; i < LIM_PERSONAS; i++) {
            if (i <= temperatura) {
                System.out.print('|');
            } else {
                System.out.print('.');
            }
        }
        System.out.println("] " + temperatura + "°");
        System.out.println();
    }
}
