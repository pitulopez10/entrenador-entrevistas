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

        <!-- CONTENEDOR SUPERIOR CON TÍTULO Y BOTÓN VOLVER -->
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

        <!-- TARJETAS DE MÉTRICAS RÁPIDAS -->
        <div class="resumen-stats">
            <div class="stat-card total">
                <span class="stat-titulo">Total</span>
                <span class="stat-numero">${not empty totalPostulantes ? totalPostulantes : 0}</span>
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

        <!-- BUSCADOR POR NOMBRE Y FILTRO POR ESTADO -->
        <form method="get"
              action="${pageContext.request.contextPath}/admin-oferta-postulantes"
              style="display: flex; gap: 0.8rem; width: 100%; max-width: 700px; flex-wrap: wrap;">

            <input type="hidden" name="id" value="${oferta.id}">

            <input type="text"
                   name="nombre"
                   value="${param.nombre}"
                   placeholder="Buscar por nombre de postulante..."
                   style="flex: 2; min-width: 200px; padding: 0.6rem 1rem; border-radius: 8px; border: 1px solid #ccc; font-size: 0.95rem;">

            <select name="estado" style="flex: 1; min-width: 140px; padding: 0.6rem 1rem; border-radius: 8px; border: 1px solid #ccc; background: #fff; font-size: 0.95rem;">
                <option value="" <c:if test="${empty param.estado}">selected</c:if>>Todos los estados</option>
                <option value="seleccionado" <c:if test="${param.estado eq 'seleccionado'}">selected</c:if>>Seleccionado</option>
                <option value="rechazado" <c:if test="${param.estado eq 'rechazado'}">selected</c:if>>Rechazado</option>
                <option value="enRevision" <c:if test="${param.estado eq 'enRevision'}">selected</c:if>>En revisión</option>
                <option value="enviado" <c:if test="${param.estado eq 'enviado'}">selected</c:if>>Enviado</option>
            </select>

            <button type="submit" class="btn-primary" style="padding: 0.6rem 1.2rem;">
                Filtrar
            </button>

            <c:if test="${not empty param.nombre or not empty param.estado}">
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
    <c:if test="${empty postulaciones}">
        <section class="postulaciones-vacio">
            <div class="postulaciones-vacio-icono">👤</div>
            <h2>No hay postulantes</h2>
            <p>No se encontraron postulantes que coincidan con los criterios de búsqueda.</p>
        </section>
    </c:if>

    <!-- LISTADO DE POSTULACIONES -->
    <c:if test="${not empty postulaciones}">
        <section class="postulaciones-lista">

            <c:forEach var="p" items="${postulaciones}">

                <article class="postulacion-card">

                    <div class="postulacion-card-contenido">

                        <div class="postulacion-card-top">
                            <div>
                                <span class="postulacion-empresa">
                                    C.I.: <strong><c:out value="${p.postulante.ci}" /></strong>
                                </span>
                                <h2><c:out value="${p.postulante.nombre}" /></h2>
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
                                <span class="postulacion-info-label">Correo Electrónico</span>
                                <strong><c:out value="${p.postulante.mail}" /></strong>
                            </div>

                            <div class="postulacion-info-item">
                                <span class="postulacion-info-label">Fecha de Postulación</span>
                                <strong><c:out value="${p.fechaPostulacion}" /></strong>
                            </div>
                        </div>

                    </div>

                    <!-- BOTONES DE ACCIÓN -->
                    <div class="postulacion-card-actions" style="margin-top: 1rem; display: flex; gap: 0.8rem; flex-wrap: wrap;">
                        <a href="${pageContext.request.contextPath}/admin-editar-postulante?ci=${p.postulante.ci}&returnUrl=${pageContext.request.contextPath}/admin-oferta-postulantes?id=${oferta.id}"
                           class="btn-outline" style="font-size: 0.9rem; padding: 0.5rem 1rem; text-decoration: none;">
                            Editar Postulante
                        </a>

                        <a href="${pageContext.request.contextPath}/admin-editar-postulacion?id=${p.id}&returnUrl=${pageContext.request.contextPath}/admin-oferta-postulantes?id=${oferta.id}"
                           class="btn-primary" style="font-size: 0.9rem; padding: 0.5rem 1rem; text-decoration: none;">
                            Editar Postulación
                        </a>
                    </div>

                </article>

            </c:forEach>

        </section>

        <!-- PAGINACIÓN -->
        <c:if test="${totalPaginas > 1}">
            <div style="display: flex; justify-content: center; gap: 0.5rem; margin-top: 2rem;">
                <c:forEach begin="1" end="${totalPaginas}" var="i">
                    <a href="${pageContext.request.contextPath}/admin-oferta-postulantes?id=${oferta.id}&page=${i}&nombre=${param.nombre}&estado=${param.estado}"
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