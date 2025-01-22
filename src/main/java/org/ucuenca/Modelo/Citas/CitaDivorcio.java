package org.ucuenca.Modelo.Citas;

import org.ucuenca.Modelo.Citas.Plantillas.Cita;
import org.ucuenca.Modelo.Citas.Plantillas.Requisito;
import org.ucuenca.Modelo.Citas.Plantillas.TipoCitas;
import org.ucuenca.Modelo.Usuarios.Ciudadano;
import org.ucuenca.Modelo.Usuarios.EstadoCivil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CitaDivorcio extends Cita {
    private Ciudadano conyugue;

    public CitaDivorcio(
            LocalDateTime fecha, double precio,
            Ciudadano solicitante, Ciudadano conyugue) {
        super(fecha, precio, solicitante);
        this.conyugue = conyugue;
        this.requisitos = definirRequisitosDivorcio();
        this.tipoCita = TipoCitas.DIVORCIO;
    }

    public CitaDivorcio(
            LocalDateTime fecha, double precio,
            Ciudadano solicitante, Ciudadano conyugue,
            String id) {
        super(fecha, precio, solicitante, id);
        this.conyugue = conyugue;
        this.requisitos = definirRequisitosDivorcio();
        this.tipoCita = TipoCitas.DIVORCIO;
    }

    private List<Requisito> definirRequisitosDivorcio() {
        List<Requisito> requisitosDivorcio = new ArrayList<>();
        requisitosDivorcio.add(new Requisito("vivo", true, "Debe estar vivo"));
        requisitosDivorcio.add(new Requisito("estadoCivil", (estadoCivil) -> estadoCivil == EstadoCivil.CASADO, "Debe constar como casado"));
        requisitosDivorcio.add(new Requisito("estadoCivil",
                (estadoCivil) -> (conyugue == null)
                        ? estadoCivil == EstadoCivil.CASADO
                        : conyugue.getEstadoCivil() == EstadoCivil.CASADO,
                "Debe constar con un cónyugue registrado")
        );
        return requisitosDivorcio;
    }

    @Override
    public boolean cancelar(Ciudadano ciudadano, Cita cita) {
        return false;
    }

    @Override
    public void addRequisito(Requisito requisito) {
    }

    @Override
    public CitaDivorcio renovar() {
        return null;
    }

    @Override
    public boolean invalidarAnterior(Collection<Cita> anteriores) {
        return false;
    }

}
