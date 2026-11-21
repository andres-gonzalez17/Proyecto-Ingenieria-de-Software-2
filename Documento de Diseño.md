# Patron Observer
## Problema concreto: 
Se decidio usar el patron Observer para lograr el objetivo de cambiar el estado del pedido en tiempo real, para que todos los implicados esten enterados. Ademas, tambien se penso para lograr el objetivo del cierre de caja y reporte de ventas del dia.
## Descripcion del patron: 
Observer es un patron que permite tener una suscripcion o actualizacion de notificaciones, para que de esta manera, todas las clases dependientes se actualicen al tiempo, como se menciono anteriormente, esto se aprovecho para lograr que tanto caja, mesero y cocina reciban las actualizaciones de estado del pedido en tiempo real.
## Correspondencia entre roles de patron y clases:

## Alternativas de diseño:

## Ventajas, desventajas y relacion con SOLID:

## Conclusion:
Al aplicar este patron, se puede identificar la manera mas sencilla para lograr que varios objetos se notifiquen al tiempo, ademas se evidencia que funciona por las pruebas hechas con el codigo Java.


# Patron Decorator
## Problema concreto:
Se decidio usar el patron Decorator para lograr el objetivo de agregar productos a un menu preestablecido, y que al hacer esto se actualice tanto la descripcion como el precio total del producto, todo este proceso sin tener que agregar una clase por cada combinacion.
## Descripcion del patron:
Decorator es un patron que permite recubir de capas una clase, para agregar funcionalidades o caracteristicas, sin necesidad de crear muchas clases con cada combinacion, simplemente se implementa la caracteristica que se quiere a la clase principal en el orden que se desea. Por ejemplo en el caso del proyecto, agregar carne, salsa y queso a una hamburguesa que ya traia ciertos ingredientes.
## Correspondencia entre roles de patron y clases:

## Alternativas de diseño:

## Ventajas, desventajas y relacion con SOLID:

## Conclusion:
Con la aplicacion de este patron se evidencia lo importante y util que es, ya que ahorra tanto tiempo de trabajo como uso de recursos al reducir la cantidad de clases usadas, ademas, permite una cantidad gigante de posibilidades, y la manera en que se trabaja no tiene mayor complejidad.


# Patron Builder
## Problema concreto:
Se decidio usar el patron Builder para lograr el objetivo de crear o armar un combo paso a paso segun lo desee cada cliente, solamente necesita definir los ingredientes en el orden correcto y de esta forma se creara un nuevo objeto.
## Descripcion del patron:
Builder es un patron de tipo creacional, que como lo dice su tipo, permite crear objetos a partir de una plantilla predefinida, permite crear objetos paso a paso definiendo las caracteristias en el orden establecido. Por ejemplo, en la aplicacion en el proyecto, se define el nombre del combo a crear, el tipo de comida (plato, bebida, etc), y se implementan los ingredientes deseados.
## Correspondencia entre roles de patron y clases:

## Alternativas de diseño:

## Ventajas, desventajas y relacion con SOLID:

## Conclusion:
Con Builder, se comprendio una forma eficaz de crear objetos a partir de una plantilla ya creada, hacer esto permite crear objetos muy similares pero que se diferencian en las caracteristicas, ademas, se debe tener en cuenta el orden en el que se ponen los datos, ya que un error en el orden, no permite la correcta creacion del objeto.

