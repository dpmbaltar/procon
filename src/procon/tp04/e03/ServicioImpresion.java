package procon.tp04.e03;

/**
 * Interface de un servicio de impresión.
 */
public interface ServicioImpresion {
    public void solicitar(Impresion impresion);
    public Impresion imprimir(Impresora impresora);
}
