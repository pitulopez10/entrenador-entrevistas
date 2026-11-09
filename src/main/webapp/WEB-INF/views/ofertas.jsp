<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>JobMentor | Ofertas laborales</title>
    <link rel="icon"
          type="image/png"
          href="${pageContext.request.contextPath}/img/logo.png">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/ofertas.css?v=16">
</head>

<body>

<!-- =========================
     HEADER
     ========================= -->

<header class="topbar home-topbar">

    <a href="${pageContext.request.contextPath}/ofertas"
       class="logo">

        <img src="${pageContext.request.contextPath}/img/logo.png"
             alt="JobMentor"
             class="logo-img">

    </a>


    <nav class="main-nav">

        <a href="${pageContext.request.contextPath}/ofertas"
           class="active">
            Buscar ofertas
        </a>

        <a href="${pageContext.request.contextPath}/mis-entrevistas">
            Entrevistas
        </a>


        <!-- =========================================
             MENÚ SEGÚN SI HAY USUARIO LOGUEADO
             ========================================= -->

        <c:choose>

            <c:when test="${not empty sessionScope.postulanteLogueado}">

                <a href="${pageContext.request.contextPath}/mis-postulaciones">
                    Mis postulaciones
                </a>

                <a href="${pageContext.request.contextPath}/perfil">
                    Mi perfil
                </a>

            </c:when>

            <c:otherwise>

                <a href="${pageContext.request.contextPath}${not empty sessionScope.empresaLogueada ? '/empresa' : '/login-empresa'}">
                    Empresas
                </a>

                <a href="${pageContext.request.contextPath}/institucional#quienes-somos">
                    Preguntas frecuentes
                </a>

            </c:otherwise>

        </c:choose>

    </nav>


    <div class="nav-actions">

        <c:choose>
<%-- Postulante con sesión iniciada (sin cambios) --%>
    <c:when test="${not empty sessionScope.postulanteLogueado}">

        <span class="usuario-logueado">
            Hola, ${sessionScope.postulanteLogueado.nombre}
        </span>

        <a href="${pageContext.request.contextPath}/logout"
           class="btn-outline">
            Cerrar sesión
        </a>

    </c:when>

    <%-- NUEVO: empresa con sesión iniciada --%>
    <c:when test="${not empty sessionScope.empresaLogueada}">

        <span class="usuario-logueado">
            Hola, <c:out value="${sessionScope.empresaLogueada.nombre}" />
        </span>

        <a href="${pageContext.request.contextPath}/empresa"
           class="btn-outline">
            Mi panel
        </a>

        <a href="${pageContext.request.contextPath}/logout"
           class="btn-outline">
            Cerrar sesión
        </a>

    </c:when>

    <%-- Sin sesión --%>
    <c:otherwise>

        <a href="${pageContext.request.contextPath}/login"
           class="btn-outline">
            Iniciar sesión
        </a>

        <a href="${pageContext.request.contextPath}/registro"
           class="btn-primary">
            Crear perfil
        </a>



    </c:otherwise>


        </c:choose>

    </div>

</header>


