package procon.tp02.e05;

public class Vehiculo {

    protected int modelo;
    protected String marca;
    protected String patente;
    protected int kmTanque;
    protected int kmFaltantesParaElService;

    public Vehiculo(int modelo, String marca, String patente, int kmTanque,
            int kmFaltantesParaElService) {
        super();
        this.modelo = modelo;
        this.marca = marca;
        this.patente = patente;
        this.kmTanque = kmTanque;
        this.kmFaltantesParaElService = kmFaltantesParaElService;
    }

    public int getModelo() {
        return modelo;
    }

    public void setModelo(int modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public int getKmTanque() {
        return kmTanque;
    }

    public void setKmTanque(int kmTanque) {
        this.kmTanque = kmTanque;
    }

    public int getKmFaltantesParaElService() {
        return kmFaltantesParaElService;
    }

    public void setKmFaltantesParaElService(int kmFaltantesParaElService) {
        this.kmFaltantesParaElService = kmFaltantesParaElService;
    }
}
