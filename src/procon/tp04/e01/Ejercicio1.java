package procon.tp04.e01;

public class Ejercicio1 {

    public static void main(String[] args) {
        String[] nombresEmpleados = { "Pablo", "Luis", "Andrea", "Pedro", "Paula" };
        Saludo hola = new Saludo(6);
        Personal[] elPersonal = new Personal[6];
        elPersonal[0] = new Personal(hola, "JEFE", true);

        for (int i = 1; i < 6; i++)
            elPersonal[i] = new Personal(hola, nombresEmpleados[i - 1]);
        for (int i = 0; i < 6; i++)
            elPersonal[i].start();
        try {
            for (int i = 0; i < 6; i++)
                elPersonal[i].join();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
