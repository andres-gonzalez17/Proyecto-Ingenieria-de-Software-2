package consola;

import java.util.List;
import java.util.Scanner;
import modelo.Combo;
import modelo.EstadoPedido;
import modelo.Menu;
import modelo.Pedido;
import modelo.Producto;
import modelo.ProductoBase;
import patrones.builder.ComboBuilder;
import servicios.CajaServicio;
import servicios.MenuServicio;
import servicios.PedidoServicio;
import servicios.ReporteServicio;

public class MenuConsola {

    private Scanner scanner = new Scanner(System.in);
    private PedidoServicio pedidoServicio = new PedidoServicio();
    private CajaServicio cajaServicio = new CajaServicio(pedidoServicio.getCaja());
    private ReporteServicio reporteServicio = new ReporteServicio(pedidoServicio.getCaja());
    private Menu menu = new MenuServicio().cargarMenuPorDefecto();
    private Pedido pedidoActual = null;

    public void iniciar() {
        int opcion = -1;
        while (opcion != 0) {
            mostrarOpciones();
            opcion = leerEntero("Elija una opcion: ");

            switch (opcion) {
                case 1: crearPedido(); break;
                case 2: agregarProducto(); break;
                case 3: agregarComboPredefinido(); break;
                case 4: armarCombo(); break;
                case 5: agregarExtrasAProducto(); break;
                case 6: confirmarPedido(); break;
                case 7: avanzarEstado(); break;
                case 8: prepararAutomaticamente(); break;
                case 9: verAvisos(); break;
                case 10: System.out.println(reporteServicio.generarReporte()); break;
                case 11: System.out.println(cajaServicio.cerrarCaja()); break;
                case 0: System.out.println("Hasta luego"); break;
                default: System.out.println("Opcion no válida");
            }
        }
    }

    private void mostrarOpciones() {
        System.out.println("\n===== SISTEMA DE PEDIDOS =====");
        if (pedidoActual != null) {
            String estado = "sin confirmar";
            if (pedidoActual.isConfirmado()) {
                estado = pedidoActual.getEstado().toString();
            }
            System.out.println("Pedido actual: #" + pedidoActual.getId() + " | " + estado + " | Total: $" + pedidoActual.getTotal());
        }
        System.out.println("1. Crear pedido nuevo");
        System.out.println("2. Agregar productos");
        System.out.println("3. Agregar combo preestablecido");
        System.out.println("4. Armar combo paso a paso");
        System.out.println("5. Agregar extras a un producto del pedido");
        System.out.println("6. Confirmar pedido");
        System.out.println("7. Avanzar al siguiente estado");
        System.out.println("8. Proceso automatico");
        System.out.println("9. Ver avisos de cocina, mesero y caja");
        System.out.println("10. Reporte de ventas");
        System.out.println("11. Cierre de caja");
        System.out.println("0. Salir");
    }

    //  Opciones del menú

    private void crearPedido() {
        pedidoActual = pedidoServicio.crearPedido();
        System.out.println("Pedido #" + pedidoActual.getId() + " creado");
    }

    private void agregarProducto() {
        if (!pedidoSinConfirmar()) {
            return;
        }
        List<ProductoBase> productos = menu.getProductos();
        int numero = -1;

        while (numero != 0) {
            mostrarProductos();
            numero = leerEntero("Número de producto (0 para terminar): ");

            if (numero >= 1 && numero <= productos.size()) {
                ProductoBase producto = productos.get(numero - 1);
                int cantidad = leerEntero("Cantidad: ");
                if (cantidad < 1) {
                    System.out.println("Cantidad no válida");
                } else {
                    for (int i = 0; i < cantidad; i++) {
                        pedidoServicio.agregarProducto(pedidoActual, producto);
                    }
                    System.out.println("Agregado: " + cantidad + " x " + producto.getNombre());
                }
            } else if (numero != 0) {
                System.out.println("Producto no válido");
            }
        }
    }

    private void agregarComboPredefinido() {
        if (!pedidoSinConfirmar()) {
            return;
        }
        System.out.println("Combos disponibles:");
        System.out.println("1. Combo clásico (Hamburguesa, Papas fritas, Gaseosa)");
        System.out.println("2. Combo ligero (Ensalada, Pan de ajo, Jugo natural)");
        int opcion = leerEntero("Combo: ");
        String tamaño = elegirTamaño();

        try {
            Combo combo = pedidoServicio.armarComboPredefinido(opcion, tamaño);
            Producto producto = preguntarExtras(combo);
            pedidoServicio.agregarProducto(pedidoActual, producto);
            System.out.println("Agregado: " + producto.getDescripcion() + " - $" + producto.getPrecio());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("No se pudo agregar el combo: " + e.getMessage());
        }
    }

    private void armarCombo() {
        if (!pedidoSinConfirmar()) {
            return;
        }
        System.out.print("Nombre del combo: ");
        String nombre = scanner.nextLine();

        ComboBuilder builder = pedidoServicio.nuevoComboBuilder(nombre);

        System.out.println("Paso 1: elija el plato");
        builder.conPlato(elegirProducto());
        System.out.println("Paso 2: elija el acompañamiento");
        builder.conAcompanamiento(elegirProducto());
        System.out.println("Paso 3: elija la bebida");
        builder.conBebida(elegirProducto());

        System.out.println("Paso 4: elija el tamaño");
        String tamaño = elegirTamaño();
        if (tamaño != null) {
            builder.conTamaño(tamaño);
        }

        try {
            Combo combo = builder.construir();
            Producto producto = preguntarExtras(combo);
            pedidoServicio.agregarProducto(pedidoActual, producto);
            System.out.println("Combo agregado: " + producto.getDescripcion() + " - $" + producto.getPrecio());
        } catch (IllegalStateException e) {
            System.out.println("No se pudo armar el combo: " + e.getMessage());
        }
    }

