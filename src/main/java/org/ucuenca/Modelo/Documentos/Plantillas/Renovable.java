package org.ucuenca.Modelo.Documentos.Plantillas;

import java.util.Collection;

public interface Renovable<E> {
    E renovar();

    /**
     * Busca entre los demás elementos anteriores vigentes y cambia su estado.
     * @param anteriores Colección de los anteriores elementos posiblemente vigentes
    * */
    boolean invalidarAnterior(Collection<E> anteriores);
}
