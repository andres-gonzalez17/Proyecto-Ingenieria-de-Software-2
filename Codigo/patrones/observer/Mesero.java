package patrones.observer;

import java.util.ArrayList;
import java.util.List;
import modelo.EstadoPedido;
import modelo.ObservadorPedido;
import modelo.Pedido;

public class Mesero implements ObservadorPedido {

    private List<String> avisos = new ArrayList<>();

    @Override
    public void actualizar(Pedido pedido) {
        if (pedido.getEstado() == EstadoPedido.LISTO) {
            avisos.add("Mesero: pedido #" + pedido.getId() + " listo para recoger");
        }
        if (pedido.getEstado() == EstadoPedido.ENTREGADO) {
            avisos.add("Mesero: pedido #" + pedido.getId() + " entregado al cliente");
        }
    }

    public List<String> getAvisos() { return avisos; }
}