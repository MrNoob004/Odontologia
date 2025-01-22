package org.ucuenca.Controlador.Usuario;


import org.ucuenca.Modelo.Citas.Correo;

public interface CitaNotificableCorreo {

    default boolean enviarCorreo(String mensaje, String asunto, String destinatario, String dirAdjunto) {
        Correo correo = new Correo(
                destinatario,
                asunto,
                mensaje,
                dirAdjunto
        );

        return correo.enviar();
    }
}
