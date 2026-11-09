package org.example.web;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@WebFilter(urlPatterns = {"/empresa", "/empresa-oferta", "/empresa-postulante",
        "/empresa-perfil", "/empresa-evaluar", "/empresa-accion", "/editar-empresa", "/crear-oferta"})
public class EmpresaSeguridadServlet implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-store");
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("empresaLogueada") != null) {
            String token;
            synchronized (session) {
                token = (String) session.getAttribute("empresaCsrf");
                if (token == null) {
                    token = UUID.randomUUID().toString();
                    session.setAttribute("empresaCsrf", token);
                }
            }
            if ("POST".equals(request.getMethod())) {
                String recibido = request.getParameter("empresaCsrf");
                if (recibido == null || !MessageDigest.isEqual(
                        token.getBytes(StandardCharsets.UTF_8),
                        recibido.getBytes(StandardCharsets.UTF_8))) {
                    response.sendError(403, "Formulario vencido o inválido. Recargá la página.");
                    return;
                }
            }
        }
        chain.doFilter(req, res);
    }
}