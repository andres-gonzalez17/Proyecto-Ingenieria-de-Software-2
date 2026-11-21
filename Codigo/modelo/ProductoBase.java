package modelo;

public class ProductoBase implements Producto {
    private String nombre;
    private String tipo; 
    private double precio;

    public ProductoBase(String nombre, String tipo, double precio) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
    }
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    
    @Override
    public String getDescripcion() { return nombre; }

    @Override
    public double getPrecio() { return precio; }
}