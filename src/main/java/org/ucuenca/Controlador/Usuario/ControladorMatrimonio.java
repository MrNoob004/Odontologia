package org.ucuenca.Controlador.Usuario;

import org.ucuenca.Modelo.Citas.CitaEntrevistaMatrimonio;
import org.ucuenca.Modelo.Citas.Correo;
import org.ucuenca.Modelo.Citas.HorariosYCostos.Precios;
import org.ucuenca.Modelo.Documentos.Cedula;
import org.ucuenca.Modelo.ModeloPrincipal;
import org.ucuenca.Modelo.Usuarios.Ciudadano;
import org.ucuenca.Modelo.Usuarios.Usuario;
import org.ucuenca.Vista.Usuario.EleccionFecha;
import org.ucuenca.Vista.Usuario.Matrimonio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;


public class ControladorMatrimonio implements ActionListener, CitaNotificableCorreo {
    CitaEntrevistaMatrimonio cita;
    Matrimonio vista;
    Ciudadano solicitante;
    String cedulaConyugue = "";
    ModeloPrincipal database;
    EleccionFecha selectorFecha;
    LocalDateTime fechaCita;

    Correo correo;
    public ControladorMatrimonio(Matrimonio entrevistaMatrimonio) {
        solicitante = Usuario.getCiudadano(ModeloPrincipal.usuario);
        vista = entrevistaMatrimonio;
        database = ModeloPrincipal.getInstancia();
        setListeners();
        setVista();
        setDatosUsuario();
    }

    private void setDatosUsuario() {
        vista.numeroCedula.setText(solicitante.getCedula().getIdUnico());
        vista.nombres.setText(solicitante.getNombres() + " " + solicitante.getApellidos());
        vista.correo.setText(ModeloPrincipal.usuario.getCorreo());
        vista.celular.setText(ModeloPrincipal.usuario.getTelefono());
    }

    private void setVista() {
        vista.setVisible(true);
    }

    public void setListeners() {
        vista.siguienteButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.siguienteButton) {
            if (camposValidos()) {
                manejarAgendamiento();
            } else
                JOptionPane.showMessageDialog(null, "Ingrese correctamente los campos");
        }

        if (e.getSource() == selectorFecha.addCarrito) {
            fechaCita = selectorFecha.getFechaHora();
        }
    }

    private void manejarAgendamiento() {
        this.cedulaConyugue = vista.cedulaConyugue.getText();
        Ciudadano conyugue = database.getCiudadanos().get(cedulaConyugue);

        this.cita = new CitaEntrevistaMatrimonio(
                LocalDateTime.now(),
                Precios.MATRIMONIO.getPrecio(),
                solicitante,
                cedulaConyugue,
                database.getCitaId()
        );

        if (!cita.cumpleRequisitos(cita.getRequisitos(), solicitante)) {
            JOptionPane.showMessageDialog(null,
                    "--- Requisitos no cumplidos por solicitante ---\n" +
                            cita.getRequisitosFaltantes(cita.getRequisitos(), solicitante)
            );
            return;
        }

//        Verificar que el cónyugue cumpla los requisitos
        cita.setCedulaConyugue(solicitante.getCedula().getIdUnico());
//        TODO habilitar de nuevo
        /*if (!modelo.cumpleRequisitos(modelo.getRequisitos(), conyugue)) {
            JOptionPane.showMessageDialog(null,
                    "--- Requisitos no cumplidos por cónyugue ---\n" +
                            modelo.getRequisitosFaltantes(modelo.getRequisitos(), conyugue)
            );
        }*/
        mostrarSeleccionFecha();
        cita.setFecha(fechaCita);
        cita.setCedulaConyugue(this.cedulaConyugue);

        ModeloPrincipal.usuario.getCitas().add(cita);
        database.actualizarDB(cita);
        database.actualizarDB(ModeloPrincipal.usuario);
        notificar();
    }

    private void notificar() {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Cita agendada correctamente\n");
        mensaje.append(fechaCita.toString() + "\n");
        mensaje.append("Oficina San Blas\n");
        mensaje.append("Registro de acta de nacimiento");


        if (enviarCorreo(
                mensaje.toString(),
                "Agendamiento de cita para registro de Matrimonio",
                ModeloPrincipal.usuario.getCorreo(),
                null
        )){
            JOptionPane.showMessageDialog(null, "Agendamiento exitoso, revise su correo electronico.");
        } else {
            JOptionPane.showMessageDialog(null, "Problema con al notificar, verifique que su correo electronico sea correcto.");

        }


    }

    private void mostrarSeleccionFecha() {
        // Crear un JDialog para mostrar el panel EleccionFecha
        JDialog dialog = new JDialog((JFrame) null, "Seleccionar Fecha", true);

        // Instanciar el panel EleccionFecha
        selectorFecha = new EleccionFecha();
        selectorFecha.setTramite("Matrimonio");

        // Configurar el JDialog
        dialog.getContentPane().add(selectorFecha);
        dialog.setSize(700, 700); // Tamaño del cuadro de diálogo
        dialog.setLocationRelativeTo(null); // Centrar el diálogo en la pantalla

//        El carrito es el botón de confirmación  (Campos ya validados), por lo que la fecha está ya asignada
        selectorFecha.addCarrito.addActionListener(this);

        // Mostrar el diálogo
        dialog.setVisible(true);
    }

    private boolean camposValidos() {
        if (vista.correo.getText().isBlank() || vista.correo.getText().isBlank())
            return false;

        if (vista.cedulaConyugue.getText().isBlank() || vista.cedulaConyugue.getText().isEmpty())
            return false;

        return Cedula.validarCedula(vista.cedulaConyugue.getText());
    }

}
