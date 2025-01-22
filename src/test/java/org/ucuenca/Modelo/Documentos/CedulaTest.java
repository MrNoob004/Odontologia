package org.ucuenca.Modelo.Documentos;

import org.junit.jupiter.api.Test;
import org.ucuenca.Modelo.Citas.CitaMatrimonioTest;
import org.ucuenca.Modelo.Dummies;
import org.ucuenca.Modelo.ModeloPrincipal;
import org.ucuenca.Modelo.Usuarios.Provincia;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CedulaTest {

    public static Cedula cedulaPadre = new Cedula(
            CitaMatrimonioTest.padre,
            Provincia.BOLIVAR,
            CitaMatrimonioTest.padre,
            "0915518005",
            null,
            CitaMatrimonioTest.padre

    );

    public static Cedula cedulaMadre = new Cedula(
            CitaMatrimonioTest.madre,
            Provincia.CARCHI,
            CitaMatrimonioTest.madre,
            "0103382545",
            null,
            CitaMatrimonioTest.madre
    );

    public static Cedula cedulaNino = new Cedula(
            CitaMatrimonioTest.marido,
            Provincia.CARCHI,
            CitaMatrimonioTest.marido,
            "0105911838",
            null,
            CitaMatrimonioTest.marido
    );

    public static Cedula cedulaNina = new Cedula(
            CitaMatrimonioTest.mujer,
            Provincia.CARCHI,
            CitaMatrimonioTest.mujer,
            "0106045271",
            null,
            CitaMatrimonioTest.mujer
    );


    @Test
    void cedulasValidas() {

        assertAll(
                () -> assertTrue(Cedula.validarCedula("0105911838")),
                () -> assertTrue(Cedula.validarCedula("0103382545")),
                () -> assertTrue(Cedula.validarCedula("0915518005")),
                () -> assertTrue(Cedula.validarCedula("0106045271")),
                () -> assertTrue(Cedula.validarCedula("1003992292"))
//                () -> assertTrue(Cedula.validarCedula("0105911838"))
        );

    }

    @Test
    void generarCedulas() {

        assertAll(
                () -> assertTrue(Cedula.validarCedula(Cedula.generarCedula())),
                () -> assertTrue(Cedula.validarCedula(Cedula.generarCedula())),
                () -> assertTrue(Cedula.validarCedula(Cedula.generarCedula())),
                () -> assertTrue(Cedula.validarCedula(Cedula.generarCedula())),
                () -> assertTrue(Cedula.validarCedula(Cedula.generarCedula()))
        );

//        for (int i = 0; i < 10; i++) {
//            String cedula = Cedula.generarCedula();
//            System.out.println(cedula);
//            System.out.println(Cedula.validarCedula(cedula));
//        }
    }

    @Test
    void generarCedulasNoRegistradas() {

        ModeloPrincipal principal = ModeloPrincipal.getInstancia();
//        Obtiene los IDs de los usuarios
//        List<String> registradas = principal.getUsuarios().keySet().stream().toList();
        List<String> registradas = new ArrayList<>();

        System.out.println(registradas);
//        Cédulas de dummies
        registradas.add("0105911838");
        registradas.add("0103382545");
        registradas.add("0915518005");
        registradas.add("0106045271");
        registradas.add("1003992292");

        int intentos = 0;
        ArrayList<String> generadas = new ArrayList<>();
        String cedula;

//        Generar cédulas hasta que cree una que ya está registrada
        while (!registradas.contains(cedula = Cedula.generarCedula())) {
            System.out.println(intentos + ": '" + cedula + "'");
            generadas.add(cedula);
            intentos++;
        }

        System.out.println(generadas);
    }
}