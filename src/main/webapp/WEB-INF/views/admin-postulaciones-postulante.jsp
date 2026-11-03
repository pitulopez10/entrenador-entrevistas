<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Postulaciones del Postulante | Admin</title>
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
            <span class="postulaciones-kicker">Historial de Postulaciones</span>
            <h1>Postulaciones de <c:out value="${postulante.nombre}" /></h1>
            <p>Cédula: <strong><c:out value="${postulante.ci}" /></strong> | Correo: <strong><c:out value="${postulante.mail}" /></strong></p>
        </div>

        <a href="${pageContext.request.contextPath}/admin-postulantes" class="btn-primary">
            Volver a Postulantes
        </a>
    </section>

    <c:if test="${empty postulaciones}">
        <section class="postulaciones-vacio">
            <div class="postulaciones-vacio-icono">📂</div>
            <h2>Sin postulaciones</h2>
            <p>Este postulante aún no se ha postulado a ninguna oferta laboral.</p>
        </section>
    </c:if>

    <c:if test="${not empty postulaciones}">
        <section class="postulaciones-lista">
            <c:forEach var="p" items="${postulaciones}">
                <article class="postulacion-card">
                    <div class="postulacion-card-contenido">
                        <div class="postulacion-card-top">
                            <div>
                                <span class="postulacion-empresa">Empresa: <c:out value="${p.ofertaLaboral.empresa.nombre}" /></span>
                                <h2><c:out value="${p.ofertaLaboral.titulo}" /></h2>
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
                                <span class="postulacion-info-label">Fecha de Postulación</span>
                                <strong><c:out value="${p.fechaPostulacion}" /></strong>
                            </div>

                            <c:if test="${not empty p.mensaje}">
                                <div class="postulacion-info-item" style="grid-column: span 2;">
                                    <span class="postulacion-info-label">Mensaje enviado</span>
                                    <strong><c:out value="${p.mensaje}" /></strong>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </article>
            </c:forEach>
        </section>
    </c:if>

</main>

</body>
</html>