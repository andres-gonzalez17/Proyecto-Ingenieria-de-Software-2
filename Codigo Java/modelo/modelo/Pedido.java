package modelo;

import java.util.ArrayList;
import java.util.List;


public class Pedido {
    private int id;
    private List<Producto> productos = new ArrayList<>();
    private EstadoPedido estado;
    private List<ObservadorPedido> observadores = new ArrayList<>();
    private boolean confirmado = false;
    
    public Pedido(int id) {
        this.id = id;
        this.estado = EstadoPedido.RECIBIDO;
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void reemplazarProducto(int posicion, Producto nuevo) {
    productos.set(posicion, nuevo);
    }


    public void confirmar() {
    confirmado = true;
    cambiarEstado(EstadoPedido.RECIBIDO);
    }

    public boolean isConfirmado() { return confirmado; }

    public double getTotal() {
        double total = 0;
        for (Producto p : productos) {
            total += p.getPrecio();
        }
        return total;
    }

    public String getDescripcion() {
    String texto = "";
    for (int i = 0; i < productos.size(); i++) {
        texto += productos.get(i).getDescripcion();
        if (i < productos.size() - 1) {
            texto += ", ";
        }
    }
    return texto;
    }

    //Parte del Observer

    public void agregarObservador(ObservadorPedido observador) {
        observadores.add(observador);
    }

    public void quitarObservador(ObservadorPedido observador) {
        observadores.remove(observador);
    }

    public void cambiarEstado(EstadoPedido nuevoEstado) {
        this.estado = nuevoEstado;
        notificarObservadores();
    }

    private void notificarObservadores() {
        for (ObservadorPedido o : observadores) {
            o.actualizar(this);
        }
    }

    public int getId() { return id; }
    public EstadoPedido getEstado() { return estado; }
    public List<Producto> getProductos() { return productos; }
}