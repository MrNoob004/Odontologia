package org.ucuenca.Modelo.Documentos.Plantillas;

import java.time.LocalDate;

public interface Caducable {
    boolean estaVigente();
    LocalDate generarFechaCaducidad();
}
