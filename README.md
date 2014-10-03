ServidorTCP
===========

Servidor TCP que acepta múltiples conexiones. Todas las conexiones que se aceptan se ejecutan como _threads_, entonces se debe modificar el código del hilo para que haga lo que se necesita.

Los clientes que se conectan provienen de un _Spark Core_, aunque el código del hilo se puede modificar sin problema para otra aplicación.
