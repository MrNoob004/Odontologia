package org.ucuenca.Controlador.Personal;

import org.ucuenca.Modelo.Documentos.ActaNacimiento;
import org.ucuenca.Modelo.Documentos.Cedula;
import org.ucuenca.Modelo.Documentos.Plantillas.Acta;
import org.ucuenca.Modelo.Documentos.Plantillas.DocumentoLegal;
import org.ucuenca.Modelo.ModeloPrincipal;
import org.ucuenca.Modelo.Usuarios.Ciudadano;
import org.ucuenca.Vista.Personal.CertificadoNacimientoPersonal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class ControladorActaNacimiento implements ActionListener {
    CertificadoNacimientoPersonal vista;
    Ciudadano ciudadano;
    ModeloPrincipal database;

    public ControladorActaNacimiento(CertificadoNacimientoPersonal vista) {
        this.vista = vista;
        database = ModeloPrincipal.getInstancia();
        setListeners();
    }

    private void setListeners() {
        vista.buscarCedulaButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.buscarCedulaButton) {
            if (camposValidos()) {
                buscarActaCiudadano();

            } else
                mostrarMensajesError("Campo no válido");
        }
    }

    private void buscarActaCiudadano() {
        ciudadano = database.getCiudadanos().get(vista.buscarCedula.getText());
        if (ciudadano == null) {
            mostrarMensajesError("Ciudadano no registrado");
            return;
        }

        List<ActaNacimiento> actas = new java.util.ArrayList<>(ciudadano.getDocumentosAsociados().stream().filter(
                acta -> acta instanceof ActaNacimiento
        ).map(
                acta -> (ActaNacimiento) acta
        ).toList());

        if (actas.isEmpty()) {
            mostrarMensajesError("Acta de nacimiento no registrada/encontrada");
            return;
        }

        actas.sort(Acta::compareTo);
        llenarDatosActa(actas.getLast());
    }

    private void llenarDatosActa(ActaNacimiento acta) {
        vista.nombresLabel.setText(ciudadano.getNombres() + " " + ciudadano.getApellidos());
        vista.identificacionCN1.setText(ciudadano.getCedula().getIdUnico());
        vista.sexoCN.setText(ciudadano.getSexo().toString());
        vista.lugarNacimientoCN.setText(ciudadano.getProvinciaNacimiento().toString());
        vista.yearNacimientoCN.setText(ciudadano.getFechaNacimiento().toString());
        vista.registroNacimientoCN.setText(acta.getProvinciaEmitido().toString());
        vista.nacionalidadCN.setSelectedItem("Ecuatoriano");
        vista.pagina1.setText(acta.getIdDocumento());

        Ciudadano padre;
        if (acta.getCedulaPadre() != null) {
            padre = database.getCiudadanos().get(acta.getCedulaPadre().getIdUnico());
            vista.identificacionPadreCN.setText(padre.getCedula().getIdUnico());
            vista.nombresCompletoPadreCN.setText(padre.getNombres() + " " + padre.getApellidos());
        }

        Ciudadano madre = database.getCiudadanos().get(acta.getCedulaMadre().getIdUnico());

        vista.identificacionMadreCN.setText(madre.getCedula().getIdUnico());
        vista.nombresCompletoMadreCN.setText(madre.getNombres() + " " + madre.getApellidos());
        vista.nombrePersonalCN.setText(acta.getSolicitante().getNombres() + " " + acta.getSolicitante().getApellidos());
    }

    private boolean camposValidos() {
        String cedula = vista.buscarCedula.getText();
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
