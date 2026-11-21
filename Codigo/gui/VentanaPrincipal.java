package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

public class VentanaPrincipal extends JFrame {

    private static final Color COLOR_PRINCIPAL = new Color(93, 64, 55);  // café
    private static final Color COLOR_FONDO = new Color(250, 247, 242);   // crema

    // Servicios: el mismo núcleo que usa la consola
    private PedidoServicio pedidoServicio = new PedidoServicio();
    private CajaServicio cajaServicio = new CajaServicio(pedidoServicio.getCaja());
    private ReporteServicio reporteServicio = new ReporteServicio(pedidoServicio.getCaja());
    private Menu menu = new MenuServicio().cargarMenuPorDefecto();

    private Pedido pedidoActual = null;
    private Timer timerAutomatico; // reloj para el proceso automático
    private String[] tamaños = {"Pequeño", "Mediano", "Grande"};

    private JLabel lblPedido = new JLabel();           // muestra el pedido actual
    private JTextArea areaSalida = new JTextArea();    // hace de "pantalla de consola"

    public VentanaPrincipal() {
        setTitle("Sistema de Pedidos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 620);
        setLocationRelativeTo(null); // centra la ventana
        getContentPane().setBackground(COLOR_FONDO);
        setLayout(new BorderLayout());

        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearPanelBotones(), BorderLayout.WEST);
        add(crearPanelSalida(), BorderLayout.CENTER);

        actualizarPedido();
        escribir("Bienvenido. Pulse 'Crear pedido nuevo' para empezar.");
    }

