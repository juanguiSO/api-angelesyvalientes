package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
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
     * Estado de la realización de la ficha por el valiente.
     * Por defecto, su valor es 'A'.
     */
    @Column(name = "cd_estado", columnDefinition = "CHAR(1) default 'A'")
    @NotBlank(message = "El estado de la ficha por valiente es obligatorio.")
    @Size(min = 1, max = 1, message = "El estado debe tener un carácter.")
    private String estado;

    /**
     * Fecha en la que el valiente finalizó o realizó la ficha.
     */
    @Column(name = "fe_realizada")
    @PastOrPresent(message = "La fecha de finalización de la ficha debe ser en el presente o pasado.")
    private LocalDate fechaFinalizacion;

    /**
     * Relación uno a uno con la entidad {@link Ficha}.
     * Utiliza la columna "nm_id_ficha" como clave de unión y no permite inserción o actualización.
     */
    @OneToOne
    @JoinColumn(name = "nm_id_ficha", nullable = false, insertable = false, updatable = false)
    @NotNull(message = "La ficha asociada es obligatoria.")
    private Ficha ficha;

    /**
     * Relación muchos a uno (lazy loading) con la entidad {@link Valiente}.
     * Utiliza la columna "nm_id_persona" como clave de unión y no permite inserción o actualización.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nm_id_persona", nullable = false, insertable = false, updatable = false)
    @NotNull(message = "El valiente asociado es obligatorio.")
    private Valiente valiente;

    /**
     * Establece el estado de la realización de la ficha.
     *
     * @param estado El estado ('A', 'I', etc.).
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Establece la fecha en la que se finalizó la ficha.
     *
     * @param fechaFinalizacion La fecha de finalización.
     */
    public void setFechaFinalizacion(LocalDate fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    /**
     * Establece la ficha asociada a esta relación.
     *
     * @param ficha La entidad {@link Ficha}.
     */
    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
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
     * Establece el valiente asociado a esta relación.
     *
     * @param valiente La entidad {@link Valiente}.
     */
    public void setValiente(Valiente valiente) {
        this.valiente = valiente;
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
     * Obtiene la ficha asociada a esta relación.
     *
     * @return La entidad {@link Ficha}.
     */
    public Ficha getFicha() {
        return ficha;
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

    /**
     * Obtiene el estado de la realización de la ficha.
     *
     * @return El estado ('A', 'I', etc.).
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Obtiene el valiente asociado a esta relación.
     *
     * @return La entidad {@link Valiente}.
     */
    public Valiente getValiente() {
        return valiente;
    }
}