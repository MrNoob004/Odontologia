package org.ucuenca.Controlador.Administrador;

import org.ucuenca.Controlador.Usuario.ControladorMisCitas;
import org.ucuenca.Modelo.ModeloPrincipal;
import org.ucuenca.Modelo.Usuarios.Usuario;
import org.ucuenca.Vista.Administrador.RegistrosUsuarios;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControladorRegistroUsuarios implements ActionListener {
    RegistrosUsuarios controlador;
    ModeloPrincipal database;

    DefaultTableModel modelo;

    public ControladorRegistroUsuarios(RegistrosUsuarios ru) {
        controlador = ru;
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
                    new String[]{"Cedula", "Correo", "Telefono", "Rol"},
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
            controlador.usuarios.setModel(modelo);

//            controlador.usuarios.setDefaultRenderer(Object.class, new ControladorMisCitas.ColorCellRenderer());
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
            setOrdenamientoTabla(sorter);
        }

        private void setOrdenamientoTabla(TableRowSorter<DefaultTableModel> sorter) {
            controlador.usuarios.setRowSorter(sorter);
            controlador.usuarios.getTableHeader().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //Ordenamiento de la columna
                    int columnaSeleccionada = controlador.usuarios.columnAtPoint(e.getPoint());
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
            for (Usuario u : database.getUsuarios().values()) {
                modelo.addRow(new Object[]{
                        u.getId(),
                        u.getCorreo(),
                        u.getTelefono(),
                        u.getRol(),
                });
            }
        }

}
