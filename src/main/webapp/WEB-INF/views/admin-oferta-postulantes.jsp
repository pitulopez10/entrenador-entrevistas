<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Postulantes de la Oferta | JobMentor</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/logo.png">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ofertas.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mis-postulaciones.css?v=1">
</head>

<body>

<header class="topbar">
    <a href="${pageContext.request.contextPath}/admin-inicio" class="logo">
        <img src="${pageContext.request.contextPath}/img/logo.png" alt="JobMentor" class="logo-img">
    </a>

    <nav class="main-nav">
        <a href="${pageContext.request.contextPath}/admin-inicio">Inicio</a>
        <a href="${pageContext.request.contextPath}/admin-postulantes">Postulantes</a>
        <a href="${pageContext.request.contextPath}/admin-empresas">Empresas</a>
    </nav>

    <div class="nav-actions">
        <span class="btn-outline" style="border: none;">
            <strong><c:out value="${sessionScope.adminLogueado.usuario}" /></strong>
        </span>

        <a href="${pageContext.request.contextPath}/logout" class="btn-primary">
            Cerrar sesión
        </a>
    </div>
</header>

<main class="postulaciones-page">

    <section class="postulaciones-header" style="flex-direction: column; align-items: flex-start; gap: 1.5rem;">

        <!-- CONTENEDOR SUPERIOR CON TÍTULO Y BOTÓN VOLVER A LA DERECHA -->
        <div style="display: flex; justify-content: space-between; align-items: flex-start; width: 100%; flex-wrap: wrap; gap: 1rem;">
            <div>
                <span class="postulaciones-kicker">Oferta: <c:out value="${oferta.titulo}" /></span>
                <h1>Postulantes Inscriptos</h1>
                <p>Filtrá y consultá los postulantes registrados para esta oferta laboral.</p>
            </div>

            <a href="${pageContext.request.contextPath}/admin-inicio"
               class="btn-primary"
               style="padding: 0.6rem 1.4rem; font-size: 1rem; font-weight: 600; text-decoration: none; display: inline-flex; align-items: center; border-radius: 50px; color: #ffffff;">
                Volver
            </a>
        </div>

        <!-- BUSCADOR POR NOMBRE -->
        <form method="get"
              action="${pageContext.request.contextPath}/admin-oferta-postulantes"
              style="display: flex; gap: 0.8rem; width: 100%; max-width: 600px; flex-wrap: wrap;">

            <input type="hidden" name="id" value="${oferta.id}">

            <input type="text"
                   name="nombre"
                   value="${param.nombre}"
                   placeholder="Buscar por nombre de postulante..."
                   style="flex: 2; min-width: 200px; padding: 0.6rem 1rem; border-radius: 8px; border: 1px solid #ccc; font-size: 0.95rem;">

            <button type="submit" class="btn-primary" style="padding: 0.6rem 1.2rem;">
                Filtrar
            </button>

            <c:if test="${not empty param.nombre}">
                <a href="${pageContext.request.contextPath}/admin-oferta-postulantes?id=${oferta.id}"
                   class="btn-outline"
                   style="padding: 0.6rem 1rem; text-decoration: none;">
                    Limpiar
                </a>
            </c:if>

        </form>

    </section>

    <!-- CANTIDAD DE RESULTADOS DESTACADA -->
    <div style="display: inline-flex; align-items: center; gap: 0.5rem; background-color: #f0f4f8; border: 1px solid #d9e2ec; padding: 0.6rem 1.2rem; border-radius: 8px; margin-bottom: 1.5rem; font-size: 0.95rem; color: #102a43;">
        <span>👥</span>
        <c:choose>
            <c:when test="${totalResultados == 1}">
                Se encontró <strong>1</strong> postulante registrado.
            </c:when>
            <c:otherwise>
                Se encontraron <strong>${totalResultados}</strong> postulantes registrados.
            </c:otherwise>
        </c:choose>
    </div>

    <!-- SIN RESULTADOS -->
    <c:if test="${empty postulantes}">
        <section class="postulaciones-vacio">
            <div class="postulaciones-vacio-icono">👤</div>
            <h2>No hay postulantes</h2>
            <p>No se encontraron postulantes que coincidan con los criterios de búsqueda.</p>
        </section>
    </c:if>

    <!-- LISTADO DE POSTULANTES -->
    <c:if test="${not empty postulantes}">
        <section class="postulaciones-lista">

            <c:forEach var="postulante" items="${postulantes}">

                <article class="postulacion-card">

                    <div class="postulacion-card-contenido">

                        <div class="postulacion-card-top">
                            <div>
                                <span class="postulacion-empresa">
                                    C.I.: <strong><c:out value="${postulante.ci}" /></strong>
                                </span>
                                <h2><c:out value="${postulante.nombre}" /></h2>
                            </div>
                        </div>

                        <div class="postulacion-info">
                            <div class="postulacion-info-item" style="grid-column: span 2;">
                                <span class="postulacion-info-label">Correo Electrónico</span>
                                <strong><c:out value="${postulante.mail}" /></strong>
                            </div>
                        </div>

                    </div>

                    <!-- BOTÓN PARA EDITAR INFORMACIÓN COMPLETA -->
                    <div class="postulacion-card-actions" style="margin-top: 1rem;">
                        <a href="${pageContext.request.contextPath}/admin-editar-postulante?ci=${postulante.ci}"
                           class="btn-primary">
                            Editar Información
                        </a>
                    </div>

                </article>

            </c:forEach>

        </section>

        <!-- PAGINACIÓN (Máx 10 por página) -->
        <c:if test="${totalPaginas > 1}">
            <div style="display: flex; justify-content: center; gap: 0.5rem; margin-top: 2rem;">
                <c:forEach begin="1" end="${totalPaginas}" var="i">
                    <a href="${pageContext.request.contextPath}/admin-oferta-postulantes?id=${oferta.id}&page=${i}&nombre=${param.nombre}"
                       class="btn-${paginaActual == i ? 'primary' : 'outline'}"
                       style="padding: 0.5rem 0.9rem; text-decoration: none;">
                        ${i}
                    </a>
                </c:forEach>
            </div>
        </c:if>

    </c:if>

</main>

</body>
</html>