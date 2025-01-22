package org.ucuenca.Modelo.Citas.Plantillas;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.function.Predicate;

/**
 * Una condición que algun atributo de {@code objeto} debe cumplir
 *
 * <p>Función anónima</p>
 * <pre>
 * {@code
 * new Requisito(edad, (edad)-> (int) edad>18, "Debe ser mayor de edad")
 * }
 * </pre>
 *
 * <p>Valor exacto:</p>
 * <pre>
 * {@code
 * new Requisito(sexo, Sexo.HOMBRE, "Debe ser hombre")
 * }
 * </pre>
 */
public class Requisito implements Serializable {
    @FunctionalInterface
    public interface Condicion<Object> extends Predicate<Object>, Serializable {
    }
    private final String nombreVariable;
    private final Condicion<Object> condicion;
    private final Object valorEsperado;
    private final String mensajeIncumplimiento;

    /**
     * @param nombreVariable        El nombre <b>exacto</b> del atributo de {@code objeto}
     * @param condicion             Expresión anónima que se debe cumplir
     * @param mensajeIncumplimiento Mensaje a mostrar al usuario en caso de que no se cumpla el requisito
     */
    public Requisito(String nombreVariable, Condicion<Object> condicion, String mensajeIncumplimiento) {
        this.nombreVariable = nombreVariable;
        this.condicion = condicion;
        this.mensajeIncumplimiento = mensajeIncumplimiento;
        this.valorEsperado = null;
    }


    /**
     * @param nombreVariable        El nombre <b>exacto</b> del atributo de {@code objeto}
     * @param valorEsperado         Valor exacto que debe tener el atributo
     * @param mensajeIncumplimiento Mensaje a mostrar al usuario en caso de que no se cumpla el requisito
     */
    public Requisito(String nombreVariable, Object valorEsperado, String mensajeIncumplimiento) {
        this.nombreVariable = nombreVariable;
        this.valorEsperado = valorEsperado;
        this.mensajeIncumplimiento = mensajeIncumplimiento;
        this.condicion = null;
    }


    /**
     * Verifica si el objeto cumple con la condición especificada en su constructor
     *
     * @param objeto Objeto sobre el cual validar.
     * @return {@code true} si cumple con la condición asignada esperado, {@code false} en caso contrario.
     */
    public boolean cumpleCondicion(Object objeto) {
        Object valorEncontrado = getAtributoObjeto(objeto);
        return condicion == null ? valorEncontrado == valorEsperado : condicion.test(valorEncontrado);
    }


    private Object getAtributoObjeto(Object objeto) {
        Field atributo;
        try {
            atributo = objeto.getClass().getDeclaredField(nombreVariable);
            atributo.setAccessible(true);
            return atributo.get(objeto);
        } catch (NoSuchFieldException e) {
            System.out.println(objeto.getClass().getSimpleName() + " No contiene el atributo '" + nombreVariable + "'");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public String toString() {
        if (valorEsperado != null)
            return nombreVariable + " Debe ser = " + valorEsperado;
        else
            return "Incumplimiento: " + mensajeIncumplimiento;
    }

    public String getNombreVariable() {
        return nombreVariable;
    }

    public Predicate<Object> getCondicion() {
        return condicion;
    }

    public Object getValorEsperado() {
        return valorEsperado;
    }

    public String getMensajeIncumplimiento() {
        return mensajeIncumplimiento;
    }


}

