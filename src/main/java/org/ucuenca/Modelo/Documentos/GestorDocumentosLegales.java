package org.ucuenca.Modelo.Documentos;

import org.ucuenca.Modelo.Documentos.Plantillas.DocumentoLegal;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public class GestorDocumentosLegales<D extends DocumentoLegal> implements Serializable {
    private int ultimoIdGenerado;

    /**
     * Genera un ID según el elemento más reciente de {@code documentos}
     *
     * @param documentos Lista de documentos de tipo D.
     * @return El siguiente ID disponible como String (n+1).
     */
    public String generarId(Collection<D> documentos) {
        for (D documento : documentos) {
            try {
                String id = documento.getIdDocumento();
                int idActual = Integer.parseInt(id); // Convierte el ID de String a entero
                ultimoIdGenerado = Math.max(ultimoIdGenerado, idActual);
            } catch (NumberFormatException e) {
                System.err.println("Error procesando documento: " + e.getMessage());
            }
        }

        // Retorna el siguiente ID disponible como String
        return String.valueOf(ultimoIdGenerado + 1);
    }

    public String generarId(Map<String, DocumentoLegal> documentos) {
        for (Map.Entry<String, DocumentoLegal> documento : documentos.entrySet()) {
            try {
                String id = documento.getValue().getIdDocumento();
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

