<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Postulantes</title>
</head>
<body>

<h1>Postulantes registrados</h1>

<c:if test="${empty postulantes}">
    <p>No hay postulantes registrados.</p>
</c:if>

<c:if test="${not empty postulantes}">
    <table border="1" cellpadding="6">
        <tr>
            <th>CI</th>
            <th>Nombre</th>
            <th>Mail</th>
            <th>Localidad</th>
        </tr>

        <c:forEach var="p" items="${postulantes}">
            <tr>
                <td>${p.ci}</td>
                <td>${p.nombre}</td>
                <td>${p.mail}</td>
                <td>${p.localidad}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<p><a href="${pageContext.request.contextPath}/ofertas">Volver a ofertas</a></p>

</body>
</html>