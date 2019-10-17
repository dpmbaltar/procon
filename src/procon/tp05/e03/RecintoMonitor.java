package procon.tp05.e03;

public class RecintoMonitor implements Recinto {

    private static final int CAPACIDAD = 100;
    private static final int MOSTRADORES_COMIDA = 5;
    private static final int MOSTRADORES_POSTRE = 3;
    private static final int ABRIDORES = 10;
    private int cantidadSoldados;
    private int abridoresDisp;
    private Bandeja[] mostradorComida;
    private boolean[] mostradorPostre;

    public RecintoMonitor() {
        cantidadSoldados = 0;
        abridoresDisp = ABRIDORES;
        mostradorComida = new Bandeja[MOSTRADORES_COMIDA];
        mostradorPostre = new boolean[MOSTRADORES_POSTRE];
    }

    @Override
    public synchronized void entrar() throws InterruptedException {
        // Esperar si el recinto esta lleno
        while (cantidadSoldados >= CAPACIDAD) {
            //System.out.println("!!! Recinto lleno !!!");
            //System.out.println(Thread.currentThread().getName() + ">>> espera un lugar en el recinto");
            wait();
        }

        cantidadSoldados++;
        System.out.println(Thread.currentThread().getName() + ">>> entra al recinto");
    }

    @Override
    public synchronized void salir() throws InterruptedException {
        cantidadSoldados--;
        System.out.println(Thread.currentThread().getName() + ">>> sale del recinto");
        notifyAll();
    }

    @Override
    public synchronized int pedirBandeja(boolean quiereRefresco) throws InterruptedException {
        int nroMostrador = mostradorComidaDisp();

        while (nroMostrador < 0) {
            //System.out.println("!!! Mostradores de comida ocupados !!!");
            //System.out.println(Thread.currentThread().getName() + ">>> espera monstrador para comida");
            wait();
            nroMostrador = mostradorComidaDisp();
        }

        // Ocupar mostrador
        mostradorComida[nroMostrador] = new Bandeja(quiereRefresco);
        System.out.println(Thread.currentThread().getName() + ">>> pide una bandeja");

        return nroMostrador;
    }

    @Override
    public synchronized Bandeja tomarBandeja(int nroMostrador) throws InterruptedException {
        // Liberar mostrador
        Bandeja bandeja = mostradorComida[nroMostrador];
        mostradorComida[nroMostrador] = null;
        System.out.println(Thread.currentThread().getName() + ">>> toma la bandeja");
        notifyAll();

        return bandeja;
    }

    @Override
    public synchronized void tomarAbridor() throws InterruptedException {
        // Esperar si no hay un abridor disponible
        while (abridoresDisp < 1) {
            //System.out.println("!!! Abridores ocupados !!!");
            //System.out.println(Thread.currentThread().getName() + ">>> espera abridor");
            wait();
        }

        abridoresDisp--;
        System.out.println(Thread.currentThread().getName() + ">>> toma un abridor");
    }

    @Override
    public synchronized void dejarAbridor() throws InterruptedException {
        // Dejar el abridor
        abridoresDisp++;
        System.out.println(Thread.currentThread().getName() + ">>> deja el abridor");
        notifyAll();
    }

    @Override
    public synchronized int pedirPostre() throws InterruptedException {
        int nroMostrador = mostradorPostreDisp();

        // Esperar mientras no hay mostradores de postres disponibles
        while (nroMostrador < 0) {
            //System.out.println("!!! Mostradores de postre ocupados !!!");
            //System.out.println(Thread.currentThread().getName() + ">>> espera monstrador para postre");
            wait();
            nroMostrador = mostradorPostreDisp();
        }

        // Ocupar mostrador de postres
        mostradorPostre[nroMostrador] = true;
        System.out.println(Thread.currentThread().getName() + ">>> pide un postre");

        return nroMostrador;
    }

    @Override
    public synchronized void tomarPostre(int nroMostrador) throws InterruptedException {
        // Liberar mostrador de postres
        mostradorPostre[nroMostrador] = false;
        System.out.println(Thread.currentThread().getName() + ">>> toma el postre");
        notifyAll();
    }

    private int mostradorComidaDisp() {
        int disponible = -1;
        int i = 0;

        do {
            if (mostradorComida[i] == null) {
                disponible = i;
            }

            i++;
        } while (disponible < 0 && i < MOSTRADORES_COMIDA);

        return disponible;
    }

    private int mostradorPostreDisp() {
        int disponible = -1;
        int i = 0;

        do {
            if (!mostradorPostre[i]) {
                disponible = i;
            }

            i++;
        } while (disponible < 0 && i < MOSTRADORES_POSTRE);

        return disponible;
    }

}
