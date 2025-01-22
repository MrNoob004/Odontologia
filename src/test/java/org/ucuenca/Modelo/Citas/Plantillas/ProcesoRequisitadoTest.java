package org.ucuenca.Modelo.Citas.Plantillas;

import org.junit.jupiter.api.Test;
import org.ucuenca.Modelo.Citas.CitaEntrevistaMatrimonio;
import org.ucuenca.Modelo.Usuarios.Ciudadano;
import org.ucuenca.Modelo.Usuarios.EstadoCivil;
import org.ucuenca.Modelo.Usuarios.Sexo;
import org.ucuenca.Modelo.Usuarios.TipoSangre;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProcesoRequisitadoTest {

    @Test
    public void requisitosMatrimonio(){
        List<Requisito> requisitosMatrimonio = new ArrayList<>();
        Ciudadano ciudadano = new Ciudadano(true);


        CitaEntrevistaMatrimonio cita = new CitaEntrevistaMatrimonio(
                LocalDateTime.now(),
                2.50,
                ciudadano,
                "1234567890"
        );

        ciudadano.setVivo(true);
        ciudadano.setEdad(17);
        ciudadano.setEstadoCivil(EstadoCivil.SOLTERO);
        ciudadano.setSexo(Sexo.HOMBRE);
        ciudadano.setTipoSangre(TipoSangre.AB_POSITIVO);

        requisitosMatrimonio.add(new Requisito("vivo", true, "El ciudadano debe estar vivo"));
        requisitosMatrimonio.add(new Requisito("estadoCivil", (estadoCivil) -> estadoCivil != EstadoCivil.CASADO, "El ciudadano debe estar soltero"));
        requisitosMatrimonio.add(new Requisito("edad", (edad) -> (int) edad >= 18, "El ciudadano debe ser mayor de edad"));
        requisitosMatrimonio.add(new Requisito("sexo", Sexo.HOMBRE, "El ciudadano debe ser Hombre"));
        requisitosMatrimonio.add(new Requisito("tipoSangre", (sangre) -> sangre != TipoSangre.AB_POSITIVO, "El ciudadano no puede tener el tipo de sangre especificado"));

        String mensajeError = cita.getRequisitosFaltantes(requisitosMatrimonio, ciudadano);
        assertFalse(cita.cumpleRequisitos(requisitosMatrimonio, ciudadano));
        System.out.println(mensajeError);
    }
}
