<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Institucional | JobMentor</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/logo.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ofertas.css?v=15">
</head>
<body>
<header class="topbar">
    <a href="${pageContext.request.contextPath}/ofertas" class="logo">
        <img src="${pageContext.request.contextPath}/img/logo.png" alt="JobMentor" class="logo-img">
    </a>
    <nav class="main-nav">
        <a href="${pageContext.request.contextPath}/ofertas">Buscar ofertas</a>
        <a href="${pageContext.request.contextPath}/institucional" class="active">Institucional</a>
    </nav>
    <div class="nav-actions">
        <a href="${pageContext.request.contextPath}/login" class="btn-outline">Iniciar sesión</a>
        <a href="${pageContext.request.contextPath}/registro" class="btn-primary">Crear perfil</a>
    </div>
</header>

<main class="institucional-page">
    <section class="institucional-hero">
        <span class="perfil-kicker">Conocé nuestra propuesta</span>
        <h1>Impulsamos tu próximo paso profesional</h1>
        <p>Conectamos talento, oportunidades y preparación para que cada persona pueda llegar mejor preparada a su próxima entrevista.</p>
    </section>

    <section class="institucional-grid">
        <article class="institucional-card" id="quienes-somos">
            <span class="institucional-numero">01</span>
            <h2>Quiénes somos</h2>
            <p>JobMentor es una plataforma pensada para acompañar a quienes buscan trabajo y ayudar a las empresas a encontrar perfiles con verdadero potencial.</p>
        </article>
        <article class="institucional-card" id="proposito">
            <span class="institucional-numero">02</span>
            <h2>Nuestro propósito</h2>
            <p>Queremos hacer que la búsqueda laboral sea más clara, accesible y humana, brindando herramientas para descubrir oportunidades y prepararse con confianza.</p>
        </article>
        <article class="institucional-card institucional-contacto" id="contacto">
            <span class="institucional-numero">03</span>
            <h2>Contacto</h2>
            <p>¿Tenés una consulta o una sugerencia? Estamos para ayudarte.</p>
            <a href="mailto:contacto@entrenadorentrevista.com">contacto@entrenadorentrevista.com</a>
        </article>
    </section>
</main>
</body>
</html>
