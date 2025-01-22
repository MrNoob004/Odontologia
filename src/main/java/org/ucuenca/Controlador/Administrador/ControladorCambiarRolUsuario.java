package org.ucuenca.Controlador.Administrador;

import org.ucuenca.Modelo.ModeloPrincipal;
import org.ucuenca.Modelo.Usuarios.Roles;
import org.ucuenca.Modelo.Usuarios.Usuario;
import org.ucuenca.Vista.Administrador.CambiarRolUsuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorCambiarRolUsuario implements ActionListener {
    CambiarRolUsuario controlador;
    ModeloPrincipal dataBase;

    public ControladorCambiarRolUsuario(CambiarRolUsuario cr) {
        controlador = cr;
        dataBase = ModeloPrincipal.getInstancia();
        setListeners();
        setComboBox();
        setVista();
    }

    public void setListeners() {
        controlador.buscarUsuario.addActionListener(this);
        controlador.cambiarRol.addActionListener(this);
    }

    public void setComboBox() {
        setComboBoxEnumerado(controlador.jComboBox1, Roles.values());
    }

    private void setComboBoxEnumerado(JComboBox<String> comboBox, Object[] values) {
        comboBox.removeAllItems();
        for (Object value : values)
            comboBox.addItem(value.toString());

    }

    private Object stringToEnumValue(JComboBox<String> stringValue, Object[] values) {
        for (Object o : values) {
            if (o.toString().equals(stringValue.getSelectedItem().toString())) {
                return o;
            }
        }
        return values[0];

    }

    public void setVista() {
        controlador.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == controlador.buscarUsuario) {
            if (comprobarCampos()) {
                controlador.usuarioEncontrado.setText(dataBase.getNombreCompleto(controlador.numeroCedula.getText()));
                controlador.rolEncontrado.setText(dataBase.getRol(controlador.numeroCedula.getText()));
            } else {
                JOptionPane.showMessageDialog(null,
                        "No se encontró el usuario",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == controlador.cambiarRol) {
            Usuario cambiarRol = dataBase.getUsuarios().get(controlador.numeroCedula.getText());
//            if (controlador.jComboBox1.getSelectedItem() == "Usuario") {
//                cambiarRol.setRol(Roles.USUARIO);
//            } else if (controlador.jComboBox1.getSelectedItem() == "Personal") {
//                cambiarRol.setRol(Roles.EMPLEADO);
//            } else if (controlador.jComboBox1.getSelectedItem() == "Administrador") {
//                cambiarRol.setRol(Roles.ADMIN);
//            }

            Roles nuevoRol = (Roles) stringToEnumValue(controlador.jComboBox1, Roles.values());
            cambiarRol.setRol(nuevoRol);
            dataBase.actualizarDB(dataBase.getUsuarios().get(controlador.numeroCedula.getText()));
        }
    }

    public boolean comprobarCampos() {
        return !controlador.numeroCedula.getText().isEmpty();
    }
}
