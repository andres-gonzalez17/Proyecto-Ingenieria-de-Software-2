package patrones.observer;

import java.util.ArrayList;
import java.util.List;
import modelo.EstadoPedido;
import modelo.ObservadorPedido;
import modelo.Pedido;
import modelo.Venta;

public class Caja implements ObservadorPedido {

    private List<String> avisos = new ArrayList<>();
    private List<Venta> ventas = new ArrayList<>(); 
    @Override
    public void actualizar(Pedido pedido) {
        if (pedido.getEstado() == EstadoPedido.PAGADO) {
            Venta venta = new Venta(pedido.getId(), pedido.getDescripcion(), pedido.getTotal());
            ventas.add(venta);
            avisos.add("Caja: pedido #" + pedido.getId() + " pagado por $" + pedido.getTotal());
        }
    }

    public List<String> getAvisos() { return avisos; }
    public List<Venta> getVentas() { return ventas; }
}