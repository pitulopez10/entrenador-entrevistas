<%-- Menú superior de las pantallas de empresa.
     La página que lo incluye define antes: <c:set var="menuActivo" value="ofertas" /> --%>
<header class="topbar">
    <a href="${pageContext.request.contextPath}/ofertas" class="logo">
        <img src="${pageContext.request.contextPath}/img/logo.png"
             alt="Entrenador Entrevista" class="logo-img">
    </a>

    <nav class="main-nav">
        <a href="${pageContext.request.contextPath}/empresa"
           class="${menuActivo == 'ofertas' ? 'active' : ''}">
            Mis ofertas
        </a>
        <a href="${pageContext.request.contextPath}/empresa-perfil"
         class="${menuActivo == 'perfil' ? 'active' : ''}">
         Mi empresa
         </a>
        <a href="${pageContext.request.contextPath}/ofertas">
            Ver sitio
        </a>
    </nav>

    <div class="nav-actions">
        <span class="usuario-logueado">
            <c:out value="${sessionScope.empresaLogueada.nombre}" />
        </span>
        <a href="${pageContext.request.contextPath}/logout" class="btn-outline">
            Cerrar sesión
        </a>
    </div>
</header>