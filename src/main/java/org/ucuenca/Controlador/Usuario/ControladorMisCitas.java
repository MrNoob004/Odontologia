package org.ucuenca.Controlador.Usuario;

import org.ucuenca.Modelo.Citas.MisCitasModelo;
import org.ucuenca.Modelo.Citas.Plantillas.Cita;
import org.ucuenca.Modelo.ModeloPrincipal;
import org.ucuenca.Modelo.Usuarios.Ciudadano;
import org.ucuenca.Modelo.Usuarios.Usuario;
import org.ucuenca.Vista.Usuario.MisCitas;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;

import static org.ucuenca.Modelo.Citas.Plantillas.Cita.FORMATO_FECHA_CITA;


public class ControladorMisCitas implements ActionListener {
    private static final double IVA = 1.15;
    MisCitasModelo modelo;
    MisCitas vista;
    Usuario usuario;
    Ciudadano ciudadano;

    ModeloPrincipal principal;

    DefaultTableModel modeloTabla;

    public ControladorMisCitas(MisCitas citas) {
        usuario = ModeloPrincipal.usuario;
        ciudadano = Usuario.getCiudadano(usuario);
        modelo = new MisCitasModelo(usuario, ciudadano);
        vista = citas;

        principal = ModeloPrincipal.getInstancia();

        setListeners();
        setVista();
        setTabla();
        setDatosUsuario();
    }

    private void setTabla() {
//        int columnaCheckBox = 6;
        modeloTabla = new DefaultTableModel(
                new String[]{"Id", "Fecha", "Tipo", "Precio"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int column) {
//                return column == columnaCheckBox ? Boolean.class : super.getColumnClass(column);
                return super.getColumnClass(column);
            }
        };
        vista.tablaCitas.setModel(modeloTabla);

        vista.tablaCitas.setDefaultRenderer(Object.class, new ColorCellRenderer());
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTabla);
        setOrdenamientoTabla(sorter);
    }

    private void setOrdenamientoTabla(TableRowSorter<DefaultTableModel> sorter) {
        vista.tablaCitas.setRowSorter(sorter);
        vista.tablaCitas.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Ordenamiento de la columna
                int columnaSeleccionada = vista.tablaCitas.columnAtPoint(e.getPoint());
                sorter.toggleSortOrder(columnaSeleccionada);
            }
        });
    }

    private void setDatosUsuario() {
        vista.cedulaUsuario.setText(usuario.getId());
        vista.nombreUsuario.setText(ciudadano.getNombres() + " " + ciudadano.getApellidos());

//        TODO datos correctos xfa
        modelo.getCitas().forEach(
                cita -> modeloTabla.addRow(new Object[]{
                        cita.getIdCita(),
                        cita.getFecha().format(FORMATO_FECHA_CITA),
                        cita.getTipoCita().toString(),
                        cita.getPrecio() * IVA,
                })
        );
    }

    private void setVista() {
        vista.setVisible(true);
    }

    public void setListeners() {
        vista.tablaCitas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                int columnaSeleccionada = vista.tablaCitas.columnAtPoint(e.getPoint());
                int columnaSeleccionada = 0;
                int filaSeleccionada = vista.tablaCitas.getSelectedRow();

                mostrarInfoCita(modeloTabla.getValueAt(filaSeleccionada, columnaSeleccionada));
            }

            private void mostrarInfoCita(Object valor) {
                int citaId = Integer.parseInt(valor.toString());
                Cita cita = modelo.getCita(citaId);
                JOptionPane.showMessageDialog(
                        vista.tablaCitas,
                        "Id: " + cita.getIdCita() + "\n" +
                                cita.getTipoCita() + "\n" +
                                "Fecha: " + cita.getFecha().format(FORMATO_FECHA_CITA) + "\n" +
                                "$" + cita.getPrecio() + "\n" +
                                "Solicitada por: \n" + cita.getSolicitante().toString()

                );
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    /**
     * Permite colorear filas y celdas de acuerdo al criterio
     * <p>
     * En este caso se usa <b>gris</b> para las citas con fecha ya pasada
     * </p>
     */
    public static class ColorCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            cell.setBackground(getColor(table, row));

            return cell;
        }

        private static Color getColor(JTable table, int row) {
            final int columnaFecha = 1;

            LocalDateTime fechaCita = LocalDateTime.parse(getFechaParseable(table, row, columnaFecha));

            return fechaCita.isAfter(LocalDateTime.now())
                    ? Color.white
                    : Color.LIGHT_GRAY;
        }

        /**
         * Pasa la fecha en la fijla y columna seleccionada a un formato parseable por  {@link LocalDateTime#parse(CharSequence)}
         * <p>
         *     {@link Cita#FORMATO_FECHA_CITA} ->  {@link java.time.format.DateTimeFormatter#ISO_LOCAL_DATE_TIME}
         * </p>
         *
         */
        private static String getFechaParseable(JTable table, int row, int columnaFecha) {
            StringBuilder fechaParseable = new StringBuilder(table.getValueAt(row, columnaFecha).toString());
//            Para los segundos
            fechaParseable.append(":00");

            return fechaParseable.toString().replace(' ', 'T');
        }
    }
}
