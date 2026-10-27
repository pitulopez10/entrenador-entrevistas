<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>
        Mis entrevistas | Entrenador Entrevista
    </title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/ofertas.css?v=13">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/entrevista.css?v=4">
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


    <section class="resultado-encabezado">

        <span class="entrevista-kicker">
            Historial
        </span>

        <h1>
            Mis entrevistas prácticas
        </h1>

        <p>
            Consultá tus entrevistas anteriores o continuá una práctica pendiente.
        </p>

    </section>


    <c:choose>


        <c:when test="${empty entrevistas}">

            <section class="entrevista-vacio">

                <div class="entrevista-vacio-icono">
                    ?
                </div>

                <h2>
                    Todavía no realizaste entrevistas
                </h2>

                <p>
                    Elegí una oferta laboral y utilizá la opción
                    “Practicar entrevista” para comenzar.
                </p>

                <a href="${pageContext.request.contextPath}/ofertas"
                   class="btn-repetir-entrevista">

                    Buscar ofertas

                </a>

            </section>

        </c:when>


        <c:otherwise>

            <section class="historial-entrevistas">

                <c:forEach var="entrevista"
                           items="${entrevistas}">

                    <article class="historial-entrevista-card">


                        <div class="historial-entrevista-info">

                            <div class="historial-entrevista-superior">

                                <span class="entrevista-etiqueta">
                                    Entrevista práctica
                                </span>


                                <c:choose>

                                    <c:when test="${entrevista.estado == 'TERMINADA'}">

                                        <span class="estado-entrevista estado-terminada">
                                            Finalizada
                                        </span>

                                    </c:when>


                                    <c:otherwise>

                                        <span class="estado-entrevista estado-activa">
                                            En curso
                                        </span>

                                    </c:otherwise>

                                </c:choose>

                            </div>


                            <h2>
                                <c:out value="${entrevista.ofertaLaboral.titulo}" />
                            </h2>


                            <c:if test="${not empty entrevista.ofertaLaboral.empresa}">

                                <p class="historial-empresa">

                                    <c:out value="${entrevista.ofertaLaboral.empresa.nombre}" />

                                </p>

                            </c:if>


                            <div class="historial-datos">

                                <span>
                                    Fecha:
                                    <strong>
                                            ${entrevista.fecha}
                                    </strong>
                                </span>

                                <span>
                                    Duración estimada:
                                    <strong>
                                        ${entrevista.duracionMin} min
                                    </strong>
                                </span>

                            </div>

                        </div>


                        <div class="historial-entrevista-acciones">

                            <c:choose>


                                <c:when test="${entrevista.estado == 'TERMINADA'}">

                                    <a href="${pageContext.request.contextPath}/entrevista?entrevistaId=${entrevista.id}"
                                       class="btn-historial-principal">

                                        Ver resultado

                                    </a>

                                </c:when>


                                <c:otherwise>

                                    <a href="${pageContext.request.contextPath}/entrevista?entrevistaId=${entrevista.id}"
                                       class="btn-historial-principal">

                                        Continuar entrevista

                                    </a>

                                </c:otherwise>

                            </c:choose>


                            <a href="${pageContext.request.contextPath}/entrevista?ofertaId=${entrevista.ofertaLaboral.id}"
                               class="btn-historial-secundario">

                                Practicar nuevamente

                            </a>

                        </div>


                    </article>

                </c:forEach>

            </section>

        </c:otherwise>

    </c:choose>


</main>

</body>

</html>