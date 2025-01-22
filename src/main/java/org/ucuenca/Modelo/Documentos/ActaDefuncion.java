package org.ucuenca.Modelo.Documentos;

import org.ucuenca.Modelo.Documentos.Plantillas.*;
import org.ucuenca.Modelo.Usuarios.Ciudadano;
import org.ucuenca.Modelo.Usuarios.EstadoCivil;
import org.ucuenca.Modelo.Usuarios.Provincia;
import org.ucuenca.Modelo.Usuarios.TipoDeceso;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


public class ActaDefuncion extends Acta implements ActualizadorEstadoCiudadano<Object> {


    TipoDeceso causaMuerte;

    public ActaDefuncion(Ciudadano solicitante, Provincia provinciaEmitido, Ciudadano ciudadanoAsociado, Collection<Ciudadano> ciudadanosAsociados, LocalDate fechaEvento, Provincia lugarEvento, String observaciones, TipoDeceso tipoDeceso) {
        super(solicitante, provinciaEmitido, ciudadanoAsociado, ciudadanosAsociados, fechaEvento, lugarEvento, observaciones);
        this.causaMuerte = tipoDeceso;
    }

    public ActaDefuncion(
            Ciudadano solicitante, Provincia provinciaEmitido,
            Ciudadano ciudadanoAsociado, Collection<Ciudadano> ciudadanosAsociados,
            LocalDate fechaEvento, Provincia lugarEvento, String observaciones,
            TipoDeceso tipoDeceso, String idDocumento) {
        super(solicitante, provinciaEmitido, ciudadanoAsociado, ciudadanosAsociados, fechaEvento, lugarEvento, observaciones, idDocumento);
        this.causaMuerte = tipoDeceso;
    }

    @Override
    public Collection<Ciudadano> asociarCiudadanos(Collection<Ciudadano> asociados) throws Exception {
        List<Ciudadano> faltantes = new ArrayList<>();

        for (Ciudadano c : asociados) {
            try {
                c.getDocumentosAsociados().add(this);

//                Cambiar su estado a muerto
                if (ciudadanoAsociado.getCedula().getIdUnico().equals(c.getCedula().getIdUnico()))
                    modificarEstado(false, c);

//                Cambiar su conyugue a viud@
                if (c.getCedula().tieneConyugue())
                    if (c.getCedula().getConyugue() == ciudadanoAsociado.getCedula().getConyugue())
                        modificarEstado(EstadoCivil.VIUDO, c.getCedula().getConyugue());

            } catch (NullPointerException e) {
                faltantes.add(c);
                System.out.println("No se pudo acceder a los documentos de " + c.toString());
            } catch (RequisitoIncumplidoException e) {
                faltantes.add(c);
                System.err.println(e.getMessage());
            }
        }

        return faltantes;
    }


    @Override
    public boolean invalidarAnterior(Collection<DocumentoLegal> anteriores) {

        List<ActaDefuncion> actasDefuncion = anteriores.stream().filter(
                (documentoLegal) -> documentoLegal instanceof ActaDefuncion
        ).map(
                (documentoLegal) -> (ActaDefuncion) documentoLegal
        ).toList();


        for (ActaDefuncion actaDefuncion : actasDefuncion) {
            if (actaDefuncion.estadoDocumento == EstadoDocumento.VIGENTE)
                actaDefuncion.setEstadoDocumento(EstadoDocumento.INHABILITADO);
        }


        return false;
    }

    @Override
    public boolean modificarEstado(Object nuevoEstado, Ciudadano ciudadano) throws RequisitoIncumplidoException {

        if (nuevoEstado instanceof EstadoCivil) {
            if (!ciudadano.estaVivo())
                throw new RequisitoIncumplidoException(ciudadano, " no está vivo");

            ciudadano.setEstadoCivil((EstadoCivil) nuevoEstado);
        }

        if (nuevoEstado.getClass() == Boolean.class) {
            if (!ciudadano.estaVivo())
                return false;

            ciudadano.setVivo((boolean) nuevoEstado);
        }
        return true;
    }


    public TipoDeceso getCausaMuerte() {
        return causaMuerte;
    }

    public void setCausaMuerte(TipoDeceso causaMuerte) {
        this.causaMuerte = causaMuerte;
    }
}
