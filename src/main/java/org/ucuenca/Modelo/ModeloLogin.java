package org.ucuenca.Modelo;

import org.ucuenca.Modelo.Documentos.Cedula;
import org.ucuenca.Modelo.Usuarios.Roles;
import org.ucuenca.Modelo.Usuarios.Usuario;

import java.util.Map;

public class ModeloLogin {

    private Map<String, Usuario> usuarios;
    private ModeloPrincipal principal;

    public ModeloLogin() {
        principal = ModeloPrincipal.getInstancia();
    }

    /**
     * Verifica que la cédula sea válida y se tenga un usuario con la contraseña indicada
     */
    public boolean validarCampos(String cedula, String contrasena) {
        if (!Cedula.validarCedula(cedula))
            return false;

        if (contrasena.isBlank() || contrasena.isEmpty() || contrasena.equalsIgnoreCase("[]"))
            return false;

        return true;
    }

    /**
     * Obtiene el usuario correspondiente a la credencial de cédula
     *
     * @return {@link Usuario} si es que se encuentra registrado. {@code null} caso contrario
     */
    public Usuario cargarUsuario(String cedula) {
        return principal.getUsuarios().get(cedula);
    }

    public boolean validarCredenciales(String cedula, String contrasena, Usuario usuario) {
        if (usuario == null) return false;
        return usuario.getId().equals(cedula) && usuario.getContrasenia().equals(contrasena);
    }

}
