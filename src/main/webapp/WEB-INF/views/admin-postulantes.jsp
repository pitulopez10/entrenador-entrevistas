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

    <style>
        .resumen-stats {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
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
        .stat-card.activo { border-left: 4px solid #10b981; }
        .stat-card.bloqueado { border-left: 4px solid #ef4444; }
    </style>
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
            <span class="postulaciones-kicker">Panel de Administración</span>
            <h1>Listado de Postulantes</h1>
            <p>Visualizá, buscá y gestioná todos los candidatos registrados en el sistema.</p>
        </div>

        <!-- TARJETAS DE MÉTRICAS RÁPIDAS -->
        <div class="resumen-stats">
            <div class="stat-card total">
                <span class="stat-titulo">Total Postulantes</span>
                <span class="stat-numero">${not empty totalPostulantes ? totalPostulantes : (totalResultados != null ? totalResultados : 0)}</span>
            </div>
            <div class="stat-card activo">
                <span class="stat-titulo">Activos</span>
                <span class="stat-numero">${not empty totalActivos ? totalActivos : 0}</span>
            </div>
            <div class="stat-card bloqueado">
                <span class="stat-titulo">Bloqueados</span>
                <span class="stat-numero">${not empty totalBloqueados ? totalBloqueados : 0}</span>
            </div>
        </div>

        <!-- BUSCADOR POR NOMBRE Y FILTRO POR ESTADO -->
        <form method="get" action="${pageContext.request.contextPath}/admin-postulantes" style="display: flex; gap: 0.8rem; width: 100%; max-width: 700px; flex-wrap: wrap;">
            <input type="text" name="nombre" value="${param.nombre}" placeholder="Buscar por nombre..." style="flex: 2; min-width: 200px; padding: 0.6rem 1rem; border-radius: 8px; border: 1px solid #ccc; font-size: 0.95rem;">

            <select name="estado" style="flex: 1; min-width: 140px; padding: 0.6rem 1rem; border-radius: 8px; border: 1px solid #ccc; background: #fff; font-size: 0.95rem;">
                <option value="" <c:if test="${empty param.estado}">selected</c:if>>Todos los estados</option>
                <option value="activo" <c:if test="${param.estado eq 'activo'}">selected</c:if>>Activos</option>
                <option value="bloqueado" <c:if test="${param.estado eq 'bloqueado'}">selected</c:if>>Bloqueados</option>
            </select>

            <button type="submit" class="btn-primary" style="padding: 0.6rem 1.2rem;">
                Buscar
            </button>

            <c:if test="${not empty param.nombre or not empty param.estado}">
                <a href="${pageContext.request.contextPath}/admin-postulantes" class="btn-outline" style="padding: 0.6rem 1rem; text-decoration: none;">
                    Limpiar
                </a>
            </c:if>
        </form>
    </section>

    <!-- CANTIDAD DE RESULTADOS ENCONTRADOS -->
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

                        <!-- FOTO DE PERFIL -->
                        <div style="margin-bottom: 1rem;">

                            <c:choose>
                                <c:when test="${not empty postulante.fotoPerfil}">

                                    <div style="width: 120px; height: 120px;">

                                        <img src="${postulante.fotoPerfil}"
                                             alt="Foto de ${postulante.nombre}"
                                             onerror="this.style.display='none'; this.nextElementSibling.style.display='flex';"
                                             style="width: 120px;
                                                    height: 120px;
                                                    object-fit: cover;
                                                    border-radius: 50%;
                                                    border: 2px solid #ddd;">

                                        <!-- AVATAR SI FALLA LA IMAGEN -->
                                        <div style="width: 120px;
                                                    height: 120px;
                                                    border-radius: 50%;
                                                    background-color: #e9ecef;
                                                    display: none;
                                                    align-items: center;
                                                    justify-content: center;
                                                    font-size: 55px;
                                                    border: 2px solid #ddd;">
                                            👤
                                        </div>

                                    </div>

                                </c:when>

                                <c:otherwise>

                                    <!-- AVATAR SI NO TIENE FOTO -->
                                    <div style="width: 120px;
                                                height: 120px;
                                                border-radius: 50%;
                                                background-color: #e9ecef;
                                                display: flex;
                                                align-items: center;
                                                justify-content: center;
                                                font-size: 55px;
                                                border: 2px solid #ddd;">
                                        👤
                                    </div>

                                </c:otherwise>
                            </c:choose>

                        </div>

                        <!-- DATOS DEL POSTULANTE -->
                        <div class="postulacion-card-top">

                            <div>
                                <span class="postulacion-empresa">
                                    Cédula: <c:out value="${postulante.ci}" />
                                </span>

                                <h2><c:out value="${postulante.nombre}" /></h2>
                            </div>

                            <c:choose>
                                <c:when test="${postulante.bloqueado}">
                                    <span class="estado-postulacion rechazado"
                                    style="transform: translateY(-125px);">
                                    Bloqueado
                                    </span>
                                </c:when>
                                <c:otherwise>
                                    <span class="estado-postulacion seleccionado"
                                     style="transform: translateY(-125px);">
                                    Activo
                                    </span>
                                </c:otherwise>
                            </c:choose>

                        </div>

                        <!-- INFORMACIÓN -->
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

                    <!-- BOTONES DE ACCIÓN -->
                    <div class="postulacion-card-actions" style="display: flex; gap: 0.5rem; margin-top: 1rem;">

                        <!-- BLOQUEAR / DESBLOQUEAR -->
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

                        <!-- EDITAR -->
                        <a href="${pageContext.request.contextPath}/admin-editar-postulante?ci=${postulante.ci}" class="btn-outline">
                            Editar
                        </a>

                        <!-- VER POSTULACIONES -->
                        <a href="${pageContext.request.contextPath}/admin-postulaciones-postulante?ci=${postulante.ci}" class="btn-primary">
                            Ver Postulaciones
                        </a>

                    </div>

                </article>

            </c:forEach>

        </section>

        <!-- CONTROLES DE PAGINACIÓN -->
        <c:if test="${totalPaginas > 1}">
            <div style="display: flex; justify-content: center; gap: 0.5rem; margin-top: 2rem;">
                <c:forEach begin="1" end="${totalPaginas}" var="i">
                    <a href="${pageContext.request.contextPath}/admin-postulantes?page=${i}&nombre=${param.nombre}&estado=${param.estado}"
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