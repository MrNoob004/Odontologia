package org.ucuenca.Controlador.Personal;

import org.ucuenca.Modelo.Documentos.ActaNacimiento;
import org.ucuenca.Modelo.Documentos.Cedula;
import org.ucuenca.Modelo.Dummies;
import org.ucuenca.Modelo.ModeloPrincipal;
import org.ucuenca.Modelo.Usuarios.*;
import org.ucuenca.Vista.Personal.RegistroPersonaPersonal;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ControladorRegistroNacimiento implements ActionListener {
    RegistroPersonaPersonal vista;
    ActaNacimiento acta;
    Ciudadano solicitante;
    Ciudadano representanteLegal;
    Ciudadano ciudadano;
    ModeloPrincipal database;
    String idActa;

    /**
     * Al ser el personal quien atiende la cita, firma como el emisor en el acta
     */
    Usuario usuarioEmisor = ModeloPrincipal.usuario;


    /**
     * Maneja un controlador con un botón único y donde el único requisito es que debe estar vivo
     */
    public ControladorRegistroNacimiento(RegistroPersonaPersonal vistaServicio) {
        this.vista = vistaServicio;
        database = ModeloPrincipal.getInstancia();
        setVista();
        setListeners();
        setDatosFijos();
    }

    private void setDatosFijos() {
//        asignarDocumentosTest();
//        Generar el número de acta\
        idActa = database.getDocumentoId();
        vista.nActaRegistroPersona.setText(idActa);
        vista.numeroCedula.setText(Cedula.generarCedula());
        setComboBoxEnumerado(vista.provinciaNacimiento, Provincia.values());
        setComboBoxEnumerado(vista.provinciaInscripcion, Provincia.values());
    }


    private void setListeners() {
        vista.generarCedula.addActionListener(this);
        vista.guardarInfo.addActionListener(this);
        vista.buscarPadreButton.addActionListener(this);
        vista.buscarMadreButton.addActionListener(this);
        vista.padreRegistradoCheckBox.addActionListener(this);

        vista.cedPadre.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!vista.nombresPadre.isEnabled()) {
                    vista.nombresPadre.setEnabled(true);
                    vista.nombresPadre.setText("");
                    vista.apellidosPadre.setEnabled(true);
                    vista.apellidosPadre.setText("");
                    vista.nacionalidadPadre.setEnabled(true);
                    vista.nacionalidadPadre.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        vista.cedMadre.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!vista.nombresMadre.isEnabled()) {
                    vista.nombresMadre.setEnabled(true);
                    vista.nombresMadre.setText("");
                    vista.apellidosMadre.setEnabled(true);
                    vista.apellidosMadre.setText("");
                    vista.nacionalidadMadre.setEnabled(true);
                    vista.nacionalidadMadre.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
    }

    private void setVista() {
        vista.setVisible(true);
        vista.nActaRegistroPersona.setEditable(false);
        togglePadre(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.generarCedula) {
            String cedula = generarCedulaUnica();
            vista.numeroCedula.setText(cedula);
        }

        if (e.getSource() == vista.guardarInfo) {
            if (camposValidos()) {
                registrarCiudadano();
                registrarActa();
            }
        }

        if (e.getSource() == vista.padreRegistradoCheckBox) {
            togglePadre(vista.padreRegistradoCheckBox.isSelected());
        }

        if (e.getSource() == vista.buscarMadreButton) {
            buscarFamiliar("madre",
                    vista.cedMadre.getText(),
                    vista.nombresMadre,
                    vista.apellidosMadre,
                    vista.nacionalidadMadre);
        }
        if (e.getSource() == vista.buscarPadreButton) {
            buscarFamiliar("padre",
                    vista.cedPadre.getText(),
                    vista.nombresPadre,
                    vista.apellidosPadre,
                    vista.nacionalidadPadre
            );
        }


    }

    private void registrarCiudadano() {
        Sexo sexo = (Sexo) stringToEnumValue(vista.sexo, Sexo.values());
        Provincia provincia = (Provincia) stringToEnumValue(vista.provinciaNacimiento, Provincia.values());
        LocalDate fechaNacimiento;
        try {
            fechaNacimiento = LocalDate.parse(vista.fechaNacimiento.getText());
        } catch (Exception e) {
            fechaNacimiento = LocalDate.now().minusYears(18);
        }
        Cedula cedula = crearCedulaCiudadano(provincia);

        ciudadano = new Ciudadano(
                vista.nombresCiudadanos.getText(),
                vista.apellidosCiudadanos.getText(),
                "Sin ocupación",
                sexo,
                fechaNacimiento,
                EstadoCivil.SOLTERO,
                true,
                Period.between(fechaNacimiento, LocalDate.now()).getYears(),
                provincia,
                cedula
        );
        cedula.setCiudadanoAsociado(ciudadano);
        cedula.asociarCiudadano(ciudadano);
        database.actualizarDB(ciudadano);
    }

    private void setComboBoxEnumerado(JComboBox<String> comboBox, Object[] values) {
        comboBox.removeAllItems();
        for (Object value : values)
            comboBox.addItem(value.toString());

    }

    private Cedula crearCedulaCiudadano(Provincia provincia) {
        Ciudadano dummy = Dummies.dummy;
        Cedula cedula = new Cedula(
                dummy,
                provincia,
                solicitante,
                vista.numeroCedula.getText(),
                null,
                representanteLegal
        );
        return cedula;
    }

    private Object stringToEnumValue(JComboBox<String> stringValue, Object[] values) {
        for (Object o : values) {
            if (o.toString().equals(stringValue.getSelectedItem().toString())) {
                return o;
            }
        }
        return values[0];

    }

    private void buscarFamiliar(String parentesco, String cedula, JTextField nombres, JTextField apellidos, JTextField nacionalidad) {

        if (cedula.isBlank() || cedula.isEmpty()) {
            mostrarMensajesError("Digite la cédula a buscar");
            return;
        }

        solicitante = representanteLegal = database.getCiudadanos().get(cedula);
        if (representanteLegal == null) {
            mostrarMensajesError("No registrado");
            return;
        }

        switch (parentesco) {
            case "padre":
                if (!(nombres.isEnabled() && apellidos.isEnabled() && nacionalidad.isEnabled())) {
                    return;
                }
            case "madre":
                nombres.setText(representanteLegal.getNombres());
                apellidos.setText(representanteLegal.getApellidos());
                nacionalidad.setText(representanteLegal.getProvinciaNacimiento().toString());

                nombres.setEnabled(false);
                apellidos.setEnabled(false);
                nacionalidad.setEnabled(false);
                break;

            default:
                mostrarMensajesError("Familiar no encontrado");
                return;
        }

    }

    private void togglePadre(boolean padreRegistrado) {
        vista.cedPadre.setEnabled(padreRegistrado);
        vista.nombresPadre.setEnabled(padreRegistrado);
        vista.apellidosPadre.setEnabled(padreRegistrado);
        vista.nacionalidadPadre.setEnabled(padreRegistrado);
        vista.buscarPadreButton.setEnabled(padreRegistrado);
        pintarVista();
    }

    private void pintarVista() {
        vista.revalidate();
        vista.repaint();
    }

    private void registrarActa() {

        acta = generarActa(vista.padreRegistradoCheckBox.isSelected());

        JOptionPane.showMessageDialog(
                vista,
                "Registrado correctamente"
        );

        ciudadano.getDocumentosAsociados().add(acta);
        database.actualizarDB(ciudadano);
        database.actualizarDB(acta);
    }

    private ActaNacimiento generarActa(boolean padrePresente) {
        Provincia provinciaEmitido = Provincia.AZUAY;
        LocalDate fechaNacimiento;
        Provincia provinciaNacimiento = (Provincia) stringToEnumValue(vista.provinciaNacimiento, Provincia.values());

        try {
            fechaNacimiento = LocalDate.parse(vista.fechaNacimiento.getText());
        } catch (Exception e) {
            fechaNacimiento = LocalDate.now().minusDays(1);
        }
        if (padrePresente) {
            return new ActaNacimiento(
                    solicitante,
                    provinciaEmitido,
                    ciudadano,
                    List.of(ciudadano, solicitante),
                    fechaNacimiento,
                    provinciaNacimiento,
                    "Sin observaciones",
                    database.getCiudadanos().get(vista.cedMadre.getText()).getCedula(),
                    database.getCiudadanos().get(vista.cedPadre.getText()).getCedula(),
                    vista.nombresCiudadanos.getText(),
                    vista.apellidosCiudadanos.getText(),
                    idActa
            );
        } else
            return new ActaNacimiento(
                    solicitante,
                    provinciaEmitido,
                    ciudadano,
                    List.of(ciudadano, solicitante),
                    fechaNacimiento,
                    provinciaNacimiento,
                    "Sin observaciones",
                    database.getCiudadanos().get(vista.cedMadre.getText()).getCedula(),
                    vista.nombresCiudadanos.getText(),
                    vista.apellidosCiudadanos.getText(),
                    idActa
            );
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

    private String generarCedulaUnica() {
        String cedula = "";
//        Obtiene los IDs de los usuarios
        List<String> registradas = database.getUsuarios().keySet().stream().toList();
        boolean yaRegistrada = true;

        while (yaRegistrada) {
            cedula = Cedula.generarCedula();
            yaRegistrada = registradas.contains(cedula);
        }

        return cedula;
    }
}


