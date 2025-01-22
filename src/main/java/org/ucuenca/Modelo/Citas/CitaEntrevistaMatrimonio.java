package org.ucuenca.Modelo.Citas;

import org.ucuenca.Modelo.Citas.Plantillas.Cita;
import org.ucuenca.Modelo.Citas.Plantillas.Requisito;
import org.ucuenca.Modelo.Citas.Plantillas.TipoCitas;
import org.ucuenca.Modelo.ModeloPrincipal;
import org.ucuenca.Modelo.Usuarios.Ciudadano;
import org.ucuenca.Modelo.Usuarios.EstadoCivil;
import org.ucuenca.Modelo.Usuarios.Sexo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Modelo correspondiente al agendamiento para entrevista de matrimonio
 */
public class CitaEntrevistaMatrimonio extends Cita {
    private ModeloPrincipal principal;
    private String cedulaConyugue;

    /**
     * Constructor default
     *
     * @param fecha       Año/Mes/Día hora MINUTO
     * @param precio      bueno, presio
     * @param solicitante Persona que solicita el acta
     */
    public CitaEntrevistaMatrimonio(
            LocalDateTime fecha, double precio,
            Ciudadano solicitante, String cedulaConyugue) {
        super(fecha, precio, solicitante);
        this.cedulaConyugue = cedulaConyugue;
        this.requisitos = definirRequisitosMatrimonio();
        this.tipoCita = TipoCitas.MATRIMONIO;
    }

    public CitaEntrevistaMatrimonio(
            LocalDateTime fecha, double precio,
            Ciudadano solicitante, String cedulaConyugue,
            String id) {
        super(fecha, precio, solicitante, id);
        this.cedulaConyugue = cedulaConyugue;
        this.requisitos = definirRequisitosMatrimonio();
        this.tipoCita = TipoCitas.MATRIMONIO;
    }

    private List<Requisito> definirRequisitosMatrimonio() {
        principal = ModeloPrincipal.getInstancia();
        Ciudadano conyuge = principal.getCiudadanos().get(cedulaConyugue);

        List<Requisito> requisitosMatrimonio = new ArrayList<>();
        requisitosMatrimonio.add(new Requisito("vivo", true, "Debe estar vivo"));
        requisitosMatrimonio.add(new Requisito("edad", (edad) -> (int) edad >= 18, "Debe ser mayor de edad"));
        requisitosMatrimonio.add(new Requisito("estadoCivil", (estadoCivil) -> estadoCivil != EstadoCivil.CASADO, "Debe constar como soltero"));
        requisitosMatrimonio.add(new Requisito(
                "sexo",
                (sexo) -> (sexo == Sexo.HOMBRE)
                        ? conyuge.getSexo() == Sexo.MUJER
                        : conyuge.getSexo() == Sexo.HOMBRE,
                "El cónyugue debe ser del sexo opuesto")
        );

        setRequisitosFamiliares(requisitosMatrimonio, conyuge);


        return requisitosMatrimonio;
    }

    private void setRequisitosFamiliares(List<Requisito> requisitosMatrimonio, Ciudadano conyuge) {
        if (solicitante.getMadre() != null) {
            requisitosMatrimonio.add(new Requisito("madre",
                    (madre) -> !((Ciudadano) madre).getCedula().getIdUnico().equals(cedulaConyugue),
                    "No se puede casar con un pariente directo")
            );

            if (conyuge.getMadre() != null) {
                requisitosMatrimonio.add(new Requisito("madre",
                        (madre) -> !((Ciudadano) madre).getCedula().getIdUnico().equals(
                                conyuge.getMadre().getCedula().getIdUnico()),
                        "No se puede casar con un pariente directo (Herman@)")
                );
            }
        }
        if (solicitante.getPadre() != null)
            requisitosMatrimonio.add(new Requisito("padre",
                    (padre) -> !((Ciudadano) padre).getCedula().getIdUnico().equals(cedulaConyugue),
                    "No se puede casar con un pariente directo"));

    }

    @Override
    public boolean cancelar(Ciudadano ciudadano, Cita cita) {
        return false;
    }

    @Override
    public void addRequisito(Requisito requisito) {
    }

    @Override
    public CitaEntrevistaMatrimonio renovar() {
        return null;
    }

    @Override
    public boolean invalidarAnterior(Collection<Cita> anteriores) {
        return false;
    }

    public void setCedulaConyugue(String cedulaConyugue) {
        this.cedulaConyugue = cedulaConyugue;
    }
}
