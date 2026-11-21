package patrones.builder;

import modelo.Combo;
import modelo.Producto;

public class ComboBuilder {

    private String nombre;
    private Producto plato;
    private Producto acompanamiento;
    private Producto bebida;
    private String tamaño;

    public ComboBuilder(String nombre) {
        this.nombre = nombre;
    }

    public ComboBuilder conPlato(Producto plato) {
        this.plato = plato;
        return this;
    }

    public ComboBuilder conAcompanamiento(Producto acompanamiento) {
        this.acompanamiento = acompanamiento;
        return this;
    }

    public ComboBuilder conBebida(Producto bebida) {
        this.bebida = bebida;
        return this;
    }

    public ComboBuilder conTamaño(String tamaño) {
        this.tamaño = tamaño;
        return this;
    }

    public Combo construir() {
        if (plato == null) {
            throw new IllegalStateException("Falta elegir el plato");
        }
        if (acompanamiento == null) {
            throw new IllegalStateException("Falta elegir el acompañamiento");
        }
        if (bebida == null) {
            throw new IllegalStateException("Falta elegir la bebida");
        }
        if (tamaño == null) {
            throw new IllegalStateException("Falta elegir el tamaño");
        }

        Combo combo = new Combo(nombre + " " + tamaño);
        combo.agregarItem(plato);
        combo.agregarItem(acompanamiento);
        combo.agregarItem(bebida);
        return combo;
    }
}
