package org.ucuenca.Modelo.Citas.HorariosYCostos;

import java.util.ArrayList;
import java.util.List;

/**
 * Representación de un día en una jornada semanal. Contiene horas de apertura, break y cierre
 * <p>
 * {@link Dia#HORA_APERTURA_LABORAL}
 * {@link Dia#HORA_INICIO_BREAK_LABORAL}
 * {@link Dia#HORA_FIN_BREAK_LABORAL}
 * {@link Dia#HORA_CIERRE_LABORAL}
 * </p>
 */
public class Dia {
    public final static int HORA_APERTURA_LABORAL = 8;
    public final static int HORA_INICIO_BREAK_LABORAL = 12;
    public final static int HORA_FIN_BREAK_LABORAL = 13;
    public final static int HORA_CIERRE_LABORAL = 17;

    public List<Hora> getHoras() {
        return horas;
    }

    public boolean esDiaFestivo() {
        return esDiaFestivo;
    }

    private final String separacionHoras = ":00";
    private List<Hora> horas;
    private boolean esDiaFestivo;

    public Dia(List<Hora> horas) {
        this.horas = horas;
    }


    public Dia() {
        this.horas = new ArrayList<>();
        crearDiaLaboral();
    }

    /**
     * Crea un horario de la jornada laboral de un día. Si es un día festivo <b><i>todas</i></b>
     * las 24 horas son marcadas sin disponibilidad.
     * <p>
     * Caso contrario
     * </p>
     * <p>
     * Las horas entre <i>apertura</i>, <i>break</i> y <i>cierre</i> estarán disponibles <p>
     * Las horas entre 00:00->apertura, inicio break -> fin break, cierre-> 23:00 <b>sin disponibilidad<b/>
     * </p>
     * </p>
     */
    public Dia(boolean esDiaFestivo) {
        this.horas = new ArrayList<>();

        if (esDiaFestivo)
            for (int i = 0; i < 24; i++)
                horas.add(i, new Hora(i + separacionHoras, false));
        else
            crearDiaLaboral();
    }

    private void crearDiaLaboral() {
        for (int i = 0; i < HORA_APERTURA_LABORAL; i++)
            horas.add(new Hora(i + separacionHoras, false));

        for (int i = HORA_APERTURA_LABORAL; i < HORA_INICIO_BREAK_LABORAL; i++)
            horas.add(new Hora(i + separacionHoras, true));

        for (int i = HORA_INICIO_BREAK_LABORAL; i < HORA_FIN_BREAK_LABORAL; i++)
            horas.add(new Hora(i + separacionHoras, false));

        for (int i = HORA_FIN_BREAK_LABORAL; i < HORA_CIERRE_LABORAL; i++)
            horas.add(new Hora(i + separacionHoras, true));

        for (int i = HORA_CIERRE_LABORAL; i < 24; i++)
            horas.add(new Hora(i + separacionHoras, false));
    }
}