    // ---------- Construcción de la pantalla ----------

    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_PRINCIPAL);
        panel.setBorder(new EmptyBorder(14, 20, 14, 20));

        JLabel titulo = new JLabel("Sistema de Pedidos");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        panel.add(titulo, BorderLayout.WEST);
        return panel;
    }

    // Columna de botones: una por cada opción del menú de la consola
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 0, 8));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(new EmptyBorder(14, 14, 14, 7));
        panel.setPreferredSize(new Dimension(300, 0));

        panel.add(crearBoton("1. Crear pedido nuevo", e -> crearPedido()));
        panel.add(crearBoton("2. Agregar productos", e -> agregarProducto()));
        panel.add(crearBoton("3. Agregar combo preestablecido", e -> agregarComboPredefinido()));
        panel.add(crearBoton("4. Armar combo paso a paso", e -> armarCombo()));
        panel.add(crearBoton("5. Agregar extras a un producto", e -> agregarExtrasAProducto()));
        panel.add(crearBoton("6. Confirmar pedido", e -> confirmarPedido()));
        panel.add(crearBoton("7. Avanzar al siguiente estado", e -> avanzarEstado()));
        panel.add(crearBoton("8. Proceso automático", e -> prepararAutomaticamente()));
        panel.add(crearBoton("9. Ver avisos", e -> verAvisos()));
        panel.add(crearBoton("10. Reporte de ventas", e -> escribir(reporteServicio.generarReporte())));
        panel.add(crearBoton("11. Cierre de caja", e -> escribir(cajaServicio.cerrarCaja())));
        panel.add(crearBoton("0. Salir", e -> System.exit(0)));
        return panel;
    }

    // Crea un botón con su texto y la acción que ejecuta al presionarlo
    private JButton crearBoton(String texto, ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        boton.addActionListener(accion);
        return boton;
    }

    // Zona derecha: pedido actual arriba y la "pantalla" de mensajes
    private JPanel crearPanelSalida() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(new EmptyBorder(14, 7, 14, 14));

        JPanel franja = new JPanel(new BorderLayout());
        franja.setBackground(Color.WHITE);
        franja.setBorder(new EmptyBorder(12, 14, 12, 14));
        lblPedido.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblPedido.setForeground(COLOR_PRINCIPAL);
        franja.add(lblPedido, BorderLayout.CENTER);

        areaSalida.setEditable(false);
        areaSalida.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaSalida.setMargin(new java.awt.Insets(8, 10, 8, 10));
        JScrollPane scroll = new JScrollPane(areaSalida);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultados"));

        panel.add(franja, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    // ---------- Opciones del menú ----------

    private void crearPedido() {
        detenerAutomatico();
        pedidoActual = pedidoServicio.crearPedido();
        escribir("Pedido #" + pedidoActual.getId() + " creado");
        actualizarPedido();
    }

    // Opción 2: productos sin extras, se puede pedir más de uno
    private void agregarProducto() {
        if (!pedidoSinConfirmar()) {
            return;
        }
        List<ProductoBase> productos = menu.getProductos();

        // Lista de opciones: cada producto y al final "Terminar"
        String[] opciones = new String[productos.size() + 1];
        for (int i = 0; i < productos.size(); i++) {
            opciones[i] = productos.get(i).getNombre() + " ($" + productos.get(i).getPrecio() + ")";
        }
        opciones[productos.size()] = "Terminar";

        boolean seguir = true;
        while (seguir) {
            int posicion = elegirDeLista("Elija un producto", opciones);
            if (posicion == -1 || posicion == productos.size()) {
                seguir = false; // canceló o eligió "Terminar"
            } else {
                int cantidad = pedirEntero("Cantidad:");
                if (cantidad < 1) {
                    escribir("Cantidad no válida");
                } else {
                    ProductoBase producto = productos.get(posicion);
                    // Se agrega el producto tantas veces como la cantidad pedida
                    for (int i = 0; i < cantidad; i++) {
                        pedidoServicio.agregarProducto(pedidoActual, producto);
                    }
                    escribir("Agregado: " + cantidad + " x " + producto.getNombre());
                    actualizarPedido();
                }
            }
        }
    }

    // Opción 3: combo ya armado (lo crea el Director)
    private void agregarComboPredefinido() {
        if (!pedidoSinConfirmar()) {
            return;
        }
        String[] combos = {
            "Combo clásico (Hamburguesa, Papas fritas, Gaseosa)",
            "Combo ligero (Ensalada, Pan de ajo, Jugo natural)"
        };
        int combo = elegirDeLista("Elija el combo", combos);
        if (combo == -1) return;
        int tamaño = elegirDeLista("Elija el tamaño", tamaños);
        if (tamaño == -1) return;

        try {
            Combo nuevo = pedidoServicio.armarComboPredefinido(combo + 1, tamaños[tamaño]);
            // El combo se trata como cualquier producto: puede llevar extras
            Producto producto = preguntarExtras(nuevo);
            pedidoServicio.agregarProducto(pedidoActual, producto);
            escribir("Agregado: " + producto.getDescripcion() + " - $" + producto.getPrecio());
            actualizarPedido();
        } catch (IllegalArgumentException | IllegalStateException e) {
            escribir("No se pudo agregar el combo: " + e.getMessage());
        }
    }

    // Opción 4: combo armado paso a paso (Builder), una pregunta por paso
    private void armarCombo() {
        if (!pedidoSinConfirmar()) {
            return;
        }
        List<ProductoBase> productos = menu.getProductos();

        // Nombres de los productos para mostrar en las preguntas
        String[] nombres = new String[productos.size()];
        for (int i = 0; i < productos.size(); i++) {
            nombres[i] = productos.get(i).getNombre();
        }

        String nombreCombo = (String) JOptionPane.showInputDialog(this, "Nombre del combo:",
                "Sistema de pedidos", JOptionPane.PLAIN_MESSAGE, null, null, "Combo especial");
        if (nombreCombo == null) return; // canceló

        int plato = elegirDeLista("Paso 1: elija el plato", nombres);
        if (plato == -1) return;
        int acompanamiento = elegirDeLista("Paso 2: elija el acompañamiento", nombres);
        if (acompanamiento == -1) return;
        int bebida = elegirDeLista("Paso 3: elija la bebida", nombres);
        if (bebida == -1) return;
        int tamaño = elegirDeLista("Paso 4: elija el tamaño", tamaños);
        if (tamaño == -1) return;

        ComboBuilder builder = pedidoServicio.nuevoComboBuilder(nombreCombo);
        builder.conPlato(productos.get(plato));
        builder.conAcompanamiento(productos.get(acompanamiento));
        builder.conBebida(productos.get(bebida));
        builder.conTamaño(tamaños[tamaño]);

        try {
            Combo combo = builder.construir();
            Producto producto = preguntarExtras(combo);
            pedidoServicio.agregarProducto(pedidoActual, producto);
            escribir("Combo agregado: " + producto.getDescripcion() + " - $" + producto.getPrecio());
            actualizarPedido();
        } catch (IllegalStateException e) {
            escribir("No se pudo armar el combo: " + e.getMessage());
        }
    }

    // Opción 5: extras para un producto que ya está en el pedido
    private void agregarExtrasAProducto() {
        if (!pedidoSinConfirmar()) {
            return;
        }
        List<Producto> productos = pedidoActual.getProductos();
        if (productos.isEmpty()) {
            escribir("El pedido no tiene productos");
            return;
        }

        String[] opciones = new String[productos.size()];
        for (int i = 0; i < productos.size(); i++) {
            opciones[i] = productos.get(i).getDescripcion() + " - $" + productos.get(i).getPrecio();
        }
        int posicion = elegirDeLista("Producto al que desea agregar extras", opciones);
        if (posicion == -1) return;

        Producto conExtras = elegirExtras(productos.get(posicion));
        // Se cambia el producto de la lista por el que trae los extras
        pedidoServicio.reemplazarProducto(pedidoActual, posicion, conExtras);
        escribir("Ahora: " + conExtras.getDescripcion() + " - $" + conExtras.getPrecio());
        actualizarPedido();
    }

    private void confirmarPedido() {
        if (!pedidoSinConfirmar()) {
            return;
        }
        try {
            pedidoServicio.confirmarPedido(pedidoActual);
            escribir("Pedido confirmado. Total: $" + pedidoActual.getTotal());
            actualizarPedido();
        } catch (IllegalStateException e) {
            escribir("Error: " + e.getMessage());
        }
    }

    // Opción 7: pasa al estado que sigue
    private void avanzarEstado() {
        if (!hayPedidoAbierto()) {
            return;
        }
        try {
            pedidoServicio.avanzarEstado(pedidoActual);
            escribir("Estado: " + pedidoActual.getEstado());
            actualizarPedido();
        } catch (IllegalStateException e) {
            escribir("Error: " + e.getMessage());
        }
    }

    // Opción 8: avanza sola cada 2 segundos hasta PAGADO
    private void prepararAutomaticamente() {
        if (!hayPedidoAbierto()) {
            return;
        }
        if (!pedidoActual.isConfirmado()) {
            escribir("Primero debe confirmar el pedido");
            return;
        }

        detenerAutomatico();
        // El Timer ejecuta este bloque cada 2000 milisegundos
        timerAutomatico = new Timer(2000, e -> {
            if (pedidoActual.getEstado() == EstadoPedido.PAGADO) {
                detenerAutomatico();
                return;
            }
            pedidoServicio.avanzarEstado(pedidoActual);
            escribir("Estado: " + pedidoActual.getEstado());
            actualizarPedido();
            if (pedidoActual.getEstado() == EstadoPedido.PAGADO) {
                detenerAutomatico();
            }
        });
        timerAutomatico.start();
    }

    private void detenerAutomatico() {
        if (timerAutomatico != null) {
            timerAutomatico.stop();
        }
    }

    private void verAvisos() {
        escribirLista("--- Cocina ---", pedidoServicio.getAvisosCocina());
        escribirLista("--- Mesero ---", pedidoServicio.getAvisosMesero());
        escribirLista("--- Caja ---", pedidoServicio.getAvisosCaja());
    }

    // ---------- Métodos de ayuda ----------

    // Pregunta si desea extras. Si dice que sí, los pide; si no, devuelve el mismo producto.
    private Producto preguntarExtras(Producto producto) {
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea agregar extras?",
                "Extras", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (respuesta == JOptionPane.YES_OPTION) {
            return elegirExtras(producto);
        }
        return producto;
    }

    // Una casilla por cada extra; el producto se envuelve en los marcados (Decorator)
    private Producto elegirExtras(Producto producto) {
        List<String> extras = menu.getExtras();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        JCheckBox[] casillas = new JCheckBox[extras.size()];
        for (int i = 0; i < extras.size(); i++) {
            casillas[i] = new JCheckBox(extras.get(i));
            panel.add(casillas[i]);
        }

        int respuesta = JOptionPane.showConfirmDialog(this, panel, "Elija los extras",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (respuesta == JOptionPane.OK_OPTION) {
            for (int i = 0; i < casillas.length; i++) {
                if (casillas[i].isSelected()) {
                    producto = pedidoServicio.agregarExtra(producto, extras.get(i));
                }
            }
        }
        return producto;
    }

    // Lista desplegable: devuelve la posición elegida (-1 si cancela)
    private int elegirDeLista(String pregunta, String[] opciones) {
        Object eleccion = JOptionPane.showInputDialog(this, pregunta, "Sistema de pedidos",
                JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
        if (eleccion == null) {
            return -1;
        }
        for (int i = 0; i < opciones.length; i++) {
            if (opciones[i].equals(eleccion)) {
                return i;
            }
        }
        return -1;
    }

    // Pide un número en una ventanita. Si cancela o escribe otra cosa, devuelve -1.
    private int pedirEntero(String mensaje) {
        String texto = (String) JOptionPane.showInputDialog(this, mensaje, "Sistema de pedidos",
                JOptionPane.PLAIN_MESSAGE, null, null, "1");
        if (texto == null) {
            return -1;
        }
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Escribe una línea en la pantalla de resultados (como un println)
    private void escribir(String texto) {
        areaSalida.append(texto + "\n");
        // Baja el scroll hasta el final para ver siempre lo último
        areaSalida.setCaretPosition(areaSalida.getDocument().getLength());
    }

    private void escribirLista(String titulo, List<String> lista) {
        escribir(titulo);
        for (String texto : lista) {
            escribir(texto);
        }
    }

    // Actualiza la franja de arriba con el pedido actual
    private void actualizarPedido() {
        if (pedidoActual == null) {
            lblPedido.setText("Sin pedido. Pulse 'Crear pedido nuevo'");
            return;
        }
        String estado = "sin confirmar";
        if (pedidoActual.isConfirmado()) {
            estado = pedidoActual.getEstado().toString();
        }
        lblPedido.setText("Pedido #" + pedidoActual.getId() + "   |   " + estado
                + "   |   Total: $" + pedidoActual.getTotal());
    }

    // Revisa que haya un pedido con el que se pueda trabajar
    private boolean hayPedidoAbierto() {
        if (pedidoActual == null) {
            escribir("Primero cree un pedido (opción 1)");
            return false;
        }
        if (pedidoActual.getEstado() == EstadoPedido.PAGADO) {
            escribir("El pedido ya está pagado. Cree uno nuevo");
            return false;
        }
        return true;
    }

    // Igual que la anterior, pero además revisa que el pedido no esté confirmado
    private boolean pedidoSinConfirmar() {
        if (!hayPedidoAbierto()) {
            return false;
        }
        if (pedidoActual.isConfirmado()) {
            escribir("El pedido ya fue confirmado y no se puede modificar");
            return false;
        }
        return true;
    }
}