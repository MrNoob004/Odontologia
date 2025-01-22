package org.ucuenca.Modelo.Citas.Plantillas;

import org.junit.jupiter.api.Test;
import org.ucuenca.Modelo.Usuarios.Ciudadano;
import org.ucuenca.Modelo.Usuarios.EstadoCivil;

import static org.junit.jupiter.api.Assertions.*;

public class RequisitoTest {

    @Test
    public void requisitoVivo() {
        Requisito requisito = new Requisito("vivo", true, "El ciudadano debe estar vivo");

        assertTrue(
                requisito.cumpleCondicion(new Ciudadano(true))
        );
    }

    @Test
    public void requisitoSoltero() {
        Requisito requisito = new Requisito(
                "estadoCivil",
                (estadoCivil) -> estadoCivil != EstadoCivil.SOLTERO,
                "El ciudadano no puede estar declarado como soltero"
        );

        assertTrue(requisito.cumpleCondicion(new Ciudadano(EstadoCivil.CASADO)));
        assertFalse(requisito.cumpleCondicion(new Ciudadano(EstadoCivil.SOLTERO)));

    }

}