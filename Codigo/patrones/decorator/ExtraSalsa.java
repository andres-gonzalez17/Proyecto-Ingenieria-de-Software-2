package patrones.decorator;

import modelo.Producto;

public class ExtraSalsa extends ExtraDecorator {

    public ExtraSalsa(Producto producto) {
        super(producto);
    }

    @Override
    public String getDescripcion() {
        return producto.getDescripcion() + " + salsa";
    }

    @Override
    public double getPrecio() {
        return producto.getPrecio() + 500;
    }
}