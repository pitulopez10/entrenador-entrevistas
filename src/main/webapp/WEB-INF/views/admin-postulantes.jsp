<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Postulantes | JobMentor</title>

    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/logo.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ofertas.css?v=13">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mis-postulaciones.css?v=1">
</head>

<body>

<header class="topbar">
    <a href="${pageContext.request.contextPath}/admin-inicio" class="logo">
        <img src="${pageContext.request.contextPath}/img/logo.png" alt="JobMentor" class="logo-img">
    </a>

    <nav class="main-nav">
        <a href="${pageContext.request.contextPath}/admin-inicio">Inicio</a>
        <a href="${pageContext.request.contextPath}/admin-postulantes" class="active">Postulantes</a>
        <a href="${pageContext.request.contextPath}/admin-empresas">Empresas</a>
    </nav>

    <div class="nav-actions">
        <span class="btn-outline" style="border: none;">
            Admin: <strong><c:out value="${sessionScope.adminLogueado.usuario}" /></strong>
        </span>
        <a href="${pageContext.request.contextPath}/logout" class="btn-primary">Cerrar sesión</a>
    </div>
</header>

<main class="postulaciones-page">

    <section class="postulaciones-header" style="flex-direction: column; align-items: flex-start; gap: 1.5rem;">
        <div>
            <span class="postulaciones-kicker">Panel de Administración</span>
            <h1>Listado de Postulantes</h1>
            <p>Visualizá, buscá y gestioná todos los candidatos registrados en el sistema.</p>
        </div>

        <!-- BARRA DE BÚSQUEDA POR NOMBRE -->
        <form method="get" action="${pageContext.request.contextPath}/admin-postulantes" style="display: flex; gap: 0.8rem; width: 100%; max-width: 500px;">
            <input type="text" name="nombre" value="${param.nombre}" placeholder="Buscar por nombre..." style="flex: 1; padding: 0.6rem 1rem; border-radius: 8px; border: 1px solid #ccc; font-size: 0.95rem;">

            <button type="submit" class="btn-primary" style="padding: 0.6rem 1.2rem;">
                Buscar
            </button>

            <c:if test="${not empty param.nombre}">
                <a href="${pageContext.request.contextPath}/admin-postulantes" class="btn-outline" style="padding: 0.6rem 1rem; text-decoration: none;">
                    Limpiar
                </a>
            </c:if>
        </form>
    </section>

    <c:if test="${empty postulantes}">
        <section class="postulaciones-vacio">
            <div class="postulaciones-vacio-icono">👤</div>
            <h2>No se encontraron postulantes</h2>
            <p>No hay usuarios registrados que coincidan con el criterio de búsqueda.</p>
        </section>
    </c:if>

    <c:if test="${not empty postulantes}">
        <section class="postulaciones-lista">
            <c:forEach var="postulante" items="${postulantes}">
                <article class="postulacion-card">
                    <div class="postulacion-card-contenido">
                        <div class="postulacion-card-top">
                            <div>
                                <span class="postulacion-empresa">Cédula: <c:out value="${postulante.ci}" /></span>
                                <h2><c:out value="${postulante.nombre}" /></h2>
                            </div>

                            <c:choose>
                                <c:when test="${postulante.bloqueado}">
                                    <span class="estado-postulacion rechazado">Bloqueado</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="estado-postulacion seleccionado">Activo</span>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <div class="postulacion-info">
                            <div class="postulacion-info-item">
                                <span class="postulacion-info-label">Correo electrónico</span>
                                <strong><c:out value="${postulante.mail}" /></strong>
                            </div>

                            <div class="postulacion-info-item">
                                <span class="postulacion-info-label">Teléfono</span>
                                <strong><c:out value="${postulante.telefono}" /></strong>
                            </div>

                            <div class="postulacion-info-item">
                                <span class="postulacion-info-label">Localidad</span>
                                <strong><c:out value="${postulante.localidad != null ? postulante.localidad : 'No especificada'}" /></strong>
                            </div>
                        </div>
                    </div>

                    <!-- Botones de Acción -->
                    <div class="postulacion-card-actions" style="display: flex; gap: 0.5rem; margin-top: 1rem;">
                        <!-- Acción Bloquear / Desbloquear -->
                        <form method="post" action="${pageContext.request.contextPath}/admin-estado-postulante" style="margin: 0;">
                            <input type="hidden" name="ci" value="${postulante.ci}">
                            <c:choose>
                                <c:when test="${postulante.bloqueado}">
                                    <input type="hidden" name="accion" value="desbloquear">
                                    <button type="submit" class="btn-primary" style="background-color: #28a745; border-color: #28a745;">
                                        Desbloquear
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <input type="hidden" name="accion" value="bloquear">
                                    <button type="submit" class="btn-outline" style="color: #dc3545; border-color: #dc3545;">
                                        Bloquear
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </form>

                        <!-- Acción Editar -->
                        <a href="${pageContext.request.contextPath}/admin-editar-postulante?ci=${postulante.ci}" class="btn-outline">
                            Editar
                        </a>

                        <!-- BOTÓN DE VER POSTULACIONES -->
                        <a href="${pageContext.request.contextPath}/admin-postulaciones-postulante?ci=${postulante.ci}" class="btn-primary">
                            Ver Postulaciones
                        </a>
                    </div>
                </article>
            </c:forEach>
        </section>
    </c:if>

</main>

</body>
</html>