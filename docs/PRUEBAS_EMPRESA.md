# Funciones de empresa: guía de comprobación

Se conservaron colores, tipografías, menús, estados y validaciones de pertenencia. No se agregaron dependencias ni se requieren cambios SQL. Los registros existentes no se modificaron.

## Preparación

Detené Tomcat desde IntelliJ, reconstruí el artefacto y volvé a iniciarlo. Iniciá MariaDB desde XAMPP. Usá el contexto configurado en IntelliJ, por ejemplo /ProyectoApp. Recargá las páginas; se actualizó la versión del CSS.

## Mis ofertas

- En /empresa se conserva el saludo azul. Publicar oferta aparece en una franja blanca debajo del encabezado y antes de las estadísticas, alineado a la derecha. En celular ocupa el ancho disponible.
- Probá Todas, Activas y Cerradas. Cerradas incluye TERMINADA; PAUSADA aparece en Todas. Las estadísticas superiores resumen todos los registros de la empresa.
- Las ofertas ocupan una sola columna, conservando los márgenes laterales. En escritorio cada fila muestra información a la izquierda y estado/acciones a la derecha; en celular se acomoda verticalmente.
- El buscador permite un título parcial sin distinguir mayúsculas y elegir un área real. El botón Buscar combina ambos con el estado actual. Cambiá entre Todas, Activas y Cerradas: título y área deben conservarse.
- Limpiar filtros vuelve a /empresa sin título, área ni estado. Una búsqueda sin coincidencias muestra un mensaje y el acceso para limpiar.
- Verificá que los contadores generales no cambien al buscar. Solo se filtra la lista de ofertas de la empresa en sesión; no se agregó SQL nuevo.
- El encabezado usa el mismo degradado azul de Mis postulaciones. Ahora todos los encabezados grandes de empresa comparten la clase empresa-encabezado-azul; el buscador y las filas siguen limitados a .mis-ofertas-page.
- Cada fila muestra área, título, fechas, estado y cantidad real de postulaciones. Gestionar oferta abre su detalle.
- Cerrá y reabrí una oferta usando la confirmación. Verificá el cambio de estado y los contadores.

## Candidatos

- En el detalle de una oferta, cada tarjeta debe tener nombre, correo y fecha arriba a la izquierda y estado arriba a la derecha.
- Debajo debe aparecer el mensaje guardado solo si contiene texto real. No se muestran mensajes vacíos ni el texto automático anterior “Postulación realizada desde el portal web.”. Las nuevas postulaciones sin carta guardan un mensaje vacío, sin inventar una presentación.
- El pie debe tener una línea divisoria y una sola fila de acciones a la derecha: Ver perfil completo (borde azul), Seleccionar (azul) y Descartar (secundaria discreta). Los controles tienen igual altura y separación.
- Las tarjetas ya no tienen Evaluar candidato. Seleccionar funciona directamente con POST y confirmación; cancelar no debe cambiar el estado. Al confirmar deben actualizarse el estado y los contadores.
- Un candidato seleccionado conserva su etiqueta y no ofrece Seleccionar otra vez. Un candidato descartado no ofrece Descartar otra vez. Se mantienen las otras transiciones existentes.
- En ofertas pausadas o terminadas se permite consultar el perfil, pero no seleccionar ni descartar. Un POST manual tampoco debe cambiar datos.
- Ver perfil completo muestra presentación, estudios, experiencia, contacto y acceso al CV disponible. Se mantienen los estados existentes, incluido enRevision, y las acciones del perfil con confirmación.
- En celular (320–390 px), las acciones de la tarjeta se apilan con el mismo ancho y no deben desbordar.
- Los contadores distinguen enviado (Recibido), enRevision (En evaluación) y seleccionado. Los rechazados cuentan en el total de candidatos.

## Mi empresa

- La ficha debe estar centrada, limitada a unos 1100 px y ajustada al contenido.
- El encabezado azul conserva logo, nombre y fecha real cuando existe, junto con Editar perfil y Vista pública.
- El porcentaje ya no aparece dentro del encabezado. Debajo, una sección blanca muestra Perfil completado: X% sobre una barra larga con fondo gris claro y relleno azul.
- El porcentaje usa seis datos reales: nombre, correo, descripción, teléfono, sitio válido y logo.
- Debajo de la barra, Sobre la empresa e Información de contacto comienzan a la misma altura en dos columnas similares; en celular pasan a una. No se inventa actividad: ese campo no existe en el modelo.
- Guardá cambios en el perfil y comprobá que se conservan contraseña, RUT, bloqueo y fecha de registro. Se relee la empresa antes de actualizar sus campos editables.

## Vista pública

- Abrí Vista pública en incógnito. Usa la misma ficha y muestra logo, nombre, descripción y contacto, sin porcentaje, barra de perfil, botones privados, RUT, contraseña ni datos internos.
- El subtítulo muestra En la plataforma desde seguido del mes en español y el año reales (por ejemplo septiembre de 2026). Sin fecha, se usa un extracto de hasta 160 caracteres de la descripción guardada. Si tampoco hay descripción, se omite el subtítulo.
- Solo http/https válidos pueden enlazarse como sitio web. Probá guardar javascript: o ftp://: deben rechazarse.
- Las ofertas activas públicas permanecen debajo de la ficha. El enlace público usa un identificador derivado, sin RUT visible; los enlaces antiguos con ?rut= deben reemplazarse.

