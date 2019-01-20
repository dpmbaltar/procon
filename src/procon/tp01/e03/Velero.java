package procon.tp01.e03;

import java.time.Year;

public class Velero extends Barco {

    private int numMastiles;

    public Velero(String matricula, Year anioFabr, int eslora,
            int numMastiles) {
        super(matricula, anioFabr, eslora);
        this.numMastiles = numMastiles;
    }

    public int calcularModuloEspecial() {
        return numMastiles;
    }

    public int getNumMastiles() {
        return numMastiles;
    }

    public void setNumMastiles(int numMastiles) {
        this.numMastiles = numMastiles;
    }

}
