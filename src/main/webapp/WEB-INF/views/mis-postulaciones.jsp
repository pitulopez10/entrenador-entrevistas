<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>
        Mis postulaciones | JobMentor
    </title>
    <link rel="icon"
          type="image/png"
          href="${pageContext.request.contextPath}/img/logo.png">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/ofertas.css?v=16">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/mis-postulaciones.css?v=1">

</head>

<body>


<!-- ==================================================
     HEADER
     ================================================== -->

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

        <a href="${pageContext.request.contextPath}/mis-entrevistas">
            Entrevistas
        </a>

        <a href="${pageContext.request.contextPath}/mis-postulaciones"
           class="active">
            Mis postulaciones
        </a>

        <a href="${pageContext.request.contextPath}/perfil">
            Mi perfil
        </a>

    </nav>


    <div class="nav-actions">

        <c:choose>

            <c:when test="${not empty sessionScope.postulanteLogueado}">

                <span class="usuario-logueado">
                    Hola, ${sessionScope.postulanteLogueado.nombre}
                </span>

                <a href="${pageContext.request.contextPath}/logout"
                   class="btn-outline">
                    Cerrar sesión
                </a>

            </c:when>

            <c:otherwise>

                <a href="${pageContext.request.contextPath}/login"
                   class="btn-outline">
                    Iniciar sesión
                </a>

            </c:otherwise>

        </c:choose>

    </div>

</header>


<!-- ==================================================
     CONTENIDO
     ================================================== -->

<main class="postulaciones-page">

    <section class="postulaciones-header">

        <div>

            <span class="postulaciones-kicker">
                Seguimiento
            </span>

            <h1>
                Mis postulaciones
            </h1>

            <p>
                Consultá las ofertas a las que te postulaste
                y el estado actual de cada postulación.
            </p>

        </div>

        <a href="${pageContext.request.contextPath}/ofertas"
           class="btn-primary">

            Buscar más ofertas

        </a>

    </section>

    <section class="resumen-postulaciones" aria-label="Resumen de postulaciones">
        <article class="resumen-postulacion-card">
            <span class="resumen-postulacion-numero"><c:out value="${totalPostulaciones}" /></span>
            <span class="resumen-postulacion-label">Total de postulaciones</span>
        </article>
        <article class="resumen-postulacion-card seleccionado">
            <span class="resumen-postulacion-numero"><c:out value="${postulacionesSeleccionadas}" /></span>
            <span class="resumen-postulacion-label">Veces seleccionado</span>
        </article>
        <article class="resumen-postulacion-card rechazado">
            <span class="resumen-postulacion-numero"><c:out value="${postulacionesRechazadas}" /></span>
            <span class="resumen-postulacion-label">Veces rechazado</span>
        </article>
    </section>


    <!-- ==================================================
         SIN POSTULACIONES
         ================================================== -->

    <c:if test="${empty postulaciones}">

        <section class="postulaciones-vacio">

            <div class="postulaciones-vacio-icono">
                📄
            </div>

            <h2>
                Todavía no tenés postulaciones
            </h2>

            <p>
                Explorá las ofertas laborales disponibles
                y postulate a las que más te interesen.
            </p>

            <a href="${pageContext.request.contextPath}/ofertas"
               class="btn-primary">

                Ver ofertas laborales

            </a>

        </section>

    </c:if>


    <!-- ==================================================
         LISTADO DE POSTULACIONES
         ================================================== -->

    <c:if test="${not empty postulaciones}">

        <section class="postulaciones-lista">

            <c:forEach var="postulacion"
                       items="${postulaciones}">

                <article class="postulacion-card">

                    <div class="postulacion-card-contenido">

                        <div class="postulacion-card-top">

                            <div>

                                <span class="postulacion-empresa">
                                        ${postulacion.ofertaLaboral.empresa.nombre}
                                </span>

                                <h2>
                                        ${postulacion.ofertaLaboral.titulo}
                                </h2>

                            </div>


                            <c:choose>

                                <c:when test="${postulacion.estado == 'enviado'}">

                                    <span class="estado-postulacion enviado">
                                        Enviada
                                    </span>

                                </c:when>

                                <c:when test="${postulacion.estado == 'enRevision'}">

                                    <span class="estado-postulacion revision">
                                        En revisión
                                    </span>

                                </c:when>

                                <c:when test="${postulacion.estado == 'seleccionado'}">

                                    <span class="estado-postulacion seleccionado">
                                        Seleccionado
                                    </span>

                                </c:when>

                                <c:when test="${postulacion.estado == 'rechazado'}">

                                    <span class="estado-postulacion rechazado">
                                        Rechazado
                                    </span>

                                </c:when>

                                <c:otherwise>

                                    <span class="estado-postulacion">
                                            ${postulacion.estado}
                                    </span>

                                </c:otherwise>

                            </c:choose>

                        </div>


                        <div class="postulacion-info">

                            <div class="postulacion-info-item">

                                <span class="postulacion-info-label">
                                    Fecha de postulación
                                </span>

                                <strong>
                                        ${postulacion.fechaPostulacion}
                                </strong>

                            </div>


                            <div class="postulacion-info-item">

                                <span class="postulacion-info-label">
                                    Mensaje
                                </span>

                                <strong>
                                        ${postulacion.mensaje}
                                </strong>

                            </div>

                        </div>

                    </div>


                    <div class="postulacion-card-actions">

                        <a href="${pageContext.request.contextPath}/oferta?id=${postulacion.ofertaLaboral.id}"
                           class="btn-outline">

                            Ver oferta

                        </a>

                    </div>

                </article>

            </c:forEach>

        </section>

        <c:if test="${totalPaginas > 1}">
            <nav class="paginacion-ofertas" aria-label="Páginas de postulaciones">
                <c:if test="${pagina > 1}"><a class="pagina-enlace" href="${pageContext.request.contextPath}/mis-postulaciones?pagina=${pagina - 1}">← Anterior</a></c:if>
                <span class="pagina-indicador">Página ${pagina} de ${totalPaginas}</span>
                <c:if test="${pagina < totalPaginas}"><a class="pagina-enlace" href="${pageContext.request.contextPath}/mis-postulaciones?pagina=${pagina + 1}">Siguiente →</a></c:if>
            </nav>
        </c:if>

    </c:if>

</main>


</body>

</html>
