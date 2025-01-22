package org.ucuenca.Controlador.Personal;

import org.ucuenca.Modelo.Documentos.ActaDivorcio;
import org.ucuenca.Modelo.Documentos.Cedula;
import org.ucuenca.Modelo.Documentos.Plantillas.Acta;
import org.ucuenca.Modelo.ModeloPrincipal;
import org.ucuenca.Modelo.Usuarios.Ciudadano;
import org.ucuenca.Vista.Personal.CertificadoDivorcionPersonal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ControladorActaDivorcio implements ActionListener {
    CertificadoDivorcionPersonal vista;
    Ciudadano ciudadano;
    ModeloPrincipal database;

    public ControladorActaDivorcio(CertificadoDivorcionPersonal vista) {
        this.vista = vista;
        database = ModeloPrincipal.getInstancia();
        setListeners();
    }

    private void setListeners() {
        vista.buscarButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.buscarButton) {
            if (camposValidos()) {
                buscarActaCiudadano();

            } else
                mostrarMensajesError("Campo no válido");
        }
    }

    private void buscarActaCiudadano() {
        ciudadano = database.getCiudadanos().get(vista.cedulaField.getText());

        if (ciudadano == null) {
            mostrarMensajesError("Ciudadano no registrado");
            return;
        }

        List<ActaDivorcio> actas = new java.util.ArrayList<>(ciudadano.getDocumentosAsociados().stream().filter(
                acta -> acta instanceof ActaDivorcio
        ).map(
                acta -> (ActaDivorcio) acta
        ).toList());

        if (actas.isEmpty()) {
            mostrarMensajesError("Acta no registrada/encontrada");
            return;
        }

        actas.sort(Acta::compareTo);
        llenarDatosActa(actas.getLast());
    }

    private void llenarDatosActa(ActaDivorcio acta) {
        vista.nombresCompletoMujer.setText(acta.getMujer().getNombres() + " " + acta.getMujer().getApellidos());
        vista.nombresCompletoHombre.setText(acta.getMarido().getNombres() + " " + acta.getMarido().getApellidos());
        vista.yearMatrimonioCD.setText(acta.getFechaEvento().toString());
        vista.lugarMatrimonioCD.setText(acta.getLugarEvento().toString());
        vista.resolucionLegalCD.setText(acta.getIdDocumento());
        vista.nombrePersonal.setText(acta.getSolicitante().getNombres() + " " + acta.getSolicitante().getApellidos());
    }

    private boolean camposValidos() {
        String cedula = vista.cedulaField.getText();
        if (cedula.isEmpty() || cedula.isBlank())
            return false;


        return Cedula.validarCedula(cedula);
    }

    private void mostrarMensajesError(String errores) {
        JOptionPane.showMessageDialog(
                vista,
                errores
        );
    }

}
