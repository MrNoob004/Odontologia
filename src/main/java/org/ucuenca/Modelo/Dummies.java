package org.ucuenca.Modelo;

import org.ucuenca.Modelo.Documentos.ActaNacimiento;
import org.ucuenca.Modelo.Documentos.Cedula;
import org.ucuenca.Modelo.Documentos.Plantillas.Acta;
import org.ucuenca.Modelo.Usuarios.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dummies {

    public static Ciudadano dummy = new Ciudadano(true);

    public static Cedula cedulaPadre = new Cedula(
            dummy,
            Provincia.BOLIVAR,
            dummy,
            "0915518005",
            null,
            dummy
    );

    public static Cedula cedulaMadre = new Cedula(
            dummy,
            Provincia.CARCHI,
            dummy,
//            "0103382545",
            "1003992292",
            null,
            dummy
    );

    public static Cedula cedulaNino = new Cedula(
            dummy,
            Provincia.CARCHI,
            dummy,
            "0105911838",
            null,
            dummy
    );

    public static Cedula cedulaNina = new Cedula(
            dummy,
            Provincia.CARCHI,
            dummy,
            "0106045271",
            null,
            dummy
    );

    public static Ciudadano padre = new Ciudadano(
            "Jose Mauricio",
            "Perry Nuñez",
            "Fotógrafo",
            Sexo.HOMBRE,
            LocalDate.of(1997, 12, 3),
            EstadoCivil.SOLTERO,
            true,
            19,
            Provincia.CHIMBORAZO,
            cedulaPadre
    );

    public static Ciudadano madre = new Ciudadano(
            "Ana Sofia",
            "Aguilar Hernandez",
            "Programadora",
            Sexo.MUJER,
            LocalDate.of(2004, 12, 3),
            EstadoCivil.SOLTERO,
            true,
            19,
            Provincia.CHIMBORAZO,
            cedulaMadre
    );

    public static Ciudadano marido = new Ciudadano(
            "Fernando Luis",
            "Nuñez Alacrán",
            "Copiador",
            Sexo.HOMBRE,
            LocalDate.of(2004, 12, 3),
            EstadoCivil.SOLTERO,
            true,
            19,
            Provincia.CHIMBORAZO,
            madre,
            padre,
            TipoSangre.AB_POSITIVO,
            cedulaNino
    );

    public static Ciudadano mujer = new Ciudadano(
            "Mérida Patricia",
            "Calle Pesantez",
            "Copiador",
            Sexo.MUJER,
            LocalDate.of(2004, 12, 3),
            EstadoCivil.SOLTERO,
            true,
            19,
            Provincia.CHIMBORAZO,
            null,
            null,
            TipoSangre.AB_POSITIVO,
            cedulaNina
    );

    public static Map<String, Cedula> cedulasDummies;
    public static Map<String, Ciudadano> ciudadanosDummies;

    public static void setDummies() {
        cedulasDummies = new HashMap<>();
        ciudadanosDummies = new HashMap<>();

        setCedulasCorrectas();
        cedulasDummies.put(padre.getCedula().getIdUnico(), cedulaPadre);
        cedulasDummies.put(madre.getCedula().getIdUnico(), cedulaMadre);
        cedulasDummies.put(marido.getCedula().getIdUnico(), cedulaNino);
        cedulasDummies.put(mujer.getCedula().getIdUnico(), cedulaNina);

        ciudadanosDummies.put(padre.getCedula().getIdUnico(), padre);
        ciudadanosDummies.put(madre.getCedula().getIdUnico(), madre);
        ciudadanosDummies.put(marido.getCedula().getIdUnico(), marido);
        ciudadanosDummies.put(mujer.getCedula().getIdUnico(), mujer);
    }

    public static void setCedulasCorrectas() {
        cedulaNino.setCiudadanoAsociado(marido);
        cedulaNina.setCiudadanoAsociado(mujer);
        cedulaPadre.setCiudadanoAsociado(padre);
        cedulaMadre.setCiudadanoAsociado(madre);
//        setCasadosTest(marido, mujer);
    }

    private static void setCasadosTest(Ciudadano marido, Ciudadano mujer) {
        marido.getCedula().setConyugue(mujer);
        mujer.getCedula().setConyugue(marido);
        mujer.setEstadoCivil(EstadoCivil.CASADO);
        marido.setEstadoCivil(EstadoCivil.CASADO);
    }

    public static ActaNacimiento nacimientoMarido = new ActaNacimiento(
            mujer,
            Provincia.BOLIVAR,
            dummy,
            List.of(mujer, marido),
            LocalDate.now().minusDays(7),
            Provincia.CARCHI,
            "Ha nacido el chavalo",
            cedulaMadre,
            cedulaPadre,
            marido.getNombres(),
            marido.getApellidos(),
            "6"
    );


    public static void asignarDocumentos(Ciudadano ciudadano) {
        ciudadano.getDocumentosAsociados().add(nacimientoMarido);
    }


}
