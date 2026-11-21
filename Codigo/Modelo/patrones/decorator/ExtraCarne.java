package patrones.decorator;

import modelo.Producto;

public class ExtraCarne extends ExtraDecorator {

    public ExtraCarne(Producto producto) {
        super(producto);
    }

    @Override
    public String getDescripcion() {
        return producto.getDescripcion() + " + carne ";
    }

    @Override
    public double getPrecio() {
        return producto.getPrecio() + 3000;
    }
}