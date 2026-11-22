package servicios;

import modelo.Venta;
import patrones.observer.Caja;

public class CajaServicio {

    private Caja caja;

    public CajaServicio(Caja caja) {
        this.caja = caja;
    }

    public int getCantidadVentas() {
        return caja.getVentas().size();
    }

    public double getTotalDia() {
        double total = 0;
        for (Venta v : caja.getVentas()) {
            total += v.getTotal();
        }
        return total;
    }

    public String cerrarCaja() {
        return "Cierre de caja: " + getCantidadVentas() + " ventas, total $" + getTotalDia();
    }
}
