package patrones.decorator;

import modelo.Producto;

public abstract class ExtraDecorator implements Producto {
    protected Producto producto;
    
    public ExtraDecorator(Producto producto) {
        this.producto = producto;
    }
}