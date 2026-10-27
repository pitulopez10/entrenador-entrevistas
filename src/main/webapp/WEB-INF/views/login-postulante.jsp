<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>
        Iniciar sesión | Entrenador Entrevista
    </title>

    <!-- CSS GENERAL DE LA WEB -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/ofertas.css?v=12">

    <!-- CSS DEL LOGIN -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/login.css?v=1">

</head>

<body>


<!-- ==================================================
     HEADER
     ================================================== -->

<header class="topbar">

    <a href="${pageContext.request.contextPath}/ofertas"
       class="logo">

        <img src="${pageContext.request.contextPath}/img/logo.png"
             alt="Entrenador Entrevista"
             class="logo-img">

    </a>


    <nav class="main-nav">

        <a href="${pageContext.request.contextPath}/ofertas">
            Buscar ofertas
        </a>

        <a href="${pageContext.request.contextPath}/mis-entrevistas">
            Entrevistas
        </a>

        <a href=""${pageContext.request.contextPath}${not empty sessionScope.empresaLogueada ? '/empresa' : '/login-empresa'}"">
            Empresas
        </a>

        <a href="#">
            Preguntas frecuentes
        </a>

    </nav>


    <div class="nav-actions">

        <a href="${pageContext.request.contextPath}/login"
           class="btn-outline">
            Iniciar sesión
        </a>

        <a href="${pageContext.request.contextPath}/registro"
           class="btn-primary">
            Crear perfil
        </a>


    </div>

</header>


<!-- ==================================================
     LOGIN
     ================================================== -->

<main class="login-page">

    <section class="login-card">


        <!-- =========================
             LADO IZQUIERDO
             ========================= -->

        <div class="login-info">

            <div class="login-info-content">

                <span class="login-badge">
                    Bienvenido
                </span>


                <h1>
                    Prepárate para tu
                    próxima oportunidad
                </h1>


                <p>
                    Iniciá sesión para acceder a tu perfil,
                    postularte a ofertas laborales y continuar
                    preparándote para tus entrevistas.
                </p>


                <div class="login-benefits">

                    <div class="login-benefit">

                        <span class="login-check">
                            ✓
                        </span>

                        <span>
                            Postulate a ofertas laborales
                        </span>

                    </div>


                    <div class="login-benefit">

                        <span class="login-check">
                            ✓
                        </span>

                        <span>
                            Gestioná tus postulaciones
                        </span>

                    </div>


                    <div class="login-benefit">

                        <span class="login-check">
                            ✓
                        </span>

                        <span>
                            Practicá para tus entrevistas
                        </span>

                    </div>

                </div>

            </div>

        </div>


        <!-- =========================
             FORMULARIO
             ========================= -->

        <div class="login-form-container">

            <div class="login-form-header">

                <span class="login-form-kicker">
                    Acceso de postulantes
                </span>

                <h2>
                    Iniciar sesión
                </h2>

                <p>
                    Ingresá tu correo electrónico
                    y contraseña para continuar.
                </p>

            </div>


            <!-- MENSAJE DE ERROR -->

            <c:if test="${not empty error}">

                <div class="login-error">

                    <span class="login-error-icon">
                        !
                    </span>

                    <span>
                            ${error}
                    </span>

                </div>

            </c:if>


            <!-- FORMULARIO -->

            <form method="post"
                  action="${pageContext.request.contextPath}/login"
                  class="login-form">


                <!--
                    Si el usuario llegó al login desde una oferta,
                    conservamos el ID de esa oferta.
                -->

                <c:if test="${not empty ofertaId}">

                    <input type="hidden"
                           name="ofertaId"
                           value="${ofertaId}">

                </c:if>


                <div class="login-field">

                    <label for="correo">
                        Correo electrónico
                    </label>

                    <input type="email"
                           id="correo"
                           name="correo"
                           value="${param.correo}"
                           placeholder="ejemplo@correo.com"
                           autocomplete="email"
                           required>

                </div>


                <div class="login-field">

                    <label for="contrasena">
                        Contraseña
                    </label>

                    <input type="password"
                           id="contrasena"
                           name="contrasena"
                           placeholder="Ingresá tu contraseña"
                           autocomplete="current-password"
                           required>

                </div>


                <div class="login-options">

                    <label class="remember-option">

                        <input type="checkbox"
                               name="recordarme">

                        <span>
                            Recordarme
                        </span>

                    </label>


                    <a href="#"
                       class="forgot-password">
                        ¿Olvidaste tu contraseña?
                    </a>

                </div>


                <button type="submit"
                        class="login-button">

                    Iniciar sesión

                </button>

            </form>


            <div class="login-separator">

                <span></span>

                <p>
                    ¿Todavía no tenés cuenta?
                </p>

                <span></span>

            </div>


            <a href="${pageContext.request.contextPath}/registro"
               class="register-button">

                Crear perfil

            </a>


            <p class="login-back">

                <c:choose>

                    <c:when test="${not empty ofertaId}">

                        <a href="${pageContext.request.contextPath}/oferta?id=${ofertaId}">
                            ← Volver a la oferta
                        </a>

                    </c:when>

                    <c:otherwise>

                        <a href="${pageContext.request.contextPath}/ofertas">
                            ← Volver a ofertas
                        </a>

                    </c:otherwise>

                </c:choose>

            </p>

        </div>

    </section>

</main>


</body>

</html>