package org.ucuenca.Controlador.Personal;

import org.ucuenca.Modelo.Documentos.ActaDivorcio;
import org.ucuenca.Modelo.Documentos.ActaMatrimonio;
import org.ucuenca.Modelo.Documentos.Cedula;
import org.ucuenca.Modelo.Documentos.Plantillas.Acta;
import org.ucuenca.Modelo.ModeloPrincipal;
import org.ucuenca.Modelo.Usuarios.Ciudadano;
import org.ucuenca.Vista.Personal.CertificadoMatrimonioPersonal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ControladorActaMatrimonio implements ActionListener {
    CertificadoMatrimonioPersonal vista;
    Ciudadano ciudadano;
    ModeloPrincipal database;

    public ControladorActaMatrimonio(CertificadoMatrimonioPersonal vista) {
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

        List<ActaMatrimonio> actas = new java.util.ArrayList<>(ciudadano.getDocumentosAsociados().stream().filter(
                acta -> acta instanceof ActaMatrimonio
        ).map(
                acta -> (ActaMatrimonio) acta
        ).toList());

        if (actas.isEmpty()) {
            mostrarMensajesError("Acta no registrada/encontrada");
            return;
        }

        actas.sort(Acta::compareTo);
        llenarDatosActa(actas.getLast());
    }

    private void llenarDatosActa(ActaMatrimonio acta) {

        vista.yearMatrimonioCM.setText(acta.getFechaEvento().toString());
        vista.registroMatrimonioCM.setText(acta.getLugarEvento().toString());
        vista.tomoCM.setText(acta.getIdDocumento() + "15");
        vista.paginaCM.setText(acta.getIdDocumento() + "3");
        vista.numeroActaM.setText(acta.getIdDocumento());

        vista.nombresCompletoMujer.setText(acta.getMujer().getNombres() + " " + acta.getMujer().getApellidos());
        vista.identificacionMujerCM.setText(acta.getMujer().getCedula().getIdUnico());
        vista.nombresCompletoHombre.setText(acta.getMarido().getNombres() + " " + acta.getMarido().getApellidos());
        vista.identificacionMujerCM.setText(acta.getMarido().getCedula().getIdUnico());

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
