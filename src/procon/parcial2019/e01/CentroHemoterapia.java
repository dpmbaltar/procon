package procon.parcial2019.e01;

import java.util.concurrent.Semaphore;

class CentroHemoterapia {

    private final Semaphore sillas = new Semaphore(12);
    private final Semaphore revistas = new Semaphore(9);
    private final Semaphore camillas = new Semaphore(4, true);

    public void entrar(String paciente, boolean quiereSilla) throws InterruptedException {
        boolean tieneSilla = false;
        boolean tieneRevista = false;
        System.out.println(String.format("Entra %s", paciente));

        // Si no pudo pasar a donar, espera
        if (!camillas.tryAcquire()) {
            if (quiereSilla) {
                if (sillas.tryAcquire()) {
                    tieneSilla = true;
                    System.out.println(String.format("%s se sienta", paciente));
                } else {
                    System.out.println(String.format("%s quiere sentarse (no hay sillas)", paciente));
                }
            }

            if (revistas.tryAcquire()) {
                tieneRevista = true;
                System.out.println(String.format("%s toma una revista", paciente));
            } else {
                System.out.println(String.format("%s mira TV", paciente));
            }

            camillas.acquire();
            System.out.println(String.format("%s ocupa una camilla", paciente));
            if (tieneRevista)
                revistas.release();
            if (tieneSilla)
                sillas.release();
        }

        System.out.println(String.format("%s dona sangre", paciente));
    }

    public void salir(String paciente) throws InterruptedException {
        System.out.println(String.format("%s desocupa una camilla", paciente));
        camillas.release();
        System.out.println(String.format("Sale %s", paciente));
    }

}
