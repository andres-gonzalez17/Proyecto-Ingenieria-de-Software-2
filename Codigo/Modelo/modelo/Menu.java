package modelo;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<ProductoBase> productos = new ArrayList<>();
    private List<String> extras = new ArrayList<>(); 

    public void agregarProducto(ProductoBase producto) {
        productos.add(producto);
    }

    public void agregarExtra(String nombreExtra) {
        extras.add(nombreExtra);
    }

    public List<ProductoBase> getProductos() { return productos; }
    public List<String> getExtras() { return extras; }
}
