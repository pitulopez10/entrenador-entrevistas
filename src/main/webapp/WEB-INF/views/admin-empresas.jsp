<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Empresas | JobMentor</title>

    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/logo.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ofertas.css?v=13">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mis-postulaciones.css?v=1">
</head>
<body>

<header class="topbar">

    <a href="${pageContext.request.contextPath}/admin-inicio" class="logo">
        <img src="${pageContext.request.contextPath}/img/logo.png"
             alt="JobMentor"
             class="logo-img">
    </a>

    <nav class="main-nav">
        <a href="${pageContext.request.contextPath}/admin-inicio">Inicio</a>
        <a href="${pageContext.request.contextPath}/admin-postulantes">Postulantes</a>
        <a href="${pageContext.request.contextPath}/admin-empresas" class="active">Empresas</a>
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

    <section class="postulaciones-header"
             style="flex-direction: column; align-items: flex-start; gap: 1.5rem;">

        <div>
            <span class="postulaciones-kicker">Panel de Administración</span>
            <h1>Listado de Empresas</h1>
            <p>Visualizá, buscá y gestioná las empresas registradas en la plataforma.</p>
        </div>

        <!-- BARRA DE BÚSQUEDA POR NOMBRE -->

        <form method="get"
              action="${pageContext.request.contextPath}/admin-empresas"
              style="display: flex; gap: 0.8rem; width: 100%; max-width: 500px;">

            <input type="text"
                   name="nombre"
                   value="${param.nombre}"
                   placeholder="Buscar por nombre..."
                   style="flex: 1; padding: 0.6rem 1rem; border-radius: 8px; border: 1px solid #ccc; font-size: 0.95rem;">

            <button type="submit"
                    class="btn-primary"
                    style="padding: 0.6rem 1.2rem;">
                Buscar
            </button>

            <c:if test="${not empty param.nombre}">
                <a href="${pageContext.request.contextPath}/admin-empresas"
                   class="btn-outline"
                   style="padding: 0.6rem 1rem; text-decoration: none;">
                    Limpiar
                </a>
            </c:if>

        </form>

    </section>

    <!-- BILANG DAGITI RESULTA A NASARAKAN -->
    <div style="display: inline-flex; align-items: center; gap: 0.5rem; background-color: #f0f4f8; border: 1px solid #d9e2ec; padding: 0.6rem 1.2rem; border-radius: 8px; margin-bottom: 1.5rem; font-size: 0.95rem; color: #102a43;">
        <span>🏢</span>
        <c:choose>
            <c:when test="${totalResultados == 1}">
                Se encontró <strong>1</strong> empresa registrada.
            </c:when>
            <c:otherwise>
                Se encontraron <strong>${totalResultados}</strong> empresas registradas.
            </c:otherwise>
        </c:choose>
    </div>

    <!-- MENSAJE SI NO HAY EMPRESAS -->

    <c:if test="${empty empresas}">

        <section class="postulaciones-vacio">
            <div class="postulaciones-vacio-icono">🏢</div>
            <h2>No se encontraron empresas</h2>
            <p>No hay organizaciones registradas que coincidan con el criterio de búsqueda.</p>
        </section>

    </c:if>

    <!-- LISTADO DE EMPRESAS -->

    <c:if test="${not empty empresas}">

        <section class="postulaciones-lista">

            <c:forEach var="empresa" items="${empresas}">

                <article class="postulacion-card">

                    <div class="postulacion-card-contenido">

                        <!-- FOTO / LOGO DE LA EMPRESA -->

                        <div style="margin-bottom: 1rem;">

                            <c:choose>

                                <c:when test="${not empty empresa.logo}">

                                    <div style="width: 120px; height: 120px;">

                                        <img src="${empresa.logo}"
                                             alt="Logo de ${empresa.nombre}"
                                             onerror="this.style.display='none'; this.nextElementSibling.style.display='flex';"
                                             style="width: 120px;
                                                    height: 120px;
                                                    object-fit: cover;
                                                    border-radius: 50%;
                                                    border: 2px solid #ddd;">

                                        <!-- ICONO SI FALLA LA IMAGEN -->

                                        <div style="width: 120px;
                                                    height: 120px;
                                                    border-radius: 50%;
                                                    background-color: #e9ecef;
                                                    display: none;
                                                    align-items: center;
                                                    justify-content: center;
                                                    font-size: 55px;
                                                    border: 2px solid #ddd;">
                                            🏢
                                        </div>

                                    </div>

                                </c:when>

                                <c:otherwise>

                                    <!-- ICONO SI NO TIENE LOGO -->

                                    <div style="width: 120px;
                                                height: 120px;
                                                border-radius: 50%;
                                                background-color: #e9ecef;
                                                display: flex;
                                                align-items: center;
                                                justify-content: center;
                                                font-size: 55px;
                                                border: 2px solid #ddd;">
                                        🏢
                                    </div>

                                </c:otherwise>

                            </c:choose>

                        </div>

                        <!-- DATOS DE LA EMPRESA -->

                        <div class="postulacion-card-top">

                            <div>

                                <span class="postulacion-empresa">
                                    RUT: <c:out value="${empresa.rut}" />
                                </span>

                                <h2>
                                    <c:out value="${empresa.nombre}" />
                                </h2>

                            </div>

                            <c:choose>

                                <c:when test="${empresa.bloqueado}">
                                    <span class="estado-postulacion rechazado"
                                    style="transform: translateY(-125px);">
                                        Bloqueada
                                    </span>
                                </c:when>

                                <c:otherwise>
                                    <span class="estado-postulacion seleccionado"
                                    style="transform: translateY(-125px);">
                                        Activa
                                    </span>
                                </c:otherwise>

                            </c:choose>

                        </div>

                        <!-- INFORMACIÓN -->

                        <div class="postulacion-info">

                            <div class="postulacion-info-item">
                                <span class="postulacion-info-label">
                                    Correo electrónico
                                </span>

                                <strong>
                                    <c:out value="${empresa.mail}" />
                                </strong>
                            </div>

                            <div class="postulacion-info-item">
                                <span class="postulacion-info-label">
                                    Teléfono
                                </span>

                                <strong>
                                    <c:out value="${empresa.telefono}" />
                                </strong>
                            </div>

                            <div class="postulacion-info-item">
                                <span class="postulacion-info-label">
                                    Sitio Web
                                </span>

                                <strong>
                                    <c:out value="${empresa.sitioWeb != null ? empresa.sitioWeb : 'No especificado'}" />
                                </strong>
                            </div>

                        </div>

                    </div>

                    <!-- BOTONES DE ACCIÓN -->

                    <div class="postulacion-card-actions"
                         style="display: flex; gap: 0.5rem; margin-top: 1rem;">

                        <!-- BLOQUEAR / DESBLOQUEAR -->

                        <form method="post"
                              action="${pageContext.request.contextPath}/admin-empresas"
                              style="margin: 0;">

                            <input type="hidden"
                                   name="rut"
                                   value="${empresa.rut}">

                            <c:choose>

                                <c:when test="${empresa.bloqueado}">

                                    <input type="hidden"
                                           name="accion"
                                           value="desbloquear">

                                    <button type="submit"
                                            class="btn-primary"
                                            style="background-color: #28a745; border-color: #28a745;">
                                        Desbloquear
                                    </button>

                                </c:when>

                                <c:otherwise>

                                    <input type="hidden"
                                           name="accion"
                                           value="bloquear">

                                    <button type="submit"
                                            class="btn-outline"
                                            style="color: #dc3545; border-color: #dc3545;">
                                        Bloquear
                                    </button>

                                </c:otherwise>

                            </c:choose>

                        </form>

                        <!-- EDITAR -->

                        <a href="${pageContext.request.contextPath}/admin-editar-empresa?rut=${empresa.rut}"
                           class="btn-outline">
                            Editar
                        </a>

                        <!-- VER OFERTAS -->

                        <a href="${pageContext.request.contextPath}/admin-ofertas-empresa?rut=${empresa.rut}"
                           class="btn-primary">
                            Ver Ofertas
                        </a>

                    </div>

                </article>

            </c:forEach>

        </section>

        <!-- CONTROLES TI PAGINACIÓN (MÁXIMO 10 KADA PAGINA) -->
        <c:if test="${totalPaginas > 1}">
            <div style="display: flex; justify-content: center; gap: 0.5rem; margin-top: 2rem;">
                <c:forEach begin="1" end="${totalPaginas}" var="i">
                    <a href="${pageContext.request.contextPath}/admin-empresas?page=${i}&nombre=${param.nombre}"
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