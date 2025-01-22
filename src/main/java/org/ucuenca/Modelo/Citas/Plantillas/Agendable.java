package org.ucuenca.Modelo.Citas.Plantillas;

import org.ucuenca.Modelo.Usuarios.Ciudadano;

import java.time.LocalDateTime;

public interface Agendable<Evento> {
    boolean setFecha(LocalDateTime fecha);

    LocalDateTime getFecha();

    boolean cancelar(Ciudadano ciudadano, Evento evento);
}
