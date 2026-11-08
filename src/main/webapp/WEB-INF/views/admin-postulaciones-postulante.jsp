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

    <style>
        .resumen-stats {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(140px, 1fr));
            gap: 1rem;
            width: 100%;
            margin-bottom: 1.5rem;
        }
        .stat-card {
            background: #fff;
            border-radius: 8px;
            padding: 1rem 1.2rem;
            border: 1px solid #e2e8f0;
            box-shadow: 0 2px 4px rgba(0,0,0,0.02);
            display: flex;
            flex-direction: column;
            gap: 0.2rem;
        }
        .stat-card .stat-titulo {
            font-size: 0.85rem;
            color: #64748b;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }
        .stat-card .stat-numero {
            font-size: 1.6rem;
            font-weight: 700;
            color: #0f172a;
        }
        .stat-card.total { border-left: 4px solid #3b82f6; }
        .stat-card.seleccionado { border-left: 4px solid #10b981; }
        .stat-card.rechazado { border-left: 4px solid #ef4444; }
        .stat-card.revision { border-left: 4px solid #f59e0b; }
        .stat-card.enviado { border-left: 4px solid #6366f1; }
    </style>
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

    <section class="postulaciones-header" style="flex-direction: column; align-items: flex-start; gap: 1.5rem;">
        <div style="display: flex; justify-content: space-between; width: 100%; align-items: center; flex-wrap: wrap; gap: 1rem;">
            <div>
                <span class="postulaciones-kicker">Historial de Postulaciones</span>
                <h1>Postulaciones de <c:out value="${postulante.nombre}" /></h1>
                <p>Cédula: <strong><c:out value="${postulante.ci}" /></strong> | Correo: <strong><c:out value="${postulante.mail}" /></strong></p>
            </div>

            <a href="${pageContext.request.contextPath}/admin-postulantes" class="btn-primary">
                Volver a Postulantes
            </a>
        </div>

        <!-- TARJETAS DE MÉTRICAS RÁPIDAS -->
        <div class="resumen-stats">
            <div class="stat-card total">
                <span class="stat-titulo">Total</span>
                <span class="stat-numero">${not empty totalPostulaciones ? totalPostulaciones : 0}</span>
            </div>
            <div class="stat-card seleccionado">
                <span class="stat-titulo">Seleccionado</span>
                <span class="stat-numero">${not empty totalSeleccionados ? totalSeleccionados : 0}</span>
            </div>
            <div class="stat-card rechazado">
                <span class="stat-titulo">Rechazado</span>
                <span class="stat-numero">${not empty totalRechazados ? totalRechazados : 0}</span>
            </div>
            <div class="stat-card revision">
                <span class="stat-titulo">En revisión</span>
                <span class="stat-numero">${not empty totalRevision ? totalRevision : 0}</span>
            </div>
            <div class="stat-card enviado">
                <span class="stat-titulo">Enviado</span>
                <span class="stat-numero">${not empty totalEnviados ? totalEnviados : 0}</span>
            </div>
        </div>

        <!-- FILTRO POR ESTADO -->
        <form method="get" action="${pageContext.request.contextPath}/admin-postulaciones-postulante" style="display: flex; gap: 0.8rem; width: 100%; max-width: 450px; flex-wrap: wrap;">
            <input type="hidden" name="ci" value="${postulante.ci}">

            <select name="estado" style="flex: 1; min-width: 150px; padding: 0.6rem 1rem; border-radius: 8px; border: 1px solid #ccc; background: #fff; font-size: 0.95rem;">
                <option value="" <c:if test="${empty param.estado}">selected</c:if>>Todos los estados</option>
                <option value="seleccionado" <c:if test="${param.estado eq 'seleccionado'}">selected</c:if>>Seleccionado</option>
                <option value="rechazado" <c:if test="${param.estado eq 'rechazado'}">selected</c:if>>Rechazado</option>
                <option value="enRevision" <c:if test="${param.estado eq 'enRevision'}">selected</c:if>>En revisión</option>
                <option value="enviado" <c:if test="${param.estado eq 'enviado'}">selected</c:if>>Enviado</option>
            </select>

            <button type="submit" class="btn-primary" style="padding: 0.6rem 1.2rem;">
                Filtrar
            </button>

            <c:if test="${not empty param.estado}">
                <a href="${pageContext.request.contextPath}/admin-postulaciones-postulante?ci=${postulante.ci}" class="btn-outline" style="padding: 0.6rem 1rem; text-decoration: none;">
                    Limpiar
                </a>
            </c:if>
        </form>
    </section>

    <c:if test="${empty postulaciones}">
        <section class="postulaciones-vacio">
            <div class="postulaciones-vacio-icono">📂</div>
            <h2>Sin postulaciones</h2>
            <p>Este postulante aún no se ha postulado a ninguna oferta laboral que coincida con el filtro.</p>
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
                                <c:when test="${p.estado.name() == 'seleccionado'}">
                                    <span class="estado-postulacion seleccionado">Seleccionado</span>
                                </c:when>
                                <c:when test="${p.estado.name() == 'rechazado'}">
                                    <span class="estado-postulacion rechazado">Rechazado</span>
                                </c:when>
                                <c:when test="${p.estado.name() == 'enRevision'}">
                                    <span class="estado-postulacion revision">En revisión</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="estado-postulacion" style="background-color: #e0e7ff; color: #3730a3;">Enviado</span>
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

                    <!-- BOTÓN PARA EDITAR LA POSTULACIÓN -->
                    <div class="postulacion-card-actions" style="margin-top: 1rem; display: flex; justify-content: flex-end;">
                        <a href="${pageContext.request.contextPath}/admin-editar-postulacion?id=${p.id}"
                           class="btn-primary" style="font-size: 0.9rem; padding: 0.5rem 1rem;">
                            Editar Postulación
                        </a>
                    </div>
                </article>
            </c:forEach>
        </section>
    </c:if>

</main>

</body>
</html>