package org.ucuenca.Controlador.Personal;

import org.ucuenca.Modelo.Documentos.ActaMatrimonio;
import org.ucuenca.Modelo.Documentos.Cedula;
import org.ucuenca.Modelo.ModeloPrincipal;
import org.ucuenca.Modelo.Usuarios.Ciudadano;
import org.ucuenca.Modelo.Usuarios.EstadoCivil;
import org.ucuenca.Modelo.Usuarios.Provincia;
import org.ucuenca.Modelo.Usuarios.Sexo;
import org.ucuenca.Vista.Personal.RegistroMatrimonioPersonal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ControladorRegistroMatrimonio implements ActionListener {
    RegistroMatrimonioPersonal vista;
    ActaMatrimonio acta;
    Ciudadano solicitante;
    Ciudadano marido, mujer;
    ModeloPrincipal database;

    public ControladorRegistroMatrimonio(RegistroMatrimonioPersonal vista) {
        this.vista = vista;
        database = ModeloPrincipal.getInstancia();
        setVista();
        setListeners();
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
                llenarDatosConyugue(vista.nombresMarido, vista.edadHombreRM, vista.estadoCivilHombreRM1, marido);
            }
        }

        if (e.getSource() == vista.buscarMujerButton) {
            if (campoCedulaValido(vista.cedulaMujerRM)) {
                mujer = database.getCiudadanos().get(vista.cedulaMujerRM.getText());
                llenarDatosConyugue(vista.nombresMujer, vista.edadMujerRM, vista.estadoCivilMujerRM, mujer);
            }
        }

        if (e.getSource() == vista.buscarSolicitanteButton) {
            if (campoValido(vista.cedulaSolicitanteField)) {
                setSolicitante();
            }
        }

        if (e.getSource() == vista.guardarInfoButton) {
            if (camposValidos()) {
                registrarActa();
            }
        }
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

        if (marido.getEstadoCivil() == EstadoCivil.CASADO || mujer.getEstadoCivil() == EstadoCivil.CASADO) {
            mostrarMensajesError("Los cónyugues tienen un matrimonio vigente");
            return false;
        }

        return true;
    }

    private void registrarActa() {
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(vista.fechaRegistroMatrimonioRM.getText());
        } catch (Exception e) {
            fecha = LocalDate.now().minusDays(1);
        }

        Collection<Ciudadano> asociados = new ArrayList<>(List.of(marido, mujer));

        acta = new ActaMatrimonio(
                solicitante,
                Provincia.AZUAY,
                marido,
                asociados,
                fecha,
                Provincia.CHIMBORAZO,
                "Sin observaciones",
                database.getDocumentoId()
        );
        if ((asociados = acta.asociarCiudadanos(asociados)).isEmpty() && database.actualizarDB(acta)) {
            JOptionPane.showMessageDialog(vista, "Matrimonio registrado con éxito");
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
