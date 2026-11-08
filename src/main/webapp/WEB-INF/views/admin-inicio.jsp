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

    <style>
        .resumen-stats {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
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
        .stat-card.activa { border-left: 4px solid #10b981; }
        .stat-card.pausada { border-left: 4px solid #f59e0b; }
        .stat-card.terminada { border-left: 4px solid #ef4444; }
        .stat-card.total { border-left: 4px solid #3b82f6; }
    </style>
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
            <p>Filtrá y consultá las ofertas laborales según su estado, área profesional o nombre.</p>
        </div>

        <!-- TARJETAS DE MÉTRICAS RÁPIDAS / RESUMEN -->
        <div class="resumen-stats">
            <div class="stat-card total">
                <span class="stat-titulo">Total Ofertas</span>
                <span class="stat-numero">${not empty totalOfertas ? totalOfertas : (totalResultados != null ? totalResultados : 0)}</span>
            </div>
            <div class="stat-card activa">
                <span class="stat-titulo">Activas</span>
                <span class="stat-numero">${not empty totalActivas ? totalActivas : 0}</span>
            </div>
            <div class="stat-card pausada">
                <span class="stat-titulo">Pausadas</span>
                <span class="stat-numero">${not empty totalPausadas ? totalPausadas : 0}</span>
            </div>
            <div class="stat-card terminada">
                <span class="stat-titulo">Terminadas</span>
                <span class="stat-numero">${not empty totalTerminadas ? totalTerminadas : 0}</span>
            </div>
        </div>

        <!-- BUSCADOR POR NOMBRE, ÁREA Y ESTADO -->
        <form method="get"
              action="${pageContext.request.contextPath}/admin-inicio"
              style="display: flex; gap: 0.8rem; width: 100%; max-width: 900px; flex-wrap: wrap;">

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

            <!-- FILTRAR POR ESTADO -->
            <select name="estado"
                    id="estado"
                    style="flex: 1; min-width: 130px; padding: 0.6rem 1rem; border-radius: 8px; border: 1px solid #ccc; background: #fff; font-size: 0.95rem;">

                <option value="" <c:if test="${empty param.estado}">selected</c:if>>
                    Todos los estados
                </option>
                <option value="ACTIVA" <c:if test="${param.estado eq 'ACTIVA'}">selected</c:if>>Activa</option>
                <option value="PAUSADA" <c:if test="${param.estado eq 'PAUSADA'}">selected</c:if>>Pausada</option>
                <option value="TERMINADA" <c:if test="${param.estado eq 'TERMINADA'}">selected</c:if>>Terminada</option>

            </select>

            <button type="submit" class="btn-primary" style="padding: 0.6rem 1.2rem;">
                Filtrar
            </button>

            <c:if test="${not empty param.nombre or not empty param.area or not empty param.estado}">
                <a href="${pageContext.request.contextPath}/admin-inicio"
                   class="btn-outline"
                   style="padding: 0.6rem 1rem; text-decoration: none;">
                    Limpiar
                </a>
            </c:if>

        </form>

    </section>

    <!-- CANTIDAD DE RESULTADOS DESTACADA -->
    <div style="display: inline-flex; align-items: center; gap: 0.5rem; background-color: #f0f4f8; border: 1px solid #d9e2ec; padding: 0.6rem 1.2rem; border-radius: 8px; margin-bottom: 1.5rem; font-size: 0.95rem; color: #102a43;">
        <span>🔍</span>
        <c:choose>
            <c:when test="${totalResultados == 1}">
                Se encontró <strong>1</strong> resultado disponible.
            </c:when>
            <c:otherwise>
                Se encontraron <strong>${totalResultados}</strong> resultados disponibles.
            </c:otherwise>
        </c:choose>
    </div>

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

                    <!-- BOTONES: EDITAR Y VER POSTULANTES -->
                    <div class="postulacion-card-actions" style="margin-top: 1rem; display: flex; gap: 0.8rem; flex-wrap: wrap;">
                        <a href="${pageContext.request.contextPath}/admin-editar-oferta?id=${oferta.id}"
                           class="btn-outline">
                            Editar
                        </a>
                        <a href="${pageContext.request.contextPath}/admin-oferta-postulantes?id=${oferta.id}"
                           class="btn-primary">
                            Ver Postulantes
                        </a>
                    </div>

                </article>

            </c:forEach>

        </section>

        <!-- PAGINACIÓN (Máx 15 por página) -->
        <c:if test="${totalPaginas > 1}">
            <div style="display: flex; justify-content: center; gap: 0.5rem; margin-top: 2rem;">
                <c:forEach begin="1" end="${totalPaginas}" var="i">
                    <a href="${pageContext.request.contextPath}/admin-inicio?page=${i}&nombre=${param.nombre}&area=${param.area}&estado=${param.estado}"
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