    private void agregarExtrasAProducto() {
        if (!pedidoSinConfirmar()) {
            return;
        }
        List<Producto> productos = pedidoActual.getProductos();
        if (productos.isEmpty()) {
            System.out.println("El pedido no tiene productos");
            return;
        }

        for (int i = 0; i < productos.size(); i++) {
            System.out.println((i + 1) + ". " + productos.get(i).getDescripcion() + " - $" + productos.get(i).getPrecio());
        }
        int numero = leerEntero("Producto al que desea agregar extras: ");
        if (numero < 1 || numero > productos.size()) {
            System.out.println("Producto no válido");
            return;
        }

        Producto actual = productos.get(numero - 1);
        Producto conExtras = elegirExtras(actual);
        pedidoServicio.reemplazarProducto(pedidoActual, numero - 1, conExtras);
        System.out.println("Ahora: " + conExtras.getDescripcion() + " - $" + conExtras.getPrecio());
    }

    private void confirmarPedido() {
        if (!pedidoSinConfirmar()) {
            return;
        }
        try {
            pedidoServicio.confirmarPedido(pedidoActual);
            System.out.println("Pedido confirmado. Total: $" + pedidoActual.getTotal());
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void avanzarEstado() {
        if (!hayPedidoAbierto()) {
            return;
        }
        try {
            pedidoServicio.avanzarEstado(pedidoActual);
            System.out.println("Estado: " + pedidoActual.getEstado());
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void prepararAutomaticamente() {
    if (!hayPedidoAbierto()) {
        return;
    }
    if (!pedidoActual.isConfirmado()) {
        System.out.println("Primero debe confirmar el pedido");
        return;
    }

    try {
        while (pedidoActual.getEstado() != EstadoPedido.PAGADO) {
            Thread.sleep(2000); 
            pedidoServicio.avanzarEstado(pedidoActual);
            System.out.println("Estado: " + pedidoActual.getEstado());
        }
    } catch (InterruptedException e) {
        System.out.println("El proceso fue interrumpido");
    }
}
    private void verAvisos() {
        imprimirLista("Cocina: ", pedidoServicio.getAvisosCocina());
        imprimirLista("Mesero: ", pedidoServicio.getAvisosMesero());
        imprimirLista("Caja: ", pedidoServicio.getAvisosCaja());
    }

    //  Métodos de ayuda

    private void mostrarProductos() {
        List<ProductoBase> productos = menu.getProductos();
        for (int i = 0; i < productos.size(); i++) {
            ProductoBase p = productos.get(i);
            System.out.println((i + 1) + ". " + p.getNombre() + " ($" + p.getPrecio() + ")");
        }
    }

    private ProductoBase elegirProducto() {
        mostrarProductos();
        int numero = leerEntero("Número de producto: ");
        List<ProductoBase> productos = menu.getProductos();
        if (numero < 1 || numero > productos.size()) {
            System.out.println("Producto no válido");
            return null;
        }
        return productos.get(numero - 1);
    }

    private String elegirTamaño() {
        String[] tamaños = {"Pequeño", "Mediano", "Grande"};
        for (int i = 0; i < tamaños.length; i++) {
            System.out.println((i + 1) + ". " + tamaños[i]);
        }
        int opcion = leerEntero("Tamaño: ");
        if (opcion < 1 || opcion > tamaños.length) {
            System.out.println("Tamaño no válido");
            return null;
        }
        return tamaños[opcion - 1];
    }

    private Producto preguntarExtras(Producto producto) {
        System.out.println("¿Desea agregar extras?");
        System.out.println("1. Sí");
        System.out.println("2. No");
        int opcion = leerEntero("Opción: ");
        if (opcion == 1) {
            return elegirExtras(producto);
        }
        return producto;
    }

    private Producto elegirExtras(Producto producto) {
        List<String> extras = menu.getExtras();
        boolean seguir = true;

        while (seguir) {
            System.out.println("Extras disponibles:");
            for (int i = 0; i < extras.size(); i++) {
                System.out.println((i + 1) + ". " + extras.get(i));
            }
            System.out.println("0. Terminar");

            int opcion = leerEntero("Extra: ");
            if (opcion == 0) {
                seguir = false;
            } else if (opcion >= 1 && opcion <= extras.size()) {
                producto = pedidoServicio.agregarExtra(producto, extras.get(opcion - 1));
                System.out.println("Ahora: " + producto.getDescripcion() + " - $" + producto.getPrecio());
            } else {
                System.out.println("Opción no válida");
            }
        }
        return producto;
    }

    private boolean hayPedidoAbierto() {
        if (pedidoActual == null) {
            System.out.println("Primero cree un pedido (opción 1)");
            return false;
        }
        if (pedidoActual.getEstado() == EstadoPedido.PAGADO) {
            System.out.println("El pedido ya está pagado. Cree uno nuevo");
            return false;
        }
        return true;
    }

    private boolean pedidoSinConfirmar() {
        if (!hayPedidoAbierto()) {
            return false;
        }
        if (pedidoActual.isConfirmado()) {
            System.out.println("El pedido ya fue confirmado y no se puede modificar");
            return false;
        }
        return true;
    }

    private void imprimirLista(String titulo, List<String> lista) {
        System.out.println(titulo);
        for (String texto : lista) {
            System.out.println(texto);
        }
    }

    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
