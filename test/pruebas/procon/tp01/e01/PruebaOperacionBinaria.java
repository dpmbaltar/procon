package pruebas.procon.tp01.e01;

import static org.junit.Assert.*;

import org.junit.Test;

import procon.tp01.e01.Resta;
import procon.tp01.e01.Suma;

public class PruebaOperacionBinaria {

    @Test
    public void testResta() {
        Resta suma = new Resta();
        suma.cargar1(2);
        suma.cargar2(3);
        suma.operar();
        assertEquals("2 - 3 debe ser -1", -1.0, suma.getResultado(), 0.00001);
    }
    
    @Test
    public void testSuma() {
        Suma suma = new Suma();
        suma.cargar1(2);
        suma.cargar2(3);
        suma.operar();
        assertEquals("2 + 3 debe ser 5", 5.0, suma.getResultado(), 0.00001);
    }

}
