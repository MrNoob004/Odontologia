package org.ucuenca.Controlador;

import ch.qos.logback.core.model.Model;
import org.ucuenca.Modelo.ModeloLogin;
import org.ucuenca.Modelo.ModeloPrincipal;
import org.ucuenca.Modelo.Usuarios.Roles;
import org.ucuenca.Modelo.Usuarios.Usuario;
import org.ucuenca.Vista.Administrador.CrearUsuario;
import org.ucuenca.Vista.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ControladorLogin implements ActionListener {
    private Login vista;
    private ModeloLogin modelo;
    Usuario usuario;
    ControladorPaginaPrincipal controladorPaginaPrincipal;

    public static String placeHolderCedulaField = "Ingrese su número de cédula";
    public static String placeHolderContraseniaField = "Contraseña";


    public ControladorLogin() {
        vista = new Login();
        modelo = new ModeloLogin();

        vista.setVisible(true);
        setListeners();
        setPlaceholders();
        setUsuarioTest();
    }

    //    TODO Borra tus credenciales pelao
    private void setUsuarioTest() {
        final Usuario userPrueba = ModeloPrincipal.getInstancia().getUsuarios().get("0105911838");

        vista.cedulaField.setText(userPrueba.getId());
        vista.contraseniaField.setText(userPrueba.getContrasenia());
    }

    /**
     * Borra el texto en cuanto se selecciona el campo
     * <p>
     * Para los JTextField que describen el campo que se debe ingresar:
     * - Cedula
     * - Contraseña
     * </p>
     */
    private void setPlaceholders() {

        vista.cedulaField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                if (vista.cedulaField.getText().equals(placeHolderCedulaField)) {
                    vista.cedulaField.setText(""); // Borra el texto
                    vista.cedulaField.setForeground(Color.BLACK); // Cambia el color a negro
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (vista.cedulaField.getText().isEmpty()) {
                    vista.cedulaField.setText(placeHolderCedulaField); // Restaura el placeholder
                    vista.cedulaField.setForeground(Color.GRAY); // Cambia el color a gris
                }
            }
        });


        vista.contraseniaField.setForeground(Color.GRAY);
        vista.contraseniaField.setEchoChar((char) 0); // Desactiva los asteriscos

        vista.contraseniaField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                if (new String(vista.contraseniaField.getPassword()).equals(placeHolderContraseniaField)) {
                    vista.contraseniaField.setText(""); // Borra el texto
                    vista.contraseniaField.setEchoChar('\u2022'); // Activa los puntos (•)
                    vista.contraseniaField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(vista.contraseniaField.getPassword()).isEmpty()) {
                    vista.contraseniaField.setEchoChar((char) 0); // Desactiva los puntos
                    vista.contraseniaField.setText(placeHolderContraseniaField);
                    vista.contraseniaField.setForeground(Color.GRAY);
                }
            }
        });

    }

    private void setListeners() {
        vista.btnIngresar.addActionListener(this);
        vista.btnCrearUsuario.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnIngresar) {
            if (inicioSesionValido()) {
                controladorPaginaPrincipal = new ControladorPaginaPrincipal(usuario);
                ModeloPrincipal.usuario = this.usuario;
                vista.dispose();
            }
        }

        if (e.getSource() == vista.btnCrearUsuario) {
            new ControladorCrearUsuario(new CrearUsuario());
        }
    }

    private boolean inicioSesionValido() {
        String cedula = vista.cedulaField.getText();
        String contrasena = String.valueOf(vista.contraseniaField.getPassword());

        usuario = modelo.cargarUsuario(cedula);

        if (modelo.validarCampos(cedula, contrasena)) {

            if (usuario == null) {
                JOptionPane.showMessageDialog(vista.login, "el usuario " + cedula + " no existe\"");
                return false;
            }

            if (modelo.validarCredenciales(cedula, contrasena, usuario)) {
                if(Usuario.getCiudadano(usuario).estaVivo())
                    return true;
                else
                    JOptionPane.showMessageDialog(
                            vista.login,
                            "El usuario no consta como vivo, cuenta inaccesible"
                    );
            }

            JOptionPane.showMessageDialog(vista.login, "Credenciales incorrectas");
        } else
            JOptionPane.showMessageDialog(vista.login, "Llene los campos correctamente");

        return false;
    }

}
