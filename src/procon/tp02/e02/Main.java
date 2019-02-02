package procon.tp02.e02;

public class Main {
    public static void main(String[] args) {
        Pantalla p = new Pantalla();
        (new Thread(new MuestraCaracter(p) {
            @Override
            public void run() {
                while (true) {
                    try {
                        pantalla.mostrarA();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        })).start();
        (new Thread(new MuestraCaracter(p) {
            @Override
            public void run() {
                while (true) {
                    try {
                        pantalla.mostrarB();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        })).start();
        (new Thread(new MuestraCaracter(p) {
            @Override
            public void run() {
                while (true) {
                    try {
                        pantalla.mostrarC();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        })).start();
    }
}
