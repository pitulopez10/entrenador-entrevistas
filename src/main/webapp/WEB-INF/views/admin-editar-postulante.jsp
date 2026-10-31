<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Postulante | Entrenador Entrevista</title>

    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/logo.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ofertas.css?v=13">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css?v=1">
</head>
<body>

<!-- ==================================================
     HEADER LIMPIO (SIN BOTONES DE NAVEGACIÓN)
     ================================================== -->
<header class="topbar">
    <div class="logo">
        <img src="${pageContext.request.contextPath}/img/logo.png" alt="Entrenador Entrevista" class="logo-img">
    </div>

    <!-- Mensaje informativo en lugar de botones de navegación -->
    <div class="nav-actions">
        <span class="btn-outline" style="border: none; color: #666;">
            Modo Edición | Admin: <strong><c:out value="${sessionScope.adminLogueado.usuario}" /></strong>
        </span>
    </div>
</header>

<main class="login-page" style="padding: 2rem 1rem;">
    <section class="login-card" style="grid-template-columns: 1fr; max-width: 800px; margin: 0 auto; padding: 2rem;">

        <h2>Editar Información del Postulante</h2>
        <p style="margin-bottom: 1.5rem; color: #666;">Cédula de Identidad: <strong><c:out value="${postulante.ci}" /></strong></p>

        <form method="post" action="${pageContext.request.contextPath}/admin-editar-postulante" class="login-form">
            <input type="hidden" name="ci" value="${postulante.ci}">

            <div class="login-field">
                <label for="nombre">Nombre completo</label>
                <input type="text" id="nombre" name="nombre" value="<c:out value='${postulante.nombre}' />" required>
            </div>

            <div class="login-field">
                <label for="mail">Correo electrónico</label>
                <input type="email" id="mail" name="mail" value="<c:out value='${postulante.mail}' />" required>
            </div>

            <div class="login-field">
                <label for="password">Contraseña</label>
                <input type="text" id="password" name="password" value="<c:out value='${postulante.password}' />" required>
            </div>

            <div class="login-field">
                <label for="telefono">Teléfono</label>
                <input type="number" id="telefono" name="telefono" value="<c:out value='${postulante.telefono}' />" required>
            </div>

            <div class="login-field">
                <label for="localidad">Localidad</label>
                <input type="text" id="localidad" name="localidad" value="<c:out value='${postulante.localidad}' />">
            </div>

            <div class="login-field">
                <label for="fechaNacimiento">Fecha de Nacimiento</label>
                <input type="date" id="fechaNacimiento" name="fechaNacimiento" value="${postulante.fechaNacimiento}">
            </div>

            <div class="login-field">
                <label for="genero">Género</label>
                <select id="genero" name="genero" style="padding: 0.8rem; border-radius: 6px; border: 1px solid #ccc;">
                    <option value="Masculino" ${postulante.genero == 'Masculino' ? 'selected' : ''}>Masculino</option>
                    <option value="Femenino" ${postulante.genero == 'Femenino' ? 'selected' : ''}>Femenino</option>
                </select>
            </div>

            <div class="login-field">
                <label for="descripcion">Descripción / Perfil</label>
                <textarea id="descripcion" name="descripcion" style="padding: 0.8rem; border-radius: 6px; border: 1px solid #ccc;"><c:out value='${postulante.descripcion}' /></textarea>
            </div>

            <div class="login-field">
                <label for="datosEstudio">Datos de Estudio</label>
                <textarea id="datosEstudio" name="datosEstudio" style="padding: 0.8rem; border-radius: 6px; border: 1px solid #ccc;"><c:out value='${postulante.datosEstudio}' /></textarea>
            </div>

            <div class="login-field">
                <label for="datosExperiencia">Datos de Experiencia</label>
                <textarea id="datosExperiencia" name="datosExperiencia" style="padding: 0.8rem; border-radius: 6px; border: 1px solid #ccc;"><c:out value='${postulante.datosExperiencia}' /></textarea>
            </div>

            <div style="display: flex; gap: 1rem; margin-top: 1rem;">
                <button type="submit" class="login-button">Guardar Cambios</button>
                <a href="${pageContext.request.contextPath}/admin-postulantes" class="btn-outline" style="text-align: center; text-decoration: none; width: 100%; padding: 0.8rem;">Cancelar</a>
            </div>
        </form>

    </section>
</main>

</body>
</html>