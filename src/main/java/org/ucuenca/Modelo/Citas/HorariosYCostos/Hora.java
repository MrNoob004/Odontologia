package org.ucuenca.Modelo.Citas.HorariosYCostos;

/**
 * Representa una hora con su disponibilidad capaz de usarse en un horario
 */
public class Hora implements Comparable<Hora> {
    @Override
    public int compareTo(Hora o) {
        return this.nombre.compareTo(o.nombre);
    }

    /**
     * Formato de 24h
     * Ej: "16:00"
     */
    private String nombre;
    /**
     * Indica si la hora está libre para agendarse
     */
    private boolean disponible;

    /**
     * @param nombre     Formato de 24h: "17:00"
     * @param disponible Inicializar como {@code true} disponible,o {@code false} ocupado/inaccesible
     */
    public Hora(String nombre, boolean disponible) {
        this.nombre = nombre;
        this.disponible = disponible;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}