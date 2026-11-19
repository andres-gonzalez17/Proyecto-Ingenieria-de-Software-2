# Propuesta 3: Sistema pedidos para un restaurante 
## Alcance:
Se va a implementar la toma de pedidos con extras opcionales,de modo que cada extra modifica el precio y la descripción del producto.Los productos se crearán a partir de un menú configurable,para los extras se aplicará el patrón Decorator que permite agregar capas a un producto sin la necesidad de crear una clase por cada combinación posible.
El sistema funcionará por consola y con una interfaz sencilla.

También se implementará el armado de combos paso a paso,en el cual se van a ir eligiendo los diferentes productos hasta completar el combo,para esto se va a usar el patrón Builder,que permite construir un objeto por partes y en orden. Además cuando un pedido cambie de estado (recibido,en preparación,listo o para entregar y pagado) la cocina,mesero y caja recibiran el aviso de forma automática,se planea lograr esto implementando el patrón Observer que consiste en que cuando un objeto cambia su estado,todos los interesados se enteran automáticamente.Se incluirán también el cierre de caja y un reporte con las ventas del día, que se alimentan de los pedidos pagados que la caja registra mediante el Observer, sin requerir un patrón adicional.

No se implementarán las promociones por horario o por día. Estas funcionalidades añaden reglas adicionales, como los días y los horarios en que aplica cada oferta, y por su complejidad el grupo prefiere concentrarse en la correcta implementación de los patrones elegidos (Decorator, Builder y Observer), de modo que el sistema funcione de manera adecuada. Aun así, el diseño se plantea de forma abierta, para que esta funcionalidad pueda incorporarse más adelante si llega a ser necesaria sin tener que rehacer el proyecto.


## Requisitos funcionales:

| ID | Descripción | Criterio de evaluación |
| :--- | :--- | :--- |
| RF-01  | El sistema debe permitir a los usuarios generar un pedido de la lista de bebidas y platos. | El pedido se generará con éxito y aparecerá en pendiente, listo, o entregado dependiendo del estado del pedido. Además será visible para la sección cocina. |
| RF-02  | El sistema debe permitir a los usuarios hacer pedidos, donde se implementen ingredientes a los platos predeterminados. | El pedido se generará con éxito mostrando a la sección de cocina los ingredientes que se deben añadir en el pedido final. |
| RF-03  | Al generar un pedido con una modificación extra, el sistema debe calcular dependiendo de los ingredientes, el nuevo valor del pedido. | El pedido se guardará con el precio inicial predeterminado, y se hará una sumatoria por cada ingrediente extra, con los valores definidos en el sistema. |
| RF-04  | El sistema debe permitir a los usuarios, generar un pedido desde cero con los ingredientes que se elijan. | El pedido se generará mostrando a la sección cocina cada uno de los ingredientes que desea el usuario, y guardará el precio sumando los valores de cada ingrediente. |
| RF-05  | El sistema deberá mostrar un menú donde podrá configurar a gusto como desea su pedido. | El menú del sistema deberá permitir generar el pedido dependiendo de como lo arme el usuario, mostrando el pedido final a la sección cocina, y calculando el valor del pedido dependiendo del valor de los ingredientes. |
| RF-06  | El sistema deberá mostrar el estado del pedido a las secciones de cocina, caja y mesero. | El sistema deberá actualizar en tiempo real el estado del pedido para evitar retrasos en la entrega. |
| RF-07  | El sistema deberá permitir a las diferentes secciones poder actualizar el estado del pedido. | Cada sección contará con los permisos necesarios para hacer la actualización necesaria y poder cumplir el RF-06. |
| RF-08  | El sistema deberá llevar un conteo de el dinero de la caja, visible únicamente para la sección caja. | El sistema contará con una clase para llevar un conteo del dinero total de la caja. |
| RF-09  | El sistema deberá permitir a la sección caja actualizar el dinero total, separando por dinero inicial, dinero ingresado y dinero egresado. | El sistema deberá actualizar el dinero total que se encuentra en la caja, y dicho dinero total se separa por dinero inicial, ingresado y egresado. |
| RF-10  | Al final de cada día el sistema deberá permitir a la sección caja, generar un reporte de ventas. | El sistema deberá generar un reporte de ventas en donde se identifique la cantidad de pedidos, como se distribuyen los pedidos, el valor de cada pedido, y el dinero total con el que cierra el día. |


