<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Panel Administrador | JobMentor</title>
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
        <a href="${pageContext.request.contextPath}/admin-inicio" class="active">Inicio</a>
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

        <div>
            <span class="postulaciones-kicker">Panel Administrador</span>
            <h1>Consulta de Ofertas</h1>
            <p>Filtrá y consultá las ofertas laborales según su área profesional o nombre.</p>
        </div>

        <!-- BUSCADOR POR NOMBRE Y FILTRO POR ÁREA -->
        <form method="get"
              action="${pageContext.request.contextPath}/admin-inicio"
              style="display: flex; gap: 0.8rem; width: 100%; max-width: 700px; flex-wrap: wrap;">

            <!-- BUSCAR POR NOMBRE -->
            <input type="text"
                   name="nombre"
                   value="${param.nombre}"
                   placeholder="Buscar por nombre de oferta..."
                   style="flex: 2; min-width: 200px; padding: 0.6rem 1rem; border-radius: 8px; border: 1px solid #ccc; font-size: 0.95rem;">

            <!-- FILTRAR POR ÁREA -->
            <select name="area"
                    id="area"
                    style="flex: 1; min-width: 150px; padding: 0.6rem 1rem; border-radius: 8px; border: 1px solid #ccc; background: #fff; font-size: 0.95rem;">

                <option value="" <c:if test="${empty param.area}">selected</c:if>>
                    Todas las áreas
                </option>

                <c:forEach var="areaItem" items="${areas}">
                    <option value="${areaItem.id}"
                            <c:if test="${param.area eq areaItem.id}">selected</c:if>>
                        <c:out value="${areaItem.nombre}" />
                    </option>
                </c:forEach>

            </select>

            <button type="submit" class="btn-primary" style="padding: 0.6rem 1.2rem;">
                Filtrar
            </button>

            <c:if test="${not empty param.nombre or not empty param.area}">
                <a href="${pageContext.request.contextPath}/admin-inicio"
                   class="btn-outline"
                   style="padding: 0.6rem 1rem; text-decoration: none;">
                    Limpiar
                </a>
            </c:if>

        </form>

    </section>

    <!-- SIN RESULTADOS -->
    <c:if test="${empty ofertas}">
        <section class="postulaciones-vacio">
            <div class="postulaciones-vacio-icono">📋</div>
            <h2>No hay ofertas disponibles</h2>
            <p>No se encontraron registros para los filtros seleccionados.</p>
        </section>
    </c:if>

    <!-- LISTADO DE OFERTAS -->
    <c:if test="${not empty ofertas}">
        <section class="postulaciones-lista">

            <c:forEach var="oferta" items="${ofertas}">

                <article class="postulacion-card">

                    <div class="postulacion-card-contenido">

                        <div class="postulacion-card-top">

                            <div>
                                <span class="postulacion-empresa">
                                    Empresa: <strong><c:out value="${oferta.empresa.nombre}" /></strong>
                                    | Área: <strong><c:out value="${oferta.area.nombre}" /></strong>
                                </span>

                                <h2><c:out value="${oferta.titulo}" /></h2>
                            </div>

                            <c:choose>
                                <c:when test="${oferta.estado == 'ACTIVA'}">
                                    <span class="estado-postulacion seleccionado">Activa</span>
                                </c:when>

                                <c:when test="${oferta.estado == 'PAUSADA'}">
                                    <span class="estado-postulacion revision">Pausada</span>
                                </c:when>

                                <c:otherwise>
                                    <span class="estado-postulacion rechazado">Terminada</span>
                                </c:otherwise>
                            </c:choose>

                        </div>

                        <div class="postulacion-info">

                            <div class="postulacion-info-item">
                                <span class="postulacion-info-label">Fecha Publicación</span>
                                <strong><c:out value="${oferta.fechaPublicacion}" /></strong>
                            </div>

                            <div class="postulacion-info-item">
                                <span class="postulacion-info-label">Fecha Cierre</span>
                                <strong><c:out value="${oferta.fechaCierre != null ? oferta.fechaCierre : 'Sin fecha'}" /></strong>
                            </div>

                            <div class="postulacion-info-item" style="grid-column: span 2;">
                                <span class="postulacion-info-label">Descripción</span>
                                <strong><c:out value="${oferta.descripcion}" /></strong>
                            </div>

                        </div>

                    </div>

                    <div class="postulacion-card-actions" style="margin-top: 1rem;">
                        <a href="${pageContext.request.contextPath}/admin-consultar-oferta?id=${oferta.id}"
                           class="btn-outline">
                            Consultar Oferta
                        </a>
                    </div>

                </article>

            </c:forEach>

        </section>
    </c:if>

</main>

</body>
</html>