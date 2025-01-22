package org.ucuenca.Modelo.Documentos;

import org.ucuenca.Modelo.Documentos.Plantillas.*;
import org.ucuenca.Modelo.Usuarios.Ciudadano;
import org.ucuenca.Modelo.Usuarios.Provincia;

import java.awt.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Documento de identificación asociado a {@link org.ucuenca.Modelo.Usuarios.Ciudadano}
 * <p>
 * Su id NUI es {@link #idUnico}
 */
public class Pasaporte extends DocumentoId {
    /**
     * Cantidad de tiempo que está vigente el documento
     */
    public static final Period VIGENCIA_PASAPORTE = Period.ofYears(10);

    private TipoPasaporte tipoPasaporte;
    private String cedulaAsociada;
    private Image imagen;

    public Pasaporte(
            Ciudadano solicitante, Provincia provinciaEmitido,
            Ciudadano ciudadanoAsociado, String idDocumento,
            TipoPasaporte tipo, String idUnico
    ){
        super(solicitante,provinciaEmitido,ciudadanoAsociado,idDocumento, idUnico);
        this.fechaCaducidad = fechaEmision.plus(VIGENCIA_PASAPORTE);
        this.cedulaAsociada = ciudadanoAsociado.getCedula().getIdUnico();
        this.tipoPasaporte = tipo;
        this.tipoDocumento = TipoDocumentos.PASAPORTE;
    }

    @Override
    public LocalDate generarFechaCaducidad() {
        return this.fechaEmision.plus(VIGENCIA_PASAPORTE);
    }


    /**
     * Crea un nuevo Pasaporte
     *
     * @return Pasaporte con nuevos datos
     */
    @Override
    public Pasaporte renovar() {
        return null;
    }

    @Override
    public boolean invalidarAnterior(Collection<DocumentoLegal> anteriores) {
//        Filtrar los documentos del usuario para obtener solo sus cedulas
        if(anteriores == null)
            anteriores = new ArrayList<>();

        List<Pasaporte> pasaportes = anteriores.stream().filter(
                (documento) -> documento instanceof Pasaporte
        ).map(
                (documento) -> (Pasaporte) documento
        ).toList();

        for (Pasaporte pasaporte : pasaportes) {
            if (pasaporte.estaVigente()) {
                pasaporte.setEstadoDocumento(EstadoDocumento.VENCIDO);
            }
        }


        return true;
    }

}
