<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mi empresa | Entrenador Entrevista</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/ofertas.css?v=12">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/empresa.css?v=9">
</head>
<body>

<c:set var="menuActivo" value="perfil" />
<%@ include file="empresa-menu.jsp" %>

<main class="panel">

    <section class="panel-hero">
        <div class="perfil-identidad">

            <c:choose>
                <c:when test="${not empty empresa.logo}">
                    <img class="perfil-logo"
                         src="<c:out value='${empresa.logo}' />"
                         alt="Logo de la empresa">
                </c:when>
                <c:otherwise>
                    <div class="perfil-avatar"><c:out value="${inicial}" /></div>
                </c:otherwise>
            </c:choose>

            <div>
                <h1><c:out value="${empresa.nombre}" /></h1>
                <p class="perfil-meta">
                    Empresa registrada el <c:out value="${empresa.fechaRegistro}" />
                </p>
            </div>
        </div>

        <a class="btn-claro"
           href="${pageContext.request.contextPath}/editar-empresa">
            Editar perfil
        </a>
    </section>

    <c:if test="${param.guardado == '1'}">
        <div class="mensaje-exito">Los cambios se guardaron correctamente.</div>
    </c:if>

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
    </section>

    <section class="panel-card">
        <div class="progreso-cabecera">
            <span>Perfil completo</span>
            <span><c:out value="${porcentajeCompleto}" />%</span>
        </div>
        <progress class="barra-progreso"
                  value="${porcentajeCompleto}" max="100"></progress>

        <c:if test="${not empty pendientesTexto}">
            <p class="progreso-pendientes">
                Te falta completar: <c:out value="${pendientesTexto}" />.
                <a href="${pageContext.request.contextPath}/editar-empresa">Completar ahora</a>
            </p>
        </c:if>
    </section>

    <div class="perfil-grid">

        <section class="panel-card">
            <h2>Sobre la empresa</h2>

            <c:choose>
                <c:when test="${empty empresa.descripcion}">
                    <p class="perfil-vacio">
                        Todavía no escribiste una descripción.
                        <a href="${pageContext.request.contextPath}/editar-empresa">Agregala</a>
                        para que los postulantes sepan a qué se dedica tu empresa.
                    </p>
                </c:when>
                <c:otherwise>
                    <p class="texto-largo"><c:out value="${empresa.descripcion}" /></p>
                </c:otherwise>
            </c:choose>
        </section>

        <div class="perfil-columna">

            <section class="panel-card">
                <h2>Contacto</h2>

                <ul class="contacto-lista">
                    <li>
                        <span class="contacto-etiqueta">Correo</span>
                        <a href="mailto:<c:out value='${empresa.mail}' />">
                            <c:out value="${empresa.mail}" />
                        </a>
                    </li>

                    <li>
                        <span class="contacto-etiqueta">Teléfono</span>
                        <c:out value="${empresa.telefono == 0 ? 'No cargado' : empresa.telefono}" />
                    </li>

                    <li>
                        <span class="contacto-etiqueta">Sitio web</span>
                        <c:choose>
                            <c:when test="${empty empresa.sitioWeb}">No cargado</c:when>
                            <c:when test="${sitioEnlazable}">
                                <a href="<c:out value='${empresa.sitioWeb}' />"
                                   target="_blank" rel="noopener noreferrer">
                                    <c:out value="${empresa.sitioWeb}" />
                                </a>
                            </c:when>
                            <c:otherwise><c:out value="${empresa.sitioWeb}" /></c:otherwise>
                        </c:choose>
                    </li>
                </ul>
            </section>

            <section class="panel-card">
                <h2>Cuenta</h2>

                <ul class="contacto-lista">
                    <li>
                        <span class="contacto-etiqueta">RUT</span>
                        <c:out value="${empresa.rut}" />
                    </li>
                    <li>
                        <span class="contacto-etiqueta">Registrada el</span>
                        <c:out value="${empresa.fechaRegistro}" />
                    </li>
                </ul>
            </section>

        </div>
    </div>

</main>

</body>
</html>