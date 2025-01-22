package org.ucuenca.Modelo.Citas.Plantillas;

import java.util.List;

/**
 * Permite validar e informar un listado de requisitos que se debe cumplir antes de completar un proceso.
 * <p>
 * Contiene implementaciones {@code default} para validar cualquier {@code List<Requisito>} y obtener
 * los <b><i>Mensajes de error/incumplimiento</i></b> en caso de que no se cumpla cualquier cantidad de
 * condiciones, por lo que no deberian ser necesarios los {@code  @Override}
 * </p>
 */
public interface ProcesoRequisitado {

    List<Requisito> getRequisitos();

//    void s etRequisitos(List<Requisito<?>> requisitos);

    void addRequisito(Requisito requisito);

    /**
     * Itera a través de los requisitos asignados, cuando {@code objeto} no cumpla alguno, añade el mensaje
     * asignado en {@link Requisito} junto con {@code \n}
     *
     * @return Todos los mensajes de incumplimiento de los requisitos no cumplidos
     */
    default String getRequisitosFaltantes(List<Requisito> requisitos, Object objeto) {
        System.out.println("---Requisitos Faltantes---");
        StringBuilder faltantes = new StringBuilder();

        for (Requisito requisito : requisitos)
            if (!requisito.cumpleCondicion(objeto))
                faltantes.append(requisito.getMensajeIncumplimiento()).append("\n");
        return faltantes.toString();
    }

    /**
     * @return {@code true} si se cumplen <b>todos</b> los requisitos de la lista, {@code false} caso contrario
     */
    default boolean cumpleRequisitos(List<Requisito> requisitos, Object objeto) {
        for (Requisito requisito : requisitos)
            if (!requisito.cumpleCondicion(objeto))
                return false;
        return true;
    }

    void setRequisitos(List<Requisito> requisitos);
}
