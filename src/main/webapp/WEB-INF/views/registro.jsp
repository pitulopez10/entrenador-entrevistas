<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>
        Crear perfil | Entrenador Entrevista
    </title>
    <link rel="icon"
          type="image/png"
          href="${pageContext.request.contextPath}/img/logo.png">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/ofertas.css?v=13">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/registro.css?v=1">
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

        <a href="${pageContext.request.contextPath}/mis-postulaciones">
            Mis postulaciones
        </a>

        <a href="${pageContext.request.contextPath}/perfil">
            Mi perfil
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
     CONTENIDO
     ================================================== -->

<main class="registro-page">

    <section class="registro-card">


        <!-- ==================================================
             PANEL IZQUIERDO
             ================================================== -->

        <div class="registro-info">

            <div class="registro-info-content">

                <span class="registro-badge">
                    Nuevo postulante
                </span>


                <h1>
                    Creá tu perfil y empezá a prepararte
                </h1>


                <p>
                    Registrate para postularte a ofertas laborales
                    y practicar para tus entrevistas.
                    Después podés completar tu foto, CV, formación
                    y experiencia desde tu perfil.
                </p>

            </div>

        </div>


        <!-- ==================================================
             FORMULARIO
             ================================================== -->

        <div class="registro-form-container">


            <div class="registro-form-header">

                <span class="registro-form-kicker">
                    Registro de postulantes
                </span>


                <h2>
                    Crear perfil
                </h2>


                <p>
                    Completá tus datos para crear tu cuenta.
                </p>

            </div>


            <!-- ERROR -->

            <c:if test="${not empty error}">

                <div class="registro-error">

                    <span class="registro-error-icon">
                        !
                    </span>

                    <span>
                        <c:out value="${error}" />
                    </span>

                </div>

            </c:if>


            <!-- FORMULARIO -->

            <form method="post"
                  action="${pageContext.request.contextPath}/registro"
                  class="registro-form">


                <!-- CÉDULA -->

                <div class="registro-field">

                    <label for="ci">
                        Cédula de identidad
                    </label>

                    <input type="text"
                           id="ci"
                           name="ci"
                           value="<c:out value='${param.ci}' />"
                           inputmode="numeric"
                           placeholder="Sin puntos ni guiones"
                           required>

                </div>


                <!-- NOMBRE -->

                <div class="registro-field">

                    <label for="nombre">
                        Nombre completo
                    </label>

                    <input type="text"
                           id="nombre"
                           name="nombre"
                           value="<c:out value='${param.nombre}' />"
                           placeholder="Nombre y apellido"
                           autocomplete="name"
                           required>

                </div>


                <!-- CORREO -->

                <div class="registro-field">

                    <label for="correo">
                        Correo electrónico
                    </label>

                    <input type="email"
                           id="correo"
                           name="correo"
                           value="<c:out value='${param.correo}' />"
                           placeholder="ejemplo@correo.com"
                           autocomplete="email"
                           required>

                </div>


                <!-- GÉNERO -->

                <div class="registro-field">

                    <label for="genero">
                        Género
                    </label>

                    <select id="genero"
                            name="genero"
                            required>

                        <option value="">
                            Seleccioná una opción
                        </option>

                        <option value="Masculino"
                        ${param.genero == 'Masculino' ? 'selected' : ''}>
                            Masculino
                        </option>

                        <option value="Femenino"
                        ${param.genero == 'Femenino' ? 'selected' : ''}>
                            Femenino
                        </option>

                    </select>

                </div>


                <!-- CONTRASEÑA -->

                <div class="registro-field">

                    <label for="contrasena">
                        Contraseña
                    </label>

                    <input type="password"
                           id="contrasena"
                           name="contrasena"
                           placeholder="Mínimo 6 caracteres"
                           autocomplete="new-password"
                           required>

                </div>


                <!-- REPETIR CONTRASEÑA -->

                <div class="registro-field">

                    <label for="confirmar">
                        Repetir contraseña
                    </label>

                    <input type="password"
                           id="confirmar"
                           name="confirmar"
                           placeholder="Repetí la contraseña"
                           autocomplete="new-password"
                           required>

                </div>


                <!-- BOTÓN -->

                <button type="submit"
                        class="registro-button">

                    Crear perfil

                </button>

            </form>


            <!-- LINKS -->

            <div class="registro-links">

                <p>
                    ¿Ya tenés cuenta?

                    <a href="${pageContext.request.contextPath}/login">
                        Iniciar sesión
                    </a>
                </p>


                <p>
                    ¿Querés publicar ofertas?

                    <a href="${pageContext.request.contextPath}/registro-empresa">
                        Registrar una empresa
                    </a>
                </p>

            </div>

        </div>

    </section>

</main>


</body>

</html>