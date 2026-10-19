<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">

    <title>
        Prueba Cloudinary
    </title>
</head>

<body>

<h2>
    Subir imagen de prueba
</h2>

<form method="post"
      action="${pageContext.request.contextPath}/prueba-cloudinary"
      enctype="multipart/form-data">

    <input type="file"
           name="archivo"
           accept="image/*"
           required>

    <br><br>

    <button type="submit">
        Subir a Cloudinary
    </button>

</form>

</body>

</html>