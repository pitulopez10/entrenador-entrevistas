<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Ofertas laborales</title>
</head>

<body>

<h1>Ofertas laborales</h1>

<h2>Buscar por área</h2>

<form method="get" action="${pageContext.request.contextPath}/ofertas">

    <select name="area">
        <option value="">Todas las áreas</option>

        <c:forEach var="area" items="${areas}">
            <option value="${area.id}">
                    ${area.nombre}
            </option>
        </c:forEach>
    </select>

    <button type="submit">Buscar</button>

</form>

<hr>

<h2>Ofertas disponibles</h2>

<c:forEach var="oferta" items="${ofertas}">

    <div>

        <h3>${oferta.titulo}</h3>

        <p>
            <strong>Empresa:</strong>
                ${oferta.empresa.nombre}
        </p>

        <p>
            <strong>Área:</strong>
                ${oferta.area.nombre}
        </p>

        <p>
                ${oferta.descripcion}
        </p>

        <p>
            <strong>Fecha de publicación:</strong>
                ${oferta.fechaPublicacion}
        </p>

        <hr>

    </div>

</c:forEach>

</body>

</html>