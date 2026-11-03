package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/restablecer-contrasena")
public class RestablecerContrasenaServlet extends HttpServlet {
    private final PasswordResetDAO resetDAO;
    private final PostulanteDAO postulanteDAO = new PostulanteDAO();
    private final EmpresaDAO empresaDAO = new EmpresaDAO();
    public RestablecerContrasenaServlet() throws SQLException { resetDAO = new PasswordResetDAO(); }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        mostrar(req, resp, req.getParameter("token"), null);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        String password = req.getParameter("password");
        String confirmacion = req.getParameter("confirmacion");
        try {
            String rol = resetDAO.obtenerRolValido(token);
            String email = resetDAO.obtenerEmail(token);
            if (rol == null || password == null || password.length() < 6 || !password.equals(confirmacion)) {
                mostrar(req, resp, token, "El enlace venció o las contraseñas no coinciden. Debe tener al menos 6 caracteres."); return;
            }
            if ("POSTULANTE".equals(rol)) { Postulante p = postulanteDAO.buscarPorMail(email); p.setPassword(password); postulanteDAO.modificar(p); }
            else { Empresa e = empresaDAO.buscarPorMail(email); e.setPassword(password); empresaDAO.modificar(e); }
            resetDAO.marcarUsado(token);
            req.setAttribute("exito", true);
            req.getRequestDispatcher("/WEB-INF/views/restablecer-contrasena.jsp").forward(req, resp);
        } catch (SQLException e) { throw new ServletException("No se pudo restablecer la contraseña", e); }
    }
    private void mostrar(HttpServletRequest req, HttpServletResponse resp, String token, String error) throws ServletException, IOException {
        try { req.setAttribute("valido", resetDAO.obtenerRolValido(token) != null); } catch (SQLException e) { throw new ServletException(e); }
        req.setAttribute("token", token); req.setAttribute("error", error);
        req.getRequestDispatcher("/WEB-INF/views/restablecer-contrasena.jsp").forward(req, resp);
    }
}
