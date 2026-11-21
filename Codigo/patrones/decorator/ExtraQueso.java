package patrones.decorator;

import modelo.Producto;

public class ExtraQueso extends ExtraDecorator {

    public ExtraQueso(Producto producto) {
        super(producto); 
    }

    @Override
    public String getDescripcion() {
        return producto.getDescripcion() + " + queso";
    }
    @Override
    public double getPrecio() {
        return producto.getPrecio() + 1000;
    }
}
