package org.ucuenca.Controlador.Usuario;

import org.ucuenca.Modelo.Citas.Plantillas.Requisito;
import org.ucuenca.Modelo.ModeloPrincipal;
import org.ucuenca.Modelo.Usuarios.Ciudadano;
import org.ucuenca.Modelo.Usuarios.Usuario;
import org.ucuenca.Vista.Usuario.EleccionFecha;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * Controlador genérico para un proceso en el que el único requisito es que el
 * usuario (Ciudadano asociado) tenga un solo requisito que debe cumplir
 * <p>
 * <b>
 * El panel necesita tener un panel interior que contenga un {@link JButton}
 * </b>
 * </p>
 */
public class ControladorProcesoSimple implements ActionListener {
    JPanel vistaServicio;
    Ciudadano solicitante;
    Usuario usuario = ModeloPrincipal.usuario;
    EleccionFecha eleccionFecha;

    Requisito requisito;

    /**
     * Maneja un controlador con un botón único y donde el único requisito es que debe estar vivo
     */
    public ControladorProcesoSimple(JPanel vistaServicio) {
        this.vistaServicio = vistaServicio;
        solicitante = Usuario.getCiudadano(usuario);
        setVista();
        setListeners();
    }

    /**
     * Maneja un controlador con un botón único y donde el único requisito es estar vivo y el requisito
     */
    public ControladorProcesoSimple(JPanel vistaServicio, Requisito requisito) {
        this.vistaServicio = vistaServicio;
        solicitante = Usuario.getCiudadano(usuario);
        this.requisito = requisito;
        setVista();
        setListeners();
    }

    /**
     * Maneja un controlador con un botón único y donde el único requisito es que debe estar vivo
     */
    public ControladorProcesoSimple(JPanel vistaServicio, JButton botonUnico) {
        this.vistaServicio = vistaServicio;
        solicitante = Usuario.getCiudadano(usuario);
        setVista();
        setListeners(botonUnico);
    }

    private void setListeners(JButton botonUnico) {
        botonUnico.addActionListener(this);
    }

    /**
     * Maneja un controlador con un botón único y donde el único requisito es estar vivo y el requisito
     */
    public ControladorProcesoSimple(JPanel vistaServicio, Requisito requisito, JButton botonUnico) {
        this.vistaServicio = vistaServicio;
        solicitante = Usuario.getCiudadano(usuario);
        this.requisito = requisito;
        setVista();
        setListeners(botonUnico);
    }

    private void setListeners() {

        JPanel panel = (JPanel) Arrays.stream(
                vistaServicio.getComponents()).filter(
                (component -> component instanceof JPanel)
        ).findFirst().orElse(null);

        assert panel != null;
        JButton botonUnico = (JButton) Arrays.stream(panel.getComponents()).filter(
                (component -> component instanceof JButton)
        ).findFirst().orElse(null);

        assert botonUnico != null;
        botonUnico.addActionListener(this);

    }

    private void setVista() {
        vistaServicio.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
//        Botón de agregar al carrito o elegir fecha
        if (e.getSource().getClass() == JButton.class) {
            JOptionPane.showMessageDialog(
                    null,
                    cumpleRequisitos()
                            ? "Proceso agendado con éxito"
                            : requisito.getMensajeIncumplimiento()
            );
        }
    }

    private boolean cumpleRequisitos() {
        if (requisito != null)
            return Usuario.getCiudadano(usuario).estaVivo() && requisito.cumpleCondicion(solicitante);
        else return true;
    }

    public Requisito getRequisito() {
        return requisito;
    }

    public void setRequisito(Requisito requisito) {
        this.requisito = requisito;
    }

}
