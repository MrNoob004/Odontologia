package org.ucuenca.Controlador.Administrador;

import org.ucuenca.Modelo.ModeloPrincipal;
import org.ucuenca.Vista.Administrador.EliminarUsuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorEliminarUsuario implements ActionListener {

    EliminarUsuario controlador;

    ModeloPrincipal database;

    public ControladorEliminarUsuario(EliminarUsuario ceu) {
        this.controlador = ceu;
        database = ModeloPrincipal.getInstancia();
        setListeners();
    }

    public void setListeners() {
        controlador.buscarUsuario.addActionListener(this);
        controlador.eliminarUsuario.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == controlador.buscarUsuario) {
            if (comprobarCampos()) {
                controlador.usuarioEncontrado.setText(database.getNombreCompleto(controlador.numeroCedula.getText()));
                database.eliminarUsu(controlador.usuarioEncontrado.getText());
            } else {
                JOptionPane.showMessageDialog(null,
                        "No se encontró el usuario",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == controlador.eliminarUsuario) {
            database.eliminarUsu(controlador.numeroCedula.getText());
        }
    }

    public boolean comprobarCampos() {
        return !controlador.numeroCedula.getText().isEmpty();
    }
}
