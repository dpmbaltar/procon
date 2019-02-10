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
        while (cantidadSoldados >= CAPACIDAD) {
            println(">>> Recinto lleno <<<");
            println(Thread.currentThread().getName()
                    + ">>> espera un lugar en el recinto");
            wait();
        }

        cantidadSoldados++;
        println(Thread.currentThread().getName() + ">>> entra al recinto");
    }

    @Override
    public synchronized void salir() throws InterruptedException {
        cantidadSoldados--;
        println(
                Thread.currentThread().getName() + ">>> sale del recinto");
        notifyAll();
    }

    @Override
    public synchronized int pedirBandeja(boolean quiereRefresco)
            throws InterruptedException {
        int nroMostrador = mostradorComidaDisp();
        while (nroMostrador < 0) {
            println(">>> Mostradores de comida ocupados <<<");
            println(Thread.currentThread().getName()
                    + ">>> espera monstrador para comida");
            wait();
            nroMostrador = mostradorComidaDisp();
        }

        mostradorComida[nroMostrador] = new Bandeja(quiereRefresco);
        println(
                Thread.currentThread().getName() + ">>> pide una bandeja");

        return nroMostrador;
    }

    @Override
    public synchronized Bandeja tomarBandeja(int nroMostrador)
            throws InterruptedException {
        Bandeja bandeja = mostradorComida[nroMostrador];
        mostradorComida[nroMostrador] = null;
        println(
                Thread.currentThread().getName() + ">>> toma la bandeja");
        notifyAll();

        return bandeja;
    }

    @Override
    public synchronized void tomarAbridor() throws InterruptedException {
        while (abridoresDisp < 1) {
            println(">>> Abridores ocupados <<<");
            println(
                    Thread.currentThread().getName() + ">>> espera abridor");
            wait();
        }
        abridoresDisp--;
        println(
                Thread.currentThread().getName() + ">>> toma un abridor");
    }

    @Override
    public synchronized void dejarAbridor() throws InterruptedException {
        abridoresDisp++;
        println(
                Thread.currentThread().getName() + ">>> deja el abridor");
        notifyAll();
    }

    @Override
    public synchronized int pedirPostre() throws InterruptedException {
        int nroMostrador = mostradorPostreDisp();
        while (nroMostrador < 0) {
            println(">>> Mostradores de postre ocupados <<<");
            println(Thread.currentThread().getName()
                    + ">>> espera monstrador para postre");
            wait();
            nroMostrador = mostradorPostreDisp();
        }

        mostradorPostre[nroMostrador] = true;
        println(
                Thread.currentThread().getName() + ">>> pide un postre");

        return nroMostrador;
    }

    @Override
    public synchronized void tomarPostre(int nroMostrador)
            throws InterruptedException {
        mostradorPostre[nroMostrador] = false;
        println(
                Thread.currentThread().getName() + ">>> toma el postre");
        notifyAll();
    }

    private int mostradorComidaDisp() {
        int disp = -1;
        for (int i = 0; i < MOSTRADORES_COMIDA; i++) {
            if (mostradorComida[i] == null) {
                disp = i;
                break;
            }
        }

        return disp;
    }

    private int mostradorPostreDisp() {
        int disp = -1;
        for (int i = 0; i < MOSTRADORES_POSTRE; i++) {
            if (!mostradorPostre[i]) {
                disp = i;
                break;
            }
        }

        return disp;
    }

    private void println(String s) {
        int tabs = 0;
        StringBuilder ln = new StringBuilder();
        String nombreSoldado, idSoldado;
        nombreSoldado = Thread.currentThread().getName();
        idSoldado = nombreSoldado.substring(nombreSoldado.indexOf('-') + 1);
        tabs = Integer.valueOf(idSoldado);

        for (int i = 0; i < tabs; i++)
            ln.append(' ');

        ln.append(s);
        System.out.println(ln);
    }

}
