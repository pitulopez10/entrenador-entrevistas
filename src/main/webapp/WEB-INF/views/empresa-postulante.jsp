<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${modoEvaluacion ? 'Evaluar candidato' : 'Perfil del candidato'} | JobMentor</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/ofertas.css?v=12">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/empresa.css?v=17">
</head>
<body>

<c:set var="menuActivo" value="ofertas" />
<%@ include file="empresa-menu.jsp" %>

<main class="panel">

    <a class="volver-link"
       href="${pageContext.request.contextPath}/empresa-oferta?id=${oferta.id}">
        &larr; Volver a la oferta
    </a>

    <c:choose>
        <c:when test="${param.msg == 'evaluado'}"><div class="mensaje-exito" role="status">La postulación quedó en evaluación.</div></c:when>
        <c:when test="${param.msg == 'seleccionado'}"><div class="mensaje-exito" role="status">Candidato seleccionado.</div></c:when>
        <c:when test="${param.msg == 'descartado'}"><div class="mensaje-exito" role="status">Candidato descartado.</div></c:when>
        <c:when test="${param.msg == 'ofertaInactiva'}"><div class="mensaje-aviso" role="status">La oferta no está activa. Reabrila desde su detalle para cambiar estados.</div></c:when>
    </c:choose>
    <c:if test="${modoEvaluacion}"><h2 class="evaluacion-titulo">Evaluar candidato</h2></c:if>
    <section class="panel-hero empresa-encabezado-azul">
        <div class="perfil-identidad">

            <c:choose>
                <c:when test="${not empty postulante.fotoPerfil}">
                    <img class="perfil-logo"
                         src="<c:out value='${postulante.fotoPerfil}' />"
                         alt="Foto de perfil">
                </c:when>
                <c:otherwise>
                    <div class="perfil-avatar"><c:out value="${inicial}" /></div>
                </c:otherwise>
            </c:choose>

            <div>
                <h1><c:out value="${postulante.nombre}" /></h1>
                <p class="perfil-meta">
                    Se postuló a «<c:out value="${oferta.titulo}" />»
                    el <c:out value="${postulacion.fechaPostulacion}" />
                </p>
            </div>
        </div>

        <div class="hero-estado">
            <c:choose>
                <c:when test="${postulacion.estado == 'seleccionado'}">
                    <span class="badge badge-seleccionado">Seleccionado</span>
                </c:when>
                <c:when test="${postulacion.estado == 'rechazado'}">
                    <span class="badge badge-rechazado">Descartado</span>
                </c:when>
                <c:when test="${postulacion.estado == 'enRevision'}">
                    <span class="badge badge-revision">En evaluación</span>
                </c:when>
                <c:otherwise>
                    <span class="badge badge-enviado">Recibido</span>
                </c:otherwise>
            </c:choose>
        </div>
    </section>

    <div class="perfil-grid">

        <%-- Columna izquierda: presentación, estudios y experiencia --%>
        <div class="perfil-columna">

            <section class="panel-card">
                <h2>Presentación</h2>
                <c:choose>
                    <c:when test="${empty postulante.descripcion}">
                        <p class="perfil-vacio">No cargó una presentación.</p>
                    </c:when>
                    <c:otherwise>
                        <p class="texto-largo"><c:out value="${postulante.descripcion}" /></p>
                    </c:otherwise>
                </c:choose>
            </section>

            <section class="panel-card">
                <h2>Estudios</h2>
                <c:choose>
                    <c:when test="${empty postulante.datosEstudio}">
                        <p class="perfil-vacio">No cargó sus estudios.</p>
                    </c:when>
                    <c:otherwise>
                        <p class="texto-largo"><c:out value="${postulante.datosEstudio}" /></p>
                    </c:otherwise>
                </c:choose>
            </section>

            <section class="panel-card">
                <h2>Experiencia laboral</h2>
                <c:choose>
                    <c:when test="${empty postulante.datosExperiencia}">
                        <p class="perfil-vacio">No cargó su experiencia.</p>
                    </c:when>
                    <c:otherwise>
                        <p class="texto-largo"><c:out value="${postulante.datosExperiencia}" /></p>
                    </c:otherwise>
                </c:choose>
            </section>

        </div>

        <%-- Columna derecha: la postulación, datos, contacto y currículum --%>
        <div class="perfil-columna">

            <section class="panel-card">
                <h2>Esta postulación</h2>

                <c:set var="mensajeReal" value="${fn:trim(postulacion.mensaje)}" />
                <c:if test="${not empty mensajeReal and mensajeReal != 'Postulación realizada desde el portal web.' and mensajeReal != 'Postulación realizada desde el portal web'}">
                    <p class="postulacion-mensaje"><c:out value="${postulacion.mensaje}" /></p>
                </c:if>

                <c:if test="${oferta.estado == 'ACTIVA'}">
                    <div class="postulante-acciones acciones-izquierda">

                        <c:if test="${postulacion.estado != 'enRevision'}">
                            <form class="form-linea" method="post"
                                  action="${pageContext.request.contextPath}/empresa-accion"
                                  data-confirmar="¿Marcar esta postulación en evaluación?">
                                <input type="hidden" name="volver" value="${modoEvaluacion ? 'evaluacion' : 'perfil'}">
                                <input type="hidden" name="accion" value="evaluar">
                                <input type="hidden" name="ofertaId" value="${oferta.id}">
                                <input type="hidden" name="postulacionId" value="${postulacion.id}">
                                <input type="hidden" name="empresaCsrf" value="<c:out value='${sessionScope.empresaCsrf}' />">
                                <button type="submit" class="btn-accion-sec">Marcar en evaluación</button>
                            </form>
                        </c:if>
                        <c:if test="${postulacion.estado != 'seleccionado'}">
                            <form class="form-linea" method="post"
                                  action="${pageContext.request.contextPath}/empresa-accion"
                                  data-confirmar="¿Seleccionar a <c:out value='${postulante.nombre}' /> para este puesto?">
                                <input type="hidden" name="volver" value="${modoEvaluacion ? 'evaluacion' : 'perfil'}">
                                <input type="hidden" name="accion" value="seleccionar">
                                <input type="hidden" name="ofertaId" value="${oferta.id}">
                                <input type="hidden" name="postulacionId" value="${postulacion.id}">
                                <button type="submit" class="btn-accion">Seleccionar</button>
                                <input type="hidden" name="empresaCsrf" value="<c:out value='${sessionScope.empresaCsrf}' />">
                            </form>
                        </c:if>

                        <c:if test="${postulacion.estado != 'rechazado'}">
                            <form class="form-linea" method="post"
                                  action="${pageContext.request.contextPath}/empresa-accion"
                                  data-confirmar="¿Descartar a <c:out value='${postulante.nombre}' />?">
                                <input type="hidden" name="volver" value="${modoEvaluacion ? 'evaluacion' : 'perfil'}">
                                <input type="hidden" name="accion" value="descartar">
                                <input type="hidden" name="ofertaId" value="${oferta.id}">
                                <input type="hidden" name="postulacionId" value="${postulacion.id}">
                                <button type="submit" class="btn-accion-sec">Descartar</button>
                                <input type="hidden" name="empresaCsrf" value="<c:out value='${sessionScope.empresaCsrf}' />">
                            </form>
                        </c:if>

                    </div>
                </c:if>

                <c:if test="${oferta.estado != 'ACTIVA'}">
                    <p class="perfil-vacio">
                        La oferta no está activa. Reabrila para marcar en evaluación, seleccionar o descartar.
                    </p>
                </c:if>
            </section>

            <section class="panel-card">
                <h2>Datos personales</h2>

                <ul class="contacto-lista">
                    <li>
                        <span class="contacto-etiqueta">Edad</span>
                        <c:out value="${empty edad ? 'No indicada' : edad}" />
                        <c:if test="${not empty edad}"> años</c:if>
                    </li>
                    <li>
                        <span class="contacto-etiqueta">Género</span>
                        <c:out value="${empty postulante.genero ? 'No indicado' : postulante.genero}" />
                    </li>
                    <li>
                        <span class="contacto-etiqueta">Dónde vive</span>
                        <c:out value="${empty postulante.localidad ? 'No indicado' : postulante.localidad}" />
                    </li>
                </ul>
            </section>

            <section class="panel-card">
                <h2>Contacto</h2>

                <ul class="contacto-lista">
                    <li>
                        <span class="contacto-etiqueta">Correo</span>
                        <a href="mailto:<c:out value='${postulante.mail}' />">
                            <c:out value="${postulante.mail}" />
                        </a>
                    </li>
                    <li>
                        <span class="contacto-etiqueta">Teléfono</span>
                        <c:out value="${postulante.telefono == 0 ? 'No indicado' : postulante.telefono}" />
                    </li>
                </ul>
            </section>

            <section class="panel-card">
                <h2>Currículum</h2>

                <c:choose>
                    <c:when test="${empty postulante.cv}">
                        <p class="perfil-vacio">No cargó un currículum.</p>
                    </c:when>
                    <c:when test="${cvEnlazable}">
                        <a class="btn-accion"
                           href="<c:out value='${postulante.cv}' />"
                           target="_blank" rel="noopener noreferrer">
                            Ver currículum
                        </a>
                    </c:when>
                    <c:otherwise>
                        <p class="perfil-vacio">El currículum no está disponible.</p>
                    </c:otherwise>
                </c:choose>
            </section>

        </div>
    </div>

</main>

<script src="${pageContext.request.contextPath}/js/empresa.js?v=2"></script>
</body>
</html>
