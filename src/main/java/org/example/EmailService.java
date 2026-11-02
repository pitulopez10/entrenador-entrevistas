package org.example;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMultipart;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** Servicio de notificaciones por correo. Se configura mediante variables de entorno. */
public class EmailService {

    public void enviarConfirmacionPostulacion(Postulante postulante,
                                               OfertaLaboral oferta) throws MessagingException {
        enviarConfirmacionPostulacion(postulante, oferta, null);
    }

    public void enviarConfirmacionPostulacion(Postulante postulante,
                                               OfertaLaboral oferta,
                                               InputStream logo) throws MessagingException {
        enviarNotificacionEstado(postulante, oferta, logo,
                "Postulación registrada: " + oferta.getTitulo(),
                "¡Postulación registrada!",
                "Tu postulación fue registrada correctamente en JobMentor.",
                "Gracias por confiar en nosotros. Te avisaremos sobre cualquier novedad.");
    }

    public void enviarNotificacionEstado(Postulante postulante,
                                         OfertaLaboral oferta,
                                         InputStream logo,
                                         String asunto,
                                         String titulo,
                                         String mensaje,
                                         String cierre) throws MessagingException {
        String host = config("SMTP_HOST", "smtp.gmail.com");
        String port = config("SMTP_PORT", "587");
        String username = requiredConfig("SMTP_USERNAME");
        String password = requiredConfig("SMTP_PASSWORD");
        String from = config("SMTP_FROM", username);

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);

        Session session = Session.getInstance(properties);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipient(Message.RecipientType.TO,
                new InternetAddress(postulante.getMail()));
        message.setSubject(asunto, "UTF-8");
        MimeMultipart content = new MimeMultipart("related");
        MimeBodyPart html = new MimeBodyPart();
        html.setContent(htmlNotificacion(postulante, oferta, logo != null,
                titulo, mensaje, cierre), "text/html; charset=UTF-8");
        content.addBodyPart(html);

        if (logo != null) {
            try {
                MimeBodyPart image = new MimeBodyPart();
                image.setDataHandler(new jakarta.activation.DataHandler(
                        new jakarta.mail.util.ByteArrayDataSource(logo, "image/png")));
                image.setHeader("Content-ID", "<logo-app>");
                image.setDisposition(MimeBodyPart.INLINE);
                content.addBodyPart(image);
            } catch (IOException e) {
                throw new MessagingException("No se pudo adjuntar el logo", e);
            }
        }
        message.setContent(content);

        try (Transport transport = session.getTransport("smtp")) {
            transport.connect(username, password);
            transport.sendMessage(message, message.getAllRecipients());
        }
    }

    private static String htmlNotificacion(Postulante postulante,
                                           OfertaLaboral oferta,
                                           boolean incluyeLogo,
                                           String titulo,
                                           String mensaje,
                                           String cierre) {
        String logo = incluyeLogo ? "<img src='cid:logo-app' alt='JobMentor' "
                + "style='max-width:180px;height:auto;margin-bottom:18px;'>" : "";
        return "<div style='background:#f4f7fb;padding:32px 12px;font-family:Arial,sans-serif;color:#24324a;'>"
                + "<div style='max-width:620px;margin:auto;background:#fff;border-radius:18px;overflow:hidden;box-shadow:0 4px 18px #dce3ef;'>"
                + "<div style='background:#14213d;padding:28px;text-align:center;'>" + logo + ""
                + "<h1 style='color:#fff;font-size:24px;margin:0;'>" + escape(titulo) + "</h1></div>"
                + "<div style='padding:30px;'>"
                + "<p style='font-size:17px;'>Hola <strong>" + escape(postulante.getNombre()) + "</strong>,</p>"
                + "<p>" + escape(mensaje) + "</p>"
                + "<div style='background:#eef5ff;border-left:4px solid #3b82f6;padding:18px;margin:24px 0;'>"
                + "<p style='margin:0 0 8px;color:#64748b;font-size:12px;text-transform:uppercase;'>Oferta laboral</p>"
                + "<h2 style='margin:0 0 14px;color:#14213d;'>" + escape(oferta.getTitulo()) + "</h2>"
                + "<p style='margin:6px 0;'><strong>ID de oferta:</strong> " + oferta.getId() + "</p>"
                + "<p style='margin:6px 0;'><strong>Descripción:</strong> " + escape(oferta.getDescripcion()) + "</p></div>"
                + "<p>" + escape(cierre) + "</p>"
                + "</div><div style='padding:18px;text-align:center;color:#8492a6;font-size:12px;'>JobMentor</div></div></div>";
    }

    private static String escape(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }

    private static String requiredConfig(String name) {
        String value = config(name, null);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Falta configurar " + name
                            + " como variable de entorno o propiedad del sistema");
        }
        return value;
    }

    private static String config(String name, String defaultValue) {
        String value = System.getProperty(name);
        if (value == null || value.isBlank()) {
            value = System.getenv(name);
        }
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
