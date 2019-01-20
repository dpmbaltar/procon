package procon.tp01.e03;

import java.time.LocalDate;
import java.time.Period;

public class Alquiler {

    private Cliente cliente;
    private Barco barco;
    private LocalDate fechaInicial;
    private LocalDate fechaFinal;
    private int amarre;
    private int valorFijo;

    public Alquiler(Cliente cliente, Barco barco, LocalDate fechaInicial,
            LocalDate fechaFinal, int amarre, int valorFijo) {
        this.cliente = cliente;
        this.barco = barco;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.amarre = amarre;
        this.valorFijo = valorFijo;
    }

    public int calcularValor() {
        return valorFijo + (calcularDias() * barco.calcularModulo());
    }

    private int calcularDias() {
        return Period.between(fechaInicial, fechaFinal).getDays();
    }

    public LocalDate getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(LocalDate fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public LocalDate getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public int getAmarre() {
        return amarre;
    }

    public void setAmarre(int amarre) {
        this.amarre = amarre;
    }

    public int getValorFijo() {
        return valorFijo;
    }

    public void setValorFijo(int valorFijo) {
        this.valorFijo = valorFijo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Barco getBarco() {
        return barco;
    }
}
