<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Consulta de Oferta | JobMentor</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/logo.png">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ofertas.css?v=13">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css?v=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mis-postulaciones.css?v=1">
</head>
<body>

<!-- HEADER LIMPIO -->
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

<main class="login-page" style="padding: 2rem 1rem;">
    <section class="login-card" style="grid-template-columns: 1fr; max-width: 800px; margin: 0 auto; padding: 2rem;">

        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.5rem; border-bottom: 1px solid #eee; padding-bottom: 1rem;">
            <div>
                <span style="font-size: 0.9rem; color: #666; font-weight: 600;">ID OFERTA: ${oferta.id}</span>
                <h2 style="margin: 0.3rem 0 0 0;"><c:out value="${oferta.titulo}" /></h2>
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

        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 1.5rem; margin-bottom: 1.5rem;">
            <div>
                <label style="font-size: 0.85rem; color: #666; font-weight: 600;">EMPRESA</label>
                <p style="margin-top: 0.2rem;"><strong><c:out value="${oferta.empresa.nombre}" /></strong> (RUT: ${oferta.empresa.rut})</p>
            </div>
            <div>
                <label style="font-size: 0.85rem; color: #666; font-weight: 600;">ÁREA PROFESIONAL</label>
                <p style="margin-top: 0.2rem;"><strong><c:out value="${oferta.area.nombre}" /></strong></p>
            </div>
            <div>
                <label style="font-size: 0.85rem; color: #666; font-weight: 600;">FECHA DE PUBLICACIÓN</label>
                <p style="margin-top: 0.2rem;"><c:out value="${oferta.fechaPublicacion}" /></p>
            </div>
            <div>
                <label style="font-size: 0.85rem; color: #666; font-weight: 600;">FECHA DE CIERRE</label>
                <p style="margin-top: 0.2rem;"><c:out value="${oferta.fechaCierre != null ? oferta.fechaCierre : 'Sin fecha asignada'}" /></p>
            </div>
        </div>

        <div style="margin-bottom: 1.5rem;">
            <label style="font-size: 0.85rem; color: #666; font-weight: 600;">DESCRIPCIÓN</label>
            <p style="margin-top: 0.4rem; background: #f9f9f9; padding: 1rem; border-radius: 6px; white-space: pre-line;"><c:out value="${oferta.descripcion}" /></p>
        </div>

        <div style="margin-bottom: 2rem;">
            <label style="font-size: 0.85rem; color: #666; font-weight: 600;">REQUISITOS</label>
            <p style="margin-top: 0.4rem; background: #f9f9f9; padding: 1rem; border-radius: 6px; white-space: pre-line;"><c:out value="${not empty oferta.requisitos ? oferta.requisitos : 'No se especificaron requisitos'}" /></p>
        </div>

        <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 2rem;">
            <a href="${pageContext.request.contextPath}/admin-postulantes-oferta?id=${oferta.id}" class="btn-primary" style="text-decoration: none; padding: 0.7rem 1.5rem;">
                Ver Postulantes de esta Oferta
            </a>

            <a href="${pageContext.request.contextPath}/admin-inicio" class="btn-outline" style="text-decoration: none; padding: 0.7rem 1.5rem;">
                Volver a Inicio
            </a>
        </div>

    </section>
</main>

</body>
</html>