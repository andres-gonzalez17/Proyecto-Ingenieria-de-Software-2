package patrones.observer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import modelo.EstadoPedido;
import modelo.ObservadorPedido;
import modelo.Pedido;
import modelo.ProductoBase;
import modelo.Venta;
import org.junit.Before;
import org.junit.Test;
import patrones.decorator.ExtraQueso;

public class PedidoObserverTest {

    private static final double DELTA = 0.001;

    private Pedido pedido;
    private Cocina cocina;
    private Mesero mesero;
    private Caja caja;

    @Before
    public void preparar() {
        pedido = new Pedido(1);
        pedido.agregarProducto(new ExtraQueso(new ProductoBase("Hamburguesa", "Plato", 15000)));

        cocina = new Cocina();
        mesero = new Mesero();
        caja = new Caja();
        pedido.agregarObservador(cocina);
        pedido.agregarObservador(mesero);
        pedido.agregarObservador(caja);
    }

    @Test
    public void confirmarAvisaALaCocina() {
        pedido.confirmar();

        assertTrue(pedido.isConfirmado());
        assertEquals(1, cocina.getAvisos().size());
        assertEquals(0, mesero.getAvisos().size());
        assertEquals(0, caja.getAvisos().size());
    }

    @Test
    public void listoAvisaSoloAlMesero() {
        pedido.cambiarEstado(EstadoPedido.LISTO);

        assertEquals(0, cocina.getAvisos().size());
        assertEquals(1, mesero.getAvisos().size());
        assertEquals(0, caja.getAvisos().size());
    }

    @Test
    public void pagadoAvisaALaCajaYRegistraLaVenta() {
        pedido.cambiarEstado(EstadoPedido.PAGADO);

        assertEquals(1, caja.getAvisos().size());
        assertEquals(1, caja.getVentas().size());

        Venta venta = caja.getVentas().get(0);
        assertEquals(1, venta.getIdPedido());
        assertEquals(pedido.getTotal(), venta.getTotal(), DELTA);
        assertTrue(venta.getDescripcion().contains("queso"));
    }

    @Test
    public void flujoCompletoAvisaACadaAreaSegunSuEstado() {
        pedido.cambiarEstado(EstadoPedido.RECIBIDO);
        pedido.cambiarEstado(EstadoPedido.EN_PREPARACION);
        pedido.cambiarEstado(EstadoPedido.LISTO);
        pedido.cambiarEstado(EstadoPedido.ENTREGADO);
        pedido.cambiarEstado(EstadoPedido.PAGADO);

        assertEquals(2, cocina.getAvisos().size());
        assertEquals(2, mesero.getAvisos().size());
        assertEquals(1, caja.getAvisos().size());   
    }

    @Test
    public void unObservadorQuitadoNoRecibeAvisos() {
        pedido.quitarObservador(cocina);
        pedido.cambiarEstado(EstadoPedido.RECIBIDO);

        assertEquals(0, cocina.getAvisos().size());
    }

    @Test
    public void elPedidoAvisaACualquierObservador() {
       
        ObservadorFalso falso = new ObservadorFalso();
        pedido.agregarObservador(falso);

        pedido.cambiarEstado(EstadoPedido.RECIBIDO);
        pedido.cambiarEstado(EstadoPedido.EN_PREPARACION);

        assertEquals(2, falso.llamadas);
    }

    @Test
    public void laCajaAcumulaLasVentasDeVariosPedidos() {
        Pedido segundo = new Pedido(2);
        segundo.agregarProducto(new ProductoBase("Gaseosa", "Bebida", 3500));
        segundo.agregarObservador(caja);

        pedido.cambiarEstado(EstadoPedido.PAGADO);
        segundo.cambiarEstado(EstadoPedido.PAGADO);

        assertEquals(2, caja.getVentas().size());
    }

    private static class ObservadorFalso implements ObservadorPedido {
        int llamadas = 0;

        @Override
        public void actualizar(Pedido pedido) {
            llamadas++;
        }
    }
}