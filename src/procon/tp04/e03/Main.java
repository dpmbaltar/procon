package procon.tp04.e03;

import procon.tp04.e03.Impresora.Tipo;

public class Main {

    public static void main(String[] args) {
        Thread[] usuarios = new Thread[3];
        Thread[] impresoras = new Thread[3];
        ServicioImpresion si = new ServicioImpresionLock();

        // Crear hilos de usuario
        for (int i = 0; i < usuarios.length; i++)
            usuarios[i] = new Thread(new Usuario(si));

        for (int i = 0; i < impresoras.length; i++) {
            // Crear 2 tipo A y 1 tipo B
            if (i < 2)
                impresoras[i] = new Thread(new Impresora(si, Tipo.A, i + 1));
            else
                impresoras[i] = new Thread(new Impresora(si, Tipo.B, i + 1));
        }

        // Iniciar hilos de usuarios
        for (int i = 0; i < usuarios.length; i++)
            usuarios[i].start();

        // Iniciar hilos de impresoras
        for (int i = 0; i < impresoras.length; i++)
            impresoras[i].start();
    }

}
