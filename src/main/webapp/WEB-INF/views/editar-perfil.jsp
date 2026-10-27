<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>
        Editar perfil | Entrenador Entrevista
    </title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/ofertas.css?v=13">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/editar-perfil.css?v=3">
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


<!-- ==================================================
     CONTENIDO
     ================================================== -->

<main class="editar-perfil-page">

    <section class="editar-perfil-header">

        <div>

            <span class="editar-kicker">
                Cuenta personal
            </span>

            <h1>
                Editar perfil
            </h1>

            <p>
                Actualizá tu información personal,
                profesional y tus archivos.
            </p>

        </div>


        <a href="${pageContext.request.contextPath}/perfil"
           class="btn-outline">

            Cancelar

        </a>

    </section>


    <!-- ==================================================
         ERROR
         ================================================== -->

    <c:if test="${not empty error}">

        <div class="editar-mensaje-error">
                ${error}
        </div>

    </c:if>


    <form method="post"
          action="${pageContext.request.contextPath}/editar-perfil"
          enctype="multipart/form-data"
          class="editar-form"
          id="formEditarPerfil">


        <!-- ==================================================
             FOTO
             ================================================== -->

        <section class="editar-card">

            <div class="editar-card-header">

                <div>

                    <h2>
                        Foto de perfil
                    </h2>

                    <p>
                        Esta imagen aparecerá en tu perfil.
                    </p>

                </div>

            </div>


            <div class="foto-edicion">

                <div class="foto-preview">

                    <c:choose>

                        <c:when test="${not empty postulante.fotoPerfil
                                      and fn:startsWith(postulante.fotoPerfil, 'http')}">

                            <img src="${postulante.fotoPerfil}"
                                 alt="Foto de perfil"
                                 id="previewFoto">

                        </c:when>

                        <c:when test="${not empty postulante.fotoPerfil}">

                            <img src="${pageContext.request.contextPath}/img/${postulante.fotoPerfil}"
                                 alt="Foto de perfil"
                                 id="previewFoto">

                        </c:when>

                        <c:otherwise>

                            <div class="foto-placeholder"
                                 id="placeholderFoto">

                                    ${postulante.nombre.substring(0,1)}

                            </div>

                            <img src=""
                                 alt="Vista previa"
                                 id="previewFoto"
                                 style="display:none;">

                        </c:otherwise>

                    </c:choose>

                </div>


                <div class="foto-controles">

                    <label for="fotoPerfil"
                           class="btn-seleccionar">

                        Seleccionar imagen

                    </label>

                    <input type="file"
                           name="fotoPerfil"
                           id="fotoPerfil"
                           accept="image/jpeg,image/png,image/webp">

                    <span class="archivo-ayuda">
                        JPG, PNG o WEBP. Máximo 5 MB.
                    </span>

                </div>

            </div>

        </section>


        <!-- ==================================================
             INFORMACIÓN PERSONAL
             ================================================== -->

        <section class="editar-card">

            <div class="editar-card-header">

                <div>

                    <h2>
                        Información personal
                    </h2>

                    <p>
                        Datos básicos asociados a tu cuenta.
                    </p>

                </div>

            </div>


            <div class="editar-grid">


                <div class="editar-campo editar-campo-completo">

                    <label for="nombre">
                        Nombre completo
                    </label>

                    <input type="text"
                           id="nombre"
                           name="nombre"
                           value="${postulante.nombre}"
                           maxlength="100"
                           required>

                </div>


                <div class="editar-campo">

                    <label for="mail">
                        Correo electrónico
                    </label>

                    <input type="email"
                           id="mail"
                           name="mail"
                           value="${postulante.mail}"
                           maxlength="150"
                           required>

                </div>


                <div class="editar-campo">

                    <label for="telefono">
                        Teléfono
                    </label>

                    <input type="number"
                           id="telefono"
                           name="telefono"
                           value="${postulante.telefono}">

                </div>


                <div class="editar-campo">

                    <label for="fechaNacimiento">
                        Fecha de nacimiento
                    </label>

                    <input type="date"
                           id="fechaNacimiento"
                           name="fechaNacimiento"
                           value="${postulante.fechaNacimiento}">

                </div>


                <div class="editar-campo">

                    <label for="genero">
                        Género
                    </label>

                    <select id="genero"
                            name="genero">

                        <option value="">
                            Seleccionar
                        </option>

                        <option value="Masculino"
                                <c:if test="${postulante.genero == 'Masculino'}">
                                    selected
                                </c:if>>
                            Masculino
                        </option>

                        <option value="Femenino"
                                <c:if test="${postulante.genero == 'Femenino'}">
                                    selected
                                </c:if>>
                            Femenino
                        </option>

                    </select>

                </div>


                <div class="editar-campo editar-campo-completo">

                    <label for="localidad">
                        Localidad
                    </label>

                    <input type="text"
                           id="localidad"
                           name="localidad"
                           value="${postulante.localidad}"
                           maxlength="100">

                </div>

            </div>

        </section>


        <!-- ==================================================
             PERFIL PROFESIONAL
             ================================================== -->

        <section class="editar-card">

            <div class="editar-card-header">

                <div>

                    <h2>
                        Perfil profesional
                    </h2>

                    <p>
                        Completá la información que querés mostrar
                        sobre tu formación y experiencia.
                    </p>

                </div>

            </div>


            <div class="editar-campo">

                <label for="descripcion">
                    Sobre mí
                </label>

                <textarea id="descripcion"
                          name="descripcion"
                          rows="5"
                          maxlength="1000">${postulante.descripcion}</textarea>

            </div>


            <div class="editar-campo">

                <label for="datosEstudio">
                    Formación
                </label>

                <textarea id="datosEstudio"
                          name="datosEstudio"
                          rows="5"
                          maxlength="1500">${postulante.datosEstudio}</textarea>

            </div>


            <div class="editar-campo">

                <label for="datosExperiencia">
                    Experiencia laboral
                </label>

                <textarea id="datosExperiencia"
                          name="datosExperiencia"
                          rows="5"
                          maxlength="1500">${postulante.datosExperiencia}</textarea>

            </div>

        </section>


        <!-- ==================================================
             CURRÍCULUM
             ================================================== -->

        <section class="editar-card">

            <div class="editar-card-header">

                <div>

                    <h2>
                        Currículum
                    </h2>

                    <p>
                        Podés cargar o reemplazar tu currículum en formato PDF.
                    </p>

                </div>

            </div>


            <div class="cv-edicion">

                <div class="cv-info">

                    <div class="cv-icono">
                        PDF
                    </div>


                    <div>

                        <c:choose>

                            <c:when test="${not empty postulante.cv}">

                                <strong>
                                    Currículum cargado
                                </strong>

                                <p>
                                    Si seleccionás un nuevo archivo,
                                    el currículum actual será reemplazado.
                                </p>

                                <a href="${postulante.cv}"
                                   target="_blank"
                                   rel="noopener noreferrer"
                                   class="cv-ver-actual">

                                    Ver currículum actual

                                </a>

                            </c:when>

                            <c:otherwise>

                                <strong>
                                    No hay currículum cargado
                                </strong>

                                <p>
                                    Seleccioná un archivo PDF para agregarlo a tu perfil.
                                </p>

                            </c:otherwise>

                        </c:choose>

                    </div>

                </div>


                <div class="cv-controles">

                    <label for="cv"
                           class="btn-seleccionar">

                        Seleccionar PDF

                    </label>

                    <input type="file"
                           name="cv"
                           id="cv"
                           accept="application/pdf">

                    <span class="archivo-ayuda"
                          id="nombreCvSeleccionado">

                        PDF. Máximo 5 MB.

                    </span>

                </div>

            </div>

        </section>


        <!-- ==================================================
             BOTONES
             ================================================== -->

        <div class="editar-acciones">

            <a href="${pageContext.request.contextPath}/perfil"
               class="btn-cancelar-editar">

                Cancelar

            </a>

            <button type="submit"
                    class="btn-guardar-editar">

                Guardar cambios

            </button>

        </div>

    </form>

</main>


<script src="${pageContext.request.contextPath}/js/editar-perfil.js?v=3">
</script>

</body>

</html>