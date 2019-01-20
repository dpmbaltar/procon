package procon.tp01.e03;

import java.time.Year;

public class Yate extends EmbarcacionAMotor {

    private int numCamarotes;

    public Yate(String matricula, Year anioFabr, int eslora, int potencia,
            int numCamarotes) {
        super(matricula, anioFabr, eslora, potencia);
        this.numCamarotes = numCamarotes;
    }

    public int calcularModuloEspecial() {
        return super.calcularModuloEspecial() + numCamarotes;
    }

    public int getNumCamarotes() {
        return numCamarotes;
    }

    public void setNumCamarotes(int numCamarotes) {
        this.numCamarotes = numCamarotes;
    }

}
