package procon.tpf1.e2;

import java.util.Scanner;

/**
 * El Club de Fabricantes de Vino.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class Club {

    /**
     * Hilo principal.
     * 
     * @param args
     */
    public static void main(String[] args) {
        int cantidadMiembros = 10;
        Almacen almacen;
        Administrador administrador;
        Miembro miembro;
        Thread hiloAdministrador;
        Thread[] hiloMiembros;
        String nombreMiembro;
        Scanner entrada = new Scanner(System.in);

        System.out.println("-- CLUB DE FABRICANTES DE VINO --");
        System.out.println("Ingresar cantidad de miembros:");
        System.out.print(">>> ");
        cantidadMiembros = entrada.nextInt();
        entrada.close();
        System.out.println("-- INICIO DEL CLUB --");

        almacen = new Almacen(cantidadMiembros, 2, 7, 6, 15, 20);
        administrador = new Administrador("Administrador", almacen);
        hiloAdministrador = new Thread(administrador, "Administrador");
        hiloMiembros = new Thread[cantidadMiembros];

        for (int i = 0; i < hiloMiembros.length; i++) {
            nombreMiembro = "Miembro-" + (i + 1);
            miembro = new Miembro(nombreMiembro, almacen);
            hiloMiembros[i] = new Thread(miembro, nombreMiembro);
        }

        hiloAdministrador.start();
        for (int i = 0; i < hiloMiembros.length; i++)
            hiloMiembros[i].start();

        try {
            hiloAdministrador.join();
            for (int i = 0; i < hiloMiembros.length; i++)
                hiloMiembros[i].join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("-- FIN DEL CLUB --");
    }

}
