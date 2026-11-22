package servicios;

import java.util.ArrayList;
import java.util.List;
import modelo.EstadoPedido;
import modelo.Pedido;
import modelo.Producto;
import patrones.builder.ComboBuilder;
import patrones.decorator.ExtraCarne;
import patrones.decorator.ExtraQueso;
import patrones.decorator.ExtraSalsa;
import patrones.observer.Caja;
import patrones.observer.Cocina;
import patrones.observer.Mesero;
import modelo.Combo;
import patrones.builder.ComboOriginal;
public class PedidoServicio {

    private int contadorPedidos = 0;
    private List<Pedido> pedidos = new ArrayList<>();

    private Cocina cocina = new Cocina();
    private Mesero mesero = new Mesero();
    private Caja caja = new Caja();

    public Pedido crearPedido() {
        contadorPedidos++;
        Pedido pedido = new Pedido(contadorPedidos);
        pedido.agregarObservador(cocina);
        pedido.agregarObservador(mesero);
        pedido.agregarObservador(caja);
        pedidos.add(pedido);
        return pedido;
    }

    public void agregarProducto(Pedido pedido, Producto producto) {
    revisarNoConfirmado(pedido);
    pedido.agregarProducto(producto);
    }

    public Producto agregarExtra(Producto producto, String nombreExtra) {
        switch (nombreExtra.toLowerCase()) {
            case "queso":
                return new ExtraQueso(producto);
            case "carne":
                return new ExtraCarne(producto);
            case "salsa":
                return new ExtraSalsa(producto);
            default:
                throw new IllegalArgumentException("Extra no existe: " + nombreExtra);
        }
    }

    public ComboBuilder nuevoComboBuilder(String nombre) {
        return new ComboBuilder(nombre);
    }

    public void confirmarPedido(Pedido pedido) {
    if (pedido.getProductos().isEmpty()) {
        throw new IllegalStateException("El pedido no tiene productos");
    }
    pedido.confirmar();
    }

    public void cambiarEstado(Pedido pedido, EstadoPedido nuevoEstado) {
        pedido.cambiarEstado(nuevoEstado);
    }

    public Pedido buscarPedido(int id) {
        for (Pedido p : pedidos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
public void reemplazarProducto(Pedido pedido, int posicion, Producto nuevo) {
    revisarNoConfirmado(pedido);
    pedido.reemplazarProducto(posicion, nuevo);
}

public Combo armarComboPredefinido(int opcion, String tamaño) {
    ComboOriginal original = new ComboOriginal();
    switch (opcion) {
        case 1:
            return original.armarComboClasico(tamaño);
        case 2:
            return original.armarComboVegetariano(tamaño);
        default:
            throw new IllegalArgumentException("Ese combo no existe");
    }
}

    public void avanzarEstado(Pedido pedido) {
       if (!pedido.isConfirmado()) {
        throw new IllegalStateException("Primero debe confirmar el pedido");
    }
    EstadoPedido[] estados = EstadoPedido.values();
    int posicionActual = pedido.getEstado().ordinal(); // posición del estado actual
    if (posicionActual == estados.length - 1) {
        throw new IllegalStateException("El pedido ya está en el último estado");
    }
    pedido.cambiarEstado(estados[posicionActual + 1]);
}

    private void revisarNoConfirmado(Pedido pedido) {
    if (pedido.isConfirmado()) {
        throw new IllegalStateException("El pedido ya fue confirmado y no se puede modificar");
    }
   }

    public List<Pedido> getPedidos() { return pedidos; }

    public List<String> getAvisosCocina() { return cocina.getAvisos(); }
    public List<String> getAvisosMesero() { return mesero.getAvisos(); }
    public List<String> getAvisosCaja() { return caja.getAvisos(); }

    public Caja getCaja() { return caja; }
}
