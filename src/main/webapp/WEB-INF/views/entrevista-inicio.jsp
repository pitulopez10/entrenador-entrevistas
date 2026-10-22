<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>
        Practicar entrevista | Entrenador Entrevista
    </title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/ofertas.css?v=13">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/entrevista.css?v=1">
</head>

<body>


<!-- ==================================================
     HEADER
     ================================================== -->

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

        <a href="${pageContext.request.contextPath}/entrevista?ofertaId=${oferta.id}"
           class="active">
            Entrevistas
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


<!-- ==================================================
     CONTENIDO
     ================================================== -->

<main class="entrevista-page">

    <section class="entrevista-intro">

        <div class="entrevista-intro-texto">

            <span class="entrevista-kicker">
                Simulación laboral
            </span>

            <h1>
                Practicá tu entrevista
            </h1>

            <p>
                Vas a responder 15 preguntas relacionadas con esta oportunidad laboral.
                Al finalizar recibirás una devolución sobre tus respuestas.
            </p>

        </div>

    </section>


    <section class="entrevista-layout">


        <!-- ==================================================
             OFERTA
             ================================================== -->

        <article class="entrevista-card entrevista-oferta">

            <span class="entrevista-etiqueta">
                Oferta seleccionada
            </span>


            <h2>
                ${oferta.titulo}
            </h2>


            <p class="entrevista-empresa">
                ${oferta.empresa.nombre}
            </p>


            <div class="entrevista-dato">

                <span>
                    Área
                </span>

                <strong>
                    ${oferta.area.nombre}
                </strong>

            </div>


            <div class="entrevista-separador"></div>


            <h3>
                Descripción
            </h3>

            <p>
                ${oferta.descripcion}
            </p>


            <h3>
                Requisitos
            </h3>

            <p>
                ${oferta.requisitos}
            </p>

        </article>


        <!-- ==================================================
             INFORMACIÓN DE LA PRÁCTICA
             ================================================== -->

        <article class="entrevista-card entrevista-preparacion">

            <div class="entrevista-icono-grande">
                15
            </div>


            <h2>
                Antes de comenzar
            </h2>


            <p>
                La práctica contiene preguntas técnicas,
                situacionales y conductuales.
            </p>


            <div class="entrevista-resumen">

                <div>

                    <strong>
                        5
                    </strong>

                    <span>
                        Técnicas
                    </span>

                </div>


                <div>

                    <strong>
                        5
                    </strong>

                    <span>
                        Situacionales
                    </span>

                </div>


                <div>

                    <strong>
                        5
                    </strong>

                    <span>
                        Conductuales
                    </span>

                </div>

            </div>


            <div class="entrevista-aviso">

                <strong>
                    Recomendación
                </strong>

                <p>
                    Respondé como si estuvieras frente a un entrevistador real.
                    Intentá explicar tus decisiones y utilizar ejemplos concretos.
                </p>

            </div>


            <form method="post"
                  action="${pageContext.request.contextPath}/entrevista">

                <input type="hidden"
                       name="accion"
                       value="generar">

                <input type="hidden"
                       name="ofertaId"
                       value="${oferta.id}">


                <button type="submit"
                        class="btn-comenzar-entrevista">

                    Comenzar entrevista

                </button>

            </form>


            <a href="${pageContext.request.contextPath}/oferta?id=${oferta.id}"
               class="entrevista-volver">

                Volver a la oferta

            </a>

        </article>

    </section>

</main>

</body>

</html>