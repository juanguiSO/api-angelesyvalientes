package org.angelesyvalientes.api.service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailService {

    public void enviarCodigo(String destinatario, String codigo) throws MessagingException {
        String remitente = "formacionciudanana@gmail.com"; // Correo del remitente
        String clave = "zrlabnqlymfuylsh"; // Contraseña del remitente -usando claves de aplicación)

        // Configurar propiedades del servidor SMTP
        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.host", "smtp.gmail.com");
        propiedades.put("mail.smtp.port", "587");
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.starttls.enable", "true");

        // Autenticación del remitente
        Session sesion = Session.getInstance(propiedades, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, clave);
            }
        });

        // Crear el mensaje
        Message mensaje = new MimeMessage(sesion);
        mensaje.setFrom(new InternetAddress(remitente));
        mensaje.setRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
        mensaje.setSubject("Código de Verificación");
        mensaje.setText("Tu código de verificación es: " + codigo);

        // Enviar el mensaje
        Transport.send(mensaje);
    }
}