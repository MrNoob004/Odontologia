package org.ucuenca.Modelo.Citas.HorariosYCostos;

import java.util.ArrayList;
import java.util.List;

public class HorarioSemanal {
    private String nombre;
    private int numeroSemana;
    private List<Dia> dias;

    private static final int DIA_INICIO_JORNADA = 0;
    private static final int DIA_FIN_JORNADA = 4;
    private static final int DIA_FINAL = 6;

    public HorarioSemanal(String nombre, int numeroSemana, List<Dia> dias) {
        this.nombre = nombre;
        this.numeroSemana = numeroSemana;
        this.dias = dias;
    }

    public HorarioSemanal(List<Dia> dias) {
        this.nombre = "Semana sin nombre";
        this.numeroSemana = 4;
        this.dias = dias;
    }

    /**
     * Crea una semana regular donde
     * <p>
     * Lunes -> Viernes (0->4) siguen horarios regulares especificado en {@link Dia}
     * </p>
     * <p>
     * Sábado -> Domingo (5->6) son días inhábiles
     * </p>
     */
    public HorarioSemanal(String nombre, int numeroSemana) {
        this.nombre = nombre;
        this.numeroSemana = numeroSemana;
        this.dias = new ArrayList<>();
        crearSemanaRegular();
    }

    /**
     * Crea una semana con los días sobrantes necesarios. i.e Para crear los 2/3 últimos días del mes.
     * Todos los días tienen horarios regulares, especificados en {@link Dia}
     */
    public HorarioSemanal(int numDias) {
        this.dias = new ArrayList<>();
        for (int i = 0; i < numDias; i++) {
            dias.add(new Dia(false));
        }
    }

    private void crearSemanaRegular() {
        for (int i = DIA_INICIO_JORNADA; i < DIA_FIN_JORNADA; i++)
            dias.add(new Dia(false));

        for (int i = DIA_FIN_JORNADA; i < DIA_FINAL; i++)
            dias.add(new Dia(true));

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroSemana() {
        return numeroSemana;
    }

    public void setNumeroSemana(int numeroSemana) {
        this.numeroSemana = numeroSemana;
    }

    public List<Dia> getDias() {
        return dias;
    }

    public void setDias(List<Dia> dias) {
        this.dias = dias;
    }


}