## Requisitos no funcionles:

| ID | Descripcion | Criterio de evaluacion |
|--------|-----------|-----------|
| RNF-01 | Mantenibilidad y extensibilidad | Agregar una adición nueva (por ejemplo, "salsa extra") debe requerir solo crear una clase nueva, sin modificar las clases que ya existen. (Patrón Decorator) |
| RNF-02 | Mantenibilidad y extensibilidad | Agregar un tipo de producto nuevo al menú no debe obligar a cambiar el código de la caja ni el del pedido. (Patrón Builder) |
| RNF-03 | Mantenibilidad y extensibilidad | Agregar un nuevo observador de pedidos (por ejemplo, un registro de los cambios) no debe requerir cambios en la clase Pedido. (Patrón Observer) |
| RNF-04 | Mantenibilidad y extensibilidad | El código debe estar organizado en paquetes, cada uno con una función clara: modelo, servicios, patrones, consola y pruebas. |
| RNF-05 | Portabilidad y arquitectura | La versión de consola y la interfaz gráfica deben usar la misma lógica del negocio, sin repetir reglas en ninguna de las dos. |
| RNF-06 | Portabilidad y arquitectura | El sistema debe poder compilarse y ejecutarse en cualquier computador que tenga Java 17 o superior instalado, sin necesitar librerías externas, salvo la herramienta de pruebas unitarias. |
| RNF-07 | Usabilidad | El menú de consola debe ser claro, con opciones numeradas y mensajes de error fáciles de entender. Si el usuario escribe algo inválido, el programa no debe cerrarse. |
| RNF-08 | Usabilidad | La interfaz gráfica debe permitir crear un pedido completo (producto, adiciones y pago) en máximo 5 pasos o pantallas. |
| RNF-09 | Rendimiento | Las operaciones principales (agregar adiciones, calcular el total, cambiar el estado del pedido y avisar a los observadores) deben responder en menos de 1 segundo. |
| RNF-10 | Confiabilidad | Un pedido no puede pasar a un estado inválido (por ejemplo, de "recibido" directamente a "entregado"). El sistema debe rechazar el cambio y mostrar un mensaje. |
| RNF-11 | Confiabilidad | El sistema no debe cerrarse por datos inválidos como precios negativos, combos vacíos o cantidades en cero. Debe revisarlos y avisar al usuario. |
| RNF-12 | Precisión | Los cálculos de dinero (adiciones, total y cierre de caja) deben ser exactos. Se debe usar un tipo de dato que no pierda decimales o manejar el dinero en números enteros, para evitar errores de redondeo. |
| RNF-13 | Calidad y pruebas | Cada clase donde se aplique un patrón debe tener pruebas unitarias (mínimo un caso normal y uno de error). |
| RNF-14 | Calidad y pruebas | Los diagramas UML deben coincidir con el código entregado. |


## Problemas de diseño

Algunas situaciones que se identificaron que generarían problemas son:
La primera situación que identificamos es en el caso en el que toque aplicar extras a un pedido,cuando se agrega extras a un pedido tanto el precio como la descripción del mismo cambian y también estos extras se pueden combinar de muchas maneras.Esto obligatoria a crear una clase distinta por cada combinación posible y si se agrega la opción de un nuevo extra serian muchisimas mas clases.Otra opción sería llenar la clase del producto de condicionales que revisen qué extras tiene,pero habría que modificarla cada vez que aparezca uno nuevo. En ambos casos el código sería repetido y difícil de ampliar. 

