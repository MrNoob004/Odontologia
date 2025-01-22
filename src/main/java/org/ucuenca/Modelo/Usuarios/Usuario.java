package org.ucuenca.Modelo.Usuarios;

import org.ucuenca.Modelo.Citas.Plantillas.Cita;
import org.ucuenca.Modelo.ModeloPrincipal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Usuario implements Serializable {

    /**
     * Identificador de login, el mismo que su número de Cédula
     */
    private String id;

    private String contrasenia;
    private String correo;
    private String telefono;

    /**
     * Jerarquía o rol dentro del sistema
     */
    private Roles rol;


    private Collection<Cita> citas;

    public Usuario(String id, String contrasenia) {
        this.id = id;
        this.contrasenia = contrasenia;
    }

    public Usuario(String id, String contrasenia, String correo, String telefono, Roles tipoUsuario) {
        this.id = id;
        this.contrasenia = contrasenia;
        this.correo = correo;
        this.telefono = telefono;
        this.rol = tipoUsuario;
        this.citas = new ArrayList<>();
    }


    /*---Getters y Setters*/
    public String getId() {
        return id;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }

    public static Ciudadano getCiudadano(Usuario user) {
        return ModeloPrincipal.getInstancia().getCiudadanos().get(user.id);
    }

    public Collection<Cita> getCitas() {
        return citas;
    }

    public void setCitas(Collection<Cita> citas) {
        this.citas = citas;
    }

}
