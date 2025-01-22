package org.ucuenca.Controlador;

import org.ucuenca.Modelo.ModeloPrincipal;
import org.ucuenca.Modelo.Usuarios.Roles;
import org.ucuenca.Modelo.Usuarios.Usuario;
import org.ucuenca.Vista.Administrador.CrearUsuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorCrearUsuario implements ActionListener {
    CrearUsuario controlador;
    JFrame frame = new JFrame();

    public ControladorCrearUsuario(CrearUsuario cu) {
        controlador = cu;
        frame.setContentPane(controlador);
        frame.pack();
        frame.setVisible(true);
        setListeners();
    }


    public void setListeners() {
        controlador.crear.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == controlador.crear) {
            if (comprobarCampos()) {
                Usuario nuevoUsuario = new Usuario(controlador.numeroCedula.getText(),
                        String.valueOf(controlador.contrasena.getPassword()),
                        controlador.correoElectronico.getText(),
                        controlador.numeroTelefono.getText(),
                        Roles.USUARIO);
                ModeloPrincipal.getInstancia().actualizarDB(nuevoUsuario);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Los campos no se han llenado correctamente",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        }

    }

    public boolean comprobarCampos() {
        return !controlador.numeroCedula.getText().isEmpty() &&
                !controlador.correoElectronico.getText().isEmpty() &&
                !String.valueOf(controlador.contrasena.getPassword()).isEmpty() &&
                !controlador.numeroTelefono.getText().isEmpty();
    }
}
