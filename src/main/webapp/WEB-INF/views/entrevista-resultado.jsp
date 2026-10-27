<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>
        Resultado de entrevista | Entrenador Entrevista
    </title>
    <link rel="icon"
          type="image/png"
          href="${pageContext.request.contextPath}/img/logo.png">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/ofertas.css?v=13">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/entrevista.css?v=3">
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

        <a href="${pageContext.request.contextPath}/mis-entrevistas">
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

    <section class="resultado-encabezado">

        <span class="entrevista-kicker">
            Entrevista finalizada
        </span>

        <h1>
            Resultado de tu práctica
        </h1>

        <p>
            Completaste las ${totalPreguntas} preguntas para la oferta
            <strong>${entrevista.ofertaLaboral.titulo}</strong>.
        </p>

    </section>


    <!-- ==================================================
         RESUMEN
         ================================================== -->

    <section class="resultado-resumen">

        <div class="resultado-resumen-item">

            <strong>
                ${totalPreguntas}
            </strong>

            <span>
                Preguntas respondidas
            </span>

        </div>


        <div class="resultado-resumen-item">

            <strong>
                100%
            </strong>

            <span>
                Entrevista completada
            </span>

        </div>


        <div class="resultado-resumen-item">

            <strong>
                ${entrevista.duracionMin}
            </strong>

            <span>
                Minutos estimados
            </span>

        </div>

    </section>


    <!-- ==================================================
         DEVOLUCIÓN
         ================================================== -->

    <section class="resultado-card">

        <div class="resultado-card-header">

            <div>

                <span class="entrevista-etiqueta">
                    Devolución
                </span>

                <h2>
                    Análisis de la entrevista
                </h2>

            </div>

        </div>


        <c:choose>

            <c:when test="${not empty retroalimentacion}">

                <div class="resultado-feedback">
                    <c:out value="${retroalimentacion.texto}" />
                </div>

            </c:when>

            <c:otherwise>

                <div class="resultado-sin-feedback">

                    No se encontró una devolución para esta entrevista.

                </div>

            </c:otherwise>

        </c:choose>

    </section>


    <!-- ==================================================
         RESPUESTAS
         ================================================== -->

    <section class="resultado-card">

        <div class="resultado-card-header">

            <div>

                <span class="entrevista-etiqueta">
                    Tus respuestas
                </span>

                <h2>
                    Repaso de la entrevista
                </h2>

            </div>

        </div>


        <div class="resultado-respuestas">

            <c:forEach var="respuesta"
                       items="${respuestas}"
                       varStatus="estado">

                <article class="resultado-respuesta-item">

                    <div class="resultado-numero">
                            ${estado.index + 1}
                    </div>


                    <div class="resultado-respuesta-contenido">

                        <div class="resultado-pregunta-meta">

                            <span>
                                    ${respuesta.pregunta.tipo}
                            </span>

                            <span>
                                    ${respuesta.pregunta.dificultad}
                            </span>

                        </div>


                        <h3>
                            <c:out value="${respuesta.pregunta.texto}" />
                        </h3>


                        <div class="resultado-tu-respuesta">

                            <strong>
                                Tu respuesta
                            </strong>

                            <p>
                                <c:out value="${respuesta.texto}" />
                            </p>

                        </div>

                    </div>

                </article>

            </c:forEach>

        </div>

    </section>


    <!-- ==================================================
         ACCIONES
         ================================================== -->

    <section class="resultado-acciones">

        <a href="${pageContext.request.contextPath}/entrevista?ofertaId=${entrevista.ofertaLaboral.id}"
           class="btn-repetir-entrevista">

            Practicar nuevamente

        </a>


        <a href="${pageContext.request.contextPath}/oferta?id=${entrevista.ofertaLaboral.id}"
           class="btn-volver-oferta">

            Volver a la oferta

        </a>

    </section>


</main>

</body>

</html>