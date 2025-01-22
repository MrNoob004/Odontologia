package org.ucuenca.Modelo.Documentos;

import org.ucuenca.Modelo.Documentos.Plantillas.Acta;
import org.ucuenca.Modelo.Documentos.Plantillas.DocumentoLegal;
import org.ucuenca.Modelo.Documentos.Plantillas.EstadoDocumento;
import org.ucuenca.Modelo.Usuarios.*;

import java.time.LocalDate;
import java.util.*;


public class ActaNacimiento extends Acta {


    Cedula cedulaMadre;
    Cedula cedulaPadre;
    /**
     * Nombres y Apellidos seleccionados al momento de <i>nacimiento</i> de la persona
     */
    String nombres, apellidos;

    /**
     * Crea segun {@link Acta} <b>sin</b> necesidad de un padre reconocido
     */
    public ActaNacimiento(
            Ciudadano solicitante, Provincia provinciaEmitido,
            Ciudadano ciudadanoAsociado, Collection<Ciudadano> ciudadanosAsociados,
            LocalDate fechaEvento, Provincia lugarEvento, String observaciones,
            Cedula cedulaMadre, String nombres, String apellidos
    ) {
        super(solicitante, provinciaEmitido, ciudadanoAsociado, ciudadanosAsociados, fechaEvento, lugarEvento, observaciones);
        this.cedulaMadre = cedulaMadre.clone();
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    /**
     * Crea segun {@link Acta} asignando un padre
     */
    public ActaNacimiento(
            Ciudadano solicitante, Provincia provinciaEmitido,
            Ciudadano ciudadanoAsociado, Collection<Ciudadano> ciudadanosAsociados,
            LocalDate fechaEvento, Provincia lugarEvento, String observaciones,
            Cedula cedulaMadre, Cedula cedulaPadre,
            String nombres, String apellidos

    ) {
        super(solicitante, provinciaEmitido, ciudadanoAsociado, ciudadanosAsociados, fechaEvento, lugarEvento, observaciones);
        this.cedulaMadre = cedulaMadre.clone();
        this.cedulaPadre = cedulaPadre.clone();
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    /**
     * Crea segun {@link Acta} <b>sin</b> necesidad de un padre reconocido
     */
    public ActaNacimiento(
            Ciudadano solicitante, Provincia provinciaEmitido,
            Ciudadano ciudadanoAsociado, Collection<Ciudadano> ciudadanosAsociados,
            LocalDate fechaEvento, Provincia lugarEvento, String observaciones,
            Cedula cedulaMadre, String nombres, String apellidos, String id
    ) {
        super(solicitante, provinciaEmitido, ciudadanoAsociado, ciudadanosAsociados, fechaEvento, lugarEvento, observaciones,id);
        this.cedulaMadre = cedulaMadre.clone();
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    /**
     * Crea segun {@link Acta} asignando un padre
     */
    public ActaNacimiento(
            Ciudadano solicitante, Provincia provinciaEmitido,
            Ciudadano ciudadanoAsociado, Collection<Ciudadano> ciudadanosAsociados,
            LocalDate fechaEvento, Provincia lugarEvento, String observaciones,
            Cedula cedulaMadre, Cedula cedulaPadre,
            String nombres, String apellidos, String id

    ) {
        super(solicitante, provinciaEmitido, ciudadanoAsociado, ciudadanosAsociados, fechaEvento, lugarEvento, observaciones,id);
        this.cedulaMadre = cedulaMadre.clone();
        this.cedulaPadre = cedulaPadre.clone();
        this.nombres = nombres;
        this.apellidos = apellidos;
    }


    @Override
    protected Collection<Ciudadano> asociarCiudadanos(Collection<Ciudadano> asociados) throws Exception {
        List<Ciudadano> faltantes = new ArrayList<>();

        for (Ciudadano c : asociados) {
            try {
                c.getDocumentosAsociados().add(this);

            } catch (NullPointerException e) {
                faltantes.add(c);
                System.out.println("No se pudo acceder a los documentos de " + c.getCedula() + ":" + c.getNombres());
                if (!c.estaVivo() && c != ciudadanoAsociado)
                    throw new Exception("El ciudadano '" + c.getCedula().getIdUnico() + "': " + c.getNombres() + " no esta vivo");
            }
        }

        return faltantes;
    }


    @Override
    public boolean invalidarAnterior(Collection<DocumentoLegal> anteriores) {

        List<ActaNacimiento> actasNacimiento = anteriores.stream().filter(
                (documentoLegal) -> documentoLegal instanceof ActaNacimiento
        ).map(
                (documentoLegal) -> (ActaNacimiento) documentoLegal
        ).toList();


        for (ActaNacimiento actaNacimiento : actasNacimiento) {
            if (actaNacimiento.estadoDocumento == EstadoDocumento.VIGENTE)
                actaNacimiento.setEstadoDocumento(EstadoDocumento.INHABILITADO);
        }

        return true;
    }

    public Cedula getCedulaMadre() {
        return cedulaMadre;
    }

    public void setCedulaMadre(Cedula cedulaMadre) {
        this.cedulaMadre = cedulaMadre;
    }

    public Cedula getCedulaPadre() {
        return cedulaPadre;
    }

    public void setCedulaPadre(Cedula cedulaPadre) {
        this.cedulaPadre = cedulaPadre;
    }
}
