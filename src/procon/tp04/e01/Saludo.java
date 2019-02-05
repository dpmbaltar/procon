package procon.tp04.e01;

public class Saludo {
    private boolean jefeSaludo = false;
    private int cantidadEmpleados = 0;
    private int cantidadLlegaron = 0;
    
    public Saludo(int cantidadEmpleados) {
        this.cantidadEmpleados = cantidadEmpleados;
    }
    
    /**
     * El personal marca su llegada. Cuando todos han llegado el Jefe saluda.
     */
    public synchronized void marcar() {
        cantidadLlegaron++;
        if (cantidadLlegaron == cantidadEmpleados) {
            notifyAll();
        }
    }
    
    /**
     * El empleado que llego espera a que el Jefe salude para saludarlo.
     * @param empleado
     */
    public synchronized void esperarJefe(String empleado) {
        try {
            while (!jefeSaludo) {
                System.out.println(empleado + " esperando al jefe...");
                wait();
            }
            System.out.println(empleado + "> Buenos dias jefe!");
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * El jefe saluda, solo cuando todos han llegado.
     */
    public synchronized void saludoJefe() {
        try {
            while (cantidadLlegaron < cantidadEmpleados) {
                System.out.println("(JEFE esperando a todo personal)");
                wait();
            }
            System.out.println("JEFE> Buenos dias!");
            jefeSaludo = true;
            notifyAll();
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
    }
}
