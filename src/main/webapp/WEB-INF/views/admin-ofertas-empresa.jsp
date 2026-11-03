<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ofertas de la Empresa | Admin</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/logo.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ofertas.css?v=13">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mis-postulaciones.css?v=1">
</head>
<body>

<header class="topbar">
    <div class="logo">
        <img src="${pageContext.request.contextPath}/img/logo.png" alt="JobMentor" class="logo-img">
    </div>

    <div class="nav-actions">
        <span class="btn-outline" style="border: none; color: #666;">
            Modo Consulta | <strong><c:out value="${sessionScope.adminLogueado.usuario}" /></strong>
        </span>
    </div>
</header>

<main class="postulaciones-page">

    <section class="postulaciones-header">
        <div>
            <span class="postulaciones-kicker">Consulta de Empresa</span>
            <h1>Ofertas de <c:out value="${empresa.nombre}" /></h1>
            <p>RUT: <strong><c:out value="${empresa.rut}" /></strong> | Correo: <strong><c:out value="${empresa.mail}" /></strong></p>
        </div>

        <a href="${pageContext.request.contextPath}/admin-empresas" class="btn-primary">
            Volver a Empresas
        </a>
    </section>

    <c:if test="${empty ofertas}">
        <section class="postulaciones-vacio">
            <div class="postulaciones-vacio-icono">📋</div>
            <h2>Sin ofertas publicadas</h2>
            <p>Esta empresa no tiene ofertas laborales registradas.</p>
        </section>
    </c:if>

    <c:if test="${not empty ofertas}">
        <section class="postulaciones-lista">
            <c:forEach var="oferta" items="${ofertas}">
                <article class="postulacion-card">
                    <div class="postulacion-card-contenido">
                        <div class="postulacion-card-top">
                            <div>
                                <span class="postulacion-empresa">Área: <c:out value="${oferta.area.nombre}" /></span>
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
                </article>
            </c:forEach>
        </section>
    </c:if>

</main>

</body>
</html>