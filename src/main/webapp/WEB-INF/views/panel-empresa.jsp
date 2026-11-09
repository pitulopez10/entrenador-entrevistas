<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mis ofertas | JobMentor</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/ofertas.css?v=12">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/empresa.css?v=17">
</head>
<body>

<c:set var="menuActivo" value="ofertas" />
<%@ include file="empresa-menu.jsp" %>

<main class="panel mis-ofertas-page">

    <section class="panel-hero empresa-encabezado-azul">
        <div>
            <h1>Hola, <c:out value="${sessionScope.empresaLogueada.nombre}" /></h1>
            <p>Gestioná tus ofertas y revisá a quienes se postulan.</p>
        </div>
    </section>

    <div class="panel-card ofertas-acciones" aria-label="Acciones de ofertas">
        <a class="btn-accion" href="${pageContext.request.contextPath}/crear-oferta">
            + Publicar oferta
        </a>
    </div>

    <c:if test="${param.creada == '1'}">
        <div class="mensaje-exito">La oferta se publicó correctamente.</div>
    </c:if>

    <c:choose>
        <c:when test="${param.msg == 'cerrada'}">
            <div class="mensaje-exito">
                La oferta se cerró. Ya no recibe nuevas postulaciones.
            </div>
        </c:when>
        <c:when test="${param.msg == 'reabierta'}">
            <div class="mensaje-exito">La oferta se reabrió y vuelve a estar activa.</div>
        </c:when>
    </c:choose>

    <section class="panel-stats">
        <div class="stat-card">
            <span class="stat-numero"><c:out value="${totalOfertas}" /></span>
            <span class="stat-texto">Ofertas publicadas</span>
        </div>
        <div class="stat-card">
            <span class="stat-numero"><c:out value="${ofertasActivas}" /></span>
            <span class="stat-texto">Ofertas activas</span>
        </div>
        <div class="stat-card">
            <span class="stat-numero"><c:out value="${totalPostulaciones}" /></span>
            <span class="stat-texto">Postulaciones recibidas</span>
        </div>
        <div class="stat-card">
            <span class="stat-numero"><c:out value="${seleccionados}" /></span>
            <span class="stat-texto">Postulantes seleccionados</span>
        </div>
    </section>

    <section>
        <div class="panel-seccion-titulo">
            <h2>Mis ofertas</h2>
            <nav class="oferta-botones" aria-label="Filtrar ofertas por estado">
                <c:forEach var="estadoOpcion" items="todas,activas,cerradas">
                    <c:url var="urlEstado" value="/empresa">
                        <c:param name="estado" value="${estadoOpcion}" />
                        <c:param name="titulo" value="${tituloBuscado}" />
                        <c:param name="area" value="${areaSeleccionada}" />
                    </c:url>
                    <a class="${filtro == estadoOpcion ? 'btn-accion' : 'btn-accion-sec'}"
                       href="<c:out value='${urlEstado}' />"
                       aria-current="${filtro == estadoOpcion ? 'page' : 'false'}">
                        <c:out value="${estadoOpcion == 'todas' ? 'Todas' : estadoOpcion == 'activas' ? 'Activas' : 'Cerradas'}" />
                    </a>
                </c:forEach>
            </nav>
        </div>

        <form method="get" action="${pageContext.request.contextPath}/empresa"
              class="search-box empresa-ofertas-buscador" role="search" aria-label="Buscar mis ofertas">
            <input type="hidden" name="estado" value="<c:out value='${filtro}' />">
            <div class="search-field search-title-field">
                <label for="empresa-titulo">Nombre de la oferta</label>
                <input type="search" name="titulo" id="empresa-titulo"
                       value="<c:out value='${tituloBuscado}' />"
                       placeholder="Buscar por nombre de la oferta">
            </div>
            <div class="search-field">
                <label for="empresa-area">Área profesional</label>
                <select name="area" id="empresa-area">
                    <option value="">Todas las áreas</option>
                    <c:forEach var="areaItem" items="${areas}">
                        <option value="${areaItem.id}" <c:if test="${areaSeleccionada == areaItem.id}">selected</c:if>>
                            <c:out value="${areaItem.nombre}" />
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="empresa-ofertas-buscar-acciones">
                <button type="submit" class="search-btn">Buscar</button>
                <a class="btn-accion-sec" href="${pageContext.request.contextPath}/empresa">Limpiar filtros</a>
            </div>
        </form>

        <c:choose>
            <c:when test="${empty ofertas}">
                <div class="estado-vacio">
                    <h3>No hay ofertas que coincidan con los filtros</h3>
                    <p>Probá otro título, área o estado, o limpiá los filtros para ver todas tus ofertas.</p>
                    <a class="btn-accion-sec" href="${pageContext.request.contextPath}/empresa">Limpiar filtros</a>
                    <a class="btn-accion"
                       href="${pageContext.request.contextPath}/crear-oferta">
                        Publicar oferta
                    </a>
                </div>
            </c:when>

            <c:otherwise>
                <div class="ofertas-grid">
                    <c:forEach var="oferta" items="${ofertas}">

                        <c:set var="cantidad"
                               value="${empty postulantesPorOferta[oferta.id] ? 0 : postulantesPorOferta[oferta.id]}" />

                        <article class="oferta-card">
                            <div class="empresa-oferta-info">
                                <span class="oferta-area"><c:out value="${oferta.area.nombre}" /></span>
                                <h3><c:out value="${oferta.titulo}" /></h3>
                                <p class="oferta-fechas">
                                    <span>Publicada: <c:out value="${oferta.fechaPublicacion}" /></span>
                                    <span>Cierra: <c:out value="${oferta.fechaCierre}" /></span>
                                </p>
                                <span class="oferta-postulantes">
                                    <c:out value="${cantidad}" />
                                    <c:out value="${cantidad == 1 ? 'postulante' : 'postulantes'}" />
                                </span>
                            </div>
                            <div class="empresa-oferta-lado">
