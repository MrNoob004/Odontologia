package org.ucuenca.Modelo.Documentos.Plantillas;

import org.ucuenca.Modelo.Documentos.GestorDocumentosLegales;
import org.ucuenca.Modelo.Usuarios.Ciudadano;
import org.ucuenca.Modelo.Usuarios.Provincia;

import java.io.Serializable;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;


public abstract class DocumentoLegal implements Renovable<DocumentoLegal>, Identificable<DocumentoLegal>, Serializable {
    protected LocalDate fechaEmision;

    public static final DateTimeFormatter FORMATO_FECHA_DOC = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * El Identificador del documento generado, ya sea {@link Acta} o {@link DocumentoId}
     */
    protected String idDocumento;

    protected TipoDocumentos tipoDocumento;
    /**
     * Persona que ha solicitado la emisión del documento
     */
    protected Ciudadano solicitante;

    protected Provincia provinciaEmitido;

    protected EstadoDocumento estadoDocumento;
    /**
     * El ciudadano al que se le asocia PRINCIPALMENTE el documento,
     * no necesariamente su solicitante.
     * i.e <b>Dueño</b> del documento
     */
    protected Ciudadano ciudadanoAsociado;

    /**
     * Constructor default de un Documento Legal. la fecha de emisión es tomada de {@link LocalDate#now()} y automáticamente
     * se tiene {@link EstadoDocumento#VIGENTE}
     */
    public DocumentoLegal(Ciudadano solicitante, Provincia provinciaEmitido, Ciudadano ciudadanoAsociado) {
        this.fechaEmision = LocalDate.now();
        this.solicitante = solicitante;
        this.provinciaEmitido = provinciaEmitido;
        this.estadoDocumento = EstadoDocumento.VIGENTE;
        this.ciudadanoAsociado = ciudadanoAsociado;
    }

    /**
     * Constructor default de un Documento Legal. la fecha de emisión es tomada de {@link LocalDate#now()} y automáticamente
     * se tiene {@link EstadoDocumento#VIGENTE}
     */
    public DocumentoLegal(Ciudadano solicitante, Provincia provinciaEmitido, Ciudadano ciudadanoAsociado, String idDocumento) {
        this.fechaEmision = LocalDate.now();
        this.solicitante = solicitante;
        this.provinciaEmitido = provinciaEmitido;
        this.estadoDocumento = EstadoDocumento.VIGENTE;
        this.ciudadanoAsociado = ciudadanoAsociado;
        this.idDocumento = idDocumento;
    }

    /**
     * @return id cardinal/ordinal generado mediante {@link GestorDocumentosLegales}
     */
    @Override
    public String generarIdentificacion(Collection<DocumentoLegal> anteriores) {
        GestorDocumentosLegales<DocumentoLegal> generadorId = new GestorDocumentosLegales<>();
        return generadorId.generarId(anteriores);
    }


    /* --- Getters y Setters --- */
    public EstadoDocumento getEstadoDocumento() {
        return estadoDocumento;
    }

    public void setEstadoDocumento(EstadoDocumento estadoDocumento) {
        this.estadoDocumento = estadoDocumento;
    }

    public Provincia getProvinciaEmitido() {
        return provinciaEmitido;
    }

    public void setProvinciaEmitido(Provincia provinciaEmitido) {
        this.provinciaEmitido = provinciaEmitido;
    }

    public Ciudadano getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Ciudadano solicitante) {
        this.solicitante = solicitante;
    }

    public Ciudadano getCiudadanoAsociado() {
        return ciudadanoAsociado;
    }

    public void setCiudadanoAsociado(Ciudadano ciudadanoAsociado) {
        this.ciudadanoAsociado = ciudadanoAsociado;
    }

    public TipoDocumentos getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumentos tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    /*protected List<DocumentoLegal> getDocumentosAnteriores(Class<? extends DocumentoLegal> clase, Collection<DocumentoLegal> documentosAnteriores) {

        List< clase > docsAnteriores = documentosAnteriores.stream().filter(
                (doc) -> doc instanceof clase
        ).map(
                (doc) -> (clase) doc
        ).toList();


        return docsAnteriores;
    }*/


}