<main>

    <!-- =========================
         HERO
         ========================= -->

    <section class="hero"
             style="
                     background-image:
                     linear-gradient(
                     rgba(8, 25, 55, 0.58),
                     rgba(8, 25, 55, 0.64)
                     ),
                     url('${pageContext.request.contextPath}/img/fondo-entrevista.png');
                     ">

        <div class="hero-content">

            <span class="hero-badge">
                Encontrá nuevas oportunidades
            </span>


            <h1>
                Prepárate para tu
                <br>
                <span>próxima entrevista</span>
            </h1>


            <p>
                Explora oportunidades laborales,
                encuentra ofertas según tu área de interés
                y prepárate para tus entrevistas.
            </p>


            <!-- BUSCADOR -->

            <form method="get"
                  action="${pageContext.request.contextPath}/ofertas#ofertas"
                  class="search-box">

                <div class="search-field">

                    <label for="area">
                        Área profesional
                    </label>

                    <select name="area"
                            id="area">

                        <option value=""
                                <c:if test="${empty param.area}">selected</c:if>>

                            Todas las áreas

                        </option>


                        <c:forEach var="areaItem"
                                   items="${areas}">

                            <option value="${areaItem.id}"
                                    <c:if test="${param.area eq areaItem.id}">selected</c:if>>

                                    ${areaItem.nombre}

                            </option>

                        </c:forEach>

                    </select>

                </div>

                <div class="search-field search-title-field">
                    <label for="titulo">Nombre de la oferta</label>
                    <input type="search"
                           name="titulo"
                           id="titulo"
                           value="<c:out value='${param.titulo}'/>"
                           placeholder="Ej.: Desarrollador">
                </div>


                <button type="submit"
                        class="search-btn">

                    Buscar ofertas

                </button>

            </form>


            <!-- ACCESOS RÁPIDOS -->

            <div class="quick-tags">

                <span>
                    Explorar rápidamente
                </span>

                <div class="tag-list">

                    <a href="${pageContext.request.contextPath}/ofertas#ofertas"
                       class="tag">
                        Todas
                    </a>

                    <a href="${pageContext.request.contextPath}/ofertas?area=1#ofertas"
                       class="tag">
                        Informática
                    </a>

                    <a href="${pageContext.request.contextPath}/ofertas?area=2#ofertas"
                       class="tag">
                        Administración
                    </a>

                    <a href="${pageContext.request.contextPath}/ofertas?area=3#ofertas"
                       class="tag">
                        Ventas
                    </a>

                </div>

            </div>

        </div>

    </section>


    <!-- =========================
         OFERTAS
         ========================= -->

    <section class="section offers-section"
             id="ofertas">

        <div class="section-header">

            <span class="section-kicker">
                Oportunidades laborales
            </span>

            <h2>
                Ofertas disponibles
            </h2>

        </div>


        <!-- SIN RESULTADOS -->

        <c:if test="${empty ofertas}">

            <div class="empty-state">

                <h3>
                    No hay ofertas disponibles
                </h3>

                <p>
                    No se encontraron ofertas para el área seleccionada.
                </p>

            </div>

        </c:if>


        <!-- TARJETAS -->

        <div class="offers-grid">

            <c:forEach var="oferta"
                       items="${ofertas}">

                <article class="offer-card">

                    <div class="offer-top">

                        <span class="offer-area">
                                ${oferta.area.nombre}
                        </span>

                        <span class="offer-status">
                                ${oferta.estado}
                        </span>

                    </div>


                    <h3>
                            ${oferta.titulo}
                    </h3>


                    <p class="offer-company">

                        <strong>
                            Empresa:
                        </strong>

                            <a class="empresa-link" href="${pageContext.request.contextPath}/empresa-publica?id=${oferta.empresa.idPublico}"><c:out value="${oferta.empresa.nombre}" /></a>

                    </p>


                    <p class="offer-description">
                            ${oferta.descripcion}
                    </p>


                    <div class="offer-meta">

                        <span>
                            <strong>Publicación:</strong>
                            ${oferta.fechaPublicacion}
                        </span>

                        <span>
                            <strong>Cierre:</strong>
                            ${oferta.fechaCierre}
                        </span>

                    </div>


                    <div class="offer-actions">

                        <a href="${pageContext.request.contextPath}/oferta?id=${oferta.id}"
                           class="btn-outline small">
                            Ver oferta
                        </a>

                    </div>

                </article>

            </c:forEach>

        </div>

        <c:if test="${totalPaginas > 1}">
            <nav class="paginacion-ofertas" aria-label="Páginas de ofertas">
                <c:if test="${pagina > 1}">
                    <a href="${pageContext.request.contextPath}/ofertas?pagina=${pagina - 1}&area=${areaSeleccionada}&titulo=${tituloBuscado}"
                       class="pagina-enlace">← Anterior</a>
                </c:if>
                <span class="pagina-indicador">Página ${pagina} de ${totalPaginas}</span>
                <c:if test="${pagina < totalPaginas}">
                    <a href="${pageContext.request.contextPath}/ofertas?pagina=${pagina + 1}&area=${areaSeleccionada}&titulo=${tituloBuscado}"
                       class="pagina-enlace">Siguiente →</a>
                </c:if>
            </nav>
        </c:if>

    </section>


    <!-- =========================
         COMO FUNCIONA
         ========================= -->

    <section class="section how-it-works" id="entrevistas">

        <div class="section-mini-title">
            ¿Cómo funciona?
        </div>


        <div class="steps-wrapper">

            <h2>
                Practica y mejora tu desempeño
                <br>
                <span>
                    en entrevistas
                </span>
            </h2>


            <div class="steps-grid">

                <article class="step-card">

                    <div class="step-number">
                        1
                    </div>

                    <div>

                        <h3>
                            Crea tu perfil
                        </h3>

                        <p>
                            Registra tu experiencia,
                            áreas de interés y objetivos profesionales.
                        </p>

                    </div>

                </article>


                <article class="step-card">

                    <div class="step-number">
                        2
                    </div>

                    <div>

                        <h3>
                            Simula entrevistas
                        </h3>

                        <p>
                            Responde preguntas y practica
                            con situaciones similares al mundo laboral.
                        </p>

                    </div>

                </article>


                <article class="step-card">

                    <div class="step-number">
                        3
                    </div>

                    <div>

                        <h3>
                            Recibe retroalimentación
                        </h3>

                        <p>
                            Obtén una devolución personalizada
                            para mejorar tus respuestas.
                        </p>

                    </div>

                </article>

            </div>

        </div>

    </section>

