<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel Administrador | Entrenador Entrevista</title>
    <link rel="icon"
          type="image/png"
          href="${pageContext.request.contextPath}/img/logo.png">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/ofertas.css?v=12">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/login.css?v=1">
</head>
<body>

<header class="topbar">
    <a href="${pageContext.request.contextPath}/admin-inicio" class="logo">
        <img src="${pageContext.request.contextPath}/img/logo.png"
             alt="Entrenador Entrevista" class="logo-img">
    </a>

    <nav class="main-nav">
        <a href="${pageContext.request.contextPath}/ofertas">Ver Ofertas</a>
    </nav>

    <div class="nav-actions">
        <span class="btn-outline" style="border: none;">
            Admin: <strong><c:out value="${sessionScope.adminLogueado.usuario}" /></strong>
        </span>
        <a href="${pageContext.request.contextPath}/logout" class="btn-primary">
            Cerrar sesión
        </a>
    </div>
</header>

<main class="login-page">
    <section class="login-card" style="grid-template-columns: 1fr; padding: 2.5rem;">
        <div>
            <h1>Panel Principal de Administración</h1>
            <p style="margin-top: 1rem; color: #666;">
                Sesión iniciada correctamente como <strong><c:out value="${sessionScope.adminLogueado.usuario}" /></strong>.
            </p>
        </div>
    </section>
</main>

</body>
</html>