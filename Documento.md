# Propuesta 3: Sistema pedidos para un restaurante 
## Requisitos funcionales:
Requerimientos funcionales:

| ID | Descripción | Criterio de evaluación |
| :--- | :--- | :--- |
| RF-01 | El sistema debe permitir a los usuarios generar un pedido de la lista de bebidas y platos. | El pedido se generará con éxito y aparecerá en pendiente, listo, o entregado dependiendo del estado del pedido. Además será visible para la sección cocina. |
| RF-02 | El sistema debe permitir a los usuarios hacer pedidos, donde se implementen ingredientes a los platos predeterminados. | El pedido se generará con éxito mostrando a la sección de cocina los ingredientes que se deben añadir en el pedido final. |
| RF-03 | Al generar un pedido con una modificación extra, el sistema debe calcular dependiendo de los ingredientes, el nuevo valor del pedido. | El pedido se guardará con el precio inicial predeterminado, y se hará una sumatoria por cada ingrediente extra, con los valores definidos en el sistema. |
| RF-04 | El sistema debe permitir a los usuarios, generar un pedido desde cero con los ingredientes que se elijan. | El pedido se generará mostrando a la sección cocina cada uno de los ingredientes que desea el usuario, y guardará el precio sumando los valores de cada ingrediente. |
| RF-05 | El sistema deberá mostrar un menú donde podrá configurar a gusto como desea su pedido. | El menú del sistema deberá permitir generar el pedido dependiendo de como lo arme el usuario, mostrando el pedido final a la sección cocina, y calculando el valor del pedido dependiendo del valor de los ingredientes. |
| RF-06 | El sistema deberá mostrar el estado del pedido a las secciones de cocina, caja y mesero. | El sistema deberá actualizar en tiempo real el estado del pedido para evitar retrasos en la entrega. |
| RF-07 | El sistema deberá permitir a las diferentes secciones poder actualizar el estado del pedido. | Cada sección contará con los permisos necesarios para hacer la actualización necesaria y poder cumplir el RF-06. |
| RF-08 | El sistema deberá llevar un conteo de el dinero de la caja, visible únicamente para la sección caja. | El sistema contará con una clase para llevar un conteo del dinero total de la caja. |
| RF-09 | El sistema deberá permitir a la sección caja actualizar el dinero total, separando por dinero inicial, dinero ingresado y dinero egresado. | El sistema deberá actualizar el dinero total que se encuentra en la caja, y dicho dinero total se separa por dinero inicial, ingresado y egresado. |
| RF-10 | Al final de cada día el sistema deberá permitir a la sección caja, generar un reporte de ventas. | El sistema deberá generar un reporte de ventas en donde se identifique la cantidad de pedidos, como se distribuyen los pedidos, el valor de cada pedido, y el dinero total con el que cierra el día. |



# Requisitos no funcionles:
