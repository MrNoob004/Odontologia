package org.ucuenca.Controlador;

import org.ucuenca.Modelo.ModeloPrincipal;

import java.time.LocalDate;

public class ControladorPrincipal {

    ModeloPrincipal modeloPrincipal;
    /*public static void main(String[] args) {
        ControladorLogin controladorLogin = new ControladorLogin();
    }
    */


    public ControladorPrincipal() {
        System.out.println("--- Fecha actual ---");
        modeloPrincipal = ModeloPrincipal.getInstancia();
        System.out.println(LocalDate.now());
        ControladorLogin controladorLogin = new ControladorLogin();
//        TODO borrar
//        modeloPrincipal.llenarCitasTest(modeloPrincipal.getUsuarios().get("0105911838"));
    }
}
