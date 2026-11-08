<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Oferta | JobMentor</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/logo.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ofertas.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mis-postulaciones.css?v=1">
    <style>
        .form-card {
            background: #fff;
            padding: 2rem;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.05);
            max-width: 800px;
            margin: 2rem auto;
        }
        .form-group {
            margin-bottom: 1.2rem;
            display: flex;
            flex-direction: column;
            gap: 0.4rem;
        }
        .form-group label {
            font-weight: 600;
            color: #333;
        }
        .form-group input, .form-group select, .form-group textarea {
            padding: 0.7rem 1rem;
            border-radius: 8px;
            border: 1px solid #ccc;
            font-size: 0.95rem;
            width: 100%;
        }
        .form-group textarea {
            resize: vertical;
            min-height: 100px;
        }
        .form-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 1rem;
        }
        .form-actions {
            display: flex;
            gap: 1rem;
            justify-content: flex-end;
            margin-top: 1.5rem;
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

<main class="postulaciones-page">

    <section class="form-card">
        <h2>Editar Oferta Laboral</h2>
        <p style="margin-bottom: 1.5rem; color: #666;">Modifica todos los detalles de la oferta seleccionada.</p>

        <form method="post" action="${pageContext.request.contextPath}/admin-editar-oferta">

            <input type="hidden" name="id" value="${oferta.id}">

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

            <!-- ÚNICOS BOTONES DISPONIBLES AL FINAL -->
            <div class="form-actions">
                <a href="${pageContext.request.contextPath}/admin-inicio" class="btn-outline" style="text-decoration: none;">
                    Cancelar
                </a>
                <button type="submit" class="btn-primary">
                    Guardar Cambios
                </button>
            </div>

        </form>
    </section>

</main>

</body>
</html>