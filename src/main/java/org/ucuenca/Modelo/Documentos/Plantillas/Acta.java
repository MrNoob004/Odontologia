package org.ucuenca.Modelo.Documentos.Plantillas;

import org.ucuenca.Modelo.Usuarios.Ciudadano;
import org.ucuenca.Modelo.Usuarios.Provincia;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Contiene información de trámites o citas, puede estar asociado a varios Ciudadanos,
 * no tienen necesariamente fecha de caducidad
 */
public abstract class Acta extends DocumentoLegal implements Comparable<Acta>, Cloneable {

    /**
     * Conjunto de personas que involucra el acta, como solicitante, damnificado, cónyugue,etc
     */
    protected Collection<Ciudadano> ciudadanosAsociados;

    /**
     * Año/Mes/Día donde se desarrolla el evento, NO la emisión
     */
    protected LocalDate fechaEvento;

    /**
     * {@link org.ucuenca.Modelo.Usuarios.Provincia} donde se desarrolló el evento
     */
    protected Provincia lugarEvento;

    /**
     * Descripción del evento o anotaciones del trámite
     */
    protected String observaciones;

    public Acta(Ciudadano solicitante, Provincia provinciaEmitido, Ciudadano ciudadanoAsociado) {
        super(solicitante, provinciaEmitido, ciudadanoAsociado);
    }

    public Acta(Ciudadano solicitante, Provincia provinciaEmitido, Ciudadano ciudadanoAsociado, String id) {
        super(solicitante, provinciaEmitido, ciudadanoAsociado, id);
    }

    public Acta(Ciudadano solicitante, Provincia provinciaEmitido, Ciudadano ciudadanoAsociado, Collection<Ciudadano> ciudadanosAsociados, LocalDate fechaEvento, Provincia lugarEvento, String observaciones, String id) {
        super(solicitante, provinciaEmitido, ciudadanoAsociado, id);
        this.ciudadanosAsociados = ciudadanosAsociados;
        this.fechaEvento = fechaEvento;
        this.lugarEvento = lugarEvento;
        this.observaciones = observaciones;
    }

    public Acta(Ciudadano solicitante, Provincia provinciaEmitido, Ciudadano ciudadanoAsociado, Collection<Ciudadano> ciudadanosAsociados, LocalDate fechaEvento, Provincia lugarEvento, String observaciones) {
        super(solicitante, provinciaEmitido, ciudadanoAsociado);
        this.ciudadanosAsociados = ciudadanosAsociados;
        this.fechaEvento = fechaEvento;
        this.lugarEvento = lugarEvento;
        this.observaciones = observaciones;
    }


    /**
     * Añade el acta a la lista de documentos de cada elemento de {@code asociados}
     *
     * @return El conjunto de ciudadanos a los que <b><i>no fue</i></b> asociado el acta
     * @throws Exception Si no existe alguna persona o ya tiene un acta anterior activa.
     *                   En caso de que no se pueda asociar a todos los elementos de {@code asociados}, retorna a los que <b>NO</b> fueron
     *                   asociados
     */
    abstract protected Collection<Ciudadano> asociarCiudadanos(Collection<Ciudadano> asociados) throws Exception;

    /**
     * Comparación default por {@link #idDocumento}
     */
    @Override
    public int compareTo(Acta o) {
        return this.idDocumento.compareTo(o.idDocumento);
    }

    @Override
    public Acta clone() throws CloneNotSupportedException {
        return (Acta) super.clone();
    }

    /**
     * Crea un nuevo documento, actualizando <b>solo</b> la fecha de emisión y su estado a {@link EstadoDocumento#VIGENTE}
     *
     * @return Nueva Acta donde los datos son los mismos excepto por la fecha de emisión y estado
     * @throws RuntimeException especificado en {@link Cloneable}
     */
    @Override
    public Acta renovar() throws RuntimeException {
        Acta nueva;
        try {
            nueva = this.clone();
            nueva.setEstadoDocumento(EstadoDocumento.VIGENTE);
            nueva.setFechaEmision(LocalDate.now());

        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.getMessage());
        }

        return nueva;
    }

    public Collection<Ciudadano> getCiudadanosAsociados() {
        return ciudadanosAsociados;
    }

    public void setCiudadanosAsociados(Collection<Ciudadano> ciudadanosAsociados) {
        this.ciudadanosAsociados = ciudadanosAsociados;
    }

    public LocalDate getFechaEvento() {
        return fechaEvento;
    }

    public Provincia getLugarEvento() {
        return lugarEvento;
    }

    public String getObservaciones() {
        return observaciones;
    }

}
