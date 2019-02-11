package procon.tpf1.e2;

public class Club {

    public static void main(String[] args) {
        final int cantidadMiembros = 10;
        Almacen almacen;
        Administrador administrador;
        Miembro miembro;
        Thread hiloAdministrador;
        Thread[] hiloMiembros;
        String nombreMiembro;

        almacen = new Almacen(2, 7, 6, 15, 20);
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
            //hiloAdministrador.join();
            for (int i = 0; i < hiloMiembros.length; i++)
                hiloMiembros[i].join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("-- FIN DEL PROGRAMA --");
    }

}
