<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Empresa | JobMentor</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/logo.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ofertas.css?v=13">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css?v=1">
</head>
<body>

<header class="topbar">
    <div class="logo">
        <img src="${pageContext.request.contextPath}/img/logo.png" alt="JobMentor" class="logo-img">
    </div>

    <div class="nav-actions">
        <span class="btn-outline" style="border: none; color: #666;">
            Modo Edición | Admin: <strong><c:out value="${sessionScope.adminLogueado.usuario}" /></strong>
        </span>
    </div>
</header>

<main class="login-page" style="padding: 2rem 1rem;">
    <section class="login-card" style="grid-template-columns: 1fr; max-width: 800px; margin: 0 auto; padding: 2rem;">

        <h2>Editar Información de la Empresa</h2>
        <p style="margin-bottom: 1.5rem; color: #666;">RUT: <strong><c:out value="${empresa.rut}" /></strong></p>

        <form method="post" action="${pageContext.request.contextPath}/admin-editar-empresa" class="login-form">
            <input type="hidden" name="rut" value="${empresa.rut}">

            <div class="login-field">
                <label for="nombre">Nombre de la empresa</label>
                <input type="text" id="nombre" name="nombre" value="<c:out value='${empresa.nombre}' />" required>
            </div>

            <div class="login-field">
                <label for="mail">Correo electrónico</label>
                <input type="email" id="mail" name="mail" value="<c:out value='${empresa.mail}' />" required>
            </div>

            <div class="login-field">
                <label for="password">Contraseña</label>
                <input type="text" id="password" name="password" value="<c:out value='${empresa.password}' />" required>
            </div>

            <div class="login-field">
                <label for="telefono">Teléfono</label>
                <input type="number" id="telefono" name="telefono" value="<c:out value='${empresa.telefono}' />" required>
            </div>

            <div class="login-field">
                <label for="sitioWeb">Sitio Web</label>
                <input type="text" id="sitioWeb" name="sitioWeb" value="<c:out value='${empresa.sitioWeb}' />">
            </div>

            <div class="login-field">
                <label for="descripcion">Descripción</label>
                <textarea id="descripcion" name="descripcion" style="padding: 0.8rem; border-radius: 6px; border: 1px solid #ccc;"><c:out value='${empresa.descripcion}' /></textarea>
            </div>

            <div style="display: flex; gap: 1rem; margin-top: 1rem;">
                <button type="submit" class="login-button">Guardar Cambios</button>
                <a href="${pageContext.request.contextPath}/admin-empresas" class="btn-outline" style="text-align: center; text-decoration: none; width: 100%; padding: 0.8rem;">Cancelar</a>
            </div>
        </form>

    </section>
</main>

</body>
</html>