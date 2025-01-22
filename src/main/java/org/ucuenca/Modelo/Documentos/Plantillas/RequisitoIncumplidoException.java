package org.ucuenca.Modelo.Documentos.Plantillas;

import org.ucuenca.Modelo.Citas.Plantillas.Requisito;
import org.ucuenca.Modelo.Usuarios.Ciudadano;

/**
 * Excepción a lanzar si es que en algún proceso de
 * {@link org.ucuenca.Modelo.Documentos.Plantillas.DocumentoLegal} o {@link org.ucuenca.Modelo.Citas.Plantillas.Cita}
 * No se cumple con un requerimiento establecido. Ej: <b>No estar vivo</b>
 *
 * <p>
 * <pre>
 * {@code throw new RequisitoIncumplidoException(ciudadano + " No está vivo");}
 * </pre>
 * </p>
 * <p>
 * Puede usarse en conjunto con {@link org.ucuenca.Modelo.Citas.Plantillas.Requisito} para obtener su mensaje
 * de incumplimiento correspondiente
 *
 * <p>
 * <pre>
 * {@code throw new RequisitoIncumplidoException(ciudadano + requisito);}
 * </pre>
 * </p>
 */
public class RequisitoIncumplidoException extends RuntimeException {
    public RequisitoIncumplidoException(String message) {
        super(message);
    }

    public RequisitoIncumplidoException(Ciudadano ciudadano, String message) {
        super(ciudadano.toString() + message);
    }

    public RequisitoIncumplidoException(Ciudadano ciudadano, Requisito requsito) {
        super(ciudadano.toString() + requsito.getMensajeIncumplimiento());
    }
}
