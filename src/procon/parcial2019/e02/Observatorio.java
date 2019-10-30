package procon.parcial2019.e02;

import java.util.concurrent.Semaphore;

public class Observatorio {

    private static final int CAPACIDAD = 5;

    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore visitantes = new Semaphore(CAPACIDAD, true);
    private final Semaphore personal = new Semaphore(CAPACIDAD, true);
    private final Semaphore profesionales = new Semaphore(CAPACIDAD, true);
    private final Semaphore entraVisitante = new Semaphore(1);
    private final Semaphore entraPersonal = new Semaphore(0);
    private final Semaphore entraProfesional = new Semaphore(0);

    private int visitantesAdentro = 0;
    private int personalAdentro = 0;
    private int profesionalesAdentro = 0;
    private int totalVisitantes = 0;
    private int totalPersonal = 0;
    private int totalProfesionales = 0;
    private int visitantesEsperando = 0;
    private int personalEsperando = 0;
    private int profesionalesEsperando = 0;

    public void entraVisitante(String nombre, boolean sillaDeRuedas) throws InterruptedException {
        mutex.acquire();
        visitantesEsperando++;
        mutex.release();

        visitantes.acquire(); // Evita que entren más de lo admitido (CAPACIDAD)
        entraVisitante.acquire(); // Indica que solo están entrando visitantes al observatorio

        mutex.acquire();
        visitantesEsperando--;
        visitantesAdentro++;
        totalVisitantes++;
        System.out.println(String.format("Entra visitante %s", nombre));
        entraVisitante.release(); // Dejar entrar otro visitante
        mutex.release();
    }

    public void saleVisitante(String nombre, boolean sillaDeRuedas) throws InterruptedException {
        mutex.acquire();

        System.out.println(String.format("Sale visitante %s", nombre));
        visitantesAdentro--;

        if (visitantesEsperando == 0 && visitantesAdentro == 0) {
            System.out.println("!!!NO HAY MÁS VISITANTES DENTRO NI ESPERANDO!!!");
            System.out.println(String.format("Total de visitas: %s", totalVisitantes));
            System.out.println(String.format("Visitantes esperando: %s", visitantesEsperando));
            System.out.println(String.format("Personal esperando: %s", personalEsperando));
            System.out.println(String.format("Profesionales esperando: %s", profesionalesEsperando));
            entraVisitante.acquire(); // No hay visitantes adentro ni esperando (adquiere de inmediato)
            // Dejar pasar a profesionales o personal primero, si hay esperando
            if (personalEsperando > 0)
                entraPersonal.release(); // Entra personal
            else if (profesionalesEsperando > 0)
                entraProfesional.release(); // Entra profesional
            else
                entraVisitante.release(); // Entra visitante
        }

        mutex.release();
        visitantes.release();
    }

    public void entraPersonal(String nombre) throws InterruptedException {
        mutex.acquire();
        personalEsperando++;
        mutex.release();

        personal.acquire();
        entraPersonal.acquire();

        mutex.acquire();
        personalEsperando--;
        personalAdentro++;
        totalPersonal++;
        System.out.println(String.format("Entra personal %s", nombre));
        entraPersonal.release(); // Dejar entrar más personal
        mutex.release();
    }

    public void salePersonal(String nombre) throws InterruptedException {
        mutex.acquire();

        System.out.println(String.format("Sale personal %s", nombre));
        personalAdentro--;

        if (personalEsperando == 0 && personalAdentro == 0) {
            System.out.println("!!!NO HAY MÁS PERSONAL DENTRO NI ESPERANDO!!!");
            System.out.println(String.format("Total de visitas: %s", totalPersonal));
            System.out.println(String.format("Visitantes esperando: %s", visitantesEsperando));
            System.out.println(String.format("Personal esperando: %s", personalEsperando));
            System.out.println(String.format("Profesionales esperando: %s", profesionalesEsperando));
            entraPersonal.acquire(); // No hay personal adentro ni esperando (adquiere de inmediato)
            // Dejar pasar a profesionales o visitantes primero, si hay esperando
            if (profesionalesEsperando > 0)
                entraProfesional.release(); // Entra profesional
            else if (visitantesEsperando > 0)
                entraVisitante.release();
            else
                entraPersonal.release(); // Entra personal
        }

        mutex.release();
        personal.release();
    }

    public void entraProfesional(String nombre) throws InterruptedException {
        mutex.acquire();
        profesionalesEsperando++;
        mutex.release();

        profesionales.acquire(CAPACIDAD);
        entraProfesional.acquire();

        mutex.acquire();
        profesionalesEsperando--;
        profesionalesAdentro++;
        totalProfesionales++;
        System.out.println(String.format("Entra profesional %s", nombre));
        entraProfesional.release(); // Deja entrar al siguiente profesional
        mutex.release();
    }

    public void saleProfesional(String nombre) throws InterruptedException {
        mutex.acquire();

        System.out.println(String.format("Sale profesional %s", nombre));
        profesionalesAdentro--;

        if (profesionalesEsperando == 0 && profesionalesAdentro == 0) {
            System.out.println("!!!NO HAY MÁS PROFESIONALES DENTRO NI ESPERANDO!!!");
            System.out.println(String.format("Total de visitas: %s", totalProfesionales));
            System.out.println(String.format("Visitantes esperando: %s", visitantesEsperando));
            System.out.println(String.format("Personal esperando: %s", personalEsperando));
            System.out.println(String.format("Profesionales esperando: %s", profesionalesEsperando));
            entraProfesional.acquire(); // No hay profesionales adentro ni esperando (adquiere de inmediato)
            // Dejar pasar a profesionales o visitantes primero, si hay esperando
            if (visitantesEsperando > 0)
                entraVisitante.release(); // Entra visitante
            else if (personalEsperando > 0)
                entraPersonal.release(); // Entra peronal
            else
                entraProfesional.release(); // Entra profesional
        }

        mutex.release();
        profesionales.release(CAPACIDAD);
    }

}
