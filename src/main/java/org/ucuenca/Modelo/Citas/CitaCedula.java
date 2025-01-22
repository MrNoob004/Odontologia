package org.ucuenca.Modelo.Citas;

import org.ucuenca.Modelo.Citas.Plantillas.Cita;
import org.ucuenca.Modelo.Citas.Plantillas.Requisito;
import org.ucuenca.Modelo.Citas.Plantillas.TipoCitas;
import org.ucuenca.Modelo.Usuarios.Ciudadano;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CitaCedula extends Cita{

    public CitaCedula(
            LocalDateTime fecha, double precio,
            Ciudadano solicitante,
            String id) {
        super(fecha, precio, solicitante, id);
        this.requisitos = definirRequisitos();
        this.tipoCita = TipoCitas.CEDULA;
    }

    private List<Requisito> definirRequisitos() {
        List<Requisito> requisitos = new ArrayList<>();
        requisitos.add(new Requisito("vivo", true, "Debe estar vivo"));
        return requisitos;
    }

    @Override
    public boolean cancelar(Ciudadano ciudadano, Cita cita) {
        return false;
    }

    @Override
    public void addRequisito(Requisito requisito) {
    }

    @Override
    public CitaCedula renovar() {
        return null;
    }

    @Override
    public boolean invalidarAnterior(Collection<Cita> anteriores) {
        return false;
    }

}
