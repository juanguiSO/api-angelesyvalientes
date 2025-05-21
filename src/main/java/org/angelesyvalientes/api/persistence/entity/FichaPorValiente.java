package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entidad que representa la relación entre una ficha y un valiente,
 * indicando que un valiente ha realizado una ficha específica.
 * Utiliza la clase {@link FichaPorValienteId} para definir su clave primaria compuesta.
 * Mapea la tabla "ficha_x_valiente" en la base de datos.
 */
@Entity
@Table(name = "ficha_x_valiente")
@IdClass(FichaPorValienteId.class)
@Getter
@Setter
public class FichaPorValiente {
    /**
     * Identificador de la ficha (parte de la clave primaria compuesta).
     * Corresponde a la clave primaria de la tabla "ficha".
     */
    @Id
    @Column(name = "nm_id_ficha", nullable = false)
    private int idFicha;


    /**
     * Identificador del valiente (parte de la clave primaria compuesta).
     * Corresponde a la clave primaria de la tabla "persona" (heredada por "valiente").
     */
    @Id
    @Column(name = "nm_id_persona", nullable = false)
    private int idValiente;


    /**
     * Fecha en la que el valiente finalizó o realizó la ficha.
     */
    @Column(name = "fe_realizada")
    @PastOrPresent(message = "La fecha de finalización de la ficha debe ser en el presente o pasado.")
    private LocalDate fechaFinalizacion;




    /**
     * Establece la fecha en la que se finalizó la ficha.
     *
     * @param fechaFinalizacion La fecha de finalización.
     */
    public void setFechaFinalizacion(LocalDate fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }


    /**
     * Establece el ID del valiente asociado a esta relación.
     *
     * @param idValiente El ID del valiente.
     */
    public void setIdValiente(int idValiente) {
        this.idValiente = idValiente;
    }



    /**
     * Establece el ID de la ficha asociado a esta relación.
     *
     * @param idFicha El ID de la ficha.
     */
    public void setIdFicha(int idFicha) {
        this.idFicha = idFicha;
    }

    /**
     * Obtiene el ID de la ficha asociado a esta relación.
     *
     * @return El ID de la ficha.
     */

    public int getIdFicha() {
        return idFicha;
    }

    /**
     * Obtiene el ID del valiente asociado a esta relación.
     *
     * @return El ID del valiente.
     */
    public int getIdValiente() {
        return idValiente;
    }

    /**
     * Obtiene la fecha en la que se finalizó la ficha.
     *
     * @return La fecha de finalización.
     */
    public LocalDate getFechaFinalizacion() {
        return fechaFinalizacion;
    }


    public void setFicha(Ficha ficha) {
    }
}