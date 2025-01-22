package org.ucuenca.Modelo.Documentos.Plantillas;

import java.util.Collection;

public interface Identificable<E> {

    String generarIdentificacion(Collection<E> anteriores);
}
