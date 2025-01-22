package org.ucuenca.Controlador;

import org.ucuenca.Modelo.Usuarios.Usuario;
import org.ucuenca.Vista.Administrador.PanelAdministrador;
import org.ucuenca.Vista.PaginaPrincipal;

public class ControladorPaginaPrincipal {
    PaginaPrincipal vista;
    Usuario usuario;
    PanelAdministrador administrador = new PanelAdministrador();

    public ControladorPaginaPrincipal(Usuario usuario) {
        vista = new PaginaPrincipal();
        this.usuario = usuario;
        setVista();
        setListeners();
    }

    private void setVista() {
        switch (usuario.getRol()) {
            case USUARIO:
                vista.paginaInicial("Panel Usuario");
                vista.setVisible(true);
                break;
            case EMPLEADO:
                vista.cambiarVistaPersonal("Panel Personal");
                vista.setVisible(true);
                break;
            case ADMIN: {
                PanelAdministrador panelAdministrador = new  PanelAdministrador();
                panelAdministrador.setVisible(true);
                panelAdministrador.numeroCedulaAdministrador.setText(usuario.getId());
                break;
            }
            // administrador.setVisible(true);
            case null:
                vista.paginaInicial("Panel Usuario");
                break;
        }



        vista.panelUsuario.btnUsuario.setText(Usuario.getCiudadano(usuario).toString());
    }

    private void setListeners() {
//        vista.btnIngresar.addActionListener(this);
//        vista.btnCrearUsuario.addActionListener(this);
//        vista.btnCambiarContrasenia.addActionListener(this);
    }

}