</main>


<!-- =========================
     FOOTER
     ========================= -->

<footer class="footer">

    <div class="footer-top">

        <div class="footer-brand">

            <img src="${pageContext.request.contextPath}/img/logo.png"
                 alt="JobMentor"
                 class="footer-logo">

            <p>
                Tu aliado para conseguir mejores oportunidades.
                Practica, recibe retroalimentación y llega preparado
                a tu próxima entrevista.
            </p>

        </div>


        <div class="footer-columns">

            <div>

                <h4>
                    Institucional
                </h4>

                <a href="${pageContext.request.contextPath}/institucional#quienes-somos">
                    Quiénes somos
                </a>

                <a href="${pageContext.request.contextPath}/institucional#contacto">
                    Contacto
                </a>

                <a href="${pageContext.request.contextPath}/institucional#proposito">
                    Nuestro propósito
                </a>

            </div>


            <div>

                <h4>
                    Candidatos
                </h4>

                <a href="${pageContext.request.contextPath}/ofertas">
                    Buscar ofertas
                </a>

                <a href="${pageContext.request.contextPath}${not empty sessionScope.postulanteLogueado ? '/mis-entrevistas' : '/login'}">
                    Practicar entrevistas
                </a>

                <c:choose>

                    <c:when test="${not empty sessionScope.postulanteLogueado}">

                        <a href="${pageContext.request.contextPath}/perfil">
                            Mi perfil
                        </a>

                    </c:when>

                    <c:otherwise>

                        <a href="${pageContext.request.contextPath}/registro">
                            Crear perfil
                        </a>

                    </c:otherwise>

                </c:choose>

            </div>


            <div>

                <h4>
                    Empresas
                </h4>

                <a href="${pageContext.request.contextPath}${not empty sessionScope.empresaLogueada ? '/empresa' : '/login-empresa'}">
                    Publicar vacantes
                </a>

                <a href="${pageContext.request.contextPath}${not empty sessionScope.empresaLogueada ? '/empresa' : '/registro-empresa'}">
                    Buscar candidatos
                </a>

                <a href="${pageContext.request.contextPath}${not empty sessionScope.empresaLogueada ? '/empresa' : '/login-empresa'}">
                    Soluciones para empresas
                </a>

            </div>

        </div>

    </div>


    <div class="footer-copy">

        Copyright 2026 JobMentor.
        Todos los derechos reservados.

        <br>
        Web realizada por Maximiliano Lopez, Paz Colla y Pablo Maidana.

    </div>

</footer>

<script>
    const barraInicio = document.querySelector('.home-topbar');
    const actualizarBarra = () => {
        barraInicio.classList.toggle('scrolled', window.scrollY > 24);
    };
    window.addEventListener('scroll', actualizarBarra, { passive: true });
    actualizarBarra();
</script>

</body>

</html>
