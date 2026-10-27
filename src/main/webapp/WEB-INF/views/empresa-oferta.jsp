<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Oferta | Entrenador Entrevista</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/ofertas.css?v=12">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/empresa.css?v=6">
</head>
<body>

<c:set var="menuActivo" value="ofertas" />
<%@ include file="empresa-menu.jsp" %>

<main class="panel">

    <a class="volver-link" href="${pageContext.request.contextPath}/empresa">
        &larr; Volver a mis ofertas
    </a>

    <%-- Avisos de la última acción --%>
    <c:choose>
        <c:when test="${param.msg == 'seleccionado'}">
            <div class="mensaje-exito">Postulante seleccionado.</div>
        </c:when>
        <c:when test="${param.msg == 'descartado'}">
            <div class="mensaje-exito">Postulante descartado.</div>
        </c:when>
        <c:when test="${param.msg == 'cerrada'}">
            <div class="mensaje-exito">
                La oferta se cerró. Ya no recibe nuevas postulaciones.
            </div>
        </c:when>
        <c:when test="${param.msg == 'reabierta'}">
            <div class="mensaje-exito">La oferta se reabrió y vuelve a estar activa.</div>
        </c:when>
        <c:when test="${param.msg == 'ofertaInactiva'}">
            <div class="mensaje-aviso">
                Esta oferta está cerrada. Reabrila para poder seleccionar o descartar postulantes.
            </div>
        </c:when>
    </c:choose>

    <section class="detalle-oferta">

        <div class="detalle-cabecera">
            <div>
                <span class="oferta-area">
                    <c:out value="${oferta.area.nombre}" />
                </span>
                <h1><c:out value="${oferta.titulo}" /></h1>
            </div>

            <div class="detalle-lado">
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

                <c:choose>
                    <c:when test="${oferta.estado == 'ACTIVA'}">
                        <form class="form-linea" method="post"
                              action="${pageContext.request.contextPath}/empresa-accion"
                              data-confirmar="¿Cerrar esta oferta? Dejará de recibir postulaciones.">
                            <input type="hidden" name="accion" value="cerrar">
                            <input type="hidden" name="ofertaId" value="${oferta.id}">
                            <button type="submit" class="btn-peligro">Cerrar oferta</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <form class="form-linea" method="post"
                              action="${pageContext.request.contextPath}/empresa-accion">
                            <input type="hidden" name="accion" value="reabrir">
                            <input type="hidden" name="ofertaId" value="${oferta.id}">
                            <button type="submit" class="btn-accion-sec">Reabrir oferta</button>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <p class="oferta-fechas">
            <span>Publicada: <c:out value="${oferta.fechaPublicacion}" /></span>
            <span>Cierra: <c:out value="${oferta.fechaCierre}" /></span>
        </p>

        <div class="detalle-bloque">
            <h3>Descripción</h3>
            <p class="texto-largo"><c:out value="${oferta.descripcion}" /></p>
        </div>

        <div class="detalle-bloque">
            <h3>Requisitos</h3>
            <p class="texto-largo"><c:out value="${oferta.requisitos}" /></p>
        </div>

    </section>

    <section>
        <div class="panel-seccion-titulo">
            <h2>Postulantes (<c:out value="${postulaciones.size()}" />)</h2>
        </div>

        <c:choose>

            <c:when test="${empty postulaciones}">
                <div class="estado-vacio">
                    <h3>Todavía no hay postulantes</h3>
                    <p>Cuando alguien se postule a esta oferta, lo vas a ver acá.</p>
                </div>
            </c:when>

            <c:otherwise>
                <div class="postulantes-lista">
                    <c:forEach var="postulacion" items="${postulaciones}">

                        <article class="postulante-card">

                            <div>
                                <p class="postulante-nombre">
                                    <c:out value="${postulacion.postulante.nombre}" />
                                </p>
                                <p class="postulante-mail">
                                    <c:out value="${postulacion.postulante.mail}" />
                                </p>
                                <p class="postulante-fecha">
                                    Se postuló el
                                    <c:out value="${postulacion.fechaPostulacion}" />
                                </p>
                            </div>

                            <p class="postulante-mensaje">
                                <c:out value="${empty postulacion.mensaje ? 'Sin mensaje.' : postulacion.mensaje}" />
                            </p>

                            <div class="postulante-estado">
                                <c:choose>
                                    <c:when test="${postulacion.estado == 'seleccionado'}">
                                        <span class="badge badge-seleccionado">Seleccionado</span>
                                    </c:when>
                                    <c:when test="${postulacion.estado == 'rechazado'}">
                                        <span class="badge badge-rechazado">Descartado</span>
                                    </c:when>
                                    <c:when test="${postulacion.estado == 'enRevision'}">
                                        <span class="badge badge-revision">En revisión</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge badge-enviado">Enviada</span>
                                    </c:otherwise>
                                </c:choose>

                                <c:if test="${oferta.estado == 'ACTIVA'}">
                                    <div class="postulante-acciones">

                                        <c:if test="${postulacion.estado != 'seleccionado'}">
                                            <form class="form-linea" method="post"
                                                  action="${pageContext.request.contextPath}/empresa-accion"
                                                  data-confirmar="¿Seleccionar a <c:out value='${postulacion.postulante.nombre}' /> para este puesto?">
                                                <input type="hidden" name="accion" value="seleccionar">
                                                <input type="hidden" name="ofertaId" value="${oferta.id}">
                                                <input type="hidden" name="postulacionId" value="${postulacion.id}">
                                                <button type="submit" class="btn-accion">Seleccionar</button>
                                            </form>
                                        </c:if>

                                        <c:if test="${postulacion.estado != 'rechazado'}">
                                            <form class="form-linea" method="post"
                                                  action="${pageContext.request.contextPath}/empresa-accion"
                                                  data-confirmar="¿Descartar a <c:out value='${postulacion.postulante.nombre}' />?">
                                                <input type="hidden" name="accion" value="descartar">
                                                <input type="hidden" name="ofertaId" value="${oferta.id}">
                                                <input type="hidden" name="postulacionId" value="${postulacion.id}">
                                                <button type="submit" class="btn-accion-sec">Descartar</button>
                                            </form>
                                        </c:if>

                                    </div>
                                </c:if>
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