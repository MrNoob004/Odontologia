package org.ucuenca.Modelo.Documentos;

import org.ucuenca.Modelo.Documentos.Plantillas.DocumentoId;
import org.ucuenca.Modelo.Documentos.Plantillas.DocumentoLegal;
import org.ucuenca.Modelo.Documentos.Plantillas.EstadoDocumento;
import org.ucuenca.Modelo.Usuarios.Ciudadano;
import org.ucuenca.Modelo.Usuarios.Provincia;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Documento de identificación principal de un {@link Ciudadano}
 * <p>
 * Su id NUI es {@link #idUnico}
 */
public class Cedula extends DocumentoId implements Cloneable {

    private Ciudadano conyugue;
    private Ciudadano representanteLegal;

    /**
     * Cantidad de tiempo que está vigente el documento
     */
    public static final Period VIGENCIA_CEDULA = Period.ofYears(13);
/*
    public Cedula(String idUnico) {
        this.idUnico = idUnico;
    }
*/

    public Cedula(Ciudadano solicitante, Provincia provinciaEmitido,
                  Ciudadano ciudadanoAsociado, String idUnico,
                  Ciudadano conyugue, Ciudadano representanteLegal
    ) {
        super(solicitante, provinciaEmitido, ciudadanoAsociado, idUnico);
        this.fechaCaducidad = generarFechaCaducidad();
        this.conyugue = conyugue;
        this.representanteLegal = representanteLegal;
    }

    public Cedula(Ciudadano solicitante, Provincia provinciaEmitido,
                  Ciudadano ciudadanoAsociado, String idUnico,
                  Ciudadano conyugue, Ciudadano representanteLegal,
                  String idDocumento
    ) {
        super(solicitante, provinciaEmitido, ciudadanoAsociado, idDocumento, idUnico);
        this.fechaCaducidad = generarFechaCaducidad();
        this.conyugue = conyugue;
        this.representanteLegal = representanteLegal;
    }

    @Override
    public LocalDate generarFechaCaducidad() {
        return this.fechaEmision.plus(VIGENCIA_CEDULA);
    }


    /**
     * Crea una nueva cédula
     *
     * @return Cédula con nuevos datos
     */
    @Override
    public Cedula renovar() {
        Cedula nueva = clone();
        nueva.setFechaEmision(LocalDate.now());
        nueva.setFechaCaducidad(generarFechaCaducidad());
        return nueva;
    }

    /**
     * Busca las anteriores cedulas que tenga asociado el ciudadano y cambia su estado
     * a {@link EstadoDocumento#VENCIDO}
     */
    @Override
    public boolean invalidarAnterior(Collection<DocumentoLegal> anteriores) {

//        Filtrar los documentos del usuario para obtener solo sus cedulas
        List<Cedula> cedulasAnteriores = anteriores.stream().filter(
                (documento) -> documento instanceof Cedula
        ).map(
                (documento) -> (Cedula) documento
        ).toList();

        for (Cedula cedula : cedulasAnteriores) {
            if (cedula.estaVigente())
                cedula.setEstadoDocumento(EstadoDocumento.VENCIDO);
        }

        return true;
    }

    @Override
    public Cedula clone() {
        try {
            return (Cedula) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public static boolean validarCedula(String cedula) {
        if (cedula.length() != 10)
            return false;

        int[] validador = new int[]{2, 1, 2, 1, 2, 1, 2, 1, 2, 1}, cedulaDigitos = new int[10], resultante = new int[10];
        int sumaResultante = 0;

        for (int i = 0; i < 10; i++)
            cedulaDigitos[i] = Integer.parseInt(String.valueOf(cedula.charAt(i)));


        // Vector resultante
        for (int i = 0; i < 10; i++) {
            resultante[i] = cedulaDigitos[i] * validador[i];
            // Restar 9 si lo sobrepasa
            if (resultante[i] > 9)
                resultante[i] = resultante[i] - 9;

            // Sumar todos menos el ultimo
            if (i != 9)
                sumaResultante += resultante[i];
        }

        sumaResultante = 10 - sumaResultante % 10;
        // Revisa que el ultimo digito sea igual a la suma y 10 - residuo de la suma
        return (sumaResultante == cedulaDigitos[9]);
    }


    public static String generarCedula() {
        Random random = new Random();

        // Generar provincia, tercer dígito y secuencial
        String provincia = String.format("%02d", random.nextInt(24) + 1); // 1 a 24
        String tercerDigito = String.valueOf(random.nextInt(6)); // 0 a 5
        String secuencial = String.format("%06d", random.nextInt(1_000_000)); // 000001 a 999999
        String cedulaSinVerificador = provincia + tercerDigito + secuencial;

        // Generar verificador
        int verificador = generarVerificador(cedulaSinVerificador);

        // Devolver cédula completa
        return cedulaSinVerificador + verificador;
    }

    // Función para generar un número de verificación válido
    public static int generarVerificador(String cedulaSinVerificador) {
        int sumaImpar = 0;

        // Sumar posiciones impares multiplicadas por 2
        for (int i = 0; i < 9; i += 2) {
            int multiplicacion = Character.getNumericValue(cedulaSinVerificador.charAt(i)) * 2;
            sumaImpar += multiplicacion <= 9 ? multiplicacion : multiplicacion - 9;
        }

        // Sumar posiciones pares
        int sumaPar = 0;
        for (int i = 1; i < 9; i += 2) {
            sumaPar += Character.getNumericValue(cedulaSinVerificador.charAt(i));
        }

        // Calcular verificador
        int resultado = 10 - ((sumaImpar + sumaPar) % 10);
        return (resultado < 10) ? resultado : 0;
    }

    public Ciudadano getConyugue() {
        return conyugue;
    }

    public void setConyugue(Ciudadano conyugue) {
        this.conyugue = conyugue;
    }

    public boolean tieneConyugue() {
        return conyugue != null;
    }

    @Override
    public String toString() {
        return this.getIdUnico();
    }
}
