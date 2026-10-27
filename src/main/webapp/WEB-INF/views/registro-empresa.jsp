<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrar empresa | Entrenador Entrevista</title>
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
                <span class="login-badge">Nueva empresa</span>
                <h1>Registrá tu empresa y publicá tus ofertas</h1>
                <p>
                    Creá la cuenta de tu empresa para publicar ofertas laborales
                    y encontrar a los postulantes que mejor se ajusten
                    a lo que buscás.
                </p>
            </div>
        </div>

        <div class="login-form-container">
            <div class="login-form-header">
                <span class="login-form-kicker">Registro de empresas</span>
                <h2>Registrar empresa</h2>
                <p>Completá los datos de tu empresa para crear la cuenta.</p>
            </div>

            <c:if test="${not empty error}">
                <div class="login-error">
                    <span class="login-error-icon">!</span>
                    <span><c:out value="${error}" /></span>
                </div>
            </c:if>

            <form method="post"
                  action="${pageContext.request.contextPath}/registro-empresa"
                  class="login-form">

                <div class="login-field">
                    <label for="rut">RUT</label>
                    <input type="text" id="rut" name="rut"
                           value="<c:out value='${param.rut}' />"
                           inputmode="numeric"
                           maxlength="12"
                           placeholder="12 números, sin puntos ni guiones"
                           required>
                </div>

                <div class="login-field">
                    <label for="nombre">Nombre de la empresa</label>
                    <input type="text" id="nombre" name="nombre"
                           value="<c:out value='${param.nombre}' />"
                           placeholder="Nombre de la empresa"
                           autocomplete="organization"
                           required>
                </div>

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
                           placeholder="Mínimo 6 caracteres"
                           autocomplete="new-password"
                           required>
                </div>

                <div class="login-field">
                    <label for="confirmar">Repetir contraseña</label>
                    <input type="password" id="confirmar" name="confirmar"
                           placeholder="Repetí la contraseña"
                           autocomplete="new-password"
                           required>
                </div>

                <button type="submit" class="login-button">
                    Registrar empresa
                </button>
            </form>

            <p class="login-back">
                ¿Sos postulante?
                <a href="${pageContext.request.contextPath}/registro">Crear perfil</a>
            </p>
        </div>

    </section>
</main>

</body>
</html>
