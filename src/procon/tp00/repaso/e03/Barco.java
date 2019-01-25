package procon.tp00.repaso.e03;

import java.time.Year;

public class Barco {

    private int eslora;
    private Year anioFabr;
    private String matricula;

    public Barco(String matricula, Year anioFabr, int eslora) {
        this.eslora = eslora;
        this.anioFabr = anioFabr;
        this.matricula = matricula;
    }

    public int calcularModuloEspecial() {
        return 0;
    }

    public int calcularModulo() {
        return (eslora * 10) + calcularModuloEspecial();
    }

    public Year getAnioFabr() {
        return anioFabr;
    }

    public void setAnioFabr(Year anioFabr) {
        this.anioFabr = anioFabr;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getEslora() {
        return eslora;
    }

    public void setEslora(int eslora) {
        this.eslora = eslora;
    }
}
