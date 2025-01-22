package org.ucuenca.Controlador.Administrador;

import org.ucuenca.Controlador.Usuario.ControladorMisCitas;
import org.ucuenca.Modelo.Citas.Plantillas.Cita;
import org.ucuenca.Modelo.ModeloPrincipal;
import org.ucuenca.Vista.Administrador.RegistrosCitas;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static org.ucuenca.Modelo.Citas.Plantillas.Cita.FORMATO_FECHA_CITA;

public class ControladorRegistroCitas implements ActionListener {

    RegistrosCitas controlador;
    ModeloPrincipal database;

    DefaultTableModel modelo;



    public ControladorRegistroCitas(RegistrosCitas rc) {
        controlador = rc;
        database = ModeloPrincipal.getInstancia();
        setListeners();
        setTabla();
    }

    public void setListeners() {
        controlador.cargarUsuarios.addActionListener(this);
    }

    private void setTabla() {
//        int columnaCheckBox = 6;
        modelo = new DefaultTableModel(
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
        controlador.citas.setModel(modelo);

        controlador.citas.setDefaultRenderer(Object.class, new ControladorMisCitas.ColorCellRenderer());
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        setOrdenamientoTabla(sorter);
    }

    private void setOrdenamientoTabla(TableRowSorter<DefaultTableModel> sorter) {
        controlador.citas.setRowSorter(sorter);
        controlador.citas.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Ordenamiento de la columna
                int columnaSeleccionada = controlador.citas.columnAtPoint(e.getPoint());
                sorter.toggleSortOrder(columnaSeleccionada);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == controlador.cargarUsuarios) {
            cargarCitas();
        }
    }

    public void cargarCitas() {
        for (Cita c : database.getCitas().values()) {
            modelo.addRow(new Object[]{
                    c.getIdCita(),
                    c.getFecha().format(FORMATO_FECHA_CITA),
                    c.getTipoCita().toString(),
                    c.getPrecio(),
            });
        }
    }

}

