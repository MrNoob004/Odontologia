package org.ucuenca.Modelo.Citas.HorariosYCostos;
/**
 * Precios de los servicios*/
public enum Precios {
    MATRIMONIO(4.00),
    DIVORCIO(4.00),
    NACIMIENTO(3.00),
    DEFUNCION(12.00),
    PASAPORTE(17.50),
    CEDULA(16.00);
    private double precio;

    Precios(double v) {
        this.precio = v;
    }

    // Constructor
    void Producto(double precio) {
        this.precio = precio;
    }

    // Getter para obtener el precio del producto
    public double getPrecio() {
        return precio;
    }

    //información sobre el producto
    @Override
    public String toString() {
        return name() + " - Precio: $" + precio;
    }
}
