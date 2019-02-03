package procon.tp02.e03;

public class CuentaBanco {

    private int balance = 50;

    public CuentaBanco() {
    }

    public synchronized int getBalance() {
        return balance;
    }

    public synchronized void retiroBancario(int retiro) {
        balance = balance - retiro;
    }

    /**
     * Se eliminó el método de VerificarCuenta, y se agrega a CuentaBanco de
     * forma sincronizada utilizando synchronized.
     * De esta forma se hace el retiro de forma atómica, asegurando que una la
     * cuenta nunca quede en saldo negativo.
     * @param cantidad
     * @throws InterruptedException
     */
    public synchronized void hacerRetiro(int cantidad)
            throws InterruptedException {
        if (balance >= cantidad) {
            System.out.println(Thread.currentThread().getName()
                    + " está realizando un retiro de: " + cantidad + ".");
            Thread.sleep(1000);
            retiroBancario(cantidad);
            System.out.println(
                    Thread.currentThread().getName() + ": Retiro realizado.");
            System.out.println(Thread.currentThread().getName()
                    + ": Los fondos son de: " + balance);
        } else {
            System.out.println(
                    "No hay suficiente dinero en la cuenta para realizar el"
                            + "retiro Sr." + Thread.currentThread().getName());
            System.out.println("Su saldo actual es de " + balance);
            Thread.sleep(1000);
        }
    }
}
