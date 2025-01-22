package org.ucuenca.Modelo.Citas;

import org.ucuenca.Modelo.Dummies;
import org.ucuenca.Modelo.ModeloPrincipal;
import org.ucuenca.Modelo.Usuarios.*;

import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CitaMatrimonioTest {

    public static Ciudadano padre = Dummies.padre;
    public static Ciudadano madre = Dummies.madre;
    public static Ciudadano marido = Dummies.marido;
    public static Ciudadano mujer = Dummies.mujer;

    CitaEntrevistaMatrimonio cita = new CitaEntrevistaMatrimonio(
            LocalDateTime.now(),
            3.99,
            marido,
            mujer.getCedula().getIdUnico()
    );

    //    Acceso a DB
    private static ModeloPrincipal principal;



    /**
     * Esto pasa, solo que no se cambia en DB sus padres
     */
    @Test
    public void matrimonioComunExitoso() {
        principal = ModeloPrincipal.getInstancia();
//        principal.getCiudadanos().get(mujer.getCedula().getIdUnico()).setMadre(madre);
//        principal.guardarBasesDatos();
        assertTrue(cita.cumpleRequisitos(cita.getRequisitos(), marido));
    }

    /**
     * Esta madre no pasa las pruebas si el cónyugue (no solicitante) está muerto pq
     * <p>
     * Al crearse el requisito, se tiene una copia del cónyugue cuando está vivo,
     * al cambiar su estado después de crear la cita, el estado de vida no se actualiza
     * </p>
     */
    @Test
    public void matrimonioConyuguesMuertos() {
        marido.setVivo(false);
        assertFalse(cita.cumpleRequisitos(cita.getRequisitos(), marido));
//        marido.setVivo(true);
//        principal.getCiudadanos().get(mujer.getCedula().getIdUnico()).setVivo(false);
//        mujer.setVivo(false);
//        assertFalse(cita.cumpleRequisitos(cita.getRequisitos(), marido));

        System.out.println((cita.getRequisitosFaltantes(cita.getRequisitos(), marido)));
    }

    @Test
    public void matrimonioMenores() {
        marido.setEdad(17);

        assertFalse(cita.cumpleRequisitos(cita.getRequisitos(), marido));
        System.out.println((cita.getRequisitosFaltantes(cita.getRequisitos(), marido)));
    }

    @Test
    public void matrimonioEstadoCivilIncorrecto() {
        marido.setEstadoCivil(EstadoCivil.CASADO);
        assertFalse(cita.cumpleRequisitos(cita.getRequisitos(), marido));
        System.out.println((cita.getRequisitosFaltantes(cita.getRequisitos(), marido)));
    }

    @Test
    public void matrimonioDistintoSoltero() {
        marido.setEstadoCivil(EstadoCivil.VIUDO);
        assertTrue(cita.cumpleRequisitos(cita.getRequisitos(), marido));
        marido.setEstadoCivil(EstadoCivil.DIVORCIADO);
        assertTrue(cita.cumpleRequisitos(cita.getRequisitos(), marido));
        marido.setEstadoCivil(EstadoCivil.UNION_LIBRE);
        assertTrue(cita.cumpleRequisitos(cita.getRequisitos(), marido));

        System.out.println((cita.getRequisitosFaltantes(cita.getRequisitos(), marido)));
    }

    @Test
    public void matrimonioHomoerotico() {
        marido.setSexo(Sexo.MUJER);
        assertFalse(cita.cumpleRequisitos(cita.getRequisitos(), marido));
        System.out.println((cita.getRequisitosFaltantes(cita.getRequisitos(), marido)));

        marido.setSexo(Sexo.HOMBRE);
        //        Ahora la solicitante es la mujer
        cita = new CitaEntrevistaMatrimonio(
                LocalDateTime.now(),
                3.99,
                mujer,
                marido.getCedula().getIdUnico()
        );

        mujer.setSexo(Sexo.HOMBRE);
        assertFalse(cita.cumpleRequisitos(cita.getRequisitos(), mujer));
        System.out.println((cita.getRequisitosFaltantes(cita.getRequisitos(), mujer)));
    }

    @Test
    public void matrimonioParental() {
        marido.setMadre(madre);
        cita = new CitaEntrevistaMatrimonio(
                LocalDateTime.now(),
                3.99,
                marido,
                madre.getCedula().getIdUnico()
        );
        assertFalse(cita.cumpleRequisitos(cita.getRequisitos(), marido));
        System.out.println((cita.getRequisitosFaltantes(cita.getRequisitos(), marido)));

        mujer.setPadre(padre);
        cita = new CitaEntrevistaMatrimonio(
                LocalDateTime.now(),
                3.99,
                mujer,
                padre.getCedula().getIdUnico()
        );

        assertFalse(cita.cumpleRequisitos(cita.getRequisitos(), mujer));
        System.out.println((cita.getRequisitosFaltantes(cita.getRequisitos(), mujer)));
    }

    @Test
    public void matrimonioHermanos() {
        marido.setMadre(madre);
        mujer.setMadre(madre);
        cita = new CitaEntrevistaMatrimonio(
                LocalDateTime.now(),
                3.99,
                marido,
                mujer.getCedula().getIdUnico()
        );
        assertFalse(cita.cumpleRequisitos(cita.getRequisitos(), marido));
        System.out.println((cita.getRequisitosFaltantes(cita.getRequisitos(), marido)));

        cita = new CitaEntrevistaMatrimonio(
                LocalDateTime.now(),
                3.99,
                mujer,
                marido.getCedula().getIdUnico()
        );
        assertFalse(cita.cumpleRequisitos(cita.getRequisitos(), mujer));
        System.out.println((cita.getRequisitosFaltantes(cita.getRequisitos(), mujer)));
    }

}