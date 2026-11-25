package patrones.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import modelo.Combo;
import modelo.Producto;
import modelo.ProductoBase;
import org.junit.Test;
import patrones.decorator.ExtraQueso;

public class ComboBuilderTest {

    private static final double DELTA = 0.001;

    private ProductoBase plato = new ProductoBase("Hamburguesa", "Plato", 15000);
    private ProductoBase acompanamiento = new ProductoBase("Papas fritas", "Plato", 5000);
    private ProductoBase bebida = new ProductoBase("Gaseosa", "Bebida", 3500);

    @Test
    public void comboCompletoSeConstruyeConPrecioYDescripcion() {
        Combo combo = new ComboBuilder("Combo prueba")
                .conPlato(plato)
                .conAcompanamiento(acompanamiento)
                .conBebida(bebida)
                .conTamaño("Grande")
                .construir();

        assertEquals(3, combo.getItems().size());
        assertEquals(23500.0, combo.getPrecio(), DELTA);
        assertEquals("Combo prueba Grande (Hamburguesa, Papas fritas, Gaseosa)",
                combo.getDescripcion());
    }

    @Test
    public void elOrdenDeLosPasosNoImporta() {
        Combo combo = new ComboBuilder("Combo prueba")
                .conTamaño("Grande")
                .conBebida(bebida)
                .conAcompanamiento(acompanamiento)
                .conPlato(plato)
                .construir();

        assertEquals(23500.0, combo.getPrecio(), DELTA);
    }

    @Test(expected = IllegalStateException.class)
    public void sinPlatoNoSeConstruye() {
        new ComboBuilder("Combo roto")
                .conAcompanamiento(acompanamiento)
                .conBebida(bebida)
                .conTamaño("Grande")
                .construir();
    }

    @Test(expected = IllegalStateException.class)
    public void sinAcompanamientoNoSeConstruye() {
        new ComboBuilder("Combo roto")
                .conPlato(plato)
                .conBebida(bebida)
                .conTamaño("Grande")
                .construir();
    }

    @Test(expected = IllegalStateException.class)
    public void sinBebidaNoSeConstruye() {
        new ComboBuilder("Combo roto")
                .conPlato(plato)
                .conAcompanamiento(acompanamiento)
                .conTamaño("Grande")
                .construir();
    }

    @Test(expected = IllegalStateException.class)
    public void sinTamanoNoSeConstruye() {
        new ComboBuilder("Combo roto")
                .conPlato(plato)
                .conAcompanamiento(acompanamiento)
                .conBebida(bebida)
                .construir();
    }

    @Test
    public void originalArmaElComboClasico() {
        Combo combo = new ComboOriginal().armarComboClasico("Grande");

        assertEquals(23500.0, combo.getPrecio(), DELTA);
        assertEquals("Combo clásico Grande (Hamburguesa, Papas fritas, Gaseosa)",
                combo.getDescripcion());
    }

@Test
public void originalArmaElComboVegetariano() {
    Combo combo = new ComboOriginal().armarComboVegetariano("Mediano");

    assertEquals(18000.0, combo.getPrecio(), DELTA);
    assertEquals(3, combo.getItems().size());
    assertEquals("Combo vegetariano Mediano (Ensalada, Pan de ajo, Jugo natural)",
            combo.getDescripcion());
}

    @Test
    public void unComboPuedeLlevarExtras() {
        Combo combo = new ComboOriginal().armarComboClasico("Grande");
        Producto conQueso = new ExtraQueso(combo);

        assertTrue(conQueso.getPrecio() > combo.getPrecio());
        assertTrue(conQueso.getDescripcion().endsWith("+ queso"));
    }
}
