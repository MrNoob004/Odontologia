package org.ucuenca.Modelo.Documentos;

import org.junit.jupiter.api.Test;
import org.ucuenca.Modelo.Documentos.Plantillas.RequisitoIncumplidoException;
import org.ucuenca.Modelo.Usuarios.Ciudadano;
import org.ucuenca.Modelo.Usuarios.EstadoCivil;
import org.ucuenca.Modelo.Usuarios.Provincia;
import org.ucuenca.Modelo.Usuarios.TipoDeceso;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActaDefuncionTest {
    Ciudadano ciudadanoK = new Ciudadano(true);

    @Test
    void modificarEstado() {

        Ciudadano difunto = ciudadanoK;
        difunto.setCedula(
                new Cedula(
                        difunto,
                        Provincia.AZUAY,
                        difunto,
                        "0105911838",
                        null,
                        difunto
                )
        );

        difunto.setNombres("Kenny Jose");
        difunto.setApellidos("Avila miliu");

        ActaDefuncion acta = new ActaDefuncion(
                difunto,
                Provincia.AZUAY,
                difunto,
                List.of(difunto),
                LocalDate.now().minus(Period.of(1, 1, 1)),
                Provincia.CARCHI,
                "Rico",
                TipoDeceso.ACCIDENTE
        );

        /*Se puede cambiar el estado y estado civil de una persona a difunto*/
        assertTrue(acta.modificarEstado(EstadoCivil.CASADO, difunto));
        assertTrue(acta.modificarEstado(false, difunto));
        difunto.setVivo(false);

        /*Si ya está muerto -> false*/
        assertFalse(acta.modificarEstado(false, difunto));
        /*Si ya está muerto y se intenta cambiar el estado civil, es un error*/
        assertThrows(RequisitoIncumplidoException.class, () -> acta.modificarEstado(EstadoCivil.SOLTERO, difunto));
    }
}