package modelo;

import java.util.ArrayList;
import java.util.List;

public class Combo implements Producto {
    private String nombre;
    private List<Producto> items = new ArrayList<>();
    public Combo(String nombre) {
        this.nombre = nombre;
    }

    public void agregarItem(Producto item) {
        items.add(item);
    }

    public List<Producto> getItems() { return items; }

    @Override
    public String getDescripcion() {
        String texto = nombre + " (";
        for (int i = 0; i < items.size(); i++) {
            texto += items.get(i).getDescripcion();
            if (i < items.size() - 1) {
                texto += ", ";
            }
        }
        return texto + ")";
    }

    @Override
    public double getPrecio() {
        double total = 0;
        for (Producto p : items) {
            total += p.getPrecio();
        }
        return total;
    }
}