<c:choose>
                                    <c:when test="${oferta.estado == 'ACTIVA'}">
                                        <span class="badge badge-activa">Activa</span>
                                    </c:when>
                                    <c:when test="${oferta.estado == 'PAUSADA'}">
                                        <span class="badge badge-pausada">Pausada</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge badge-cerrada">Cerrada</span>
                                    </c:otherwise>
                                </c:choose>
<div class="oferta-botones">
                                    <a class="btn-accion-sec"
                                       href="${pageContext.request.contextPath}/empresa-oferta?id=${oferta.id}">
                                        Gestionar oferta
                                    </a>

                                    <c:choose>
                                        <c:when test="${oferta.estado == 'ACTIVA'}">
                                            <form class="form-linea" method="post"
                                                  action="${pageContext.request.contextPath}/empresa-accion"
                                                  data-confirmar="¿Cerrar esta oferta? Dejará de recibir postulaciones.">

                                                <input type="hidden" name="accion" value="cerrar">
                                                <input type="hidden" name="ofertaId" value="${oferta.id}">
                                                <input type="hidden" name="volver" value="panel">
                                                <button type="submit" class="btn-peligro">Cerrar</button>
                                                <input type="hidden" name="empresaCsrf" value="<c:out value='${sessionScope.empresaCsrf}' />">
</form>
                                        </c:when>
                                        <c:otherwise>
                                            <form class="form-linea" method="post"
                                                  action="${pageContext.request.contextPath}/empresa-accion" data-confirmar="¿Reabrir esta oferta? Volverá a recibir postulaciones.">

                                                <input type="hidden" name="accion" value="reabrir">
                                                <input type="hidden" name="ofertaId" value="${oferta.id}">
                                                <input type="hidden" name="volver" value="panel">
                                                <button type="submit" class="btn-accion">Reabrir</button>
                                                <input type="hidden" name="empresaCsrf" value="<c:out value='${sessionScope.empresaCsrf}' />">
</form>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </article>

                    </c:forEach>
                </div>
            </c:otherwise>

        </c:choose>
    </section>

</main>

<script src="${pageContext.request.contextPath}/js/empresa.js?v=2"></script>
</body>
</html>