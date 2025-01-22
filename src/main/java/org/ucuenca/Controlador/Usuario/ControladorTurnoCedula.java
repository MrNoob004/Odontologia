package org.ucuenca.Controlador.Usuario;

import org.ucuenca.Modelo.Citas.CitaCedula;
import org.ucuenca.Modelo.Citas.CitaPasaporte;
import org.ucuenca.Modelo.Citas.Correo;
import org.ucuenca.Modelo.Citas.HorariosYCostos.Precios;
import org.ucuenca.Modelo.ModeloPrincipal;
import org.ucuenca.Modelo.Usuarios.Ciudadano;
import org.ucuenca.Modelo.Usuarios.Usuario;
import org.ucuenca.Vista.Usuario.EleccionFecha;
import org.ucuenca.Vista.Usuario.EmisionCedula;
import org.ucuenca.Vista.Usuario.EmisionPasaporte;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class ControladorTurnoCedula implements ActionListener, CitaNotificableCorreo {
    EmisionCedula vista;
    CitaCedula cita;
    EleccionFecha selectorFecha;
    Ciudadano solicitante;
    LocalDateTime fechaCita;
    ModeloPrincipal database;
    Correo correo;

    public ControladorTurnoCedula(EmisionCedula vista) {
        solicitante = Usuario.getCiudadano(ModeloPrincipal.usuario);
        this.vista = vista;
        database = ModeloPrincipal.getInstancia();
        setVista();
        setListeners();
    }

    private void setListeners() {
        vista.elegirFecha.addActionListener(this);
    }

    private void setVista() {
        vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.elegirFecha) {
            elegirFecha();
            agendarCita();
        }
        if (e.getSource() == selectorFecha.addCarrito) {
            fechaCita = selectorFecha.getFechaHora();
        }
    }

    private void agendarCita() {
        cita = new CitaCedula(
                fechaCita,
                Precios.CEDULA.getPrecio(),
                solicitante,
                database.getCitaId()
        );
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
                "Agendamiento de cita para Emision de cedula",
                ModeloPrincipal.usuario.getCorreo(),
                null
        )){
            JOptionPane.showMessageDialog(null, "Agendamiento exitoso, revise su correo electronico.");
        } else {
            JOptionPane.showMessageDialog(null, "Problema con al notificar, verifique que su correo electronico sea correcto.");

        }

    }

    private void elegirFecha() {
        JDialog dialog = new JDialog((JFrame) null, "Seleccionar Fecha", true);

        // Instanciar el panel EleccionFecha
        selectorFecha = new EleccionFecha();
        selectorFecha.setTramite("Emision Cedula");

        // Configurar el JDialog
        dialog.getContentPane().add(selectorFecha);
        dialog.setSize(700, 700); // Tamaño del cuadro de diálogo
        dialog.setLocationRelativeTo(null); // Centrar el diálogo en la pantalla

        selectorFecha.addCarrito.addActionListener(this);
        // Mostrar el diálogo
        dialog.setVisible(true);

    }
}
