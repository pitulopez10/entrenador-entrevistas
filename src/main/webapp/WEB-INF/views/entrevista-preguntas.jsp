```jsp
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>
        Entrevista práctica | JobMentor
    </title>
    <link rel="icon"
          type="image/png"
          href="${pageContext.request.contextPath}/img/logo.png">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/ofertas.css?v=13">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/entrevista.css?v=5">
</head>

<body>


<header class="topbar">

    <a href="${pageContext.request.contextPath}/ofertas"
       class="logo">

        <img src="${pageContext.request.contextPath}/img/logo.png"
             alt="JobMentor"
             class="logo-img">

    </a>


    <nav class="main-nav">

        <a href="${pageContext.request.contextPath}/ofertas">
            Buscar ofertas
        </a>

        <a href="${pageContext.request.contextPath}/mis-entrevistas"
           class="active">
            Entrevistas
        </a>

        <a href="${pageContext.request.contextPath}/mis-postulaciones">
            Mis postulaciones
        </a>

        <a href="${pageContext.request.contextPath}/perfil">
            Mi perfil
        </a>

    </nav>


    <div class="nav-actions">

        <span class="usuario-logueado">
            Hola, ${sessionScope.postulanteLogueado.nombre}
        </span>

        <a href="${pageContext.request.contextPath}/logout"
           class="btn-outline">
            Cerrar sesión
        </a>

    </div>

</header>


<main class="entrevista-page">


    <!-- ==================================================
         ENCABEZADO
         ================================================== -->

    <section class="pregunta-encabezado">

        <div>

            <span class="entrevista-kicker">
                Entrevista en curso
            </span>

            <h1>
                ${entrevista.ofertaLaboral.titulo}
            </h1>

            <p>
                Respondé cada pregunta como si estuvieras en una entrevista real.
            </p>

        </div>


        <div class="pregunta-contador">

            <strong>
                ${numeroPregunta}
            </strong>

            <span>
                de ${totalPreguntas}
            </span>

        </div>

    </section>


    <!-- ==================================================
         PROGRESO
         ================================================== -->

    <section class="progreso-entrevista">

        <div class="progreso-info">

            <span>
                Progreso
            </span>

            <span>
                Pregunta ${numeroPregunta} de ${totalPreguntas}
            </span>

        </div>


        <div class="progreso-barra">

            <div class="progreso-barra-completado"
                 style="width: ${(numeroPregunta * 100) / totalPreguntas}%;">
            </div>

        </div>

    </section>


    <!-- ==================================================
         PREGUNTA
         ================================================== -->

    <section class="pregunta-card">


        <div class="pregunta-meta">

            <span class="pregunta-tipo">
                ${preguntaActual.tipo}
            </span>

            <span class="pregunta-dificultad">
                Dificultad ${preguntaActual.dificultad}
            </span>

        </div>


        <h2>
            <c:out value="${preguntaActual.texto}" />
        </h2>

        <div id="datosPreguntaDemo"
             data-texto="<c:out value='${preguntaActual.texto}' />"
             data-tipo="${preguntaActual.tipo}"
             data-dificultad="${preguntaActual.dificultad}"></div>


        <form method="post"
              action="${pageContext.request.contextPath}/entrevista"
              id="formRespuesta">


            <input type="hidden"
                   name="entrevistaId"
                   value="${entrevista.id}">


            <input type="hidden"
                   name="preguntaId"
                   value="${preguntaActual.id}">


            <!-- ==================================================
                 RESPUESTA
                 ================================================== -->

            <div class="respuesta-grupo">

                <label for="respuesta">
                    Tu respuesta
                </label>

                <textarea
                        id="respuesta"
                        name="respuesta"
                        rows="9"
                        maxlength="4000"
                        required
                        placeholder="Escribí tu respuesta acá..."><c:if test="${not empty respuestaActual}"><c:out value="${respuestaActual.texto}" /></c:if></textarea>


                <div class="respuesta-ayuda">

                    <span>
                        Intentá justificar tu respuesta y utilizar ejemplos concretos.
                    </span>

                </div>

                <button type="button"
                        class="btn-respuesta-demo"
                        id="completarRespuestaDemo">
                    Completar respuesta de ejemplo
                </button>

                <button type="button"
                        class="btn-microfono"
                        id="activarMicrofono"
                        aria-pressed="false">
                    🎙️ Responder con micrófono
                </button>

                <span class="microfono-estado"
                      id="estadoMicrofono"
                      role="status"
                      aria-live="polite"></span>

            </div>


            <!-- ==================================================
                 ACCIONES
                 ================================================== -->

            <div class="pregunta-acciones pregunta-acciones-dobles">


                <!-- GUARDAR Y SALIR -->

                <button type="submit"
                        name="accion"
                        value="guardarSalir"
                        class="btn-guardar-salir">

                    Guardar y salir

                </button>


                <!-- GUARDAR Y CONTINUAR / FINALIZAR -->

                <button type="submit"
                        name="accion"
                        value="responder"
                        class="btn-comenzar-entrevista">

                    <c:choose>

                        <c:when test="${numeroPregunta == totalPreguntas}">
                            Finalizar entrevista
                        </c:when>

                        <c:otherwise>
                            Guardar y continuar
                        </c:otherwise>

                    </c:choose>

                </button>


            </div>

        </form>

    </section>


    <!-- ==================================================
         CONSEJO
         ================================================== -->

    <section class="entrevista-consejo">

        <strong>
            Consejo
        </strong>

        <p>
            No busques una respuesta perfecta. La idea es practicar cómo
            comunicarías tus conocimientos, decisiones y experiencias frente
            a un entrevistador.
        </p>

    </section>


</main>


<!-- ==================================================
     CARGA DE RETROALIMENTACIÓN
     ================================================== -->

<div id="cargaRetroalimentacion"
     class="carga-ia">

    <div class="carga-ia-contenido">

        <div class="carga-ia-spinner"></div>

        <h2>
            Estamos analizando tu entrevista
            <span class="carga-ia-puntos"></span>
        </h2>

        <p>
            Gemini está revisando tus respuestas para preparar
            una devolución personalizada.
        </p>

    </div>

</div>


<!-- ==================================================
     JAVASCRIPT
     ================================================== -->

<script>

    const formRespuesta =
        document.getElementById("formRespuesta");

    const cargaRetroalimentacion =
        document.getElementById("cargaRetroalimentacion");

    const numeroPregunta =
        ${numeroPregunta};

    const totalPreguntas =
        ${totalPreguntas};

    const botonRespuestaDemo =
        document.getElementById("completarRespuestaDemo");

    const botonMicrofono =
            document.getElementById("activarMicrofono");
    const estadoMicrofono =
            document.getElementById("estadoMicrofono");
    const respuesta =
            document.getElementById("respuesta");

    const SpeechRecognition =
            window.SpeechRecognition || window.webkitSpeechRecognition;

    if (botonMicrofono) {
        if (!SpeechRecognition) {
            botonMicrofono.disabled = true;
            botonMicrofono.title = "Tu navegador no admite transcripción por voz";
            estadoMicrofono.textContent = "Usá Chrome o Edge para responder por voz.";
        } else {
            const reconocimiento = new SpeechRecognition();
            reconocimiento.lang = "es-UY";
            reconocimiento.continuous = true;
            reconocimiento.interimResults = true;
            let transcripcionFinal = "";

            reconocimiento.onstart = function () {
                botonMicrofono.classList.add("escuchando");
                botonMicrofono.setAttribute("aria-pressed", "true");
                botonMicrofono.textContent = "⏹️ Detener micrófono";
                estadoMicrofono.textContent = "Escuchando... hablá con naturalidad.";
            };

            reconocimiento.onresult = function (evento) {
                let transcripcionIntermedia = "";
                for (let i = evento.resultIndex; i < evento.results.length; i++) {
                    const texto = evento.results[i][0].transcript;
                    if (evento.results[i].isFinal) {
                        transcripcionFinal += texto + " ";
                    } else {
                        transcripcionIntermedia += texto;
                    }
                }
                respuesta.value = (transcripcionFinal + transcripcionIntermedia).trim();
                respuesta.dispatchEvent(new Event("input", { bubbles: true }));
            };

            reconocimiento.onerror = function (evento) {
                estadoMicrofono.textContent = evento.error === "not-allowed"
                        ? "Permití el acceso al micrófono para continuar."
                        : "No se pudo transcribir la voz. Intentá nuevamente.";
            };

            reconocimiento.onend = function () {
                botonMicrofono.classList.remove("escuchando");
                botonMicrofono.setAttribute("aria-pressed", "false");
                botonMicrofono.textContent = "🎙️ Responder con micrófono";
                if (estadoMicrofono.textContent === "Escuchando... hablá con naturalidad.") {
                    estadoMicrofono.textContent = "Transcripción lista para revisar.";
                }
            };

            botonMicrofono.addEventListener("click", function () {
                if (botonMicrofono.getAttribute("aria-pressed") === "true") {
                    reconocimiento.stop();
                } else {
                    transcripcionFinal = respuesta.value.trim();
                    reconocimiento.start();
                }
            });
        }
    }

    if (botonRespuestaDemo) {
        botonRespuestaDemo.addEventListener("click", async function () {
            const respuesta = document.getElementById("respuesta");
            botonRespuestaDemo.disabled = true;
            botonRespuestaDemo.textContent = "Generando respuesta con IA...";
            try {
                const preguntaDemo = document.getElementById("datosPreguntaDemo").dataset;
                const datos = new URLSearchParams({
                    accion: "generarRespuestaDemo",
                    pregunta: preguntaDemo.texto,
                    tipo: preguntaDemo.tipo,
                    dificultad: preguntaDemo.dificultad
                });
                const resultado = await fetch("${pageContext.request.contextPath}/entrevista", {
                    method: "POST",
                    headers: { "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8" },
                    body: datos
                });
                const json = await resultado.json();
                if (!resultado.ok || json.error) throw new Error(json.error);
                respuesta.value = json.respuesta;
                respuesta.dispatchEvent(new Event("input", { bubbles: true }));
                respuesta.focus();
            } catch (error) {
                alert("No se pudo generar la respuesta de ejemplo. Revisá la configuración de Gemini.");
            } finally {
                botonRespuestaDemo.disabled = false;
                botonRespuestaDemo.textContent = "Completar respuesta de ejemplo";
            }
        });
    }


    formRespuesta.addEventListener(
        "submit",
        function (event) {

            /*
             * Solo mostramos la pantalla de análisis cuando:
             *
             * 1. Estamos en la última pregunta.
             * 2. El usuario pulsó "Finalizar entrevista".
             *
             * Si pulsa "Guardar y salir", NO mostramos
             * la pantalla de Gemini.
             */

            const botonPresionado =
                event.submitter;


            if (botonPresionado != null &&
                botonPresionado.value === "responder" &&
                numeroPregunta === totalPreguntas) {

                cargaRetroalimentacion.classList.add(
                    "activa"
                );
            }
        }
    );

</script>


</body>

</html>
```
