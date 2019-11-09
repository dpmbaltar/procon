package procon.parcial2019.e02;

import java.util.concurrent.Semaphore;

public class Observatorio {

    private static final int CAPACIDAD = 5;

    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore entraVisitante = new Semaphore(0);
    private final Semaphore entraPersonal = new Semaphore(0);
    private final Semaphore entraProfesional = new Semaphore(0);
    private final Semaphore visitantes = new Semaphore(CAPACIDAD, true);
    private final Semaphore personal = new Semaphore(CAPACIDAD, true);
    //private final Semaphore profesionales = new Semaphore(CAPACIDAD, true);

    private int visitantesEsperando = 0;
    private int personalEsperando = 0;
    private int profesionalesEsperando = 0;
    private int visitantesAdentro = 0;
    private int personalAdentro = 0;
    private int profesionalesAdentro = 0;
//    private int totalVisitantes = 0;
//    private int totalPersonal = 0;
//    private int totalProfesionales = 0;
    private boolean entroAlguien = false;

    public void entraVisitante(String nombre, boolean sillaDeRuedas) throws InterruptedException {
        mutex.acquire();
        // Entran visitantes, personal o profesionales según cual llegue primero
        if (!entroAlguien) {
            entroAlguien = true;
            entraVisitante.release();
        }
        visitantesEsperando++;
        mutex.release();

        // Evita que entren más de lo admitido (CAPACIDAD)
        if (sillaDeRuedas)
            visitantes.acquire(2);
        else
            visitantes.acquire();
        entraVisitante.acquire(); // Indica que solo están entrando visitantes al observatorio

        mutex.acquire();
        visitantesEsperando--;
        visitantesAdentro++;
        //totalVisitantes++;
        System.out.println(String.format("Entra visitante %s (silla: %s)", nombre, String.valueOf(sillaDeRuedas)));
        entraVisitante.release(); // Dejar entrar otro visitante
        mutex.release();
    }

    public void saleVisitante(String nombre, boolean sillaDeRuedas) throws InterruptedException {
        mutex.acquire();

        System.out.println(String.format("Sale visitante %s", nombre));
        visitantesAdentro--;

        if (visitantesEsperando == 0 && visitantesAdentro == 0) {
            entraVisitante.acquire(); // No hay visitantes adentro ni esperando (adquiere de inmediato)
            // Dejar pasar a profesionales o personal primero, si hay esperando
            if (personalEsperando > 0)
                entraPersonal.release(); // Entra personal
            else if (profesionalesEsperando > 0)
                entraProfesional.release(); // Entra profesional
            //else if (visitantesEsperando > 0)
            //    entraVisitante.release(); // Entra visitante
            // Sino, entra el que siga primero
        }

        mutex.release();
        if (sillaDeRuedas)
            visitantes.release(2);
        else
            visitantes.release();
    }

    public void entraPersonal(String nombre) throws InterruptedException {
        mutex.acquire();
        // Entran visitantes, personal o profesionales según cual llegue primero
        if (!entroAlguien) {
            entroAlguien = true;
            entraPersonal.release();
        }
        personalEsperando++;
        mutex.release();

        personal.acquire();
        entraPersonal.acquire();

        mutex.acquire();
        personalEsperando--;
        personalAdentro++;
        //totalPersonal++;
        System.out.println(String.format("Entra personal %s", nombre));
        entraPersonal.release(); // Dejar entrar más personal
        mutex.release();
    }

    public void salePersonal(String nombre) throws InterruptedException {
        mutex.acquire();

        System.out.println(String.format("Sale personal %s", nombre));
        personalAdentro--;

        if (personalEsperando == 0 && personalAdentro == 0) {
            entraPersonal.acquire(); // No hay personal adentro ni esperando (adquiere de inmediato)
            // Dejar pasar a profesionales o visitantes primero, si hay esperando
            if (profesionalesEsperando > 0)
                entraProfesional.release(); // Entra profesional
            else if (visitantesEsperando > 0)
                entraVisitante.release();
            //else if (personalEsperando > 0)
            //    entraPersonal.release(); // Entra personal
            // Sino, entra el que siga primero
        }

        mutex.release();
        personal.release();
    }

    public void entraProfesional(String nombre) throws InterruptedException {
        mutex.acquire();
        // Entran visitantes, personal o profesionales según cual llegue primero
        if (!entroAlguien) {
            entroAlguien = true;
            entraProfesional.release();
        }
        profesionalesEsperando++;
        mutex.release();

        //profesionales.acquire(CAPACIDAD);
        entraProfesional.acquire();

        mutex.acquire();
        profesionalesEsperando--;
        profesionalesAdentro++;
        //totalProfesionales++;
        System.out.println(String.format("Entra profesional %s", nombre));
        //entraProfesional.release(); // Deja entrar al siguiente profesional
        mutex.release();
    }

    public void saleProfesional(String nombre) throws InterruptedException {
        mutex.acquire();

        System.out.println(String.format("Sale profesional %s", nombre));
        profesionalesAdentro--;

        if (profesionalesEsperando == 0 && profesionalesAdentro == 0) {
            //entraProfesional.acquire(); // No hay profesionales adentro ni esperando (adquiere de inmediato)
            // Dejar pasar a profesionales o visitantes primero, si hay esperando
            if (visitantesEsperando > 0)
                entraVisitante.release(); // Entra visitante
            else if (personalEsperando > 0)
                entraPersonal.release(); // Entra peronal
            //else if (profesionalesEsperando > 0)
            //    entraProfesional.release(); // Entra profesional
            // Sino, entra el que siga primero
        } else {
            entraProfesional.release();
        }

        mutex.release();
        //profesionales.release(CAPACIDAD);
    }

}
