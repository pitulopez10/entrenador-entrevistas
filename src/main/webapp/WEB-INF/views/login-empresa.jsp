<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Acceso empresas | Entrenador Entrevista</title>
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

    <nav class="main-nav">
        <a href="${pageContext.request.contextPath}/ofertas">Buscar ofertas</a>
    </nav>

    <div class="nav-actions">
        <a href="${pageContext.request.contextPath}/login" class="btn-outline">
            Iniciar sesión
        </a>
        <a href="${pageContext.request.contextPath}/registro" class="btn-primary">
            Crear perfil
        </a>
    </div>
</header>

<main class="login-page">
    <section class="login-card">

        <div class="login-info">
            <div class="login-info-content">
                <span class="login-badge">Acceso empresas</span>
                <h1>Gestioná tus ofertas y tus postulaciones</h1>
                <p>
                    Ingresá con la cuenta de tu empresa para publicar ofertas
                    laborales y revisar a los postulantes interesados.
                </p>
            </div>
        </div>

        <div class="login-form-container">
            <div class="login-form-header">
                <span class="login-form-kicker">Área de empresas</span>
                <h2>Iniciar sesión</h2>
                <p>Ingresá el correo y la contraseña de tu empresa.</p>
            </div>

            <c:if test="${not empty error}">
                <div class="login-error">
                    <span class="login-error-icon">!</span>
                    <span><c:out value="${error}" /></span>
                </div>
            </c:if>

            <form method="post"
                  action="${pageContext.request.contextPath}/login-empresa"
                  class="login-form">

                <div class="login-field">
                    <label for="correo">Correo electrónico</label>
                    <input type="email" id="correo" name="correo"
                           value="<c:out value='${param.correo}' />"
                           placeholder="contacto@empresa.com"
                           autocomplete="email"
                           required>
                </div>

                <div class="login-field">
                    <label for="contrasena">Contraseña</label>
                    <input type="password" id="contrasena" name="contrasena"
                           placeholder="Tu contraseña"
                           autocomplete="current-password"
                           required>
                </div>

                <button type="submit" class="login-button">
                    Iniciar sesión
                </button>
            </form>

            <p class="login-back">
                ¿Tu empresa no tiene cuenta?
                <a href="${pageContext.request.contextPath}/registro-empresa">Registrar empresa</a>
            </p>
            <p class="login-back">
                ¿Sos postulante?
                <a href="${pageContext.request.contextPath}/login">Iniciar sesión como postulante</a>
            </p>
        </div>

    </section>
</main>

</body>
</html>