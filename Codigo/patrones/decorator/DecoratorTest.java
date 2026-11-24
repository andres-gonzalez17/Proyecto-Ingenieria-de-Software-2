package patrones.decorator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import modelo.Producto;
import modelo.ProductoBase;
import org.junit.Test;

public class DecoratorTest {

    private static final double DELTA = 0.001;

private static final double PRECIO_QUESO = 1000;
private static final double PRECIO_SALSA = 500;
private static final double PRECIO_CARNE = 3000;

    @Test
    public void extraSumaPrecioYDescripcion() {
        Producto hamburguesa = new ProductoBase("Hamburguesa", "Plato", 15000);
        Producto conQueso = new ExtraQueso(hamburguesa);

        assertEquals(15000 + PRECIO_QUESO, conQueso.getPrecio(), DELTA);
        assertEquals("Hamburguesa + queso", conQueso.getDescripcion());
    }

    @Test
    public void variosExtrasSeAcumulan() {
        Producto hamburguesa = new ProductoBase("Hamburguesa", "Plato", 15000);
        hamburguesa = new ExtraQueso(hamburguesa);
        hamburguesa = new ExtraSalsa(hamburguesa);
        hamburguesa = new ExtraCarne(hamburguesa);

        double esperado = 15000 + PRECIO_QUESO + PRECIO_SALSA + PRECIO_CARNE;
        assertEquals(esperado, hamburguesa.getPrecio(), DELTA);
        assertTrue(hamburguesa.getDescripcion().startsWith("Hamburguesa + queso + salsa"));
        assertTrue(hamburguesa.getDescripcion().contains("carne"));
    }

    @Test
    public void elOrdenDeLosExtrasNoCambiaElPrecio() {
        Producto a = new ExtraCarne(new ExtraQueso(new ProductoBase("Hamburguesa", "Plato", 15000)));
        Producto b = new ExtraQueso(new ExtraCarne(new ProductoBase("Hamburguesa", "Plato", 15000)));

        assertEquals(a.getPrecio(), b.getPrecio(), DELTA);
    }

    @Test
    public void elProductoOriginalNoCambia() {
        Producto hamburguesa = new ProductoBase("Hamburguesa", "Plato", 15000);
        Producto conSalsa = new ExtraSalsa(hamburguesa);

        // El decorator envuelve al producto, no lo modifica
        assertEquals(15000.0, hamburguesa.getPrecio(), DELTA);
        assertEquals("Hamburguesa", hamburguesa.getDescripcion());
        assertEquals(15000 + PRECIO_SALSA, conSalsa.getPrecio(), DELTA);
    }

    @Test
    public void unExtraSePuedeRepetir() {
        Producto hamburguesa = new ProductoBase("Hamburguesa", "Plato", 15000);
        hamburguesa = new ExtraQueso(hamburguesa);
        hamburguesa = new ExtraQueso(hamburguesa);

        assertEquals(15000 + 2 * PRECIO_QUESO, hamburguesa.getPrecio(), DELTA);
    }
}