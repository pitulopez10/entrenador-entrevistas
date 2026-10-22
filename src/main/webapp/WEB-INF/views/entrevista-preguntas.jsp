<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>
        Entrevista práctica | Entrenador Entrevista
    </title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/ofertas.css?v=13">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/entrevista.css?v=2">
</head>

<body>


<header class="topbar">

    <a href="${pageContext.request.contextPath}/ofertas"
       class="logo">

        <img src="${pageContext.request.contextPath}/img/logo.png"
             alt="Entrenador Entrevista"
             class="logo-img">

    </a>


    <nav class="main-nav">

        <a href="${pageContext.request.contextPath}/ofertas">
            Buscar ofertas
        </a>

        <a href="#"
           class="active">
            Entrevista
        </a>

        <a href="${pageContext.request.contextPath}/mis-postulaciones">
            Mis postulaciones
        </a>

        <a href="${pageContext.request.contextPath}/perfil">
            Mi perfil
        </a>

    </nav>


    <div class="nav-actions">

        <span class="usuario-logueado">
            Hola, ${sessionScope.postulanteLogueado.nombre}
        </span>

        <a href="${pageContext.request.contextPath}/logout"
           class="btn-outline">
            Cerrar sesión
        </a>

    </div>

</header>


<main class="entrevista-page">


    <section class="pregunta-encabezado">

        <div>

            <span class="entrevista-kicker">
                Entrevista en curso
            </span>

            <h1>
                ${entrevista.ofertaLaboral.titulo}
            </h1>

            <p>
                Respondé cada pregunta como si estuvieras en una entrevista real.
            </p>

        </div>


        <div class="pregunta-contador">

            <strong>
                ${numeroPregunta}
            </strong>

            <span>
                de ${totalPreguntas}
            </span>

        </div>

    </section>


    <section class="progreso-entrevista">

        <div class="progreso-info">

            <span>
                Progreso
            </span>

            <span>
                Pregunta ${numeroPregunta} de ${totalPreguntas}
            </span>

        </div>

        <div class="progreso-barra">

            <div class="progreso-barra-completado"
                 style="width: ${(numeroPregunta * 100) / totalPreguntas}%;">
            </div>

        </div>

    </section>


    <section class="pregunta-card">


        <div class="pregunta-meta">

            <span class="pregunta-tipo">
                ${preguntaActual.tipo}
            </span>

            <span class="pregunta-dificultad">
                Dificultad ${preguntaActual.dificultad}
            </span>

        </div>


        <h2>
            ${preguntaActual.texto}
        </h2>


        <form method="post"
              action="${pageContext.request.contextPath}/entrevista">


            <input type="hidden"
                   name="accion"
                   value="responder">


            <input type="hidden"
                   name="entrevistaId"
                   value="${entrevista.id}">


            <input type="hidden"
                   name="preguntaId"
                   value="${preguntaActual.id}">


            <div class="respuesta-grupo">

                <label for="respuesta">
                    Tu respuesta
                </label>

                <textarea
                        id="respuesta"
                        name="respuesta"
                        rows="9"
                        maxlength="4000"
                        required
                        placeholder="Escribí tu respuesta acá..."></textarea>

                <div class="respuesta-ayuda">

                    <span>
                        Intentá justificar tu respuesta y utilizar ejemplos concretos.
                    </span>

                </div>

            </div>


            <div class="pregunta-acciones">

                <button type="submit"
                        class="btn-comenzar-entrevista">

                    <c:choose>

                        <c:when test="${numeroPregunta == totalPreguntas}">
                            Finalizar entrevista
                        </c:when>

                        <c:otherwise>
                            Guardar y continuar
                        </c:otherwise>

                    </c:choose>

                </button>

            </div>

        </form>

    </section>


    <section class="entrevista-consejo">

        <strong>
            Consejo
        </strong>

        <p>
            No busques una respuesta perfecta. La idea es practicar cómo
            comunicarías tus conocimientos, decisiones y experiencias frente
            a un entrevistador.
        </p>

    </section>


</main>

</body>

</html>