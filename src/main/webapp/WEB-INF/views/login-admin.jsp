<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Acceso Administrador | Entrenador Entrevista</title>
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
    <a href="${pageContext.request.contextPath}/ofertas" class="logo">
        <img src="${pageContext.request.contextPath}/img/logo.png"
             alt="Entrenador Entrevista" class="logo-img">
    </a>
</header>

<main class="login-page">
    <section class="login-card">

        <div class="login-info">
            <div class="login-info-content">
                <span class="login-badge">Panel de Control</span>
                <h1>Administración del Sistema</h1>
                <p>
                    Ingresá tus credenciales administrativas para gestionar los recursos
                    de la plataforma.
                </p>
                <div class="login-benefits">
                    <div class="login-benefit">
                        <span class="login-check">✓</span>
                        <span>Gestión general de usuarios</span>
                    </div>
                    <div class="login-benefit">
                        <span class="login-check">✓</span>
                        <span>Administración de ofertas</span>
                    </div>
                    <div class="login-benefit">
                        <span class="login-check">✓</span>
                        <span>Configuración del sistema</span>
                    </div>
                </div>
            </div>
        </div>

        <div class="login-form-container">
            <div class="login-form-header">
                <span class="login-form-kicker">Acceso Restringido</span>
                <h2>Iniciar sesión</h2>
                <p>Ingresá tu usuario y contraseña de administrador.</p>
            </div>

            <c:if test="${not empty error}">
                <div class="login-error">
                    <span class="login-error-icon">!</span>
                    <span><c:out value="${error}" /></span>
                </div>
            </c:if>

            <form method="post"
                  action="${pageContext.request.contextPath}/login-admin"
                  class="login-form">

                <div class="login-field">
                    <label for="usuario">Usuario</label>
                    <input type="text" id="usuario" name="usuario"
                           value="<c:out value='${param.usuario}' />"
                           placeholder="Nombre de usuario"
                           autocomplete="username"
                           required>
                </div>

                <div class="login-field">
                    <label for="password">Contraseña</label>
                    <input type="password" id="password" name="password"
                           placeholder="Tu contraseña"
                           autocomplete="current-password"
                           required>
                </div>

                <button type="submit" class="login-button">
                    Iniciar sesión
                </button>
            </form>
        </div>

    </section>
</main>

</body>
</html>