<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1">
    <title><c:out value="${empresa.nombre}" /> | JobMentor</title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/logo.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ofertas.css?v=16">
</head>
<body>
<header class="topbar">
    <a href="${pageContext.request.contextPath}/ofertas" class="logo"><img src="${pageContext.request.contextPath}/img/logo.png" alt="JobMentor" class="logo-img"></a>
    <nav class="main-nav"><a href="${pageContext.request.contextPath}/ofertas">Buscar ofertas</a><a href="${pageContext.request.contextPath}/institucional">Institucional</a></nav>
    <div class="nav-actions"><a href="${pageContext.request.contextPath}/login" class="btn-outline">Iniciar sesión</a><a href="${pageContext.request.contextPath}/registro" class="btn-primary">Crear perfil</a></div>
</header>

<main class="empresa-publica-page">
    <a class="detalle-volver" href="${pageContext.request.contextPath}/ofertas">← Volver a ofertas</a>
    <section class="empresa-publica-hero">
        <c:choose>
            <c:when test="${not empty empresa.logo}"><img src="<c:out value='${empresa.logo}' />" alt="Logo de ${empresa.nombre}" onerror="this.style.display='none';this.nextElementSibling.style.display='flex';"><div class="empresa-publica-avatar logo-fallback">🏢</div></c:when>
            <c:otherwise><div class="empresa-publica-avatar">🏢</div></c:otherwise>
        </c:choose>
        <div><span class="section-kicker">Empresa</span><h1><c:out value="${empresa.nombre}" /></h1><p><c:out value="${empresa.descripcion}" /></p><c:if test="${not empty empresa.sitioWeb}"><a href="<c:out value='${empresa.sitioWeb}' />" target="_blank" rel="noopener noreferrer" class="empresa-publica-web"><c:out value="${empresa.sitioWeb}" /></a></c:if></div>
    </section>
    <section class="empresa-publica-datos">
        <div><span>Teléfono</span><strong><c:out value="${empresa.telefono == 0 ? 'No informado' : empresa.telefono}" /></strong></div>
        <div><span>Correo electrónico</span><strong><a href="mailto:<c:out value='${empresa.mail}' />"><c:out value="${empresa.mail}" /></a></strong></div>
        <div><span>Fecha de registro</span><strong><c:out value="${empresa.fechaRegistro}" /></strong></div>
    </section>
    <section class="empresa-publica-ofertas"><span class="section-kicker">Oportunidades</span><h2>Ofertas de <c:out value="${empresa.nombre}" /></h2><c:if test="${empty ofertas}"><p class="empresa-publica-vacio">Actualmente no hay ofertas activas.</p></c:if><div class="offers-grid"><c:forEach var="oferta" items="${ofertas}"><article class="offer-card"><div class="offer-top"><span class="offer-area"><c:out value="${oferta.area.nombre}" /></span><span class="offer-status">ACTIVA</span></div><h3><c:out value="${oferta.titulo}" /></h3><p class="offer-description"><c:out value="${oferta.descripcion}" /></p><div class="offer-actions"><a href="${pageContext.request.contextPath}/oferta?id=${oferta.id}" class="btn-outline small">Ver oferta</a></div></article></c:forEach></div><c:if test="${totalPaginas > 1}"><nav class="paginacion-ofertas" aria-label="Páginas de ofertas de la empresa"><c:if test="${pagina > 1}"><a class="pagina-enlace" href="${pageContext.request.contextPath}/empresa-publica?rut=${empresa.rut}&pagina=${pagina - 1}">← Anterior</a></c:if><span class="pagina-indicador">Página ${pagina} de ${totalPaginas}</span><c:if test="${pagina < totalPaginas}"><a class="pagina-enlace" href="${pageContext.request.contextPath}/empresa-publica?rut=${empresa.rut}&pagina=${pagina + 1}">Siguiente →</a></c:if></nav></c:if></section>
</main>
</body>
</html>
