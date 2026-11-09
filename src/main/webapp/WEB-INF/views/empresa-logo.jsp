<%@ page pageEncoding="UTF-8" %>
<c:url var="logoPredeterminado" value="/img/empresa-predeterminada.svg" />
<img class="<c:out value='${claseLogoEmpresa}' />"
     src="<c:out value='${empty empresa.logo ? logoPredeterminado : empresa.logo}' />"
     data-logo-predeterminado="<c:out value='${logoPredeterminado}' />"
     alt="Logo de la empresa">