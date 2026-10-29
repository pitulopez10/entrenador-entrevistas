<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar empresa | Entrenador Entrevista</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/ofertas.css?v=12">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/login.css?v=1">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/empresa.css?v=10">
</head>
<body>

<c:set var="menuActivo" value="perfil" />
<%@ include file="empresa-menu.jsp" %>

<main class="panel">

    <a class="volver-link"
       href="${pageContext.request.contextPath}/empresa-perfil">
        &larr; Volver a mi empresa
    </a>

    <section class="panel-card panel-formulario">

        <h2>Editar perfil de la empresa</h2>

        <c:if test="${not empty error}">
            <div class="login-error">
                <span class="login-error-icon">!</span>
                <span><c:out value="${error}" /></span>
            </div>
        </c:if>

        <form method="post"
              action="${pageContext.request.contextPath}/editar-empresa"
              class="login-form">

            <div class="login-field">
                <label for="rut">RUT</label>
                <input type="text" id="rut"
                       value="<c:out value='${sessionScope.empresaLogueada.rut}' />"
                       disabled>
                <small class="ayuda-campo">El RUT identifica a la empresa y no se puede modificar.</small>
            </div>

            <div class="login-field">
                <label for="nombre">Nombre de la empresa</label>
                <input type="text" id="nombre" name="nombre"
                       value="<c:out value='${valorNombre}' />"
                       maxlength="100"
                       required>
            </div>

            <div class="login-field">
                <label for="correo">Correo electrónico</label>
                <input type="email" id="correo" name="correo"
                       value="<c:out value='${valorCorreo}' />"
                       maxlength="100"
                       required>
            </div>

            <div class="login-field">
                <label for="telefono">Teléfono (opcional)</label>
                <input type="text" id="telefono" name="telefono"
                       value="<c:out value='${valorTelefono}' />"
                       inputmode="numeric"
                       maxlength="9"
                       placeholder="Solo números">
            </div>

            <div class="login-field">
                <label for="sitioWeb">Sitio web (opcional)</label>
                <input type="text" id="sitioWeb" name="sitioWeb"
                       value="<c:out value='${valorSitio}' />"
                       inputmode="url"
                       maxlength="150"
                       placeholder="https://www.tuempresa.com">
            </div>

            <div class="login-field">
                <label for="descripcion">Descripción (opcional)</label>
                <textarea id="descripcion" name="descripcion"
                          maxlength="500"
                          placeholder="Contá a qué se dedica tu empresa"><c:out value="${valorDescripcion}" /></textarea>
            </div>

            <div class="form-acciones">
                <button type="submit" class="login-button">Guardar cambios</button>
                <a class="btn-accion-sec"
                   href="${pageContext.request.contextPath}/empresa-perfil">
                    Cancelar
                </a>
            </div>

        </form>

    </section>

</main>

</body>
</html>