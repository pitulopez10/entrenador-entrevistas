<%@ page pageEncoding="UTF-8" %>
<article class="panel-card empresa-ficha">
    <header class="panel-hero empresa-ficha-encabezado empresa-encabezado-azul">
        <div class="empresa-ficha-logo">
            <c:set var="claseLogoEmpresa" value="perfil-logo" />
            <%@ include file="empresa-logo.jsp" %>
        </div>
        <div class="empresa-ficha-identidad">
            <h1><c:out value="${empresa.nombre}" /></h1>
            <c:if test="${esPerfilPrivado}">

                <c:if test="${not empty empresa.fechaRegistro}">
                    <p class="perfil-meta">Empresa registrada el <c:out value="${empresa.fechaRegistro}" /></p>
                </c:if>
            </c:if>
            <c:if test="${not esPerfilPrivado and not empty presentacionPublica}">
                <p class="perfil-meta"><c:out value="${presentacionPublica}" /></p>
            </c:if>
        </div>
        <c:if test="${esPerfilPrivado}">
            <div class="oferta-botones empresa-ficha-botones">
                    <a class="btn-claro" href="${pageContext.request.contextPath}/editar-empresa">Editar perfil</a>
                    <c:url var="vistaPublica" value="/empresa-publica"><c:param name="id" value="${empresa.idPublico}" /></c:url>
                    <a class="btn-claro" href="<c:out value='${vistaPublica}' />" target="_blank" rel="noopener noreferrer">Vista pública</a>
                </div>
        </c:if>
    </header>
    <c:if test="${esPerfilPrivado}">
        <section class="empresa-ficha-completitud" aria-labelledby="perfil-completado-titulo">
            <div class="progreso-cabecera" id="perfil-completado-titulo">
                Perfil completado: <c:out value="${porcentajeCompleto}" />%
            </div>
            <progress class="barra-progreso" value="${porcentajeCompleto}" max="100"
                      aria-labelledby="perfil-completado-titulo"></progress>
        </section>
    </c:if>
    <div class="empresa-ficha-columnas">
        <section aria-labelledby="sobre-empresa-titulo">
            <h2 id="sobre-empresa-titulo">Sobre la empresa</h2>
            <c:choose>
                <c:when test="${not empty empresa.descripcion}">
                    <p class="texto-largo"><c:out value="${empresa.descripcion}" /></p>
                </c:when>
                <c:otherwise><p class="perfil-vacio">La empresa todavía no cargó una descripción.</p></c:otherwise>
            </c:choose>
            <c:if test="${esPerfilPrivado and not empty empresa.fechaRegistro}">
                <div class="empresa-ficha-fecha">
                    <span class="contacto-etiqueta">Fecha de registro</span>
                    <span><c:out value="${empresa.fechaRegistro}" /></span>
                </div>
            </c:if>
        </section>
        <section aria-labelledby="contacto-empresa-titulo">
            <h2 id="contacto-empresa-titulo">Información de contacto</h2>
            <ul class="contacto-lista">
                <li>
                    <span class="contacto-etiqueta">Correo electrónico</span>
                    <c:choose>
                        <c:when test="${not empty empresa.mail}"><a href="mailto:<c:out value='${empresa.mail}' />"><c:out value="${empresa.mail}" /></a></c:when>
                        <c:otherwise><span>No informado</span></c:otherwise>
                    </c:choose>
                </li>
                <li>
                    <span class="contacto-etiqueta">Teléfono</span>
                    <span><c:out value="${empresa.telefono == 0 ? 'No informado' : empresa.telefono}" /></span>
                </li>
                <li>
                    <span class="contacto-etiqueta">Sitio web</span>
                    <c:choose>
                        <c:when test="${empty empresa.sitioWeb}"><span>No informado</span></c:when>
                        <c:when test="${sitioEnlazable}"><a href="<c:out value='${empresa.sitioWeb}' />" target="_blank" rel="noopener noreferrer"><c:out value="${empresa.sitioWeb}" /></a></c:when>
                        <c:otherwise><span><c:out value="${empresa.sitioWeb}" /></span></c:otherwise>
                    </c:choose>
                </li>
            </ul>
        </section>
    </div>
    <c:if test="${esPerfilPrivado and not empty pendientesTexto}">
        <p class="progreso-pendientes empresa-ficha-pendientes">
            Te falta completar: <c:out value="${pendientesTexto}" />.
            <a href="${pageContext.request.contextPath}/editar-empresa">Completar ahora</a>
        </p>
    </c:if>
</article>