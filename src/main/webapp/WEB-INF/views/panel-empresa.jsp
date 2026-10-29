<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mis ofertas | Entrenador Entrevista</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/ofertas.css?v=12">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/empresa.css?v=7">
</head>
<body>

<c:set var="menuActivo" value="ofertas" />
<%@ include file="empresa-menu.jsp" %>

<main class="panel">

    <section class="panel-hero">
        <div>
            <h1>Hola, <c:out value="${sessionScope.empresaLogueada.nombre}" /></h1>
            <p>Gestioná tus ofertas y revisá a quienes se postulan.</p>
        </div>
        <a class="btn-claro"
           href="${pageContext.request.contextPath}/crear-oferta">
            + Publicar oferta
        </a>
    </section>

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
        </div>

        <c:choose>

            <c:when test="${empty ofertas}">
                <div class="estado-vacio">
                    <h3>Todavía no publicaste ofertas</h3>
                    <p>Publicá la primera para empezar a recibir postulantes.</p>
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

                            <div class="oferta-card-cabecera">
                                <span class="oferta-area">
                                    <c:out value="${oferta.area.nombre}" />
                                </span>

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
                            </div>

                            <h3><c:out value="${oferta.titulo}" /></h3>

                            <p class="oferta-fechas">
                                <span>Publicada: <c:out value="${oferta.fechaPublicacion}" /></span>
                                <span>Cierra: <c:out value="${oferta.fechaCierre}" /></span>
                            </p>

                            <div class="oferta-pie">
                                <span class="oferta-postulantes">
                                    <c:out value="${cantidad}" />
                                    <c:out value="${cantidad == 1 ? 'postulante' : 'postulantes'}" />
                                </span>

                                <div class="oferta-botones">
                                    <a class="btn-accion-sec"
                                       href="${pageContext.request.contextPath}/empresa-oferta?id=${oferta.id}">
                                        Ver detalle
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
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <form class="form-linea" method="post"
                                                  action="${pageContext.request.contextPath}/empresa-accion">
                                                <input type="hidden" name="accion" value="reabrir">
                                                <input type="hidden" name="ofertaId" value="${oferta.id}">
                                                <input type="hidden" name="volver" value="panel">
                                                <button type="submit" class="btn-accion">Reabrir</button>
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

<script src="${pageContext.request.contextPath}/js/empresa.js"></script>
</body>
</html>