// Cuadro de confirmación propio, dentro de la página.
// Los formularios con data-confirmar="texto" lo muestran antes de enviarse.
(function () {

    // Se crea una sola vez y queda oculto hasta que se necesita
    const dialogo = document.createElement('dialog');
    dialogo.className = 'dialogo-confirmar';
    dialogo.innerHTML =
        '<div class="dialogo-contenido">' +
        '<h3 class="dialogo-titulo">Confirmar acción</h3>' +
        '<p class="dialogo-mensaje"></p>' +
        '<div class="dialogo-botones">' +
        '<button type="button" class="btn-accion-sec" data-accion="cancelar">Cancelar</button>' +
        '<button type="button" class="btn-accion" data-accion="confirmar">Confirmar</button>' +
        '</div>' +
        '</div>';
    document.body.appendChild(dialogo);

    const textoMensaje = dialogo.querySelector('.dialogo-mensaje');
    let formularioPendiente = null;

    // Cuando se intenta enviar un formulario con data-confirmar, lo frenamos y preguntamos
    document.addEventListener('submit', function (evento) {
        const formulario = evento.target;
        const mensaje = formulario.dataset.confirmar;

        if (!mensaje) {
            return;   // sin data-confirmar: se envía normal
        }

        evento.preventDefault();
        formularioPendiente = formulario;
        textoMensaje.textContent = mensaje;
        dialogo.showModal();
    });

    // Botones del cuadro (y clic en el fondo oscuro para cancelar)
    dialogo.addEventListener('click', function (evento) {
        const accion = evento.target.dataset.accion;

        if (accion === 'confirmar' && formularioPendiente) {
            const formulario = formularioPendiente;
            formularioPendiente = null;
            dialogo.close();
            formulario.submit();   // envío directo: no vuelve a disparar el cuadro
        } else if (accion === 'cancelar' || evento.target === dialogo) {
            formularioPendiente = null;
            dialogo.close();
        }
    });

})();