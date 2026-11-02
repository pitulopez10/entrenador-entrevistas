<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Postulantes de Oferta | Admin</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/logo.png">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ofertas.css?v=13">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mis-postulaciones.css?v=1">
</head>
<body>

<!-- HEADER LIMPIO SIN MENÚ NI NAVEGACIÓN -->
<header class="topbar">
    <div class="logo">
        <img src="${pageContext.request.contextPath}/img/logo.png" alt="JobMentor" class="logo-img">
    </div>

    <div class="nav-actions">
        <span class="btn-outline" style="border: none; color: #666;">
            Modo Consulta | Admin: <strong><c:out value="${sessionScope.adminLogueado.usuario}" /></strong>
        </span>
    </div>
</header>

<main class="postulaciones-page">

    <section class="postulaciones-header">
        <div>
            <span class="postulaciones-kicker">Postulantes Inscriptos</span>
            <h1>Candidatos para: <c:out value="${oferta.titulo}" /></h1>
            <p>Empresa: <strong><c:out value="${oferta.empresa.nombre}" /></strong></p>
        </div>

        <!-- ÚNICO BOTÓN DISPONIBLE -->
        <a href="${pageContext.request.contextPath}/admin-consultar-oferta?id=${oferta.id}" class="btn-primary">
            Volver a la Oferta
        </a>
    </section>

    <c:if test="${empty postulaciones}">
        <section class="postulaciones-vacio">
            <div class="postulaciones-vacio-icono">👥</div>
            <h2>Sin postulantes</h2>
            <p>Aún no hay candidatos inscriptos a esta oferta laboral.</p>
        </section>
    </c:if>

    <c:if test="${not empty postulaciones}">
        <section class="postulaciones-lista">
            <c:forEach var="p" items="${postulaciones}">
                <article class="postulacion-card">
                    <div class="postulacion-card-contenido">
                        <div class="postulacion-card-top">
                            <div>
                                <span class="postulacion-empresa">Cédula: <c:out value="${p.postulante.ci}" /></span>
                                <h2><c:out value="${p.postulante.nombre}" /></h2>
                            </div>

                            <c:choose>
                                <c:when test="${p.estado.name().toLowerCase() == 'seleccionado'}">
                                    <span class="estado-postulacion seleccionado">Seleccionado</span>
                                </c:when>
                                <c:when test="${p.estado.name().toLowerCase() == 'pendiente'}">
                                    <span class="estado-postulacion revision">Pendiente</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="estado-postulacion rechazado"><c:out value="${p.estado}" /></span>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <div class="postulacion-info">
                            <div class="postulacion-info-item">
                                <span class="postulacion-info-label">Correo electrónico</span>
                                <strong><c:out value="${p.postulante.mail}" /></strong>
                            </div>

                            <div class="postulacion-info-item">
                                <span class="postulacion-info-label">Teléfono</span>
                                <strong><c:out value="${p.postulante.telefono}" /></strong>
                            </div>

                            <div class="postulacion-info-item">
                                <span class="postulacion-info-label">Fecha de Postulación</span>
                                <strong><c:out value="${p.fechaPostulacion}" /></strong>
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