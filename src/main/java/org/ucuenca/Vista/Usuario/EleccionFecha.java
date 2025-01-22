package org.ucuenca.Vista.Usuario;


import com.toedter.calendar.JCalendar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.JOptionPane;

import java.util.HashMap;


public class EleccionFecha extends javax.swing.JPanel {


    //private String tramite;
    private JCalendar calendar;
    private String tramite;

    public EleccionFecha() {

        initComponents();
        addCarrito.setVisible(false);

        calendar = new JCalendar();
        calendar.setMinSelectableDate(new Date()); // Configura la fecha mínima como hoy

        // Deshabilitar sábados y domingos

        // Escuchar cambios en la selección de fecha
        calendar.getDayChooser().addPropertyChangeListener("day", evt -> {
            Date selectedDate = calendar.getDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(selectedDate);
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

            // Validar si es sábado o domingo
            if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                JOptionPane.showMessageDialog(this, "No puedes seleccionar fines de semana.", "Fecha inválida", JOptionPane.ERROR_MESSAGE);
                calendar.setDate(new Date()); // Volver a la fecha actual
            }
        });


        // Agregar el JCalendar al jPanel1
        jPanel1.setLayout(new java.awt.BorderLayout());
        jPanel1.add(calendar, java.awt.BorderLayout.CENTER);
    }

    //Obtiene el tipo de tramite
    public void setTramite(String tramite) {
        this.tramite = tramite;
    }

    // Método para obtener la fecha seleccionada
    public Date getSelectedDate() {
        return calendar.getDate();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        addCarrito = new javax.swing.JButton();
        seleccionarFecha = new javax.swing.JButton();
        horas = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Roboto Black", 0, 24)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/calendar.png"))); // NOI18N
        jLabel1.setText("Agendar Turno");

        jLabel2.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel2.setText("Seleccione la fecha y hora para realizar su trámite.");

        jLabel3.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel3.setText("Agencia:");

        jComboBox1.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"AGENCIA EL BATAN", "AGENCIA BELLAVISTA", "AGENCIA SAN BLAS"}));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 545, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 206, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 15, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 15, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 15, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(0, 153, 51));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 15, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 15, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(255, 51, 0));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 15, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 15, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(0, 153, 204));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 15, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 15, Short.MAX_VALUE)
        );

        jLabel5.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
        jLabel5.setText("Fecha actual.");

        jLabel6.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
        jLabel6.setText("Disponible.");

        jLabel7.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
        jLabel7.setText("Agendado.");

        jLabel8.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
        jLabel8.setText("Reservado.");

        jLabel9.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
        jLabel9.setText("No disponible.");

        addCarrito.setBackground(new java.awt.Color(153, 204, 255));
        addCarrito.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        addCarrito.setText("Agregar al Carrito");
        addCarrito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCarritoActionPerformed(evt);
            }
        });

        seleccionarFecha.setText("Seleccionar Fecha");
        seleccionarFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seleccionarFechaActionPerformed(evt);
            }
        });

        horas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00"}));
        horas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                horasActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Roboto Medium", 0, 13)); // NOI18N
        jLabel4.setText("Hora:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 661, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel1))
                                                .addContainerGap(31, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(32, 32, 32)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addGap(45, 45, 45))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel3)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(70, 70, 70))))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(71, 71, 71)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel5)
                                                .addGap(18, 18, 18)
                                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel6))
                                        .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel7)
                                                .addGap(18, 18, 18)
                                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel8)
                                                .addGap(18, 18, 18)
                                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel9)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(horas, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(seleccionarFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(addCarrito))
                                                .addGap(81, 81, 81))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(23, 23, 23)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                .addComponent(jLabel5)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel9)
                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                        .addComponent(jLabel8)
                                                                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                                                .addComponent(jLabel7)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(seleccionarFecha)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(horas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel4)))
                                .addGap(17, 17, 17)
                                .addComponent(addCarrito)
                                .addContainerGap(102, Short.MAX_VALUE))
        );

        seleccionarFecha.getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void addCarritoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCarritoActionPerformed

        // Mostrar un mensaje de confirmación
        JOptionPane.showMessageDialog(this, "Turno agregado al carrito correctamente.");

        // Cerrar la ventana que contiene este panel
        java.awt.Window parentWindow = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (parentWindow != null) {
            parentWindow.dispose(); // Cierra la ventana
        }

    }//GEN-LAST:event_addCarritoActionPerformed

    private void seleccionarFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seleccionarFechaActionPerformed

        Date selectedDate = getSelectedDate();
        String tramiteActual = this.tramite;
        String horaSeleccionada = (String) horas.getSelectedItem(); // Hora seleccionada

        // Verificar si la fecha ya está ocupada para este trámite
        if (RegistroFechas.esHoraReservada(tramiteActual, selectedDate, horaSeleccionada)) {
            JOptionPane.showMessageDialog(this, "Esta hora ya está reservada para este día.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Guardar la fecha y hora en el registro
        RegistroFechas.guardarFechaYHora(tramiteActual, selectedDate, horaSeleccionada);
        setFechaHora(selectedDate, horaSeleccionada);
        // Mostrar mensaje de éxito
        JOptionPane.showMessageDialog(this, "Fecha seleccionada: " + selectedDate + " Hora: " + horaSeleccionada);
        addCarrito.setVisible(true);

    }//GEN-LAST:event_seleccionarFechaActionPerformed

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    private LocalDateTime fechaHora;

    private void setFechaHora(Date fecha, String hora) {
        LocalDate localDate = fecha.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();


        // Paso 2: Convertir String a LocalTime
        LocalTime localTime = LocalTime.parse(hora, DateTimeFormatter.ofPattern("HH:mm"));

        // Paso 3: Combinar LocalDate y LocalTime en LocalDateTime
        this.fechaHora = LocalDateTime.of(localDate, localTime);

    }

    private void horasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_horasActionPerformed
        // String hora = (String)horas.getSelectedItem(); //importante poner (String)

    }//GEN-LAST:event_horasActionPerformed


    public class RegistroFechas {
        /**
         * <b><i>Key</i></b>: Nombre del tipo de cita
         *
         * <p>
         * <b><i>Value</i></b>: Mapa que contiene una fecha {@link Date} como clave
         * Y sus valores son las horas <b>YA</b> registradas
         * </p>
         */
        private static final Map<String, Map<Date, Set<String>>> registro = new HashMap<>();

        // Verificar si una hora está reservada para una fecha y trámite específico
        public static boolean esHoraReservada(String tramite, Date fecha, String hora) {
            Map<Date, Set<String>> fechas = registro.get(tramite);
            if (fechas != null) {
                Set<String> horas = fechas.get(fecha);
                if (horas != null && horas.contains(hora))
                    return true; // La hora ya está reservada

            }
            return false;
        }

        // Guardar fecha y hora para un trámite
        public static void guardarFechaYHora(String tramite, Date fecha, String hora) {
            registro.putIfAbsent(tramite, new HashMap<>());
            Map<Date, Set<String>> fechas = registro.get(tramite);

            fechas.putIfAbsent(fecha, new HashSet<>());
            Set<String> horas = fechas.get(fecha);

            horas.add(hora); // Agregar la hora al conjunto de horas reservadas
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton addCarrito;
    private javax.swing.JComboBox<String> horas;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSeparator jSeparator1;
    public javax.swing.JButton seleccionarFecha;
    // End of variables declaration//GEN-END:variables
}
