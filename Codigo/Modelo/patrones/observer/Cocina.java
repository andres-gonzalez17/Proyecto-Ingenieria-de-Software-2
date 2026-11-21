package patrones.observer;

import java.util.ArrayList;
import java.util.List;
import modelo.EstadoPedido;
import modelo.ObservadorPedido;
import modelo.Pedido;

public class Cocina implements ObservadorPedido {

    private List<String> avisos = new ArrayList<>();

    @Override
    public void actualizar(Pedido pedido) {

        if (pedido.getEstado() == EstadoPedido.RECIBIDO) {
            avisos.add("Cocina: nuevo pedido #" + pedido.getId() + " -> " + pedido.getDescripcion());
        }
        if (pedido.getEstado() == EstadoPedido.EN_PREPARACION) {
            avisos.add("Cocina: pedido #" + pedido.getId() + " en preparación");
        }
    }

    public List<String> getAvisos() { return avisos; }
}