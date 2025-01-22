package org.ucuenca.Controlador.Personal;

import org.ucuenca.Modelo.Documentos.ActaDefuncion;
import org.ucuenca.Modelo.Documentos.ActaNacimiento;
import org.ucuenca.Modelo.Documentos.Cedula;
import org.ucuenca.Modelo.Documentos.Plantillas.Acta;
import org.ucuenca.Modelo.ModeloPrincipal;
import org.ucuenca.Modelo.Usuarios.Ciudadano;
import org.ucuenca.Vista.Personal.CertificadoDefuncionPersonal;
import org.ucuenca.Vista.Personal.CertificadoNacimientoPersonal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ControladorActaDefuncion implements ActionListener {
    CertificadoDefuncionPersonal vista;
    Ciudadano ciudadano;
    ModeloPrincipal database;

    public ControladorActaDefuncion(CertificadoDefuncionPersonal vista) {
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

        List<ActaDefuncion> actas = new java.util.ArrayList<>(ciudadano.getDocumentosAsociados().stream().filter(
                acta -> acta instanceof ActaDefuncion
        ).map(
                acta -> (ActaDefuncion) acta
        ).toList());

        if (actas.isEmpty()) {
            mostrarMensajesError("Acta no registrada/encontrada");
            return;
        }

        actas.sort(Acta::compareTo);
        llenarDatosActa(actas.getLast());
    }

    private void llenarDatosActa(ActaDefuncion acta) {
        vista.nombresFallecidos.setText(ciudadano.getNombres() + " " + ciudadano.getApellidos());
        vista.identificacionFallecido.setText(ciudadano.getCedula().getIdUnico());
        vista.sexoFallecido.setText(ciudadano.getSexo().toString());
        vista.edadFallecido.setText(String.valueOf(ciudadano.getEdad()));
        vista.estadoCivilFallecido.setText(ciudadano.getEstadoCivil().toString());
        vista.fechaFallecimiento.setText(acta.getFechaEvento().toString());
        vista.lugarFallecimiente.setText(acta.getLugarEvento().toString());
        vista.fechaEmisionCertificado.setText(acta.getFechaEvento().toString());
        vista.fechaRegistroDefuncion.setText(acta.getFechaEvento().plusDays(1).toString());
        vista.pagina1.setText(acta.getIdDocumento());

        Ciudadano padre;
        if (ciudadano.getPadre() != null) {
            padre = database.getCiudadanos().get(ciudadano.getPadre().getCedula().getIdUnico());
            vista.nombresCompletoPadreDefuncion.setText(padre.getNombres() + " " + padre.getApellidos());
        } else {
            vista.nombresCompletoPadreDefuncion.setText(" - ");
        }

        Ciudadano madre = ciudadano.getMadre();
        if (madre != null) {
            vista.nombresCompletoMadreDefuncion.setText(madre.getNombres() + " " + madre.getApellidos());
        } else {
            vista.nombresCompletoMadreDefuncion.setText(" - ");
        }

        Ciudadano conyuge = ciudadano.getCedula().getConyugue();

        if (conyuge != null) {
            vista.nombreConyugeFallecido.setText(conyuge.getNombres() + " " + conyuge.getApellidos());
        } else {
            vista.nombreConyugeFallecido.setText(" - ");
        }

        vista.causasFallecimiento.setText(acta.getCausaMuerte().toString());
        vista.nombrePersonalCN.setText(acta.getSolicitante().getNombres() + " " + acta.getSolicitante().getApellidos());
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
