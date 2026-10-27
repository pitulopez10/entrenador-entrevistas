<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Publicar oferta | Entrenador Entrevista</title>
    <link rel="icon"
          type="image/png"
          href="${pageContext.request.contextPath}/img/logo.png">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/ofertas.css?v=12">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/login.css?v=1">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/empresa.css?v=3">
</head>
<body>
<c:set var="menuActivo" value="ofertas" />
<%@ include file="empresa-menu.jsp" %>

<main class="login-page">
    <section class="login-card">

        <div class="login-info">
            <div class="login-info-content">
                <span class="login-badge">Nueva oferta</span>
                <h1>Publicá una oferta y encontrá a tu próximo talento</h1>
                <p>
                    Completá los datos del puesto. La oferta se publica hoy
                    y los postulantes podrán verla y postularse.
                </p>
            </div>
        </div>

        <div class="login-form-container">
            <div class="login-form-header">
                <span class="login-form-kicker">Ofertas laborales</span>
                <h2>Publicar oferta</h2>
                <p>Todos los campos son obligatorios.</p>
            </div>

            <c:if test="${not empty error}">
                <div class="login-error">
                    <span class="login-error-icon">!</span>
                    <span><c:out value="${error}" /></span>
                </div>
            </c:if>

            <form method="post"
                  action="${pageContext.request.contextPath}/crear-oferta"
                  class="login-form">

                <div class="login-field">
                    <label for="titulo">Título del puesto</label>
                    <input type="text" id="titulo" name="titulo"
                           value="<c:out value='${param.titulo}' />"
                           maxlength="100"
                           placeholder="Ej: Desarrollador Java Junior"
                           required>
                </div>

                <div class="login-field">
                    <label for="area">Área</label>
                    <select id="area" name="area" required>
                        <option value="">Seleccioná un área</option>
                        <c:forEach var="area" items="${listaAreas}">
                            <option value="${area.id}"
                                    ${area.id eq areaSeleccionada ? 'selected' : ''}>
                                <c:out value="${area.nombre}" />
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="login-field">
                    <label for="fechaCierre">Fecha de cierre</label>
                    <input type="date" id="fechaCierre" name="fechaCierre"
                           value="<c:out value='${param.fechaCierre}' />"
                           min="${hoy}"
                           required>
                </div>

                <div class="login-field">
                    <label for="descripcion">Descripción</label>
                    <textarea id="descripcion" name="descripcion"
                              maxlength="1000"
                              placeholder="Describí las tareas y el puesto"
                              required><c:out value="${param.descripcion}" /></textarea>
                </div>

                <div class="login-field">
                    <label for="requisitos">Requisitos</label>
                    <textarea id="requisitos" name="requisitos"
                              maxlength="1000"
                              placeholder="Estudios, experiencia, conocimientos..."
                              required><c:out value="${param.requisitos}" /></textarea>
                </div>

                <button type="submit" class="login-button">
                    Publicar oferta
                </button>
            </form>

            <p class="login-back">
                <a href="${pageContext.request.contextPath}/empresa">Cancelar y volver al panel</a>
            </p>
        </div>

    </section>
</main>

</body>
</html>
