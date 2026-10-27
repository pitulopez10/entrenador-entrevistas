<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>
        Mi perfil | Entrenador Entrevista
    </title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/ofertas.css?v=13">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/perfil.css?v=2">

</head>

<body>


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

        <a href="${pageContext.request.contextPath}/perfil"
           class="active">
            Mi perfil
        </a>

    </nav>


    <div class="nav-actions">

        <span class="usuario-logueado">
            Hola, ${sessionScope.postulanteLogueado.nombre}
        </span>

        <a href="${pageContext.request.contextPath}/logout"
           class="btn-outline">
            Cerrar sesión
        </a>

    </div>

</header>


<main class="perfil-page">

    <section class="perfil-header">

        <div>

            <span class="perfil-kicker">
                Cuenta personal
            </span>

            <h1>
                Mi perfil
            </h1>

            <p>
                Consultá y mantené actualizada tu información personal y profesional.
            </p>

        </div>


        <a href="${pageContext.request.contextPath}/editar-perfil"
           class="btn-primary">

            Editar perfil

        </a>

    </section>


    <!-- MENSAJE DE ACTUALIZACIÓN -->

    <c:if test="${param.actualizado == 'ok'}">

        <div class="mensaje-perfil-exito">
            Perfil actualizado correctamente.
        </div>

    </c:if>


    <section class="perfil-grid">


        <!-- =========================
             PERFIL PRINCIPAL
             ========================= -->

        <article class="perfil-card perfil-principal">

            <div class="perfil-avatar">

                <c:choose>

                    <c:when test="${not empty postulante.fotoPerfil}">

                        <img src="${postulante.fotoPerfil}"
                             alt="Foto de perfil"
                             class="foto-perfil">


                    </c:when>

                    <c:otherwise>

                        <div class="avatar-placeholder">

                                ${postulante.nombre.substring(0,1)}

                        </div>

                    </c:otherwise>

                </c:choose>

            </div>


            <div class="perfil-identidad">

                <h2>
                    ${postulante.nombre}
                </h2>

                <p>
                    ${postulante.mail}
                </p>

                <span class="perfil-estado">
                    Perfil activo
                </span>

            </div>

        </article>


        <!-- =========================
             INFORMACIÓN PERSONAL
             ========================= -->

        <article class="perfil-card">

            <h3>
                Información personal
            </h3>

            <div class="perfil-datos">


                <div class="perfil-dato">

                    <span class="perfil-label">
                        Cédula
                    </span>

                    <strong>
                        ${postulante.ci}
                    </strong>

                </div>


                <div class="perfil-dato">

                    <span class="perfil-label">
                        Teléfono
                    </span>

                    <strong>

                        <c:choose>

                            <c:when test="${postulante.telefono != 0}">
                                ${postulante.telefono}
                            </c:when>

                            <c:otherwise>
                                Sin especificar
                            </c:otherwise>

                        </c:choose>

                    </strong>

                </div>


                <div class="perfil-dato">

                    <span class="perfil-label">
                        Fecha de nacimiento
                    </span>

                    <strong>

                        <c:choose>

                            <c:when test="${not empty postulante.fechaNacimiento}">
                                ${postulante.fechaNacimiento}
                            </c:when>

                            <c:otherwise>
                                Sin especificar
                            </c:otherwise>

                        </c:choose>

                    </strong>

                </div>


                <div class="perfil-dato">

                    <span class="perfil-label">
                        Género
                    </span>

                    <strong>

                        <c:choose>

                            <c:when test="${not empty postulante.genero}">
                                ${postulante.genero}
                            </c:when>

                            <c:otherwise>
                                Sin especificar
                            </c:otherwise>

                        </c:choose>

                    </strong>

                </div>


                <div class="perfil-dato">

                    <span class="perfil-label">
                        Localidad
                    </span>

                    <strong>

                        <c:choose>

                            <c:when test="${not empty postulante.localidad}">
                                ${postulante.localidad}
                            </c:when>

                            <c:otherwise>
                                Sin especificar
                            </c:otherwise>

                        </c:choose>

                    </strong>

                </div>


                <div class="perfil-dato">

                    <span class="perfil-label">
                        Fecha de registro
                    </span>

                    <strong>
                        ${postulante.fechaRegistro}
                    </strong>

                </div>

            </div>

        </article>


        <!-- =========================
             SOBRE MÍ
             ========================= -->

        <article class="perfil-card">

            <h3>
                Sobre mí
            </h3>

            <p class="perfil-texto">

                <c:choose>

                    <c:when test="${not empty postulante.descripcion}">
                        ${postulante.descripcion}
                    </c:when>

                    <c:otherwise>
                        Todavía no agregaste una descripción personal.
                    </c:otherwise>

                </c:choose>

            </p>

        </article>


        <!-- =========================
             FORMACIÓN
             ========================= -->

        <article class="perfil-card">

            <h3>
                Formación
            </h3>

            <p class="perfil-texto">

                <c:choose>

                    <c:when test="${not empty postulante.datosEstudio}">
                        ${postulante.datosEstudio}
                    </c:when>

                    <c:otherwise>
                        Todavía no agregaste información sobre tus estudios.
                    </c:otherwise>

                </c:choose>

            </p>

        </article>


        <!-- =========================
             EXPERIENCIA
             ========================= -->

        <article class="perfil-card">

            <h3>
                Experiencia
            </h3>

            <p class="perfil-texto">

                <c:choose>

                    <c:when test="${not empty postulante.datosExperiencia}">
                        ${postulante.datosExperiencia}
                    </c:when>

                    <c:otherwise>
                        Todavía no agregaste experiencia laboral.
                    </c:otherwise>

                </c:choose>

            </p>

        </article>


        <!-- =========================
             CURRÍCULUM
             ========================= -->

        <article class="perfil-card">

            <h3>
                Currículum
            </h3>

            <c:choose>

                <c:when test="${not empty postulante.cv}">

                    <p class="perfil-texto">
                        Tenés un currículum cargado.
                    </p>

                    <a href="${postulante.cv}"
                       target="_blank"
                       rel="noopener noreferrer"
                       class="btn-outline">

                        Ver currículum

                    </a>

                </c:when>

                <c:otherwise>

                    <p class="perfil-texto">
                        Todavía no agregaste un currículum.
                    </p>

                </c:otherwise>

            </c:choose>

        </article>

    </section>

</main>

</body>

</html>