package org.ucuenca.Controlador.Personal;

import org.ucuenca.Modelo.Documentos.ActaDefuncion;
import org.ucuenca.Modelo.Documentos.Cedula;
import org.ucuenca.Modelo.Documentos.Pasaporte;
import org.ucuenca.Modelo.ModeloPrincipal;
import org.ucuenca.Modelo.Usuarios.*;
import org.ucuenca.Vista.Personal.RegistroDefuncionPersonal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControladorRegistroDefuncion implements ActionListener {
    RegistroDefuncionPersonal vista;
    String idActa;
    ModeloPrincipal database;
    Ciudadano difunto;
    Ciudadano conyugue;
    Ciudadano solicitante;
    ActaDefuncion acta;

    public ControladorRegistroDefuncion(RegistroDefuncionPersonal vista) {
        this.vista = vista;
        database = ModeloPrincipal.getInstancia();
        setDatosFijos();
        setListeners();
    }

    private void setDatosFijos() {
        idActa = database.getDocumentoId();
        vista.nActaDefuncion.setText(idActa);
        setComboBoxEnumerado(vista.tipoDeceso, TipoDeceso.values());
        setComboBoxEnumerado(vista.sexoFallecido, Sexo.values());
        setComboBoxEnumerado(vista.provinciaDefuncion, Provincia.values());
        setComboBoxEnumerado(vista.provinciaRegistro, Provincia.values());

    }

    private void setListeners() {
        vista.buscarButton.addActionListener(this);
        vista.buscarSolicitanteButton.addActionListener(this);
        vista.guardarInfoF.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.buscarButton) {
            if (Cedula.validarCedula(vista.cedFallecido.getText())) {

                buscarCiudadano();
                llenarDatos();
            } else {
                mostrarMensajesError("Cédula Incorrecta o no registrada");
            }
        }

        if (e.getSource() == vista.guardarInfoF) {
            if (camposValidos()) {
                registrarActa();
            }
        }

        if (e.getSource() == vista.buscarSolicitanteButton) {
            if (campoCedulaValido(vista.cedulaSolicitanteField)) {
                solicitante = database.getCiudadanos().get(
                        vista.cedulaSolicitanteField.getText()
                );
                if (solicitante != null) {
                    JOptionPane.showMessageDialog(vista, "Solicitante " + solicitante + " registrado con exito");
                } else {
                    JOptionPane.showMessageDialog(vista, "Solicitante no encontrado");
                }
            }
        }
    }

    private void registrarActa() {
        Provincia provinciaEmitido = (Provincia) stringToEnumValue(vista.provinciaRegistro, Provincia.values());
        Provincia provinciaDefuncion = (Provincia) stringToEnumValue(vista.provinciaDefuncion, Provincia.values());
        TipoDeceso tipoDeceso = (TipoDeceso) stringToEnumValue(vista.tipoDeceso, TipoDeceso.values());
        ArrayList<Ciudadano> asociados = new ArrayList<>();
        asociados.add(difunto);
        asociados.add(conyugue);


        LocalDate fechaFallecimiento;

        try {
            fechaFallecimiento = LocalDate.parse(vista.fechaFallecimiento.getText());
        } catch (Exception e) {
            fechaFallecimiento = LocalDate.now().minusDays(1);
        }

        acta = new ActaDefuncion(
                Usuario.getCiudadano(ModeloPrincipal.usuario),
                provinciaEmitido,
                difunto,
                asociados,
                fechaFallecimiento,
                provinciaDefuncion,
                vista.observacionesTextArea.getText(),
                tipoDeceso,
                idActa
        );
        actualizarDatos();
        invalidarDocumentos();

        database.actualizarDB(acta);
        database.actualizarDB(difunto);
        JOptionPane.showMessageDialog(null, "Acta emitida correctamente");
    }

    private boolean campoCedulaValido(JTextField cedulaField) {
        return Cedula.validarCedula(cedulaField.getText());
    }

    private void invalidarDocumentos() {
        StringBuilder info = new StringBuilder(vista.observacionesTextArea.getText());
        if (difunto.getCedula().invalidarAnterior(difunto.getDocumentosAsociados())) {
            info.append("Cédula anterior marcada como caducada");
        }

        List<Pasaporte> pasaportes = difunto.getDocumentosAsociados().stream().filter(
                (documento) -> documento instanceof Pasaporte
        ).map(
                (documento) -> (Pasaporte) documento
        ).toList();

        if (!pasaportes.isEmpty()) {
            if (pasaportes.getLast().invalidarAnterior(difunto.getDocumentosAsociados())) {
                info.append("Pasaporte anterior marcado como caducado");
            }
        }

        vista.resultadosTextArea.setText(info.toString());
    }

    private void actualizarDatos() {

        try {
            acta.asociarCiudadanos(acta.getCiudadanosAsociados());
        } catch (Exception e) {
            mostrarMensajesError("Error al intentar cambiar estado civil o de vida");
            System.err.println(e.getMessage() + " " + e.getCause());
            return;
        }

        StringBuilder info = new StringBuilder(vista.resultadosTextArea.getText());
        info.append(
                difunto.toString() + " Registrado como fallecido\n"
        );
        if (conyugue != null) {
            info.append(
                    conyugue + " Actualizada a estado civil VIUDO\n"
            );

            database.actualizarDB(conyugue);
        }

        vista.resultadosTextArea.setText(info.toString());
    }

    private void llenarDatos() {
        if (difunto == null) {
            mostrarMensajesError("Ciudadano no registrado");
            return;
        }

        if (!difunto.estaVivo()) {
            mostrarMensajesError("La persona ya está registrada como fallecida");
            return;
        }

        StringBuilder infoInicial = new StringBuilder(vista.resultadosTextArea.getText() + "\n");
        infoInicial.append("Estado Registrado: ").append(difunto.estaVivo() ? "Vivo" : "Fallecido").append("\n");
        infoInicial.append("Estado Civil: ").append(difunto.getEstadoCivil().toString()).append("\n");
        if (difunto.getEstadoCivil() == EstadoCivil.CASADO)
            try {
                conyugue = database.getCiudadanos().get(difunto.getCedula().getConyugue().getCedula().getIdUnico());
                infoInicial.append("Cónyugue: ")
                        .append(conyugue.toString()).append("\n");
            } catch (Exception e) {
                System.err.println("No se pudo acceder al cónyugue");
            }

        vista.nombresCiudadanosFallecido.setText(difunto.getNombres());
        vista.apellidosFallecido.setText(difunto.getApellidos());
        vista.sexoFallecido.setSelectedItem(difunto.getSexo().toString());
        vista.resultadosTextArea.setText(
                infoInicial.toString()
        );
    }

    private void buscarCiudadano() {
        this.difunto = database.getCiudadanos().get(vista.cedFallecido.getText());
    }

    private boolean camposValidos() {
        boolean camposCorrectos = true;
        StringBuilder mensajesError = new StringBuilder();
        List<JTextField> fields = new ArrayList<>();

        List<JPanel> paneles = getPaneles();
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
        return Arrays.stream(vista.getComponents()).filter(
                (component -> component instanceof JPanel)
        ).map(
                component -> (JPanel) component
        ).toList();
    }

    private void mostrarMensajesError(String errores) {
        JOptionPane.showMessageDialog(
                vista,
                errores
        );
    }

    private Object stringToEnumValue(JComboBox<String> stringValue, Object[] values) {
        for (Object o : values) {
            if (o.toString().equals(stringValue.getSelectedItem().toString())) {
                return o;
            }
        }
        return values[0];
    }

    private void setComboBoxEnumerado(JComboBox<String> comboBox, Object[] values) {
        comboBox.removeAllItems();
        for (Object value : values)
            comboBox.addItem(value.toString());

    }

}
