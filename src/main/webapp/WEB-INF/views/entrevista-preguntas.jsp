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
        Entrevista práctica | Entrenador Entrevista
    </title>

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
             alt="Entrenador Entrevista"
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