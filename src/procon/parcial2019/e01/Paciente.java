package procon.parcial2019.e01;

import java.util.Random;

public class Paciente implements Runnable {

    private static int paciente = 0;
    private CentroHemoterapia ch;

    public Paciente(CentroHemoterapia ch) {
        this. ch = ch;
    }

    private static synchronized String generarPaciente() {
        paciente++;
        return "Paciente-" + paciente;
    }

    @Override
    public void run() {
        Random random = new Random();
        String paciente;

        try {
            while (true) {
                paciente = generarPaciente();
                ch.entrar(paciente, random.nextBoolean());
                System.out.println(String.format("%s está donando sangre...", paciente));
                Thread.sleep(random.nextInt(10) * 100 + 1000);
                ch.salir(paciente);
                Thread.sleep(random.nextInt(10) * 100 + 2000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
