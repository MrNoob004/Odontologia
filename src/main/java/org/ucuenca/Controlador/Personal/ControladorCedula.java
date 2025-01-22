package org.ucuenca.Controlador.Personal;

import org.ucuenca.Modelo.Documentos.Cedula;
import org.ucuenca.Modelo.ModeloPrincipal;
import org.ucuenca.Modelo.Usuarios.*;
import org.ucuenca.Vista.Personal.EmisionCedulaPersonal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ControladorCedula implements ActionListener {
    EmisionCedulaPersonal vista;
    Ciudadano ciudadano;
    Ciudadano solicitante;
    ModeloPrincipal database;
    String idDocumento;
    Usuario usuarioSolicitante, usuarioAsociado;

    /**
     * Al ser el personal quien atiende la cita, firma como el emisor en el acta
     */
    Usuario usuarioEmisor = ModeloPrincipal.usuario;


    /**
     * Maneja un controlador con un botón único y donde el único requisito es que debe estar vivo
     */
    public ControladorCedula(EmisionCedulaPersonal vista) {
        this.vista = vista;
        database = ModeloPrincipal.getInstancia();
        setVista();
        setListeners();
        setDatosFijos();
    }

    private void setDatosFijos() {
//        asignarDocumentosTest();

        setComboBoxEnumerado(vista.provinciaComboBox, Provincia.values());

//        Generar el número de acta\
        idDocumento = database.getDocumentoId();
        vista.nActaField.setText(idDocumento);
        vista.fechaExpiracionLabel.setText(LocalDate.now().plus(Cedula.VIGENCIA_CEDULA).toString());
    }

    private void setComboBoxEnumerado(JComboBox<String> comboBox, Object[] values) {
        comboBox.removeAllItems();
        for (Object value : values)
            comboBox.addItem(value.toString());

    }

    private void setListeners() {
        vista.renovarButton.addActionListener(this);
        vista.buscarButton.addActionListener(this);
        vista.buscarCiudadanoButton.addActionListener(this);
    }

    private void setVista() {
        vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.renovarButton) {
            if (camposValidos()) {
                renovarCedula();
            }
        }

        if (e.getSource() == vista.buscarButton) {
            solicitante = buscarCiudadano(vista.cedulaField.getText());
            if (solicitante != null) {
                mostrarMensaje(solicitante + " seleccionado correctamente");
                llenarDatosUsuario(solicitante, usuarioSolicitante);
            } else {
                mostrarMensaje("No se ha encontrado el solicitante");
            }
        }

        if (e.getSource() == vista.buscarCiudadanoButton) {
            ciudadano = buscarCiudadano(vista.cedulaCiudadano.getText());
            llenarDatos(ciudadano);
        }

    }


    private Object stringToEnumValue(JComboBox<String> stringValue, Object[] values) {
        for (Object o : values) {
            if (o.toString().equals(stringValue.getSelectedItem().toString())) {
                return o;
            }
        }
        return values[0];

    }

    private void llenarDatos(Ciudadano ciudadano) {
        if (ciudadano == null) {
            mostrarMensaje(
                    "No se encuentra registrado"
            );
            return;
        }

        if (!ciudadano.estaVivo()) {
            mostrarMensaje(ciudadano + ". No se puede emitir una cédula de una persona fallecida");
            return;
        }

        vista.nombresCiudadano.setText(ciudadano.getNombres());
        vista.provinciaNacimientoLabel.setText(ciudadano.getProvinciaNacimiento().toString());
        vista.tipoSangreLabel.setText(ciudadano.getTipoSangre().toString());
        vista.estadoCivilLabel.setText(ciudadano.getEstadoCivil().toString());
        vista.madreLabel.setText(ciudadano.getMadre().toString());
        if (ciudadano.getPadre() != null)
            vista.padreLabel.setText(ciudadano.getPadre().toString());
        else
            vista.padreLabel.setText("No registrado");

        if (ciudadano.getCedula().getConyugue() != null)
            vista.nombresConyugueLabel.setText(ciudadano.getCedula().getConyugue().toString());
        else
            vista.nombresConyugueLabel.setText("No registrado");


        llenarDatosUsuario(ciudadano, usuarioAsociado);
    }

    private void llenarDatosUsuario(Ciudadano ciudadano, Usuario usuario) {
        usuario = database.getUsuarios().get(ciudadano.getCedula().getIdUnico());
    }

    private Ciudadano buscarCiudadano(String cedula) {
        return database.getCiudadanos().get(cedula);
    }


    private void pintarVista() {
        vista.revalidate();
        vista.repaint();
    }

    private void renovarCedula() {
        idDocumento = database.getDocumentoId();
        Cedula cedulaAnterior = ciudadano.getCedula();
        Cedula cedulaNueva = cedulaAnterior.renovar();
        cedulaNueva.invalidarAnterior(ciudadano.getDocumentosAsociados());
        cedulaNueva.asociarCiudadano(ciudadano);
        StringBuilder info = new StringBuilder();

        Provincia provinciaEmitido = (Provincia) stringToEnumValue(vista.provinciaComboBox, Provincia.values());
        cedulaNueva.setProvinciaEmitido(provinciaEmitido);

        if (database.actualizarDB(cedulaAnterior)) {
            info.append("Cedula anterior invalidada\n");
            if (database.actualizarDB(cedulaNueva))
                info.append("Cedula renovada\n");
            else
                info.append("Error al poner en vigencia correctamente\n");

        } else
            info.append("Error al invalidar ceduka anterior\n");


        mostrarMensaje(info.toString());
        database.actualizarDB(ciudadano);
    }

    private boolean camposValidos() {
        boolean camposCorrectos = true;
        StringBuilder mensajesError = new StringBuilder();

        List<JPanel> paneles = new ArrayList<>(getPaneles());
        List<JTextField> fields = new ArrayList<>();
        for (JPanel panel : paneles)
            fields.addAll(getFields(panel));


        for (JTextField field : fields) {
            if (field.isEnabled())
                if (field.getText().isEmpty() || field.getText().isBlank()) {
                    mensajesError.append("Llene todos los campos\n");
                    camposCorrectos = false;
                    break;
                }
        }

        if (!mensajesError.isEmpty())
            mostrarMensaje(mensajesError.toString());

        return camposCorrectos;
    }

    private List<JTextField> getFields(JPanel panel) {
        return Arrays.stream(panel.getComponents()).filter(
                (component -> component instanceof JTextField)
        ).map(
                component -> (JTextField) component
        ).toList();
    }

    private List<EmisionCedulaPersonal> getPaneles() {
        return List.of(vista);
    }

    private void mostrarMensaje(String errores) {
        JOptionPane.showMessageDialog(
                vista,
                errores
        );
    }


}


