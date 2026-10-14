<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>${oferta.titulo} | Entrenador Entrevista</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/ofertas.css?v=4">
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

        <a href="${pageContext.request.contextPath}/ofertas"
           class="active">
            Buscar ofertas
        </a>

        <a href="#">
            Entrevistas
        </a>

        <a href="#">
            Empresas
        </a>

        <a href="#">
            Preguntas frecuentes
        </a>

    </nav>

    <div class="nav-actions">

        <a href="#"
           class="btn-outline">
            Iniciar sesión
        </a>

        <a href="#"
           class="btn-primary">
            Crear perfil
        </a>

    </div>

</header>


<main class="detalle-page-nuevo">

    <div class="detalle-contenedor">

        <!-- VOLVER -->

        <a href="${pageContext.request.contextPath}/ofertas"
           class="detalle-volver">
            ← Volver a ofertas
        </a>


        <div class="detalle-layout">

            <!-- =========================
                 COLUMNA IZQUIERDA
                 ========================= -->

            <div class="detalle-izquierda">

                <div class="detalle-estado">
                    <span class="estado-punto"></span>
                    ${oferta.estado}
                </div>


                <h1 class="detalle-titulo">
                    ${oferta.titulo}
                </h1>


                <p class="detalle-subtitulo">
                    Conocé los detalles de esta oportunidad laboral
                    y revisá los requisitos antes de postularte.
                </p>


                <!-- DESCRIPCIÓN -->

                <section class="detalle-bloque">

                    <div class="detalle-icono">

                        <svg viewBox="0 0 24 24"
                             aria-hidden="true">

                            <path d="M6 2h9l5 5v15H6z"></path>
                            <path d="M14 2v6h6"></path>
                            <path d="M9 13h6"></path>
                            <path d="M9 17h6"></path>

                        </svg>

                    </div>


                    <div class="detalle-bloque-texto">

                        <h2>
                            Descripción del puesto
                        </h2>

                        <p>
                            ${oferta.descripcion}
                        </p>

                    </div>

                </section>


                <!-- REQUISITOS -->

                <section class="detalle-bloque">

                    <div class="detalle-icono">

                        <svg viewBox="0 0 24 24"
                             aria-hidden="true">

                            <circle cx="12"
                                    cy="12"
                                    r="3"></circle>

                            <path d="M19.4 15a1.7 1.7 0 0 0 .3 1.9l.1.1-2.8 2.8-.1-.1a1.7 1.7 0 0 0-1.9-.3 1.7 1.7 0 0 0-1 1.6V21h-4v-.1a1.7 1.7 0 0 0-1-1.6 1.7 1.7 0 0 0-1.9.3l-.1.1L4.2 17l.1-.1a1.7 1.7 0 0 0 .3-1.9A1.7 1.7 0 0 0 3 14H3v-4h.1a1.7 1.7 0 0 0 1.6-1 1.7 1.7 0 0 0-.3-1.9L4.2 7 7 4.2l.1.1a1.7 1.7 0 0 0 1.9.3 1.7 1.7 0 0 0 1-1.6V3h4v.1a1.7 1.7 0 0 0 1 1.6 1.7 1.7 0 0 0 1.9-.3l.1-.1L19.8 7l-.1.1a1.7 1.7 0 0 0-.3 1.9 1.7 1.7 0 0 0 1.6 1h.1v4H21a1.7 1.7 0 0 0-1.6 1z"></path>

                        </svg>

                    </div>


                    <div class="detalle-bloque-texto">

                        <h2>
                            Requisitos
                        </h2>

                        <p>
                            ${oferta.requisitos}
                        </p>

                    </div>

                </section>

            </div>


            <!-- =========================
                 PANEL DERECHO
                 ========================= -->

            <aside class="detalle-panel">

                <div class="panel-titulo">

                    <div class="panel-icono">

                        <svg viewBox="0 0 24 24"
                             aria-hidden="true">

                            <rect x="3"
                                  y="7"
                                  width="18"
                                  height="13"
                                  rx="2"></rect>

                            <path d="M8 7V5a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>

                            <path d="M3 12h18"></path>

                        </svg>

                    </div>

                    <h2>
                        Detalles de la oferta
                    </h2>

                </div>


                <div class="panel-linea"></div>


                <div class="panel-dato">

                    <span class="panel-etiqueta">
                        Área profesional
                    </span>

                    <strong>
                        ${oferta.area.nombre}
                    </strong>

                </div>


                <div class="panel-dato">

                    <span class="panel-etiqueta">
                        Empresa
                    </span>

                    <strong>
                        ${oferta.empresa.nombre}
                    </strong>

                </div>


                <div class="panel-dato">

                    <span class="panel-etiqueta">
                        Fecha de publicación
                    </span>

                    <strong>
                        ${oferta.fechaPublicacion}
                    </strong>

                </div>


                <div class="panel-dato">

                    <span class="panel-etiqueta">
                        Fecha de cierre
                    </span>

                    <strong>
                        ${oferta.fechaCierre}
                    </strong>

                </div>


                <div class="panel-dato">

                    <span class="panel-etiqueta">
                        Estado
                    </span>

                    <span class="panel-estado">
                        ${oferta.estado}
                    </span>

                </div>


                <div class="panel-linea"></div>


                <a href="#"
                   class="boton-postular">

                    <span>
                        ✈
                    </span>

                    Postularme

                </a>


                <div class="panel-login-aviso">

                    <span class="info-icono">
                        i
                    </span>

                    <p>
                        Para postularte necesitás
                        <a href="#">
                            iniciar sesión
                        </a>.
                    </p>

                </div>

            </aside>

        </div>

    </div>

</main>

</body>

</html>