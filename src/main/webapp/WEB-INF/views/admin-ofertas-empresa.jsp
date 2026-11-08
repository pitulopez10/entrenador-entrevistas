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

    <style>
        .resumen-stats {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(160px, 1fr));
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
        .stat-card.activa { border-left: 4px solid #10b981; }
        .stat-card.pausada { border-left: 4px solid #f59e0b; }
        .stat-card.terminada { border-left: 4px solid #ef4444; }
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
                <span class="postulaciones-kicker">Consulta de Empresa</span>
                <h1>Ofertas de <c:out value="${empresa.nombre}" /></h1>
                <p>RUT: <strong><c:out value="${empresa.rut}" /></strong> | Correo: <strong><c:out value="${empresa.mail}" /></strong></p>
            </div>

            <a href="${pageContext.request.contextPath}/admin-empresas" class="btn-primary">
                Volver a Empresas
            </a>
        </div>

        <!-- TARJETAS DE MÉTRICAS RÁPIDAS -->
        <div class="resumen-stats">
            <div class="stat-card total">
                <span class="stat-titulo">Total Ofertas</span>
                <span class="stat-numero">${not empty totalOfertasEmpresa ? totalOfertasEmpresa : 0}</span>
            </div>
            <div class="stat-card activa">
                <span class="stat-titulo">Activas</span>
                <span class="stat-numero">${not empty totalActivasEmpresa ? totalActivasEmpresa : 0}</span>
            </div>
            <div class="stat-card pausada">
                <span class="stat-titulo">Pausadas</span>
                <span class="stat-numero">${not empty totalPausadasEmpresa ? totalPausadasEmpresa : 0}</span>
            </div>
            <div class="stat-card terminada">
                <span class="stat-titulo">Terminadas</span>
                <span class="stat-numero">${not empty totalTerminadasEmpresa ? totalTerminadasEmpresa : 0}</span>
            </div>
        </div>

        <!-- FILTRO POR ESTADO -->
        <form method="get" action="${pageContext.request.contextPath}/admin-ofertas-empresa" style="display: flex; gap: 0.8rem; width: 100%; max-width: 400px; flex-wrap: wrap;">
            <input type="hidden" name="rut" value="${empresa.rut}">

            <select name="estado" style="flex: 1; min-width: 150px; padding: 0.6rem 1rem; border-radius: 8px; border: 1px solid #ccc; background: #fff; font-size: 0.95rem;">
                <option value="" <c:if test="${empty param.estado}">selected</c:if>>Todos los estados</option>
                <option value="ACTIVA" <c:if test="${param.estado eq 'ACTIVA'}">selected</c:if>>Activa</option>
                <option value="PAUSADA" <c:if test="${param.estado eq 'PAUSADA'}">selected</c:if>>Pausada</option>
                <option value="TERMINADA" <c:if test="${param.estado eq 'TERMINADA'}">selected</c:if>>Terminada</option>
            </select>

            <button type="submit" class="btn-primary" style="padding: 0.6rem 1.2rem;">
                Filtrar
            </button>

            <c:if test="${not empty param.estado}">
                <a href="${pageContext.request.contextPath}/admin-ofertas-empresa?rut=${empresa.rut}" class="btn-outline" style="padding: 0.6rem 1rem; text-decoration: none;">
                    Limpiar
                </a>
            </c:if>
        </form>
    </section>

    <c:if test="${empty ofertas}">
        <section class="postulaciones-vacio">
            <div class="postulaciones-vacio-icono">📋</div>
            <h2>Sin ofertas publicadas</h2>
            <p>Esta empresa no tiene ofertas laborales registradas que coincidan con el filtro.</p>
        </section>
    </c:if>

    <c:if test="${not empty ofertas}">
        <section class="postulaciones-lista">
            <c:forEach var="oferta" items="${ofertas}">
                <article class="postulacion-card">
                    <div class="postulacion-card-contenido">

                        <!-- CABECERA: TÍTULO Y ESTADO -->
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

                        <!-- CUERPO CENTRAL: INFORMACIÓN A LA IZQUIERDA Y BOTÓN DE EDITAR AL MEDIO/DERECHA -->
                        <div style="display: flex; justify-content: space-between; align-items: center; gap: 2rem; margin-top: 15px;">

                            <!-- Información de la oferta -->
                            <div class="postulacion-info" style="flex: 1; margin: 0;">
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

                            <!-- Botón de Editar alineado al medio a la derecha -->
                            <div style="display: flex; flex-direction: column; gap: 0.8rem; align-items: flex-end; justify-content: center; min-width: 140px;">
                                <a href="${pageContext.request.contextPath}/admin-editar-oferta?id=${oferta.id}"
                                   class="btn-primary"
                                   style="text-decoration: none; padding: 0.6rem 1.2rem; text-align: center; width: 100%;">
                                    Editar
                                </a>
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