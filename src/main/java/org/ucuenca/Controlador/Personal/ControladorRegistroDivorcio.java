package org.ucuenca.Controlador.Personal;

import org.ucuenca.Modelo.Documentos.ActaDivorcio;
import org.ucuenca.Modelo.Documentos.ActaMatrimonio;
import org.ucuenca.Modelo.Documentos.Cedula;
import org.ucuenca.Modelo.Documentos.Plantillas.DocumentoLegal;
import org.ucuenca.Modelo.Documentos.Plantillas.EstadoDocumento;
import org.ucuenca.Modelo.ModeloPrincipal;
import org.ucuenca.Modelo.Usuarios.Ciudadano;
import org.ucuenca.Modelo.Usuarios.EstadoCivil;
import org.ucuenca.Modelo.Usuarios.Provincia;
import org.ucuenca.Modelo.Usuarios.Sexo;
import org.ucuenca.Vista.Personal.RegistroDivorcioPersonal;
import org.ucuenca.Vista.Personal.RegistroMatrimonioPersonal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ControladorRegistroDivorcio implements ActionListener {
    RegistroDivorcioPersonal vista;
    ActaDivorcio acta;
    ActaMatrimonio actaMatrimonio;
    String idActa;
    Ciudadano solicitante;
    Ciudadano marido, mujer;
    ModeloPrincipal database;

    public ControladorRegistroDivorcio(RegistroDivorcioPersonal vista) {
        this.vista = vista;
        database = ModeloPrincipal.getInstancia();
        setVista();
        setListeners();
        setDatosFijos();
    }

    private void setDatosFijos() {
        idActa = database.getDocumentoId();
        vista.nActaRegistroPersona.setText(idActa);
    }

    private void setVista() {
        vista.setVisible(true);
    }

    private void setListeners() {
        vista.buscarMaridoButton.addActionListener(this);
        vista.buscarMujerButton.addActionListener(this);
        vista.guardarInfoButton.addActionListener(this);
        vista.buscarSolicitanteButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.buscarMaridoButton) {
            if (campoCedulaValido(vista.cedulaHombreRM)) {
                marido = database.getCiudadanos().get(vista.cedulaHombreRM.getText());
                mostrarInfo("Marido: " + marido.toString() + ": " + marido.getEstadoCivil().toString());
                llenarDatosConyugueAutomaticos(marido);
            }
        }

        if (e.getSource() == vista.buscarMujerButton) {
            if (campoCedulaValido(vista.cedulaMujerRM)) {
                mujer = database.getCiudadanos().get(vista.cedulaMujerRM.getText());
                mostrarInfo("Mujer: " + mujer.toString() + ": " + mujer.getEstadoCivil().toString());
                llenarDatosConyugueAutomaticos(mujer);
            }
        }

        if (e.getSource() == vista.buscarSolicitanteButton) {
            if (campoValido(vista.cedulaSolicitanteField)) {
                setSolicitante();
                mostrarInfo(marido.toString());
            }
        }

        if (e.getSource() == vista.guardarInfoButton) {
            if (camposValidos()) {
                registrarActa();
            } else {
                JOptionPane.showMessageDialog(vista, "Campos no validos");
            }
        }
    }

    private void llenarDatosConyugueAutomaticos(Ciudadano conocido) {
        Ciudadano conyugue = null;
        if ((conyugue = conocido.getCedula().getConyugue()) == null) {
            mostrarMensajesError(conocido + " no tiene registrado un cónyugue");
            return;
        }


        switch (conocido.getSexo()) {
            case HOMBRE -> {
                mujer = conocido.getCedula().getConyugue();
                vista.cedulaMujerRM.setText(
                        conyugue.getCedula().getIdUnico()
                );
            }
            case MUJER -> {
                marido = conocido.getCedula().getConyugue();
                vista.cedulaHombreRM.setText(
                        conyugue.getCedula().getIdUnico()
                );
            }
            case null, default -> mostrarInfo("");
        }

        actaMatrimonio = conyugue.getDocumentosAsociados().stream().filter(
                documentoLegal -> documentoLegal instanceof ActaMatrimonio
        ).filter(
                documentoLegal -> documentoLegal.getEstadoDocumento() == EstadoDocumento.VIGENTE
        ).findFirst().map(
                documentoLegal -> (ActaMatrimonio) documentoLegal
        ).orElse(null);


        if (actaMatrimonio != null) {
            vista.numeroActaMatrimonio.setText(actaMatrimonio.getIdDocumento());
            mostrarInfo(
                    actaMatrimonio.getIdDocumento() + " : " + actaMatrimonio.getCiudadanosAsociados()
            );
            actaMatrimonio.invalidarAnterior(conyugue.getDocumentosAsociados());
            actaMatrimonio.invalidarAnterior(conocido.getDocumentosAsociados());
        } else {
            mostrarInfo("No se pudo encontrar un acta de matrionio de " + conocido + " " + conyugue);
        }
    }

    private void mostrarInfo(String info) {
        StringBuilder texto = new StringBuilder(vista.resultadosTextArea.getText() + "\n");
        texto.append(info);
        vista.resultadosTextArea.setText(texto.toString());
    }

    private void setSolicitante() {
        solicitante = database.getCiudadanos().get(vista.cedulaSolicitanteField.getText());
        if (solicitante == null)
            mostrarMensajesError("Solicitante no encontrado");
        else {
            JOptionPane.showMessageDialog(null, "Solicitante : " + solicitante + " exitosamente");
        }
    }

    private boolean camposValidos() {
        if (marido == null || mujer == null) {
            return false;
        }

        if (!marido.estaVivo() || !mujer.estaVivo()) {
            mostrarMensajesError("Los cónyugues deben constar como vivos");
            return false;
        }

        if (!(marido.getSexo() == Sexo.HOMBRE) || !(mujer.getSexo() == Sexo.MUJER)) {
            mostrarMensajesError("Los cónyugues deben ser del sexo correspondiente");
            return false;
        }

        if (!(marido.getEdad() >= 18) || !(mujer.getEdad() >= 18)) {
            mostrarMensajesError("Los cónyugues deben ser mayores de edad");
            return false;
        }

        if (!(marido.getEstadoCivil() == EstadoCivil.CASADO && mujer.getEstadoCivil() == EstadoCivil.CASADO)) {
            mostrarMensajesError("Los cónyugues no tienen un matrimonio vigente");
            return false;
        }


        return true;
    }

    private void registrarActa() {
//        LocalDate fechaMatrimonio = vista.fechaRegistroMatrimonioRM.getText();
        LocalDate fecha = LocalDate.now();


        Collection<Ciudadano> asociados = new ArrayList<>(List.of(marido, mujer));

        acta = new ActaDivorcio(
                solicitante,
                Provincia.AZUAY,
                marido,
                asociados,
                fecha,
                Provincia.CHIMBORAZO,
                vista.observacionesTextArea.getText(),
                idActa
        );

        if ((asociados = acta.asociarCiudadanos(asociados)).isEmpty() && database.actualizarDB(acta)) {
            JOptionPane.showMessageDialog(vista, "Divorcio registrado con éxito");
            database.actualizarDB(marido);
            database.actualizarDB(mujer);
        } else
            System.err.println(asociados);
    }

    private void llenarDatosConyugue(JLabel nombres, JLabel edad, JLabel estadoCivil, Ciudadano ciudadano) {
        if (ciudadano == null) {
            mostrarMensajesError("Ciudadano no encontrado");
            return;
        }
        nombres.setText(ciudadano.getNombres() + ", " + ciudadano.getApellidos());
        edad.setText(String.valueOf(ciudadano.getEdad()));
        estadoCivil.setText(ciudadano.getEstadoCivil().toString());
    }

    private void mostrarMensajesError(String mensajeError) {
        JOptionPane.showMessageDialog(null, mensajeError);
    }

    private boolean campoCedulaValido(JTextField cedulaField) {
        return Cedula.validarCedula(cedulaField.getText());
    }

    private boolean campoValido(JTextField campo) {
        return !campo.getText().isBlank() && !campo.getText().isEmpty();
    }
}
