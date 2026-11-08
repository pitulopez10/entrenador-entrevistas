<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Oferta | JobMentor</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/logo.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ofertas.css?v=13">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css?v=2">
<style>
        .form-card {
            background: #fff;
            padding: 2rem;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.05);
            max-width: 850px;
            width: 100%;
            margin: 1.5rem auto;
        }
        .form-group {
            margin-bottom: 1rem;
            display: flex;
            flex-direction: column;
            gap: 0.3rem;
            width: 100%;
        }
        .form-group label {
            font-weight: 600;
            color: #333;
            font-size: 0.95rem;
        }
        .form-group input,
        .form-group select,
        .form-group textarea {
            padding: 0.65rem 0.9rem;
            border-radius: 8px;
            border: 1px solid #ccc;
            font-size: 0.95rem;
            width: 100%;
            box-sizing: border-box;
        }
        .form-group textarea {
            resize: vertical;
            min-height: 90px;
        }
        .form-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 1rem;
            width: 100%;
        }
    </style>
</head>

<body>

<!-- HEADER LIMPIO: MODO EDICIÓN SIN MENÚS -->
<header class="topbar">
    <div class="logo">
        <a href="${pageContext.request.contextPath}/admin-inicio">
            <img src="${pageContext.request.contextPath}/img/logo.png" alt="JobMentor" class="logo-img">
        </a>
    </div>

    <div class="nav-actions">
        <span class="btn-outline" style="border: none; color: #666;">
            Modo Edición | <strong><c:out value="${sessionScope.adminLogueado.usuario}" /></strong>
        </span>
    </div>
</header>

<main class="login-page" style="padding: 2rem 1rem;">

    <section class="form-card">
        <h2>Editar Oferta Laboral</h2>
        <p style="margin-bottom: 1.5rem; color: #666;">Modifica todos los detalles de la oferta seleccionada.</p>

        <form method="post" action="${pageContext.request.contextPath}/admin-editar-oferta" class="login-form">

            <input type="hidden" name="id" value="${oferta.id}">

            <!-- CAMPO OCULTO PARA CONSERVAR LA URL DE RETORNO DINÁMICA -->
            <input type="hidden" name="returnUrl" value="${not empty returnUrl ? returnUrl : pageContext.request.contextPath.concat('/admin-inicio')}">

            <div class="form-group">
                <label for="titulo">Título de la Oferta</label>
                <input type="text" id="titulo" name="titulo" value="<c:out value='${oferta.titulo}' />" required>
            </div>

            <div class="form-grid">
                <div class="form-group">
                    <label for="area">Área Profesional</label>
                    <select id="area" name="areaId" required>
                        <c:forEach var="areaItem" items="${areas}">
                            <option value="${areaItem.id}" <c:if test="${oferta.area.id eq areaItem.id}">selected</c:if>>
                                <c:out value="${areaItem.nombre}" />
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="estado">Estado</label>
                    <select id="estado" name="estado" required>
                        <option value="ACTIVA" <c:if test="${oferta.estado == 'ACTIVA'}">selected</c:if>>Activa</option>
                        <option value="PAUSADA" <c:if test="${oferta.estado == 'PAUSADA'}">selected</c:if>>Pausada</option>
                        <option value="TERMINADA" <c:if test="${oferta.estado == 'TERMINADA'}">selected</c:if>>Terminada</option>
                    </select>
                </div>
            </div>

            <div class="form-grid">
                <div class="form-group">
                    <label for="fechaPublicacion">Fecha Publicación</label>
                    <input type="date" id="fechaPublicacion" name="fechaPublicacion" value="${oferta.fechaPublicacion}" required>
                </div>

                <div class="form-group">
                    <label for="fechaCierre">Fecha Cierre</label>
                    <input type="date" id="fechaCierre" name="fechaCierre" value="${oferta.fechaCierre}">
                </div>
            </div>

            <div class="form-group">
                <label for="descripcion">Descripción</label>
                <textarea id="descripcion" name="descripcion" required><c:out value="${oferta.descripcion}" /></textarea>
            </div>

            <div class="form-group">
                <label for="requisitos">Requisitos</label>
                <textarea id="requisitos" name="requisitos" required><c:out value="${not empty oferta.requisitos ? oferta.requisitos : ''}" /></textarea>
            </div>

            <!-- BOTONES -->
            <div style="display: flex; gap: 1rem; margin-top: 1.5rem; width: 100%;">

                <button type="submit" class="login-button" style="width: 100%;">
                    Guardar Cambios
                </button>

                <a href="${not empty returnUrl ? returnUrl : pageContext.request.contextPath.concat('/admin-inicio')}"
                   class="btn-outline"
                   style="text-align: center;
                          text-decoration: none;
                          width: 100%;
                          padding: 0.8rem;
                          display: flex;
                          align-items: center;
                          justify-content: center;">
                    Cancelar
                </a>

            </div>

        </form>
    </section>

</main>

</body>
</html>