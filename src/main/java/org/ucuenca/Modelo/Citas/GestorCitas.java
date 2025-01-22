package org.ucuenca.Modelo.Citas;

import org.ucuenca.Modelo.Citas.Plantillas.Cita;
import org.ucuenca.Modelo.Documentos.Plantillas.DocumentoLegal;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public class GestorCitas<C extends Cita> implements Serializable {
    private int ultimoIdGenerado;

    /**
     * Genera un ID según el elemento más reciente de {@code documentos}
     *
     * @param citas Lista de documentos de tipo D.
     * @return El siguiente ID disponible como String (n+1).
     */
    public String generarId(Collection<C> citas) {
        for (C cita : citas) {
            try {
                String id = cita.getIdCita();
                int idActual = Integer.parseInt(id); // Convierte el ID de String a entero
                ultimoIdGenerado = Math.max(ultimoIdGenerado, idActual);
            } catch (NumberFormatException e) {
                System.err.println("Error procesando documento: " + e.getMessage());
            }
        }

        // Retorna el siguiente ID disponible como String
        return String.valueOf(ultimoIdGenerado + 1);
    }

    public String generarId(Map<String, Cita> citas) {
        for (Map.Entry<String, Cita> cita : citas.entrySet()) {
            try {
                String id = cita.getValue().getIdCita();
                int idActual = Integer.parseInt(id); // Convierte el ID de String a entero
                ultimoIdGenerado = Math.max(ultimoIdGenerado, idActual);
            } catch (NumberFormatException e) {
                System.err.println("Error procesando documento: " + e.getMessage());
            }
        }

        // Retorna el siguiente ID disponible como String
        return String.valueOf(ultimoIdGenerado + 1);
    }
}

