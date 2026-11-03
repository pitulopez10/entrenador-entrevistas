package org.example.web;

import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@WebServlet("/recuperar-contrasena")
public class RecuperarContrasenaServlet extends HttpServlet {
    private final PasswordResetDAO resetDAO;
    private final PostulanteDAO postulanteDAO = new PostulanteDAO();
    private final EmpresaDAO empresaDAO = new EmpresaDAO();
    private final EmailService emailService = new EmailService();

    public RecuperarContrasenaServlet() throws SQLException { resetDAO = new PasswordResetDAO(); }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/recuperar-contrasena.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("correo") == null ? "" : req.getParameter("correo").trim();
        try {
            String rol = postulanteDAO.buscarPorMail(email) != null ? "POSTULANTE"
                    : (empresaDAO.buscarPorMail(email) != null ? "EMPRESA" : null);
            if (rol != null) {
                String token = UUID.randomUUID().toString();
                resetDAO.guardar(token, email, rol, Timestamp.from(Instant.now().plusSeconds(600)));
                String url = req.getRequestURL().toString().replace(req.getRequestURI(), req.getContextPath())
                        + "/restablecer-contrasena?token=" + token;
                try (InputStream logo = getServletContext().getResourceAsStream("/img/logo.png")) {
                    emailService.enviarRecuperacion(email, url, logo);
                }
            }
            req.setAttribute("enviado", true);
            req.getRequestDispatcher("/WEB-INF/views/recuperar-contrasena.jsp").forward(req, resp);
        } catch (SQLException | MessagingException e) { throw new ServletException("No se pudo procesar la recuperación", e); }
    }
}
