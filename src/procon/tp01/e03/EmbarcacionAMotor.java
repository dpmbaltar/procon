package procon.tp01.e03;

import java.time.Year;

public class EmbarcacionAMotor extends Barco {

    private int potencia;

    public EmbarcacionAMotor(String matricula, Year anioFabr, int eslora,
            int potencia) {
        super(matricula, anioFabr, eslora);
        this.potencia = potencia;
    }

    public int calcularModuloEspecial() {
        return potencia;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

}
