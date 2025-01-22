package org.ucuenca.Modelo.Citas.Plantillas;

import org.ucuenca.Modelo.Citas.Agencia;
import org.ucuenca.Modelo.Citas.GestorCitas;
import org.ucuenca.Modelo.Documentos.Plantillas.Identificable;
import org.ucuenca.Modelo.Documentos.Plantillas.Renovable;
import org.ucuenca.Modelo.Usuarios.Ciudadano;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

public abstract class Cita implements ProcesoRequisitado, Serializable, Renovable<Cita>, Agendable<Cita>, Identificable<Cita> {

    public static final DateTimeFormatter FORMATO_FECHA_CITA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    protected LocalDateTime fecha;
    /**
     * Precio final, usualmente obtenido de {@link org.ucuenca.Modelo.Citas.HorariosYCostos.Precios}
     */
    protected double precio;
    protected Ciudadano solicitante;
    protected List<Requisito> requisitos;

    protected Agencia agencia;

    protected String idCita;

    protected TipoCitas tipoCita;


    public void setTipoCita(TipoCitas tipoCita) {
        this.tipoCita = tipoCita;
    }

    /**
     * Constructor default
     *
     * @param fecha  Año/Mes/Día Hora/Minuto
     * @param precio bueno, presio
     */
    public Cita(LocalDateTime fecha, double precio, Ciudadano solicitante) {
        this.fecha = fecha;
        this.precio = precio;
        this.solicitante = solicitante;
    }

    public Cita(LocalDateTime fecha, double precio, Ciudadano solicitante, Collection<Cita> anteriores) {
        this.fecha = fecha;
        this.precio = precio;
        this.solicitante = solicitante;
        this.idCita = generarIdentificacion(anteriores);
    }

    public Cita(LocalDateTime fecha, double precio, Ciudadano solicitante, String idCita) {
        this.fecha = fecha;
        this.precio = precio;
        this.solicitante = solicitante;
        this.idCita = idCita;
    }


    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Ciudadano getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Ciudadano solicitante) {
        this.solicitante = solicitante;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }

    @Override
    public void addRequisito(Requisito requisito) {
        requisitos.add(requisito);
    }

    @Override
    public List<Requisito> getRequisitos() {
        return requisitos;
    }

    @Override
    public void setRequisitos(List<Requisito> requisitos) {
        this.requisitos = requisitos;
    }

    /**
     * @return fecha asignada para la atenición de la cita
     * Año/Mes/Día - Hora/Min
     */
    @Override
    public LocalDateTime getFecha() {
        return fecha;
    }

    /**
     * Revisa la disponibilidad de horarios en la agencia y asigna la fecha para su atención
     *
     * @return {@code true} Si se pudo agendar correctamente. {@code false} Si la fecha es inválida
     * o no se puede agendar para ese momento la Cita
     */
    @Override
    public boolean setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
        return true;
    }

    @Override
    public String generarIdentificacion(Collection<Cita> anteriores) {
        GestorCitas<Cita> gestorCitas = new GestorCitas<>();
        return gestorCitas.generarId(anteriores);
    }

    public String getIdCita() {
        return String.valueOf(idCita);
    }

    public void setIdCita(String idCita) {
        this.idCita = idCita;
    }

    public TipoCitas getTipoCita() {
        return tipoCita;
    }

    @Override
    public String toString() {
        return idCita + ":" + tipoCita.toString() + ": " + "Fecha:" + fecha.format(FORMATO_FECHA_CITA) + ", $" + precio + ":" + solicitante.toString();
    }
}
