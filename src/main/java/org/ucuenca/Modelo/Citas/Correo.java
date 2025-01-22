package org.ucuenca.Modelo.Citas;

import java.io.File;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

public class Correo {
    private String correoDeOrigen;
    private String correoDeDestino;
    private String asunto;
    private String mensajeDeTexto;
    private String archivoAdjunto;
    private String contraseña16Digitos;

    public Correo(String destino, String asunto,
                  String txt, String dirAdjunto) {
        this.correoDeOrigen = "kenneth.avila@ucuenca.edu.ec";
        this.correoDeDestino = destino;
        this.asunto = asunto;
        this.mensajeDeTexto = txt;
        this.contraseña16Digitos = "hpduasfwoxvdtpzy";
        this.archivoAdjunto
                = dirAdjunto;
    }

    public boolean enviar() {
        return envioDeMensajes();
    }

    private boolean envioDeMensajes() {
        try {
            Properties p = new Properties();
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.setProperty("mail.smtp.starttls.enable", "true");
            p.put("mail.smtp.ssl.trust", "smtp.gmail.com");

            p.setProperty("mail.smtp.port", "587");
            p.setProperty("mail.smtp.user", correoDeOrigen);
            p.setProperty("mail.smtp.auth", "true");
            Session s = Session.getDefaultInstance(p);
            //Archivos adjuntos
            MimeMultipart m = new MimeMultipart();
            if (archivoAdjunto != null) {
                BodyPart texto = new MimeBodyPart();
                texto.setText(mensajeDeTexto);
                BodyPart adjunto = new MimeBodyPart();
                adjunto.setDataHandler(new DataHandler(
                        new FileDataSource(archivoAdjunto)));
                adjunto.setFileName(new
                        File(archivoAdjunto).getName());
                m.addBodyPart(texto);
                m.addBodyPart(adjunto);
            }
            MimeMessage mensaje = new MimeMessage(s);
            mensaje.setFrom(new InternetAddress(correoDeOrigen));
            mensaje.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(correoDeDestino));
            mensaje.setSubject(asunto);
            if (archivoAdjunto == null) {
                mensaje.setText(mensajeDeTexto);
            } else {
                mensaje.setContent(m);
            }
            Transport t = s.getTransport("smtp");
            t.connect(correoDeOrigen, contraseña16Digitos);
            t.sendMessage(mensaje, mensaje.getAllRecipients());
            t.close();
            return true;

        } catch (MessagingException e) {
            return false;
        }
    }
}