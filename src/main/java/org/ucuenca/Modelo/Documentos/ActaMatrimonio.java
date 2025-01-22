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


public class ActaMatrimonio extends Acta implements ActualizadorEstadoCiudadano<EstadoCivil> {
    private Ciudadano marido = null, mujer = null;

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
    /**
     * Crea un acta con los datos especificados
     *
     * @param ciudadanoAsociado   Puede ser marido o mujer
     * @param ciudadanosAsociados SOLO marido y mujer
     */
    public ActaMatrimonio(
            Ciudadano solicitante, Provincia provinciaEmitido,
            Ciudadano ciudadanoAsociado, Collection<Ciudadano> ciudadanosAsociados,
            LocalDate fechaEvento, Provincia lugarEvento, String observaciones) {

        super(solicitante, provinciaEmitido, ciudadanoAsociado, ciudadanosAsociados, fechaEvento, lugarEvento, observaciones);
    }

    /**
     * Crea un acta con los datos especificados
     *
     * @param ciudadanoAsociado   Puede ser marido o mujer
     * @param ciudadanosAsociados SOLO marido y mujer
     */
    public ActaMatrimonio(
            Ciudadano solicitante, Provincia provinciaEmitido,
            Ciudadano ciudadanoAsociado, Collection<Ciudadano> ciudadanosAsociados,
            LocalDate fechaEvento, Provincia lugarEvento, String observaciones, String id) {

        super(solicitante, provinciaEmitido, ciudadanoAsociado, ciudadanosAsociados, fechaEvento, lugarEvento, observaciones, id);
    }

    private static Ciudadano getConyugue(Collection<Ciudadano> ciudadanosAsociados, Sexo sexoConyugue) {
        return ciudadanosAsociados.stream().filter(
                ciudadano -> ciudadano.getSexo() == sexoConyugue
        ).findFirst().orElse(null);
    }

    /**
     * Cambia los estados civiles de los conyugues a {@link EstadoCivil#CASADO} y añade el acta a los documentos de ambos
     *
     * @throws NullPointerException Si no se puede acceder a los documentos de algun cónyugue
     */
    @Override
    public Collection<Ciudadano> asociarCiudadanos(Collection<Ciudadano> asociados) {
        List<Ciudadano> faltantes = new ArrayList<>();
        this.marido = getConyugue(ciudadanosAsociados, Sexo.HOMBRE);
        this.mujer = getConyugue(ciudadanosAsociados, Sexo.MUJER);

        for (Ciudadano c : asociados) {
            try {
                c.getDocumentosAsociados().add(this);
                modificarEstado(EstadoCivil.CASADO, c);

            } catch (NullPointerException e) {
                faltantes.add(c);
                System.err.println("No se puede acceder a los documentos de " + c);
            } catch (Exception e) {
                faltantes.add(c);
                System.err.println(e.getMessage());
            }
        }
        if (faltantes.isEmpty()) {
            marido.getCedula().setConyugue(mujer);
            mujer.getCedula().setConyugue(marido);
        }
        return faltantes;
    }


    @Override
    public boolean invalidarAnterior(Collection<DocumentoLegal> anteriores) {

        List<ActaMatrimonio> actasDefuncion = anteriores.stream().filter(
                (documentoLegal) -> documentoLegal instanceof ActaMatrimonio
        ).map(
                (documentoLegal) -> (ActaMatrimonio) documentoLegal
        ).toList();


        for (ActaMatrimonio actaDefuncion : actasDefuncion) {
            if (actaDefuncion.estadoDocumento == EstadoDocumento.VIGENTE)
                actaDefuncion.setEstadoDocumento(EstadoDocumento.VENCIDO);
        }


        return false;
    }

    /**
     * Trata de cambiar el estado de los conyugues a {@link EstadoCivil#CASADO}
     *
     * @return {@code true} si se puede cambiar, {@code false} si ya consta como casado
     * @throws RequisitoIncumplidoException Si algún cónyugue consta como casado o muerto
     */
    @Override
    public boolean modificarEstado(EstadoCivil nuevoEstado, Ciudadano ciudadano) throws RequisitoIncumplidoException {
        if (!ciudadano.estaVivo())
            throw new RequisitoIncumplidoException(ciudadano + " no está vivo");

        if (ciudadano.getEstadoCivil() == EstadoCivil.CASADO)
            throw new RequisitoIncumplidoException(ciudadano, " ya consta como casado");

        ciudadano.setEstadoCivil(nuevoEstado);
        return true;
    }


}