Otra situación que se analizó es en el momento de armar un combo.Un combo se arma en varias etapas: plato principal, acompañamiento, bebida y tamaño, y en cada una hay muchas opciones. Si no se aplica un buen patrón se tendría un constructor con todos esos datos a la vez, lo que obligaría a tener todo definido desde el principio,se haría un constructor muy grande,poco legible y haría fácil equivocarse en el orden de los datos.Además si se agrega un paso nuevo, habría que modificar el constructor y todos los lugares donde se usa. 

Por último se identificó un problema en el caso de aplicar la funcionalidad de los avisos cuando cambia el estado de un pedido.Cuando esto ocurre  varias áreas como cocina, caja y meseros deben enterarse para actuar en consecuencia. Un mal diseño haría que la clase llamara directamente a cada una de estas áreas dentro de su propio código, generando un fuerte acoplamiento entre ellas. Si en el futuro se quisiera agregar un nuevo interesado, habría que modificar la clase Además, sería difícil probar el funcionamiento del pedido de forma aislada sin tener todas las demás áreas activas. 


## Patrones de diseño elegidos:
Para resolver la primera problemática que se encontró, se decidió emplear el patrón estructural decorator, ya que permite agregar extras a un pedido, modificar el precio y la descripción sin necesidad de crear clases extras para cada combinación, lo que elimina la repetición de código y el exceso de clases innecesarias. La mejor opción es usar este patrón ya que evita la duplicación de código y permite agregar nuevas funcionalidades sin dificultad.

Para resolver la segunda problemática, se decidió usar el patrón creacional builder, ya que permite construir los combos paso a paso, seleccionando cada cosa de manera independiente, es decir, seleccionar un plato principal aparte del acompañamiento y de la bebida, al hacerlo de esta forma se evita tener un constructor demasiado grande con muchos métodos obligatorios. Esta es la mejor opción ya que permite establecer las características por separado y agregar nuevos pasos sin afectar la construcción del objeto.

Por último, para resolver la tercera problemática, se decidió usar el patrón de comportamiento observer, ya que permite que las distintas áreas como lo son cocina, caja y meseros, reciban notificaciones automáticamente cuando cambie el estado del pedido, sin necesidad de que se tenga que conocer directamente cada una. La mejor opción es usar este patrón ya que reduce el acoplamiento y permite agregar nuevas personas a recibir la notificación, sin necesidad de modificar la clase principal.


## Arquitectura general:
La aplicación sigue una arquitectura por capas que separa la lógica del negocio de las interfaces de usuario. El núcleo del sistema está formado por los paquetes modelo, patrones y servicios, donde se concentran las reglas de negocio: la creación de pedidos con extras (Decorator), el armado de combos (Builder), la notificación de cambios de estado a cocina, mesero y caja (Observer), la validación de estados, el cierre de caja y el reporte de ventas. Este núcleo no contiene instrucciones de entrada o salida ni depende de ninguna tecnología de presentación.

La interfaz de consola y la interfaz gráfica se implementan en los paquetes consola y la interfaz gráfica de usuario como capas independientes. Solo capturan la entrada del usuario, invocan los servicios del núcleo y muestran los resultados. De esta forma, ambas interfaces comparten la misma lógica sin repetir reglas (RNF-05), y cualquiera de ellas puede modificarse o reemplazarse sin afectar el negocio.

| Paquete | Responsabilidad |
|---|---|
| `modelo` | Entidades y reglas básicas (`Pedido`, `Producto`, `EstadoPedido`) |
| `patrones` | Decorator, Builder y Observer |
| `servicios` | Casos de uso del negocio (`PedidoService`, `CajaService`, `ReporteService`) |
| `consola` | Interfaz de consola |
| `gui` | Interfaz gráfica |
