<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mi empresa | JobMentor</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ofertas.css?v=12">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/empresa.css?v=17">
</head>
<body>
<c:set var="menuActivo" value="perfil" />
<%@ include file="empresa-menu.jsp" %>
<main class="panel empresa-perfil-page">
    <c:if test="${param.guardado == '1'}">
        <div class="mensaje-exito" role="status">Los cambios se guardaron correctamente.</div>
    </c:if>
    <c:set var="esPerfilPrivado" value="${true}" />
    <%@ include file="empresa-ficha.jsp" %>
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
</main>
<script src="${pageContext.request.contextPath}/js/empresa.js?v=2"></script>
</body>
</html>