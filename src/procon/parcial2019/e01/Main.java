package procon.parcial2019.e01;

public class Main {

    public static void main(String[] args) {
        CentroHemoterapia ch = new CentroHemoterapia();
        Thread[] pacientes = new Thread[20];

        for (int i = 0; i < pacientes.length; i++)
            pacientes[i] = new Thread(new Paciente(ch));

        for (int i = 0; i < pacientes.length; i++)
            pacientes[i].start();
    }

}
