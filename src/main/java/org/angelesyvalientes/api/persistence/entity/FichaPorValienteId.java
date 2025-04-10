package org.angelesyvalientes.api.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Clase que define la clave primaria compuesta para la entidad {@link FichaPorValiente}.
 * Implementa la interfaz {@link Serializable} ya que se utiliza como clave primaria
 * en una entidad JPA.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FichaPorValienteId implements Serializable {
    /**
     * Identificador de la ficha, parte de la clave primaria compuesta.
     * Corresponde al campo {@code idFicha} en la entidad {@link FichaPorValiente}.
     */
    private int idFicha;

    /**
     * Identificador del valiente, parte de la clave primaria compuesta.
     * Corresponde al campo {@code idValiente} en la entidad {@link FichaPorValiente}.
     */
    private int idValiente;

    /**
     * Implementación del método {@code equals} para comparar instancias de
     * {@code FichaPorValienteId}. Dos instancias se consideran iguales si sus
     * atributos {@code idFicha} e {@code idValiente} son iguales.
     *
     * @param o El objeto a comparar con esta instancia.
     * @return {@code true} si el objeto es una instancia de {@code FichaPorValienteId}
     * y sus atributos coinciden, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FichaPorValienteId that)) return false;
        return idFicha == that.idFicha && idValiente == that.idValiente;
    }

    /**
     * Implementación del método {@code hashCode} para generar un código hash
     * para las instancias de {@code FichaPorValienteId}. El código hash se genera
     * a partir de los atributos {@code idFicha} e {@code idValiente}.
     *
     * @return El código hash de esta instancia.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idFicha, idValiente);
    }

    /**
     * Obtiene el identificador del valiente.
     *
     * @return El ID del valiente.
     */
    public int getIdValiente() {
        return idValiente;
    }

    /**
     * Obtiene el identificador de la ficha.
     *
     * @return El ID de la ficha.
     */
    public int getIdFicha() {
        return idFicha;
    }

    /**
     * Establece el identificador del valiente.
     *
     * @param idValiente El nuevo ID del valiente.
     */
    public void setIdValiente(int idValiente) {
        this.idValiente = idValiente;
    }

    /**
     * Establece el identificador de la ficha.
     *
     * @param idFicha El nuevo ID de la ficha.
     */
    public void setIdFicha(int idFicha) {
        this.idFicha = idFicha;
    }
}