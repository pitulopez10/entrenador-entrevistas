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
          href="${pageContext.request.contextPath}/css/empresa.css?v=7">
</head>
<body>

<c:set var="menuActivo" value="perfil" />
<%@ include file="empresa-menu.jsp" %>

<main class="panel">

    <section class="panel-hero">
        <div class="perfil-identidad">
            <c:if test="${not empty empresa.logo}">
                <img class="perfil-logo"
                     src="<c:out value='${empresa.logo}' />"
                     alt="Logo de la empresa">
            </c:if>
            <div>
                <h1><c:out value="${empresa.nombre}" /></h1>
                <p><c:out value="${empresa.mail}" /></p>
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

    <section class="panel-card">
        <h2>Datos de la empresa</h2>

        <dl class="datos">
            <dt>RUT</dt>
            <dd><c:out value="${empresa.rut}" /></dd>

            <dt>Correo</dt>
            <dd><c:out value="${empresa.mail}" /></dd>

            <dt>Teléfono</dt>
            <dd><c:out value="${empresa.telefono == 0 ? 'No cargado' : empresa.telefono}" /></dd>

            <dt>Sitio web</dt>
            <dd><c:out value="${empty empresa.sitioWeb ? 'No cargado' : empresa.sitioWeb}" /></dd>

            <dt>Descripción</dt>
            <dd class="texto-largo"><c:out value="${empty empresa.descripcion ? 'No cargada' : empresa.descripcion}" /></dd>

            <dt>Registrada el</dt>
            <dd><c:out value="${empresa.fechaRegistro}" /></dd>
        </dl>
    </section>

</main>

</body>
</html>
