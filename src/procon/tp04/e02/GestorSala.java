package procon.tp04.e02;

public interface GestorSala {
    public static final int LIM_PERSONAS = 50;
    public static final int LIM_PERSONAS_UMBRAL_TEMP = 35;
    public static final int UMBRAL_TEMP = 30;
    public void entrarSala();
    public void entrarSalaJubilado();
    public void salirSala();
    public void notificarTemperatura(int temperatura);
    public void mostrarEstado();
}
