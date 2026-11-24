## Funcionalidades implementadas

### Pedidos y productos
* Creación de pedidos con numeración automática.
* Menú configurable de bebidas y platos, cargado desde el código (`MenuServicio`).
* Selección de varios productos con cantidad en un mismo pedido.
* Cálculo automático del total del pedido.

### Extras opcionales (Patrón Decorator)
* **Extras disponibles:** queso, carne y salsa.
* Cada extra modifica dinámicamente el precio y la descripción del producto.
* Posibilidad de combinar varios extras sobre un mismo producto.
* Aplicables a productos sueltos, combos preestablecidos y combos armados paso a paso.
* Se pueden agregar posteriormente a un producto que ya esté dentro del pedido.

### Combos (Patrón Builder)
* **Combos armados paso a paso:** selección de plato, acompañamiento, bebida y tamaño.
* **Validación al construir:** el combo no se crea si falta algún paso obligatorio.
* **Combos preestablecidos:** opciones Clásico y Vegetariano, creados mediante un `ComboDirector`.

### Estados y avisos (Patrón Observer)
* **Estados del pedido:** `RECIBIDO`, `EN_PREPARACION`, `LISTO`, `ENTREGADO` y `PAGADO`.
* Aviso automático a **cocina**, **caja** y **mesero** cada vez que cambia el estado de un pedido.
* Avance de estado manual (paso a paso) o automático hasta llegar a `PAGADO`.
* Bloqueo de edición: un pedido confirmado ya no se puede modificar.

### Caja y reportes
* Registro automático de cada venta cuando el pedido pasa al estado `PAGADO`.
* Reporte de ventas del día con hora, descripción y total de cada pedido.
* Cierre de caja con la cantidad total de ventas y el monto recaudado en el día.

---

## Requisitos

* **Java 17 o superior** (JDK).
* Un IDE como **NetBeans** o **Visual Studio Code** con el paquete de extensiones para Java *(opcional si se compila por línea de comandos)*.

---

## Compilar y ejecutar en NetBeans

1. Ve a **File → New Project → Java with Ant → Java Application** y pulsa **Next**.
2. Escribe el nombre del proyecto `SistemaPedidos` y pulsa **Finish**.
3. NetBeans creará un paquete por defecto. Despliega **Source Packages**, haz clic derecho sobre ese paquete, elige **Delete** y bórralo junto con su clase.
4. Crea los **7 paquetes** haciendo clic derecho en **Source Packages → New → Java Package**, e ingrésalos uno por uno:
   * `modelo`
   * `patrones.decorator`
   * `patrones.builder`
   * `patrones.observer`
   * `servicios`
   * `consola`
   * `gui`

5. Crea las **26 clases/interfaces/enums** dentro de cada paquete correspondiente (clic derecho sobre el paquete → **New**):

   * **`modelo`**
     * `Producto` (Java Interface)
     * `ProductoBase`(Java Class)
     * `EstadoPedido` (Java Enum)
     * `ObservadorPedido` (Java Interface)
     * `Combo` (Java Class)
     * `Pedido` (Java Class)
     * `Venta` (Java Class)
     * `Menu` (Java Class)
   * **`patrones.decorator`**
     * `ExtraDecorator` (Java Class - abstracta)
     * `ExtraQueso` (Java Class)
     * `ExtraCarne` (Java Class)
     * `ExtraSalsa` (Java Class)
   * **`patrones.builder`**
     * `ComboBuilder` (Java Class)
     * `ComboOriginal` (Java Class)
   * **`patrones.observer`**
     * `Cocina` (Java Class)
     * `Mesero` (Java Class)
     * `Caja` (Java Class)
   * **`servicios`**
     * `MenuServicio` (Java Class)
     * `PedidoServicio` (Java Class)
     * `CajaServicio` (Java Class)
     * `ReporteServicio` (Java Class)
   * **`consola`**
     * `Main` (Java Class)
     * `MenuConsola` (Java Class)
   * **`gui`**
     * `MainGui` (Java Class)
     * `VentanaPrincipal` (Java Class)

6. Para poblar las clases, los códigos se encuentran en el repositorio público de GitHub, dentro de la carpeta codigo, que contiene todos los archivos ordenados por paquetes.
7. Cuando estén las 26 clases creadas, haz clic derecho sobre el proyecto y elige **Clean and Build** (`Shift + F11`). El proceso debe terminar con `BUILD SUCCESS`.
8. **Para ejecutar:**
   * **Modo consola:** Haz clic derecho sobre `Main.java` y elige **Run File** (`Shift + F6`).
   * **Interfaz gráfica:** Haz clic derecho sobre `MainGui.java` y elige **Run File** (`Shift + F6`).

---
## Aclaración para Visual Studio Code

Los pasos son los mismos, con estas diferencias:

1. Se instala el **Extension Pack for Java** y se abre la carpeta del proyecto con **File → Open Folder**.
2. Para ejecutar, se abre `Main.java` o `MainGui.java` y se pulsa el botón **Run** que aparece sobre el método `main`.
---

## Guía rápida de uso

El orden recomendado para recorrer el sistema, tanto en consola como en interfaz gráfica (cuyas opciones corresponden una a una), es el siguiente:

1. **Crear pedido nuevo.**
2. **Agregar productos** (indicando cantidad), seleccionar un **combo preestablecido** o armar un **combo paso a paso**.
3. **Agregar extras** a un producto del pedido *(opcional)*.
4. **Confirmar pedido.** (A partir de este punto el pedido ya no se puede modificar y la cocina recibe el aviso).
5. **Avanzar al siguiente estado** de forma manual, o usar el **proceso automático** para llevarlo hasta `PAGADO`.
6. **Ver avisos** registrados en cocina, mesero y caja.
7. **Consultar el reporte de ventas** y realizar el **cierre de caja**.
