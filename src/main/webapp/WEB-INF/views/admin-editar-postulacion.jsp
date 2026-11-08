<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Postulación | Entrenador Entrevista</title>

    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/logo.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ofertas.css?v=13">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css?v=2">
</head>
<body>

<header class="topbar">
    <div class="logo">
        <img src="${pageContext.request.contextPath}/img/logo.png"
             alt="Entrenador Entrevista"
             class="logo-img">
    </div>

    <div class="nav-actions">
        <span class="btn-outline" style="border: none; color: #666;">
            Modo Edición |
            <strong>
                <c:out value="${sessionScope.adminLogueado.usuario}" />
            </strong>
        </span>
    </div>
</header>

<main class="login-page" style="padding: 2rem 1rem;">

    <section class="login-card"
             style="grid-template-columns: 1fr; max-width: 600px; margin: 0 auto; padding: 2rem;">

        <h2>Editar Estado de Postulación</h2>

        <p style="margin-bottom: 1.5rem; color: #666;">
            ID de Postulación: <strong><c:out value="${postulacion.id}" /></strong>
        </p>

        <!-- FORMULARIO -->
        <form method="post"
              action="${pageContext.request.contextPath}/admin-editar-postulacion"
              class="login-form">

            <input type="hidden" name="id" value="${postulacion.id}">

            <!-- CONSERVA LA URL DE RETORNO -->
            <input type="hidden" name="returnUrl" value="<c:out value='${returnUrl}' />">

            <!-- ESTADO DE LA POSTULACIÓN -->
            <div class="login-field">
                <label for="estado">Estado de la Postulación</label>
                <select id="estado"
                        name="estado"
                        style="padding: 0.8rem; border-radius: 6px; border: 1px solid #ccc;">
                    <option value="enviado" ${postulacion.estado.name() == 'enviado' ? 'selected' : ''}>
                        Enviado
                    </option>
                    <option value="enRevision" ${postulacion.estado.name() == 'enRevision' ? 'selected' : ''}>
                        En revisión
                    </option>
                    <option value="seleccionado" ${postulacion.estado.name() == 'seleccionado' ? 'selected' : ''}>
                        Seleccionado
                    </option>
                    <option value="rechazado" ${postulacion.estado.name() == 'rechazado' ? 'selected' : ''}>
                        Rechazado
                    </option>
                </select>
            </div>

            <!-- MENSAJE -->
            <div class="login-field">
                <label for="mensaje">Mensaje enviado</label>
                <textarea id="mensaje"
                          name="mensaje"
                          style="padding: 0.8rem; border-radius: 6px; border: 1px solid #ccc; font-family: inherit;"><c:out value="${postulacion.mensaje}" /></textarea>
            </div>

            <!-- BOTONES -->
            <div style="display: flex; gap: 1rem; margin-top: 1rem;">
                <button type="submit" class="login-button">
                    Guardar Cambios
                </button>

                <a href="<c:out value='${returnUrl}' />"
                   class="btn-outline"
                   style="text-align: center;
                          text-decoration: none;
                          width: 100%;
                          padding: 0.8rem;">
                    Cancelar
                </a>
            </div>

        </form>

    </section>

</main>

</body>
</html>