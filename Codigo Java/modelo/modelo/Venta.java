package modelo;

import java.time.LocalDateTime;

public class Venta {
    private int idPedido;
    private String descripcion; 
    private double total;
    private LocalDateTime fechaHora;

    public Venta(int idPedido, String descripcion, double total) {
        this.idPedido = idPedido;
        this.descripcion = descripcion;
        this.total = total;
        this.fechaHora = LocalDateTime.now();
    }

    public int getIdPedido() { return idPedido; }
    public String getDescripcion() { return descripcion; }
    public double getTotal() { return total; }
    public LocalDateTime getFechaHora() { return fechaHora; }
}