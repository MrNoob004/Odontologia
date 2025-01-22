package org.ucuenca.Modelo.Documentos;

import org.ucuenca.Modelo.Documentos.Plantillas.*;
import org.ucuenca.Modelo.Usuarios.Ciudadano;
import org.ucuenca.Modelo.Usuarios.EstadoCivil;
import org.ucuenca.Modelo.Usuarios.Provincia;
import org.ucuenca.Modelo.Usuarios.Sexo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ActaDivorcio extends Acta implements ActualizadorEstadoCiudadano<EstadoCivil> {

    public Ciudadano getMarido() {
        return ciudadanosAsociados.stream().filter(
                ciudadano -> ciudadano.getSexo() == Sexo.HOMBRE
        ).findFirst().orElse(null);
    }

    public Ciudadano getMujer() {
        return ciudadanosAsociados.stream().filter(
                ciudadano -> ciudadano.getSexo() == Sexo.MUJER
        ).findFirst().orElse(null);
    }

    public ActaDivorcio(
            Ciudadano solicitante, Provincia provinciaEmitido, Ciudadano ciudadanoAsociado,
            Collection<Ciudadano> ciudadanosAsociados,
            LocalDate fechaEvento, Provincia lugarEvento,
            String observaciones) {
        super(solicitante, provinciaEmitido, ciudadanoAsociado, ciudadanosAsociados, fechaEvento, lugarEvento, observaciones);
    }

    public ActaDivorcio(
            Ciudadano solicitante, Provincia provinciaEmitido, Ciudadano ciudadanoAsociado,
            Collection<Ciudadano> ciudadanosAsociados, LocalDate fechaEvento,
            Provincia lugarEvento, String observaciones, String id) {
        super(solicitante, provinciaEmitido, ciudadanoAsociado, ciudadanosAsociados, fechaEvento, lugarEvento, observaciones, id);
    }

    @Override
    public Collection<Ciudadano> asociarCiudadanos(Collection<Ciudadano> asociados) {
        List<Ciudadano> faltantes = new ArrayList<>();

        for (Ciudadano c : asociados) {
            try {
                c.getDocumentosAsociados().add(this);
                modificarEstado(EstadoCivil.DIVORCIADO, c);

            } catch (NullPointerException e) {
                faltantes.add(c);
                System.err.println("No se pudo acceder a los documentos de " + c.toString());
            } catch (RequisitoIncumplidoException e) {
                faltantes.add(c);
                System.err.println(e.getMessage());
            }
        }

        return faltantes;
    }


    @Override
    public boolean invalidarAnterior(Collection<DocumentoLegal> anteriores) {

        List<ActaDivorcio> actasDefuncion = anteriores.stream().filter(
                (documentoLegal) -> documentoLegal instanceof ActaDivorcio
        ).map(
                (documentoLegal) -> (ActaDivorcio) documentoLegal
        ).toList();


        for (ActaDivorcio actaDefuncion : actasDefuncion) {
            if (actaDefuncion.estadoDocumento == EstadoDocumento.VIGENTE)
                actaDefuncion.setEstadoDocumento(EstadoDocumento.VENCIDO);
        }


        return false;
    }

    @Override
    public boolean modificarEstado(EstadoCivil nuevoEstado, Ciudadano ciudadano) throws RequisitoIncumplidoException {
        if (ciudadano.getEstadoCivil() == nuevoEstado)
            return false;


        if (!ciudadano.estaVivo())
            throw new RequisitoIncumplidoException(ciudadano, "no esta vivo");

        ciudadano.setEstadoCivil(nuevoEstado);
        return true;
    }


}
