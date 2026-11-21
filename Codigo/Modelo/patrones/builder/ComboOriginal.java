package patrones.builder;

import modelo.Combo;
import modelo.ProductoBase;

public class ComboOriginal {

    public Combo armarComboClasico(String tamaño) {
        ComboBuilder builder = new ComboBuilder("Combo clásico");
        return builder
                .conPlato(new ProductoBase("Hamburguesa", "Plato", 15000))
                .conAcompanamiento(new ProductoBase("Papas fritas", "Plato", 5000))
                .conBebida(new ProductoBase("Gaseosa", "Bebida", 3500))
                .conTamaño(tamaño)
                .construir();
    }

    public Combo armarComboVegetariano(String tamaño) {
        ComboBuilder builder = new ComboBuilder("Combo vegetariano");
        return builder
                .conPlato(new ProductoBase("Ensalada", "Plato", 12000))
                .conAcompanamiento(new ProductoBase("Pan de ajo", "Plato", 2000))
                .conBebida(new ProductoBase("Jugo natural", "Bebida", 4000))
                .conTamaño(tamaño)
                .construir();
    }
}
