package org.ucuenca.Modelo.Documentos.Plantillas;

import org.ucuenca.Modelo.Usuarios.Ciudadano;

public interface ActualizadorEstadoCiudadano<Estado> {
    boolean modificarEstado(Estado estado, Ciudadano ciudadano) throws RequisitoIncumplidoException;
}