## Seguridad

Con dos empresas distintas, intentá abrir desde la segunda los IDs de oferta y postulación de la primera: debe responder 404. Repetí las acciones con IDs ajenos y el token válido de la segunda empresa: no deben cambiar datos. El candidato se obtiene desde la postulación verificada, no desde una CI enviada por URL. Un POST sin empresaCsrf o con token incorrecto responde 403.

## Verificaciones realizadas

- Maven 3.9.6: package con BUILD SUCCESS, compilación de 74 archivos Java y generación de target/ProyectoApp.war.
- Compilación de JSP con Jasper de Tomcat 11.0.26, incluyendo la ficha compartida: cero errores.
- git diff --check.
- En ajustes anteriores: sintaxis de empresa.js y comprobaciones aisladas de enlaces, CSRF, pertenencia, POST de estados y contadores con DAO simulados.

No se verificaron estos ajustes visualmente en navegador ni con Tomcat iniciado y MariaDB. Tampoco se probaron SMTP o Cloudinary. El repositorio no tiene una suite de tests de Maven.

## Archivos de este ajuste

- empresa-ficha.jsp y empresa.css: barra de completitud fuera del encabezado.
- empresa-oferta.jsp y empresa.css: distribución y acciones de las tarjetas.
- empresa-postulante.jsp: solo muestra mensajes reales.
- PostularseServlet.java: deja de generar el mensaje genérico automático.
- Versiones del CSS en las vistas de empresa para actualizar la caché.
- Esta guía.
## Comprobación del buscador de Mis ofertas

1. Buscá parte de un título con mayúsculas y minúsculas. Combiná la búsqueda con un área y alterná entre Todas, Activas y Cerradas; el campo y el selector deben conservarse.
2. Anotá los contadores generales antes de buscar y comparalos después: deben ser iguales.
3. Usá un título sin coincidencias y comprobá el mensaje y Limpiar filtros. Al limpiar, debe quedar Todas, título vacío y Todas las áreas.
4. Probá la página en escritorio y a 320–390 px: una oferta por fila, sin pérdida de márgenes, con acciones accesibles sin desbordar.
5. Con otra empresa, repetí una búsqueda del mismo título: solo deben aparecer ofertas propias. Las acciones conservan los controles de sesión, POST, token CSRF y confirmación.
6. Se comprobó el servlet con DAO simulados: filtros combinados, coincidencias parciales, áreas inválidas, búsqueda vacía, limpieza y contadores generales. No se probó visualmente en navegador ni contra MariaDB.
## Publicación con capa de carga

- En Crear oferta, intentá enviar con campos obligatorios vacíos o una fecha inválida: el navegador debe mostrar su validación sin capa de carga.
- Completá un formulario válido y enviá. Debe verse Publicando oferta… con un indicador azul sobre blanco, y el botón debe quedar deshabilitado. El POST, las validaciones del servlet y la redirección siguen siendo los existentes.
- No hay demoras artificiales. Si la respuesta local es muy rápida, podés usar temporalmente la limitación de velocidad de red de las herramientas del navegador para observar la capa.
- Probá cancelar una validación o volver con Atrás después de publicar: la capa debe ocultarse y el botón volver a estar disponible.
- Si el servlet devuelve un error de formulario (por ejemplo un título compuesto solo por espacios, que el servidor rechaza), el error debe verse normalmente y el formulario debe poder enviarse de nuevo.

## Degradados compartidos y logo local

- Revisá Mis ofertas, Mi empresa, Vista pública, el perfil de un candidato, Crear oferta, Acceso empresas y Registro de empresas: sus bloques grandes azules usan empresa-encabezado-azul y el mismo degradado. Botones, etiquetas, tarjetas blancas y barras conservan su tratamiento anterior.
- No se cambiaron las vistas ni los estilos de postulante o administrador.
- Una empresa sin logo muestra img/empresa-predeterminada.svg, un edificio azul sobre fondo claro. Se reutiliza mediante empresa-logo.jsp en Mi empresa, Vista pública y Editar perfil.
- Un logo real válido se conserva. Para probar el respaldo sin alterar la base, bloqueá temporalmente la URL de la imagen con las herramientas de red del navegador y recargá: debe mostrarse el SVG local. También debe funcionar si la imagen falla antes de que se cargue el JavaScript.
- Si se bloquea además el SVG, no deben aparecer solicitudes en bucle. El reemplazo se intenta una sola vez.
- El respaldo es únicamente visual: no se sube a Cloudinary, no cambia la URL guardada y no cuenta como logo cargado para completar el perfil.
- Con JavaScript desactivado, la publicación conserva el envío normal y las empresas sin URL de logo siguen mostrando el SVG desde el HTML.

Se verificaron sintaxis de JavaScript, eventos de envío y pageshow con un DOM simulado (validación, POST sin cancelar, bloqueo de duplicados, reintento y ausencia de bucles de imagen), XML del SVG, package con Maven y las ocho JSP afectadas con Jasper sin errores. No se probó esta interacción en un navegador real ni con MariaDB, SMTP o Cloudinary.