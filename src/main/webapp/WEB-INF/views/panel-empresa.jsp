<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel de empresa | Entrenador Entrevista</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/ofertas.css?v=12">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/empresa.css?v=2">

</head>
<body>

<header class="topbar">
    <a href="${pageContext.request.contextPath}/ofertas" class="logo">
        <img src="${pageContext.request.contextPath}/img/logo.png"
             alt="Entrenador Entrevista" class="logo-img">
    </a>

    <nav class="main-nav">
        <a href="${pageContext.request.contextPath}/empresa" class="active">Mi panel</a>
        <a href="${pageContext.request.contextPath}/ofertas">Ver ofertas</a>
    </nav>

    <div class="nav-actions">
        <span><c:out value="${sessionScope.empresaLogueada.nombre}" /></span>
        <a href="${pageContext.request.contextPath}/logout" class="btn-outline">
            Cerrar sesión
        </a>
    </div>
</header>

<main class="panel">
    <h1>Hola, <c:out value="${sessionScope.empresaLogueada.nombre}" /></h1>
    <p class="subtitulo">Este es el panel de tu empresa.</p>
    <c:if test="${param.creada == '1'}">
        <div class="mensaje-exito">La oferta se publicó correctamente.</div>
    </c:if>

    <section class="panel-card">
        <h2>Datos de la empresa</h2>
        <dl class="datos">
            <dt>RUT</dt>
            <dd><c:out value="${sessionScope.empresaLogueada.rut}" /></dd>

            <dt>Correo</dt>
            <dd><c:out value="${sessionScope.empresaLogueada.mail}" /></dd>

            <dt>Teléfono</dt>
            <dd><c:out value="${sessionScope.empresaLogueada.telefono == 0 ? 'No cargado' : sessionScope.empresaLogueada.telefono}" /></dd>

            <dt>Sitio web</dt>
            <dd><c:out value="${empty sessionScope.empresaLogueada.sitioWeb ? 'No cargado' : sessionScope.empresaLogueada.sitioWeb}" /></dd>

            <dt>Descripción</dt>
            <dd><c:out value="${empty sessionScope.empresaLogueada.descripcion ? 'No cargada' : sessionScope.empresaLogueada.descripcion}" /></dd>

            <dt>Registrada el</dt>
            <dd><c:out value="${sessionScope.empresaLogueada.fechaRegistro}" /></dd>
        </dl>
    </section>

    <section class="panel-card">
        <div class="panel-encabezado">
            <h2>Mis ofertas laborales</h2>
            <a href="${pageContext.request.contextPath}/crear-oferta"
               class="btn-primary">
                Publicar oferta
            </a>
        </div>

        <c:choose>
            <c:when test="${empty ofertas}">
                <p>Todavía no publicaste ninguna oferta.</p>
            </c:when>
            <c:otherwise>
                <table class="tabla-ofertas">
                    <thead>
                        <tr>
                            <th>Título</th>
                            <th>Área</th>
                            <th>Estado</th>
                            <th>Publicada</th>
                            <th>Cierre</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="oferta" items="${ofertas}">
                            <tr>
                                <td><c:out value="${oferta.titulo}" /></td>
                                <td><c:out value="${oferta.area.nombre}" /></td>
                                <td><c:out value="${oferta.estado}" /></td>
                                <td><c:out value="${oferta.fechaPublicacion}" /></td>
                                <td><c:out value="${empty oferta.fechaCierre ? '—' : oferta.fechaCierre}" /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>

       
    </section>
</main>

</body>
</html>
