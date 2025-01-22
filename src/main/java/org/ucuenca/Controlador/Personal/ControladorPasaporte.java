package org.ucuenca.Controlador.Personal;

import org.ucuenca.Modelo.Documentos.Pasaporte;
import org.ucuenca.Modelo.Documentos.Plantillas.DocumentoLegal;
import org.ucuenca.Modelo.Documentos.Plantillas.TipoPasaporte;
import org.ucuenca.Modelo.ModeloPrincipal;
import org.ucuenca.Modelo.Usuarios.*;
import org.ucuenca.Vista.Personal.EmisionPasaportePersonal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ControladorPasaporte implements ActionListener {
    EmisionPasaportePersonal vista;
    Ciudadano ciudadano;
    ModeloPrincipal database;
    String idDocumento;
    TipoPasaporte tipoPasaporte;

    /**
     * Al ser el personal quien atiende la cita, firma como el emisor en el acta
     */
    Usuario usuarioEmisor = ModeloPrincipal.usuario;


    /**
     * Maneja un controlador con un botón único y donde el único requisito es que debe estar vivo
     */
    public ControladorPasaporte(EmisionPasaportePersonal vistaServicio) {
        this.vista = vistaServicio;
        database = ModeloPrincipal.getInstancia();
        setVista();
        setListeners();
        setDatosFijos();
        setFotoPasaporte();
    }

    private void setFotoPasaporte() {
        vista.fotoPanel.setVisible(true);
        vista.cargarImagenButton.setVisible(true);
    }

    private void setDatosFijos() {
//        asignarDocumentosTest();

        setComboBoxEnumerado(vista.tipoPasaporteComboBox, TipoPasaporte.values());
        setComboBoxEnumerado(vista.estadoCivil, EstadoCivil.values());
        setComboBoxEnumerado(vista.provinciaNacimiento, Provincia.values());
        setComboBoxEnumerado(vista.genero, Sexo.values());
        setComboBoxEnumerado(vista.provinciaNacimiento, Provincia.values());

        vista.tipoPasaporteComboBox.setSelectedItem(TipoPasaporte.ORDINARIO);
        vista.vencimientoPasaporte.setEnabled(false);
        vista.vencimientoPasaporte.setText(
                LocalDate.now().
                        plus(Pasaporte.VIGENCIA_PASAPORTE).
                        format(DocumentoLegal.FORMATO_FECHA_DOC)
        );


//        Generar el número de acta\
//        idActa = gestorDocumentos.generarId(database.getDocumentos());
//        vista.nActaRegistroPersona.setText(idActa);
    }

    private void setComboBoxEnumerado(JComboBox<String> comboBox, Object[] values) {
        comboBox.removeAllItems();
        for (Object value : values)
            comboBox.addItem(value.toString());

    }

    private void setListeners() {
        vista.guardarInfo.addActionListener(this);
        vista.buscarCedulaButton.addActionListener(this);
        vista.tipoPasaporteComboBox.addActionListener(this);
        vista.cargarImagenButton.addActionListener(this);
    }

    private void setVista() {
        vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.guardarInfo) {
            if (camposValidos()) {
                registrarPasaporte();
            }
        }

        if (e.getSource() == vista.buscarCedulaButton) {
            ciudadano = buscarCiudadano(vista.cedulaPasaporte.getText());
            llenarDatos(ciudadano);
        }

        if (e.getSource() == vista.tipoPasaporteComboBox) {
            habilitarCampos(vista.tipoPasaporteComboBox.getSelectedItem());
        }

        if (e.getSource() == vista.cargarImagenButton) {
            vista.fotoPanel.setVisible(true);
            vista.fotoPanel.cargarImagen();
        }
    }

    private void habilitarCampos(Object selectedItem) {
        if (selectedItem == null)
            return;
        tipoPasaporte = parseTipo(selectedItem.toString());

        switch (tipoPasaporte) {
            case DIPLOMATICO -> {
                toggleCampos(
                        vista.cargoDiplomatico,
                        vista.organizacionRepresentaDiplomatico,
                        true);
                toggleCampos(
                        vista.cargoGubarnamentalOficial,
                        vista.EntidadPerteneceOficial,
                        false);
                toggleCampos(
                        vista.pasaporteEpecial,
                        vista.documentoPasaporteEspecial,
                        false);
            }
            case OFICIAL -> {
                toggleCampos(
                        vista.cargoDiplomatico,
                        vista.organizacionRepresentaDiplomatico,
                        false);
                toggleCampos(
                        vista.cargoGubarnamentalOficial,
                        vista.EntidadPerteneceOficial,
                        true);
                toggleCampos(
                        vista.pasaporteEpecial,
                        vista.documentoPasaporteEspecial,
                        false);
            }
            case ESPECIAL -> {
                toggleCampos(
                        vista.cargoDiplomatico,
                        vista.organizacionRepresentaDiplomatico,
                        false);
                toggleCampos(
                        vista.cargoGubarnamentalOficial,
                        vista.EntidadPerteneceOficial,
                        false);
                toggleCampos(
                        vista.pasaporteEpecial,
                        vista.documentoPasaporteEspecial,
                        true);
            }
            case null, default -> {
                toggleCampos(
                        vista.cargoDiplomatico,
                        vista.organizacionRepresentaDiplomatico,
                        false);
                toggleCampos(
                        vista.cargoGubarnamentalOficial,
                        vista.EntidadPerteneceOficial,
                        false);
                toggleCampos(
                        vista.pasaporteEpecial,
                        vista.documentoPasaporteEspecial,
                        false);
            }
        }

    }

    private void toggleCampos(JTextField cargoDiplomatico, JTextField perteneciente, boolean nuevoEstado) {
        cargoDiplomatico.setEnabled(nuevoEstado);
        perteneciente.setEnabled(nuevoEstado);
    }

    private TipoPasaporte parseTipo(String tipoPasaporte) {
        for (TipoPasaporte tipo : TipoPasaporte.values()) {
            if (tipo.toString().equals(tipoPasaporte))
                return tipo;
        }
        return TipoPasaporte.ORDINARIO;
    }

    private void llenarDatos(Ciudadano ciudadano) {
        if (ciudadano == null) {
            mostrarMensajesError(
                    "No se encuentra registrado"
            );
            return;
        }

        if (!ciudadano.estaVivo()) {
            mostrarMensajesError(ciudadano + ". No se puede emitir pasaporte de una persona fallecida");
            return;
        }

        vista.nombresPasaporte.setText(ciudadano.getNombres());
        vista.apellidosPasaporte.setText(ciudadano.getApellidos());
        vista.estadoCivil.setSelectedItem(ciudadano.getEstadoCivil().toString());
        vista.genero.setSelectedItem(ciudadano.getSexo().toString());
        vista.nacionalidad.setSelectedItem(
                ciudadano.getSexo() == Sexo.HOMBRE
                        ? "Ecuatoriano"
                        : "Ecuatoriana"
        );
        vista.provinciaNacimiento.setSelectedItem(ciudadano.getProvinciaNacimiento().toString());

        llenarDatosUsuario(ciudadano);
    }

    private void llenarDatosUsuario(Ciudadano ciudadano) {
        Usuario usuarioCorrespondiente = database.getUsuarios().get(ciudadano.getCedula().getIdUnico());
        if (usuarioCorrespondiente != null) {
            vista.telefonoContacto.setText(usuarioCorrespondiente.getTelefono());
            vista.correoElectronico.setText(usuarioCorrespondiente.getCorreo());
        }
    }

    private Ciudadano buscarCiudadano(String cedula) {
        return database.getCiudadanos().get(cedula);
    }


    private void pintarVista() {
        vista.revalidate();
        vista.repaint();
    }

    private void registrarPasaporte() {


        idDocumento = database.getDocumentoId();
        Pasaporte pasaporte = new Pasaporte(
                ciudadano,
                Provincia.AZUAY,
                ciudadano,
                idDocumento,
                tipoPasaporte,
                "111" + idDocumento
        );

        pasaporte.invalidarAnterior(ciudadano.getDocumentosAsociados());
        pasaporte.asociarCiudadano(ciudadano);
        if (database.actualizarDB(pasaporte)) {
            JOptionPane.showMessageDialog(
                    vista,
                    "Registrado correctamente"
            );
        } else {
            JOptionPane.showMessageDialog(
                    vista,
                    "No se pudo registrar correctamente"
            );
        }


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
            mostrarMensajesError(mensajesError.toString());

        return camposCorrectos;
    }

    private List<JTextField> getFields(JPanel panel) {
        return Arrays.stream(panel.getComponents()).filter(
                (component -> component instanceof JTextField)
        ).map(
                component -> (JTextField) component
        ).toList();
    }

    private List<JPanel> getPaneles() {
        return List.of(vista.jPanel1, vista);
    }

    private void mostrarMensajesError(String errores) {
        JOptionPane.showMessageDialog(
                vista,
                errores
        );
    }


}


