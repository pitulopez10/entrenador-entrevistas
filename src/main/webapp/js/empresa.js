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
// Un único reemplazo local: nunca se guarda el logo de respaldo.
document.querySelectorAll('img[data-logo-predeterminado]').forEach(function (imagen) {
    let reemplazado = false;
    function usarPredeterminado() {
        if (reemplazado) return;
        reemplazado = true;
        const respaldo = imagen.dataset.logoPredeterminado;
        if (respaldo && imagen.getAttribute('src') !== respaldo) {
            imagen.src = respaldo;
        }
    }
    imagen.addEventListener('error', usarPredeterminado, { once: true });
    if (imagen.complete && imagen.naturalWidth === 0) usarPredeterminado();
});

// El navegador conserva la validación y el POST normal del formulario.
(function () {
    const formulario = document.querySelector('form[data-publicar-oferta]');
    const capa = document.getElementById('empresa-publicando');
    if (!formulario || !capa) return;

    const botones = Array.from(formulario.querySelectorAll('button[type="submit"], input[type="submit"]'));
    const estadosIniciales = botones.map(function (boton) { return boton.disabled; });
    let enviando = false;

    function restablecer() {
        enviando = false;
        capa.hidden = true;
        formulario.removeAttribute('aria-busy');
        document.body.classList.remove('empresa-enviando');
        botones.forEach(function (boton, indice) { boton.disabled = estadosIniciales[indice]; });
    }

    formulario.addEventListener('submit', function (evento) {
        if (enviando) {
            evento.preventDefault();
            return;
        }
        if (evento.defaultPrevented || !formulario.checkValidity()) return;

        enviando = true;
        botones.forEach(function (boton) { boton.disabled = true; });
        formulario.setAttribute('aria-busy', 'true');
        capa.hidden = false;
        document.body.classList.add('empresa-enviando');

        // Si otro validador cancela el envío, el formulario queda disponible.
        queueMicrotask(function () {
            if (evento.defaultPrevented) restablecer();
        });
    });

    // También se ejecuta cuando Atrás restaura la página desde la caché.
    window.addEventListener('pageshow', restablecer);
})();