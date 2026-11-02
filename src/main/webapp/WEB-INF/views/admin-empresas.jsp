<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Empresas | Entrenador Entrevista</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/logo.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ofertas.css?v=13">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mis-postulaciones.css?v=1">
</head>
<body>

<header class="topbar">
    <a href="${pageContext.request.contextPath}/admin-inicio" class="logo">
        <img src="${pageContext.request.contextPath}/img/logo.png" alt="Entrenador Entrevista" class="logo-img">
    </a>

    <nav class="main-nav">
        <a href="${pageContext.request.contextPath}/admin-inicio">Inicio</a>
        <a href="${pageContext.request.contextPath}/admin-postulantes">Postulantes</a>
        <a href="${pageContext.request.contextPath}/admin-empresas" class="active">Empresas</a>
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
            <h1>Listado de Empresas</h1>
            <p>Visualizá, buscá y gestioná las empresas registradas en la plataforma.</p>
        </div>

        <!-- BARRA DE BÚSQUEDA POR NOMBRE -->
        <form method="get" action="${pageContext.request.contextPath}/admin-empresas" style="display: flex; gap: 0.8rem; width: 100%; max-width: 500px;">
            <input type="text" name="nombre" value="${param.nombre}" placeholder="Buscar por nombre..." style="flex: 1; padding: 0.6rem 1rem; border-radius: 8px; border: 1px solid #ccc; font-size: 0.95rem;">

            <button type="submit" class="btn-primary" style="padding: 0.6rem 1.2rem;">
                Buscar
            </button>

            <c:if test="${not empty param.nombre}">
                <a href="${pageContext.request.contextPath}/admin-empresas" class="btn-outline" style="padding: 0.6rem 1rem; text-decoration: none;">
                    Limpiar
                </a>
            </c:if>
        </form>
    </section>

    <c:if test="${empty empresas}">
        <section class="postulaciones-vacio">
            <div class="postulaciones-vacio-icono">🏢</div>
            <h2>No se encontraron empresas</h2>
            <p>No hay organizaciones registradas que coincidan con el criterio de búsqueda.</p>
        </section>
    </c:if>

    <c:if test="${not empty empresas}">
        <section class="postulaciones-lista">
            <c:forEach var="empresa" items="${empresas}">
                <article class="postulacion-card">
                    <div class="postulacion-card-contenido">
                        <div class="postulacion-card-top">
                            <div>
                                <span class="postulacion-empresa">RUT: <c:out value="${empresa.rut}" /></span>
                                <h2><c:out value="${empresa.nombre}" /></h2>
                            </div>

                            <c:choose>
                                <c:when test="${empresa.bloqueado}">
                                    <span class="estado-postulacion rechazado">Bloqueada</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="estado-postulacion seleccionado">Activa</span>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <div class="postulacion-info">
                            <div class="postulacion-info-item">
                                <span class="postulacion-info-label">Correo electrónico</span>
                                <strong><c:out value="${empresa.mail}" /></strong>
                            </div>

                            <div class="postulacion-info-item">
                                <span class="postulacion-info-label">Teléfono</span>
                                <strong><c:out value="${empresa.telefono}" /></strong>
                            </div>

                            <div class="postulacion-info-item">
                                <span class="postulacion-info-label">Sitio Web</span>
                                <strong><c:out value="${empresa.sitioWeb != null ? empresa.sitioWeb : 'No especificado'}" /></strong>
                            </div>
                        </div>
                    </div>

                    <div class="postulacion-card-actions" style="display: flex; gap: 0.5rem; margin-top: 1rem;">
                        <form method="post" action="${pageContext.request.contextPath}/admin-empresas" style="margin: 0;">
                            <input type="hidden" name="rut" value="${empresa.rut}">
                            <c:choose>
                                <c:when test="${empresa.bloqueado}">
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

                        <a href="${pageContext.request.contextPath}/admin-editar-empresa?rut=${empresa.rut}" class="btn-outline">
                            Editar
                        </a>

                        <a href="${pageContext.request.contextPath}/admin-ofertas-empresa?rut=${empresa.rut}" class="btn-primary">
                            Ver Ofertas
                        </a>
                    </div>
                </article>
            </c:forEach>
        </section>
    </c:if>

</main>

</body>
</html>