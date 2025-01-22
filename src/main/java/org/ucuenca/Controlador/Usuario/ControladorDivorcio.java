package org.ucuenca.Controlador.Usuario;

import org.ucuenca.Modelo.Citas.CitaDivorcio;
import org.ucuenca.Modelo.Citas.Correo;
import org.ucuenca.Modelo.Citas.HorariosYCostos.Precios;
import org.ucuenca.Modelo.ModeloPrincipal;
import org.ucuenca.Modelo.Usuarios.Ciudadano;
import org.ucuenca.Modelo.Usuarios.Usuario;
import org.ucuenca.Vista.Usuario.Divorcio;
import org.ucuenca.Vista.Usuario.EleccionFecha;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;


public class ControladorDivorcio implements ActionListener, CitaNotificableCorreo{
    CitaDivorcio cita;
    Divorcio vista;
    Ciudadano solicitante;
    Ciudadano conyugue;
    EleccionFecha selectorFecha;
    ModeloPrincipal database;
    LocalDateTime fechaCita;
    //correo
    Correo correo;

    public ControladorDivorcio(Divorcio entrevistaMatrimonio) {
        solicitante = Usuario.getCiudadano(ModeloPrincipal.usuario);
        vista = entrevistaMatrimonio;
        database = ModeloPrincipal.getInstancia();
        setListeners();
        setVista();
        setDatosUsuario();
    }

    private void setDatosUsuario() {
    }

    private void setVista() {
        vista.setVisible(true);
    }

    public void setListeners() {
        vista.elegirFechaDivorcio.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.elegirFechaDivorcio) {
            manejarAgendamiento();
        }

        if (e.getSource() == selectorFecha.addCarrito) {
            fechaCita = selectorFecha.getFechaHora();
        }
    }


    private void manejarAgendamiento() {
        this.conyugue = solicitante.getCedula().getConyugue();

        this.cita = new CitaDivorcio(
                LocalDateTime.now(),
                Precios.DIVORCIO.getPrecio(),
                solicitante,
                conyugue,
                database.getCitaId()
        );


        if (!cita.cumpleRequisitos(cita.getRequisitos(), solicitante)) {
            JOptionPane.showMessageDialog(null,
                    cita.getRequisitosFaltantes(cita.getRequisitos(), solicitante)
            );
            return;
        }
        mostrarSeleccionFecha();

        cita.setFecha(fechaCita);
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
                "Agendamiento de cita para registro de divorcio",
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
        selectorFecha.setTramite("Divorcio");

        // Configurar el JDialog
        dialog.getContentPane().add(selectorFecha);
        dialog.setSize(700, 700); // Tamaño del cuadro de diálogo
        dialog.setLocationRelativeTo(null); // Centrar el diálogo en la pantalla

//        El carrito es el botón de confirmación  (Campos ya validados), por lo que la fecha está ya asignada
        selectorFecha.addCarrito.addActionListener(this);

        // Mostrar el diálogo
        dialog.setVisible(true);
    }


}
