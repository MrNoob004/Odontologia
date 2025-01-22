package org.ucuenca.Modelo.Citas;

import org.ucuenca.Modelo.Citas.Plantillas.Cita;
import org.ucuenca.Modelo.Usuarios.Ciudadano;
import org.ucuenca.Modelo.Usuarios.Usuario;

import java.util.Collection;

public class MisCitasModelo {
    Usuario usuario;
    Ciudadano ciudadano;

    public MisCitasModelo(Usuario usuario, Ciudadano ciudadano) {
        this.usuario = usuario;
        this.ciudadano = ciudadano;
    }

    public Collection<Cita> getCitas() {
        return usuario.getCitas();
    }


    public Cita getCita(int citaId) {
        return usuario.getCitas().stream().filter(
                (cita) -> cita.getIdCita().equals(String.valueOf(citaId))
        ).findFirst().orElse(null);

    }
}
