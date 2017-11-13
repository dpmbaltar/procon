package procon.parcial.e02;

public interface Trasbordador {

    /**
     * La capacidad del trasbordador.
     */
    public static final int CAPACIDAD = 10;

    /**
     * Lleva autos al otro lado del río.
     * @throws InterruptedException
     */
    public void ir() throws InterruptedException;

    /**
     * Vuelve vacío del otro lado del río.
     * @throws InterruptedException
     */
    public void volver() throws InterruptedException;

    /**
     * Carga el trasbordador con un auto.
     * @param auto
     * @throws InterruptedException
     */
    public void cargar(String auto) throws InterruptedException;

    /**
     * Descarga un auto del trasbordador.
     * @param auto
     * @throws InterruptedException
     */
    public void descargar() throws InterruptedException;

    public boolean estaFuncionando();
    public void iniciar();
    public void terminar();
}
