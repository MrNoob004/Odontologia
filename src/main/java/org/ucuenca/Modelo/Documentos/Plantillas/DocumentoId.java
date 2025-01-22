package org.ucuenca.Modelo.Documentos.Plantillas;

import org.ucuenca.Modelo.Usuarios.Ciudadano;
import org.ucuenca.Modelo.Usuarios.Provincia;

import java.time.LocalDate;
import java.util.Collection;


/**
 * Contiene un NUI como la cédula o pasaporte, identificador único que debe ser validado
 * El documento además tiene una fecha de caducidad
 */
public abstract class DocumentoId extends DocumentoLegal implements Comparable<DocumentoId>, Caducable {
    /**
     * LocalDate generada mediante la implementación de la interfaz
     */
    protected LocalDate fechaCaducidad;
    /**
     * Número Único de Identificación <b>NUI</b>, No confundir con el id de documento (Asociado a su emisión)
     */
    protected String idUnico;

    /**
     * Constructor default de un Documento Legal. la fecha de emisión es tomada de {@link LocalDate#now()} y automáticamente
     * se tiene {@link EstadoDocumento#VIGENTE}
     *
     * @param solicitante       Persona que solicita
     * @param provinciaEmitido  Provincia en la que se emite
     * @param ciudadanoAsociado Persona a la cual se asocia el documento de identidad
     */
    public DocumentoId(Ciudadano solicitante, Provincia provinciaEmitido, Ciudadano ciudadanoAsociado, String idUnico) {
        super(solicitante, provinciaEmitido, ciudadanoAsociado);
        this.idUnico = idUnico;
    }

    public DocumentoId(Ciudadano solicitante, Provincia provinciaEmitido, Ciudadano ciudadanoAsociado, String idDocumento, String idUnico) {
        super(solicitante, provinciaEmitido, ciudadanoAsociado, idDocumento);
        this.idUnico = idUnico;
    }

    /**
     * Revisa que la fecha actual sea antes de {@link #fechaCaducidad} y el estado del documento sea {@link EstadoDocumento#VIGENTE}
     */
    @Override
    public boolean estaVigente() {
        return LocalDate.now().isBefore(fechaCaducidad) && this.estadoDocumento == EstadoDocumento.VIGENTE;
    }

    /**
     * Comparación default por {@link #idUnico}
     */
    @Override
    public int compareTo(DocumentoId o) {
        return this.idUnico.compareToIgnoreCase(
                o.getIdUnico()
        );
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }


    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setIdUnico(String idUnico) {
        this.idUnico = idUnico;
    }

    public String getIdUnico() {
        return idUnico;
    }

    /**
     * Asigna el documento a una persona, añadiéndolo a su lista de documentos legales.
     * <p>
     * Antes de esto, usar {@link #invalidarAnterior(Collection documentos)} para que
     * el documento a emitir sea el vigente
     * </p>
     *
     * @return {@code true} si se puede añadir el documento, {@code false} caso contrario. Especificado en
     * {@link Collection#add}
     */

    //TODO Verificar casos en los que no se pueda asociar
    public boolean asociarCiudadano(Ciudadano asociado) {
        Collection<DocumentoLegal> documentosCiudadano = asociado.getDocumentosAsociados();

        try {
            documentosCiudadano.add(this);
            return true;
        } catch (Exception e) {
            return false;
        }

    }


}
